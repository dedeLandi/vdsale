package vdsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vdsale.business.facades.CashbackFacade;
import vdsale.model.vos.cashback.CashbackVO;

import javax.validation.Valid;

import static vdsale.controllers.CashbackController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class CashbackController {

    protected static final String REQUEST_MAPPING = "/cashback";

    private static final String REQUEST_GET_CASHBACK = "/{id}";
    private static final String REQUEST_DELETE_CASHBACK = "/{id}";

    @Autowired
    private CashbackFacade cashbackFacade;

//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(cashbackFacade.getAll());
//    }

    @GetMapping
    public ResponseEntity getAll(final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(cashbackFacade.getAll(pageRequest));
    }

    @GetMapping(REQUEST_GET_CASHBACK)
    public ResponseEntity get(@PathVariable("id") int id){
        var data = cashbackFacade.get(id);
        if(data == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody CashbackVO cashback){
        return ResponseEntity.status(HttpStatus.CREATED).body(cashbackFacade.save(cashback));
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody CashbackVO cashback){
        return ResponseEntity.status(HttpStatus.OK).body(cashbackFacade.update(cashback));
    }

    @DeleteMapping(REQUEST_DELETE_CASHBACK)
    public ResponseEntity delete(@PathVariable("id") int id){
        cashbackFacade.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
