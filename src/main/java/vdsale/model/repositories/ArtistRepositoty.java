package vdsale.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vdsale.model.entities.ArtistEntity;

import java.util.Optional;

@Repository
public interface ArtistRepositoty  extends JpaRepository<ArtistEntity, Integer> {

    @Query("SELECT COUNT(a) FROM #{#entityName} a WHERE a.spotifyId = :spotifyId")
    int countBySpotfyID(@Param("spotifyId") String spotifyId);


    @Query("SELECT a FROM #{#entityName} a WHERE a.spotifyId = :spotifyId")
    Optional<ArtistEntity> findBySpotifyId(@Param("spotifyId") String spotifyId);
}