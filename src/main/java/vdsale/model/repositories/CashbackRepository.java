package vdsale.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vdsale.model.entities.CashbackEntity;
import vdsale.model.enums.Weekday;

import java.util.Optional;

@Repository
public interface CashbackRepository extends JpaRepository<CashbackEntity, Integer> {

    @Query("SELECT c.cashback FROM #{#entityName} c WHERE c.category.id = :categoryId AND c.weekday = :weekday")
    Optional<Double> findCashbackByCategoryIdAndWeekday(@Param("categoryId") int categoryId, @Param("weekday") Weekday weekday);
}