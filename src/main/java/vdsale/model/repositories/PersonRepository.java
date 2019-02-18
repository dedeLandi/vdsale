package vdsale.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vdsale.model.entities.PersonEntity;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    @Query("SELECT p FROM #{#entityName} p WHERE p.name = :name")
    Optional<PersonEntity> findByName(@Param("name") String name);
}
