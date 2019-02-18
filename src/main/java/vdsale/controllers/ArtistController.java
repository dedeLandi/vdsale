package vdsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vdsale.business.basicService.IBasicCrudService;
import vdsale.business.services.ArtistService;

import static vdsale.controllers.ArtistController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class ArtistController {

    protected static final String REQUEST_MAPPING = "/artist";

    private static final String REQUEST_GET_ARTIST = "/{id}";

    @Autowired
    @Qualifier(ArtistService.QUALIFIER)
    private IBasicCrudService artistService;

//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(artistService.getAll());
//    }

    @GetMapping
    public ResponseEntity getAll(final Pageable pageRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(artistService.getAll(pageRequest));
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(REQUEST_GET_ARTIST)
    public ResponseEntity get(@PathVariable("id") int id){
        var data = artistService.get(id);
        if(data == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
