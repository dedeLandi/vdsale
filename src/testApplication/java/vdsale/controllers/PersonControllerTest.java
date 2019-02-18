package vdsale.controllers;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import vdsale.BasicApplicationTest;
import vdsale.exceptions.DefaultError;
import vdsale.model.repositories.PersonRepository;
import vdsale.model.vos.page.PageVO;
import vdsale.model.vos.person.PersonVO;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.of;

@Sql(value = "/sqls/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sqls/person.sql")
public class PersonControllerTest extends BasicApplicationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testGetPersonList(){
        ResponseEntity<PageVO<PersonVO>> response = restTemplate.exchange("/person",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageVO<PersonVO>>() {});

        Assert.assertNotNull(response);

        PageVO<PersonVO> data = response.getBody();

        Assert.assertThat(data.getContents(), Matchers.hasSize(2));

        Assert.assertEquals("Landi", data.getContents().stream().filter(p -> p.getId() == 2).findFirst().get().getName());
    }

    @ParameterizedTest()
    @MethodSource("getPersonsParameter")
    @DisplayName("Testing get person")
    public void testGetPerson(int id, String expected){
        ResponseEntity<PersonVO> response = restTemplate.exchange("/person/"+id, HttpMethod.GET, null, PersonVO.class);

        Assert.assertNotNull(response);

        PersonVO data = response.getBody();

        Assert.assertNotNull(data);

        Assert.assertEquals(expected, data.getName());

    }

    @Test
    public void testGetPersonNotFound(){
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/person/99", HttpMethod.GET, null, HttpStatus.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    public void testSavePerson(){
        PersonVO vo = createPerson("Test");
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<PersonVO> response = restTemplate.exchange("/person", HttpMethod.POST, request, PersonVO.class);

        Assert.assertNotNull(response);

        PersonVO responseData = response.getBody();

        Assert.assertNotNull(responseData);

        final var entity = personRepository.findById(responseData.getId());

        Assert.assertNotNull(entity);

    }

    @Test
    public void testDeletePerson(){
        int id = savePerson();
        ResponseEntity<Integer> response = restTemplate.exchange("/person/"+id, HttpMethod.DELETE, null, Integer.class);

        Assert.assertNotNull(response);

        Integer data = response.getBody();

        Assert.assertNotNull(data);

        Assert.assertEquals(id, data.intValue());

    }

    @Test
    public void testDeletePersonNotFound(){
        ResponseEntity<DefaultError> response = restTemplate.exchange("/person/99", HttpMethod.DELETE, null, DefaultError.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        DefaultError error = response.getBody();

        Assert.assertNotNull(error);

        Assert.assertEquals("Dados não encontrados", error.getError());

    }

    private int savePerson() {
        PersonVO vo = createPerson("Test 1");
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<PersonVO> response = restTemplate.exchange("/person", HttpMethod.POST, request, PersonVO.class);

        return response.getBody().getId();
    }

    private PersonVO createPerson(String name) {
        PersonVO vo = new PersonVO();
        vo.setName(name);
        return vo;
    }

    private static Stream<Arguments> getPersonsParameter(){
        return Stream.of(
                of(1,"André"),
                of(2,"Landi")
        );
    }
}
