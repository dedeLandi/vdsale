package vdsale.model.vos.disc;

import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import org.springframework.data.domain.Page;
import vdsale.model.entities.ArtistEntity;
import vdsale.model.entities.CategoryEntity;
import vdsale.model.entities.DiscEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.category.CategoryConverter;
import vdsale.model.vos.page.PageConverter;
import vdsale.model.vos.page.PageVO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscConverter implements IBasicConverter<DiscEntity, DiscVO> {

    private static DiscConverter INSTANCE;

    private DiscConverter() {
    }

    public static DiscConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new DiscConverter();
        }
        return INSTANCE;
    }

    @Override
    public DiscVO fromEntityToVO(DiscEntity entity){
        Objects.requireNonNull(entity, "disc entity can't be null");

        var vo = new DiscVO();
        vo.setId(entity.getId());
        vo.setSpotifyId(entity.getSpotifyId());
        vo.setName(entity.getName());
        vo.setArtist(ArtistConverter.getInstance().fromEntityToVO(entity.getArtist()));
        vo.setType(entity.getType());
        vo.setCategory(CategoryConverter.getInstance().fromEntityToVO(entity.getCategory()));
        vo.setPrice(entity.getPrice());

        return vo;
    }

    @Override
    public DiscEntity toEntity(DiscVO vo) {
        Objects.requireNonNull(vo, "disc vo can't be null");

        var entity = new DiscEntity();
        entity.setId(vo.getId());
        entity.setSpotifyId(vo.getSpotifyId());
        entity.setName(vo.getName());
        entity.setArtist(ArtistConverter.getInstance().toEntity(vo.getArtist()));
        entity.setType(vo.getType());
        entity.setCategory(CategoryConverter.getInstance().toEntity(vo.getCategory()));
        entity.setPrice(vo.getPrice());

        return entity;
    }

    public DiscEntity toEntity(AlbumSimplified album, CategoryEntity category, ArtistEntity artist) {
        Objects.requireNonNull(album, "album can't be null");

        var entity = new DiscEntity();

        entity.setSpotifyId(album.getId());
        entity.setName(album.getName());
        entity.setType(album.getAlbumType());
        entity.setCategory(category);
        entity.setArtist(artist);
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(50);
        entity.setPrice(randomInt);

        return entity;
    }

    @Override
    public List<DiscVO> toVO(List<DiscEntity> all) {
        return Optional.ofNullable(all)
                .map(d -> d.stream()
                        .map(INSTANCE::fromEntityToVO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageVO<DiscVO> toVO(Page<DiscEntity> page, Function<DiscEntity, DiscVO> function) {
        return PageConverter.toVO(page, function);
    }

}
