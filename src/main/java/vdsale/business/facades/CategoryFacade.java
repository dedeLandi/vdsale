package vdsale.business.facades;


import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vdsale.business.services.CategoryService;
import vdsale.business.services.SpotifyRestService;
import vdsale.model.vos.category.CategoryConverter;
import vdsale.model.vos.category.CategoryVO;
import vdsale.model.vos.page.PageVO;

import java.util.Arrays;

@Slf4j
@Component
public class CategoryFacade {

    private final SpotifyRestService spotifyRestService;
    private final CategoryService categoryService;

    @Autowired
    public CategoryFacade(SpotifyRestService spotifyRestService, CategoryService categoryService) {
        this.spotifyRestService = spotifyRestService;
        this.categoryService = categoryService;
    }

    public Paging<Category> getAllSpotify(int size, int offset) {
        log.info("M=getAllSpotify, message=Getting spotify categories");
        return spotifyRestService.getSpotifyCategories(size, offset);
    }

//    public String[] getAllGenre() {
//        log.info("M=getAllGenre, message=Getting spotify genre");
//        return spotifyRestService.getSpotifyGenre();
//    }

    public Integer importCategoriesFromSpotify() {
        log.info("M=importCategoriesFromSpotify, message=Importing spotify categories");

        final int size = 10;
        int offset = 0;
        Paging<Category> categories = spotifyRestService.getSpotifyCategories(size, offset);

        if(categories == null) return 0;

        importCategories(categories.getItems());

        if(10 < categories.getTotal()){
            int pages = categories.getTotal() / 10;
            for (int page = 0; page < pages; page++){
                offset += size;
                Paging<Category> categoriesPage = spotifyRestService.getSpotifyCategories(size, offset);
                if(categoriesPage == null) continue;
                importCategories(categoriesPage.getItems());
            }
        }

        return categories.getTotal();
    }

    private void importCategories(Category[] items) {
        Arrays.stream(items).forEach(category -> {
            if(categoryService.countBySpotifyID(category.getId()) == 0){
                log.info("M=importCategories, spotifyId={}, message=Importing category", category.getId());
                categoryService.save(CategoryConverter.getInstance().toVO(category));
            }
        });

    }

    public PageVO<CategoryVO> getAll(Pageable pageRequest) {
        log.info("M=getAll, message=");
        return categoryService.getAll(pageRequest);
    }
}
