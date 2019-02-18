package vdsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vdsale.business.facades.OrderFacade;
import vdsale.model.vos.order.OrderSimpleVO;

import javax.validation.Valid;
import java.util.Date;

import static vdsale.controllers.OrderController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class OrderController {

    protected static final String REQUEST_MAPPING = "/order";

    private static final String REQUEST_GET_ORDER = "/{id}";
    private static final String REQUEST_DELETE_ORDER = "/{id}";
    private static final String REQUEST_GET_ORDER_DATE = "/{dateinit}/{datefinal}";

    @Autowired
    private OrderFacade orderFacade;

//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll());
//    }

    @GetMapping(REQUEST_GET_ORDER_DATE)
    public ResponseEntity getAllByDate(@PathVariable("dateinit") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateinit,
                                       @PathVariable("datefinal") @DateTimeFormat(pattern = "dd-MM-yyyy") Date datefinal,
                                       final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderFacade.getAll(dateinit, datefinal, pageRequest));
    }

    @GetMapping
    public ResponseEntity getAll(final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderFacade.getAll(pageRequest));
    }

    @GetMapping(REQUEST_GET_ORDER)
    public ResponseEntity get(@PathVariable("id") int id){
        var data = orderFacade.get(id);
        if(data == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody OrderSimpleVO order){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderFacade.save(order));
    }

    @DeleteMapping(REQUEST_DELETE_ORDER)
    public ResponseEntity delete(@PathVariable("id") int id){
        orderFacade.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
