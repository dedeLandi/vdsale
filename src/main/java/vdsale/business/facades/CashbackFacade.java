package vdsale.business.facades;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vdsale.business.services.CashbackService;
import vdsale.business.services.CategoryService;
import vdsale.exceptions.DataNotFoundException;
import vdsale.model.vos.cashback.CashbackVO;
import vdsale.model.vos.page.PageVO;

import java.util.List;

@Slf4j
@Component
public class CashbackFacade {

    private final CashbackService cashbackService;
    private final CategoryService categoryService;

    @Autowired
    public CashbackFacade(CashbackService cashbackService, CategoryService categoryService){
        this.cashbackService = cashbackService;
        this.categoryService = categoryService;
    }

    public List<CashbackVO> getAll() {
        log.info("M=getAll, message=Getting a list of cashback");
        return cashbackService.getAll();
    }

    public PageVO<CashbackVO> getAll(Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of cashback");
        return cashbackService.getAll(pageRequest);
    }

    public CashbackVO get(int id) {
        log.info("M=get, cashbackId={}, message=Get a specific cashback", id);
        return cashbackService.get(id);
    }

    public CashbackVO save(CashbackVO cashback) {
        log.info("M=save, cashbackId={}, message=Saving a cashback", cashback);
        return cashbackService.save(cashback);
    }

    public CashbackVO update(CashbackVO cashback) {
        log.info("M=update, cashbackId={}, message=Updating a cashback", cashback);
        var data = cashbackService.getById(cashback.getId());
        var update = data.orElseThrow(() -> new DataNotFoundException("Person not found"));
        if(update.getCategory().getId() != cashback.getCategory().getId()){
            var category = categoryService.findById(cashback.getCategory().getId());
            var newCategory = category.orElseThrow(() -> new DataNotFoundException("Person not found"));
            update.setCategory(newCategory);
        }
        update.setWeekday(cashback.getWeekday());
        update.setCashback(cashback.getCashback());
        return cashbackService.update(update);
    }

    public void delete(int id) {
        log.info("M=delete, cashbackId={}, message=Delete a specific cashback", id);
        cashbackService.delete(id);
    }
}