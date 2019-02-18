package vdsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vdsale.business.facades.CategoryFacade;
import vdsale.model.vos.category.CategoryVO;
import vdsale.model.vos.page.PageVO;

import static vdsale.controllers.CategoryController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class CategoryController {

    protected static final String REQUEST_MAPPING = "/category";
    private static final String REQUEST_MAPPING_SPOTIFY = "/spotify";
    private static final String REQUEST_MAPPING_SPOTIFY_IMPORT = "/spotify/import";

    @Autowired
    private CategoryFacade categoryFacade;

    @GetMapping(REQUEST_MAPPING_SPOTIFY)
    public ResponseEntity getAllSpotify(
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "offset", defaultValue = "0") int offset ){
        return ResponseEntity.status(HttpStatus.OK).body(categoryFacade.getAllSpotify(size, offset));
    }


    @PostMapping(REQUEST_MAPPING_SPOTIFY_IMPORT)
    public ResponseEntity importCategoriesFromSpotify(){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryFacade.importCategoriesFromSpotify());
    }

//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(categoryFacade.getAll());
//    }

    @GetMapping
    public ResponseEntity<PageVO<CategoryVO>> getAll(final Pageable pageRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryFacade.getAll(pageRequest));
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
