package vdsale.business.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vdsale.business.basicService.BasicCrudService;
import vdsale.exceptions.DataNotFoundException;
import vdsale.exceptions.MethodNotImplementedException;
import vdsale.model.entities.DiscEntity;
import vdsale.model.repositories.DiscRepository;
import vdsale.model.vos.disc.DiscConverter;
import vdsale.model.vos.disc.DiscSimpleVO;
import vdsale.model.vos.disc.DiscVO;
import vdsale.model.vos.page.PageVO;

import java.util.Objects;

import static vdsale.business.services.DiscService.QUALIFIER;

@Slf4j
@Service(QUALIFIER)
public class DiscService extends BasicCrudService<DiscVO,
        DiscEntity,
        DiscConverter,
        DiscRepository> {

    public final static String QUALIFIER = "DiscService";

    @Autowired
    public DiscService(DiscRepository discRepository) {
        super(discRepository, DiscConverter.getInstance());
    }

    @Override
    @Deprecated
    public DiscVO save(DiscVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    @Deprecated
    public DiscVO update(DiscVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    @Deprecated
    public void delete(int id) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    protected String getEntitySimpleName() {
        return DiscEntity.class.getSimpleName();
    }

    @Override
    @Deprecated
    protected void updateData(DiscVO discVO, DiscEntity discEntity) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    public int countBySpotifyID(String id) {
        log.info("M=countBySpotifyID, discId={}, message=Counting by spotify id", id);
        return repository.countBySpotifyID(id);
    }

    public DiscEntity save(DiscEntity entity) {
        log.info("M=save, disc={}, message=Saving a new disc", entity);
        return repository.save(entity);
    }

    public DiscVO getBySpotifyId(String spotifyId) {
        log.info("M=getBySpotifyId, spotifyId={}, message=Get a specific disc", spotifyId);
        var data = repository.findBySpotifyId(spotifyId);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    public DiscVO getByName(String name) {
        log.info("M=getByName, name={}, message=Get a specific disc", name);
        var data = repository.findByName(name);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    public DiscVO getDisc(DiscSimpleVO simple) {
        log.info("M=getDisc, disc={}, message=Searching for a disc", simple);
        DiscVO discFounded = null;
        if(StringUtils.isNotEmpty(simple.getSpotifyId())){
            discFounded = getBySpotifyId(simple.getSpotifyId());
        }
        if (Objects.isNull(discFounded) && simple.getId() > 0) {
            discFounded = get(simple.getId());
        }
        if (Objects.isNull(discFounded) && StringUtils.isNotEmpty(simple.getName())) {
            discFounded = getByName(simple.getName());
        }
        if (!Objects.isNull(discFounded)) return discFounded;
        throw new DataNotFoundException("Not found disc with id = " + simple.getId() + ", spotifyId = " + simple.getSpotifyId() + ", name = " + simple.getName());
    }

    public PageVO<DiscVO> getAllByGenre(int categoryId, Pageable pageRequest) {
        log.info("M=getAllByGenre, categoryId={}, message=Getting a list of {} by category", categoryId, getEntitySimpleName());
        Page<DiscEntity> all = repository.findByCategoryId(categoryId, pageRequest);
        return converter.toVO(all, converter::fromEntityToVO);
    }
}
