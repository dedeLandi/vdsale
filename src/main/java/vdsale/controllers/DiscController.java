package vdsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vdsale.business.facades.DiscFacade;

import static vdsale.controllers.DiscController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class DiscController {

    protected static final String REQUEST_MAPPING = "/disc";

    private static final String REQUEST_MAPPING_GENRE = "/category/{spotifyCategoryID}";
    private static final String REQUEST_MAPPING_GET = "/{id}";
    private static final String REQUEST_MAPPING_GET_SPOTIFY_ID = "/spotify/{spotifyId}";
    private static final String REQUEST_MAPPING_SPOTIFY = "/spotify";
//    private static final String REQUEST_MAPPING_SPOTIFY2 = "/spotify2";
//    private static final String REQUEST_MAPPING_SPOTIFY3 = "/spotify3";
    private static final String REQUEST_MAPPING_SPOTIFY_IMPORT = "/spotify/import";

    @Autowired
    private DiscFacade discFacade;

    @GetMapping(REQUEST_MAPPING_SPOTIFY)
    public ResponseEntity getAllSpotify(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            @RequestParam(value = "genre") String genre){
        if(StringUtils.isEmpty(genre)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(discFacade.getAllSpotify(size, offset, genre));
    }


//    @GetMapping(REQUEST_MAPPING_SPOTIFY2)
//    public ResponseEntity getAllSpotify2(
//            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
//            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
//            @RequestParam(value = "genre") String genre){
//        if(StringUtils.isEmpty(genre)){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(discFacade.getAllSpotify2(size, offset, genre));
//    }

//    @GetMapping(REQUEST_MAPPING_SPOTIFY3)
//    public ResponseEntity getAllSpotify3(
//            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
//            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
//            @RequestParam(value = "genre") String genre){
//        if(StringUtils.isEmpty(genre)){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(discFacade.getAllSpotify3(size, offset, genre));
//    }

    @PostMapping(REQUEST_MAPPING_SPOTIFY_IMPORT)
    public ResponseEntity importCategoriesFromSpotify(){
        return ResponseEntity.status(HttpStatus.CREATED).body(discFacade.importAlbumsFromSpotify());
    }

//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(discFacade.getAll());
//    }

    @GetMapping
    public ResponseEntity getAll(final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(discFacade.getAll(pageRequest));
    }

    @GetMapping(REQUEST_MAPPING_GENRE)
    public ResponseEntity getAllByGenre(@PathVariable("spotifyCategoryID") String spotifyCategoryID, final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(discFacade.getAllByGenre(spotifyCategoryID, pageRequest));
    }

    @GetMapping(REQUEST_MAPPING_GET)
    public ResponseEntity get(@PathVariable("id") int id){
        var data = discFacade.get(id);
        if(data == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping(REQUEST_MAPPING_GET_SPOTIFY_ID)
    public ResponseEntity getBySpotifyId(@PathVariable("spotifyId") String spotifyId){
        var data = discFacade.getBySpotifyId(spotifyId);
        if(data == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
