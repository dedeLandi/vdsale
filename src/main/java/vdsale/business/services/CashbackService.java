package vdsale.business.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vdsale.business.basicService.BasicCrudService;
import vdsale.exceptions.DataNotFoundException;
import vdsale.exceptions.MethodNotImplementedException;
import vdsale.model.entities.CashbackEntity;
import vdsale.model.enums.Weekday;
import vdsale.model.repositories.CashbackRepository;
import vdsale.model.vos.cashback.CashbackConverter;
import vdsale.model.vos.cashback.CashbackVO;
import vdsale.model.vos.category.CategoryVO;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static vdsale.business.services.CashbackService.QUALIFIER;

@Slf4j
@Service(QUALIFIER)
public class CashbackService extends BasicCrudService<CashbackVO,
        CashbackEntity,
        CashbackConverter,
        CashbackRepository> {

    public final static String QUALIFIER = "CashbackService";

    @Autowired
    public CashbackService(CashbackRepository cashbackRepository) {
        super(cashbackRepository, CashbackConverter.getInstance());
    }


    @Override
    @Deprecated
    public CashbackVO update(CashbackVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override

    protected String getEntitySimpleName() {
        return CashbackEntity.class.getSimpleName();
    }

    @Override
    @Deprecated
    protected void updateData(CashbackVO cashbackVO, CashbackEntity cashbackEntity) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    public CashbackVO update(CashbackEntity data) {
        log.info("M=update, cashbackId={}, message=Updating a cashback", data);
        return converter.fromEntityToVO(repository.save(data));
    }

    public Optional<CashbackEntity> getById(int id) {
        log.info("M=getById, cashbackId={}, message=Searching a cashback by id", id);
        return repository.findById(id);
    }

    public double getCashbackByCategoryAndDate(CategoryVO category, Date date) {
        log.info("M=getCashbackByCategoryAndDate, category={}, date={}, message=Searching a cashback by category and date", category, date);
        Weekday weekday = Weekday.valueOf(date);
        if(Objects.isNull(weekday)) throw new DataNotFoundException("Not found day of the week");
        return repository.findCashbackByCategoryIdAndWeekday(category.getId(), weekday).orElse(Double.valueOf(0)).doubleValue();
    }
}
