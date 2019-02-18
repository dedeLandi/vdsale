package vdsale.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vdsale.model.entities.OrderEntity;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query("SELECT o FROM #{#entityName} o WHERE o.creationDate BETWEEN :dateinit AND :datefinal")
    Page<OrderEntity> findAllByDate(@Param("dateinit") Date dateinit, @Param("datefinal") Date datefinal, Pageable pageRequest);
}
