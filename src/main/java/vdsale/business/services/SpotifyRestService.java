package vdsale.business.services;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;
import com.wrapper.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SpotifyRestService {

    private final SpotifyApi spotifyApi;

    @Autowired
    public SpotifyRestService(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    public Paging<Category> getSpotifyCategories(int size, int offset) {
        log.info("M=getSpotifyCategories, message=Getting spotify categories");
        try {
            final GetListOfCategoriesRequest getListOfCategoriesRequest = spotifyApi.getListOfCategories()
                    .country(CountryCode.BR)
                    .limit(size)
                    .offset(offset)
                    .locale("sv_BR")
                    .build();

            return getListOfCategoriesRequest.execute();
        } catch (IOException e) {
            log.error("M=getSpotifyCategories, size={}, offset={}, genre={}, message=Unexpected IO error getting spotify categories", size, offset, e);
        } catch (SpotifyWebApiException e) {
            log.error("M=getSpotifyCategories, size={}, offset={}, genre={}, message=Unexpected API error getting spotify categories", size, offset, e);
        }
        log.info("M=getSpotifyCategories, message=Not Found spotify categories");
        return null;
    }

    public Paging<PlaylistSimplified> getSpotifyCategorysPlaylists(int size, int offset, String genre){
        log.info("M=getSpotifyAlbums, message=Getting spotify playlists");
        try {
            final GetCategorysPlaylistsRequest getCategoryRequest = spotifyApi.getCategorysPlaylists(genre)
                    .country(CountryCode.BR)
                    .limit(10)
                    .offset(0)
                    .build();
            return getCategoryRequest.execute();
        } catch (IOException e) {
            log.error("M=getSpotifyAlbums, size={}, offset={}, genre={}, message=Unexpected IO error getting spotify playlists", size, offset, genre, e);
        } catch (SpotifyWebApiException e) {
            log.error("M=getSpotifyAlbums, size={}, offset={}, genre={}, message=Unexpected API error getting spotify playlists", size, offset, genre, e);
        }
        log.info("M=getSpotifyAlbums, message=Not Found spotify playlists");
        return null;

    }

    public SearchResult getSpotifyAlbums(int size, int offset, String genre) {
        log.info("M=getSpotifyAlbums, message=Getting spotify albums");
        try {
            final SearchItemRequest searchItemRequest = spotifyApi.searchItem(genre, ModelObjectType.ALBUM.getType())
                    .limit(size)
                    .offset(offset)
                    .market(CountryCode.BR)
                    .build();
            return searchItemRequest.execute();

        } catch (IOException e) {
            log.error("M=getSpotifyAlbums, size={}, offset={}, genre={}, message=Unexpected IO error getting spotify albums", size, offset, genre, e);
        } catch (SpotifyWebApiException e) {
            log.error("M=getSpotifyAlbums, size={}, offset={}, genre={}, message=Unexpected API error getting spotify albums", size, offset, genre, e);
        }
        log.info("M=getSpotifyAlbums, message=Not Found spotify albums");
        return null;
    }

    public SearchResult getSpotifyAlbumsByGenre(int size, int offset, String genre) {
        log.info("M=getSpotifyAlbums, message=Getting spotify albums");
        try {
            final SearchItemRequest searchItemRequest = spotifyApi.searchItem(genre, ModelObjectType.GENRE.getType())
                    .market(CountryCode.BR)
                    .limit(10)
                    .offset(0)
                    .build();
            return searchItemRequest.execute();

        } catch (IOException e) {
            log.error("M=getSpotifyAlbums, size={}, offset={}, genre={}, message=Unexpected IO error getting spotify albums", size, offset, genre, e);
        } catch (SpotifyWebApiException e) {
            log.error("M=getSpotifyAlbums, size={}, offset={}, genre={}, message=Unexpected API error getting spotify albums", size, offset, genre, e);
        }
        log.info("M=getSpotifyAlbums, message=Not Found spotify albums");
        return null;
    }

    public String[] getSpotifyGenre() {
        log.info("M=getSpotifyGenre, message=Getting spotify genre");
        try {
            final GetAvailableGenreSeedsRequest getAvailableGenreSeedsRequest = spotifyApi.getAvailableGenreSeeds().build();;
            return getAvailableGenreSeedsRequest.execute();

        } catch (IOException e) {
            log.error("M=getSpotifyGenre, message=Unexpected IO error getting spotify genre", e);
        } catch (SpotifyWebApiException e) {
            log.error("M=getSpotifyGenre, message=Unexpected API error getting spotify genre", e);
        }
        log.info("M=getSpotifyGenre, message=Not Found spotify genre");
        return null;
    }


}
