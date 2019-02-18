package vdsale.model.vos.disc;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import org.springframework.data.domain.Page;
import vdsale.model.entities.ArtistEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.page.PageConverter;
import vdsale.model.vos.page.PageVO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArtistConverter implements IBasicConverter<ArtistEntity, ArtistVO> {

    private static ArtistConverter INSTANCE;

    private ArtistConverter() {
    }

    public static ArtistConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new ArtistConverter();
        }
        return INSTANCE;
    }

    @Override
    public List<ArtistVO> toVO(List<ArtistEntity> all) {
        return Optional.ofNullable(all)
                .map(a -> a.stream()
                        .map(INSTANCE::fromEntityToVO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageVO<ArtistVO> toVO(Page<ArtistEntity> page, Function<ArtistEntity, ArtistVO> function) {
        return PageConverter.toVO(page, function);
    }

    @Override
    public ArtistVO fromEntityToVO(ArtistEntity entity) {
        Objects.requireNonNull(entity, "artist entity can't be null");

        var vo = new ArtistVO();
        vo.setId(entity.getId());
        vo.setSpotifyId(entity.getSpotifyId());
        vo.setName(entity.getName());

        return vo;
    }

    @Override
    public ArtistEntity toEntity(ArtistVO vo) {
        Objects.requireNonNull(vo, "artist vo can't be null");

        var entity = new ArtistEntity();
        entity.setId(vo.getId());
        entity.setSpotifyId(vo.getSpotifyId());
        entity.setName(vo.getName());

        return entity;
    }

    public ArtistEntity toEntity(ArtistSimplified[] artists) {
        Objects.requireNonNull(artists, "artists can't be null");
        Optional<ArtistSimplified> firstArtist = Arrays.stream(artists).findFirst();
        Objects.requireNonNull(firstArtist.get(), "artist can't be null");

        var entity = new ArtistEntity();
        entity.setSpotifyId(firstArtist.get().getId());
        entity.setName(firstArtist.get().getName());

        return entity;
    }

}