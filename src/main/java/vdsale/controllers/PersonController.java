package vdsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import vdsale.business.basicService.IBasicCrudService;
import vdsale.business.services.PersonService;
import vdsale.model.vos.person.PersonVO;

import javax.validation.Valid;

import static vdsale.controllers.PersonController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class PersonController {

    protected static final String REQUEST_MAPPING = "/person";

    private static final String REQUEST_GET_PERSON = "/{id}";
    private static final String REQUEST_DELETE_PERSON = "/{id}";

    @Autowired
    @Qualifier(PersonService.QUALIFIER)
    private IBasicCrudService personService;

//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll());
//    }

    @GetMapping
    public ResponseEntity getAll(final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll(pageRequest));
    }

    @GetMapping(REQUEST_GET_PERSON)
    public ResponseEntity get(@PathVariable("id") int id){
        var data = personService.get(id);
        if(data == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody PersonVO person){
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(person));
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody PersonVO person){
        return ResponseEntity.status(HttpStatus.OK).body(personService.update(person));
    }

    @DeleteMapping(REQUEST_DELETE_PERSON)
    public ResponseEntity delete(@PathVariable("id") int id){
        personService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
