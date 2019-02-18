package vdsale.controllers;

import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import vdsale.BasicApplicationTest;
import vdsale.business.facades.CategoryFacade;
import vdsale.model.entities.CategoryEntity;
import vdsale.model.repositories.CategoryRepository;
import vdsale.model.vos.category.CategoryVO;
import vdsale.model.vos.page.PageVO;

import java.util.List;

@Sql(value = "/sqls/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sqls/category.sql")
public class CategoryControllerTest extends BasicApplicationTest {

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetCategoryList(){
        ResponseEntity<PageVO<CategoryVO>> response = restTemplate.exchange("/category",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageVO<CategoryVO>>() {});

        Assert.assertNotNull(response);

        PageVO<CategoryVO> data = response.getBody();

        Assert.assertThat(data.getContents(), Matchers.hasSize(4));

    }

    @Test
    public void testGetSpotifyCategory(){
        //TODO find an alternative to use the REST call instead the Service
        //TODO calling the real spotify API, mock this call

        Paging<Category> data = categoryFacade.getAllSpotify(10,0);

        Assert.assertNotNull(data);

        Assert.assertThat(data.getTotal(), Matchers.equalTo(42));
    }

    @Test
    public void testSpotifyCategoryImport(){
        //TODO calling the real spotify API, mock this call

        ResponseEntity<Integer> response = restTemplate.exchange("/category/spotify/import",
                HttpMethod.POST,
                null,
                Integer.class);

        Assert.assertNotNull(response);

        Assert.assertThat(response.getBody(), Matchers.equalTo(42));

        List<CategoryEntity> allEntities = categoryRepository.findAll();

        Assert.assertNotNull(allEntities);
        Assert.assertThat(allEntities, Matchers.hasSize(42));
    }


}
