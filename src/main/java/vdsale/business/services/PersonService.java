package vdsale.business.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vdsale.business.basicService.BasicCrudService;
import vdsale.exceptions.DataNotFoundException;
import vdsale.model.entities.PersonEntity;
import vdsale.model.repositories.PersonRepository;
import vdsale.model.vos.person.PersonConverter;
import vdsale.model.vos.person.PersonVO;

import java.util.Objects;

import static vdsale.business.services.PersonService.QUALIFIER;


@Slf4j
@Service(QUALIFIER)
public class PersonService  extends BasicCrudService<PersonVO,
        PersonEntity,
        PersonConverter,
        PersonRepository> {

    public static final String QUALIFIER = "PersonService";

    @Autowired
    public PersonService(PersonRepository personRepository){
        super(personRepository, PersonConverter.getInstance());
    }

    public PersonVO getByName(String name) {
        log.info("M=getByName, name={}, message=Get a specific Person", name);
        var data = repository.findByName(name);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    public PersonVO findPersonByIdOrName(int id, String name) {
        log.info("M=findPersonByIdOrName, personId={}, name={}, message=Searching for a specifc person", id, name);
        PersonVO personFounded = null;
        if (id > 0) {
            personFounded = get(id);
        }
        if (Objects.isNull(personFounded) && StringUtils.isNotEmpty(name)) {
            personFounded = getByName(name);
        }
        if (!Objects.isNull(personFounded)) return personFounded;
        throw new DataNotFoundException("Not found person with id = " + id + ", name = " + name);
    }

    @Override
    protected String getEntitySimpleName() {
        return PersonEntity.class.getSimpleName();
    }

    @Override
    protected void updateData(PersonVO vo, PersonEntity entity) {
        entity.setName(vo.getName());
    }
}
