package vdsale.controllers;

import com.wrapper.spotify.model_objects.special.SearchResult;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import vdsale.BasicApplicationTest;
import vdsale.business.facades.DiscFacade;
import vdsale.model.entities.DiscEntity;
import vdsale.model.repositories.DiscRepository;
import vdsale.model.vos.disc.DiscVO;
import vdsale.model.vos.page.PageVO;

import java.util.List;

@Sql(value = "/sqls/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqls/category.sql", "/sqls/disc.sql"})
public class DiscControllerTest extends BasicApplicationTest {

    @Autowired
    private DiscFacade discFacade;

    @Autowired
    private DiscRepository discRepository;

    @Test
    public void testGetDiscList(){
        ResponseEntity<PageVO<DiscVO>> response = restTemplate.exchange("/disc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageVO<DiscVO>>() {});

        Assert.assertNotNull(response);

        PageVO<DiscVO> data = response.getBody();

        Assert.assertThat(data.getContents(), Matchers.hasSize(1));

    }

    @Test
    public void testGetSpotifyDisc(){
        //TODO find an alternative to use the REST call instead the Service
        //TODO calling the real spotify API, mock this call

        SearchResult data = discFacade.getAllSpotify(10,0,"pop");

        Assert.assertNotNull(data);

        Assert.assertThat(data.getAlbums().getTotal(), Matchers.greaterThan(31174));
    }

    @Test
    public void testSpotifyDiscImport(){
        //TODO calling the real spotify API, mock this call

        ResponseEntity<Integer> response = restTemplate.exchange("/disc/spotify/import",
                HttpMethod.POST,
                null,
                Integer.class);

        Assert.assertNotNull(response);

        Assert.assertThat(response.getBody(), Matchers.equalTo(200));

        List<DiscEntity> allEntities = discRepository.findAll();

        Assert.assertNotNull(allEntities);
        Assert.assertThat(allEntities, Matchers.hasSize(198));
    }


}
