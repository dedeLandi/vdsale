package vdsale.business.basicService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import vdsale.exceptions.DataNotFoundException;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.page.PageVO;

import java.util.List;

@Slf4j
public abstract class BasicCrudService<VO,
        ENTITY,
        CONVERTER extends IBasicConverter<ENTITY, VO>,
        REPOSITORY extends JpaRepository<ENTITY, Integer>> implements IBasicCrudService<VO>{

    protected final REPOSITORY repository;

    protected final CONVERTER converter;

    public BasicCrudService(REPOSITORY repository, CONVERTER converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public List<VO> getAll() {
        log.info("M=getAll, message=Getting a list of {}", getEntitySimpleName() );
        List<ENTITY> all = repository.findAll();
        return converter.toVO(all);
    }

    @Override
    @Transactional
    public PageVO<VO> getAll(Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of {}", getEntitySimpleName());
        Page<ENTITY> all = repository.findAll(pageRequest);
        return converter.toVO(all, converter::fromEntityToVO);
    }

    @Override
    @Transactional
    public VO get(int id) {
        log.info("M=get, id={}, message=Get a specific {}", id, getEntitySimpleName());
        var data = repository.findById(id);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    @Override
    public VO save(VO data) {
        log.info("M=save, data={}, message=Saving a {}", data, getEntitySimpleName());
        var saved = repository.save(converter.toEntity(data));
        return converter.fromEntityToVO(saved);
    }

    @Override
    public VO update(VO data) {
        log.info("M=update, data={}, message=Updating a {}", data, getEntitySimpleName());
        var entity = repository.findById(1);
        var update = entity.orElseThrow(() -> new DataNotFoundException(getEntitySimpleName() + " not found"));
        updateData(data, update);
        return converter.fromEntityToVO(repository.save(update));
    }

    @Override
    public void delete(int id) {
        log.info("M=delete, id={}, message=Delete a specific {}", id, getEntitySimpleName());
        var data = repository.findById(id);
        repository.delete(data.orElseThrow(() -> new DataNotFoundException(getEntitySimpleName() + " not found")));
    }

    protected abstract String getEntitySimpleName();

    protected abstract void updateData(VO vo, ENTITY entity);
}
