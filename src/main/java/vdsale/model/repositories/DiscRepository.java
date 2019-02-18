package vdsale.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vdsale.model.entities.DiscEntity;

import java.util.Optional;

@Repository
public interface DiscRepository  extends JpaRepository<DiscEntity, Integer> {

    @Query("SELECT COUNT(d) FROM #{#entityName} d WHERE d.spotifyId = :spotifyId")
    int countBySpotifyID(@Param("spotifyId") String spotifyId);

    @Query("SELECT d FROM #{#entityName} d WHERE d.spotifyId = :spotifyId")
    Optional<DiscEntity> findBySpotifyId(@Param("spotifyId") String spotifyId);

    @Query("SELECT d FROM #{#entityName} d WHERE d.name = :name")
    Optional<DiscEntity> findByName(@Param("name")String name);

    @Query("SELECT d FROM #{#entityName} d WHERE d.category.id = :categoryId")
    Page<DiscEntity> findByCategoryId(@Param("categoryId") int categoryId, Pageable pageRequest);
}