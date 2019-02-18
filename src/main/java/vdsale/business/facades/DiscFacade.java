package vdsale.business.facades;

import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vdsale.business.services.ArtistService;
import vdsale.business.services.CategoryService;
import vdsale.business.services.DiscService;
import vdsale.business.services.SpotifyRestService;
import vdsale.exceptions.DataNotFoundException;
import vdsale.model.entities.CategoryEntity;
import vdsale.model.vos.disc.DiscConverter;
import vdsale.model.vos.disc.DiscSimpleVO;
import vdsale.model.vos.disc.DiscVO;
import vdsale.model.vos.page.PageVO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class DiscFacade {

    private final SpotifyRestService spotifyRestService;
    private final DiscService discService;
    private final CategoryService categoryService;
    private final ArtistService artistService;

    @Autowired
    public DiscFacade(SpotifyRestService spotifyRestService, DiscService discService, CategoryService categoryService, ArtistService artistService) {
        this.spotifyRestService = spotifyRestService;
        this.discService = discService;
        this.categoryService = categoryService;
        this.artistService = artistService;
    }

    public SearchResult getAllSpotify(int size, int offset, String genre) {
        log.info("M=getAllSpotify, message=Getting spotify albums");
        return spotifyRestService.getSpotifyAlbums(size, offset, genre);
    }

//    public Paging<PlaylistSimplified> getAllSpotify2(int size, int offset, String genre) {
//        log.info("M=getAllSpotify, message=Getting spotify albums");
//        return spotifyRestService.getSpotifyCategorysPlaylists(size, offset, genre);
//    }

//    public SearchResult getAllSpotify3(int size, int offset, String genre) {
//        log.info("M=getAllSpotify, message=Getting spotify albums");
//        return spotifyRestService.getSpotifyAlbumsByGenre(size, offset, genre);
//    }

    public Integer importAlbumsFromSpotify() {
        log.info("M=importAlbumsFromSpotify, message=Importing spotify albums");

        AtomicInteger countImported = new AtomicInteger();
        List<CategoryEntity> allCategories = categoryService.findAll();

        allCategories.forEach(category -> {
            countImported.addAndGet(importAlbumsFromSpotify(category));
        });

        return countImported.get();
    }

    private Integer importAlbumsFromSpotify(CategoryEntity category){
        final int size = 50;
        int offset = 0;
        SearchResult albums = spotifyRestService.getSpotifyAlbums(size, offset, category.getName());

        if(albums == null) return 0;

        importAlbums(category, albums.getAlbums());

        return albums.getAlbums().getItems().length;
    }

    private void importAlbums(CategoryEntity category, Paging<AlbumSimplified> albums) {
        Arrays.stream(albums.getItems()).forEach(album -> {
            if(discService.countBySpotifyID(album.getId()) == 0){
                log.info("M=importAlbums, spotifyId={}, message=Importing album", album.getId());
                discService.save(DiscConverter.getInstance().toEntity(album, category, artistService.findOrSave(album.getArtists())));
            }
        });
    }

    public List<DiscVO> getAll() {
        log.info("M=getAll, message=Getting a list of disc");
        return discService.getAll();
    }

    public PageVO<DiscVO> getAll(Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of disc");
        return discService.getAll(pageRequest);
    }

    public DiscVO get(int id) {
        log.info("M=get, discId={}, message=Get a specific disc", id);
        return discService.get(id);
    }

    public DiscVO getDisc(DiscSimpleVO simple) {
        log.info("M=getDisc, disc={}, message=Searching for a disc", simple);
        return discService.getDisc(simple);
    }

    public PageVO<DiscVO> getAllByGenre(String spotifyCategoryID, Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of disc");
        Optional<CategoryEntity> category = categoryService.findBySpotifyID(spotifyCategoryID);
        int categoryId = category.orElseThrow(() -> new DataNotFoundException("Category not found")).getId();
        return discService.getAllByGenre(categoryId, pageRequest);
    }

    public DiscVO getBySpotifyId(String spotifyId) {
        log.info("M=getBySpotifyId, spotifyId={}, message=Get a specific disc", spotifyId);
        return discService.getBySpotifyId(spotifyId);
    }
}
