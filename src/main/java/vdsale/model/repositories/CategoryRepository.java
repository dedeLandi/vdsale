package vdsale.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vdsale.model.entities.CategoryEntity;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query("SELECT COUNT(c) FROM #{#entityName} c WHERE c.spotifyId = :spotifyId")
    int countBySpotifyID(@Param("spotifyId") String spotifyId);

    @Query("SELECT c FROM #{#entityName} c WHERE c.spotifyId = :spotifyCategoryID")
    Optional<CategoryEntity> findBySpotifyID(@Param("spotifyCategoryID") String spotifyCategoryID);
}
