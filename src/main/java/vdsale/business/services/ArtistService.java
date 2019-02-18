package vdsale.business.services;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vdsale.business.basicService.BasicCrudService;
import vdsale.exceptions.MethodNotImplementedException;
import vdsale.model.entities.ArtistEntity;
import vdsale.model.repositories.ArtistRepositoty;
import vdsale.model.vos.disc.ArtistConverter;
import vdsale.model.vos.disc.ArtistVO;

import java.util.Optional;

import static vdsale.business.services.ArtistService.QUALIFIER;

@Slf4j
@Service(QUALIFIER)
public class ArtistService extends BasicCrudService<ArtistVO,
        ArtistEntity,
        ArtistConverter,
        ArtistRepositoty> {

    public static final String QUALIFIER = "ArtistService";

    @Autowired
    public ArtistService(ArtistRepositoty artistRepositoty){
        super(artistRepositoty, ArtistConverter.getInstance());
    }

    @Override
    @Deprecated
    public ArtistVO save(ArtistVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    @Deprecated
    public ArtistVO update(ArtistVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    @Deprecated
    public void delete(int id) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    protected String getEntitySimpleName() {
        return ArtistEntity.class.getSimpleName();
    }

    @Override
    @Deprecated
    protected void updateData(ArtistVO artistVO, ArtistEntity artistEntity) {
        throw new MethodNotImplementedException("Method not supported.");
    }


    public ArtistEntity findOrSave(ArtistSimplified[] artists) {
        ArtistEntity saved = null;
        ArtistEntity spotifyArtist = converter.toEntity(artists);
        log.info("M=findOrSave, spotifyId={}, message=Get a specific artist", spotifyArtist.getId());
        Optional<ArtistEntity> artist = repository.findBySpotifyId(spotifyArtist.getSpotifyId());
        if(!artist.isPresent()){
            log.info("M=findOrSave, spotifyId={}, message=Saving new artist", spotifyArtist.getId());
            saved = repository.save(spotifyArtist);
        }else{
            saved = artist.get();
            log.info("M=findOrSave, id={}, spotifyId={}, message=Artist found", saved.getId(), saved.getSpotifyId());
        }
        return saved;
    }
}
