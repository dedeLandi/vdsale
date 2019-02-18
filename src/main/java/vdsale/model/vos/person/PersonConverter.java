package vdsale.model.vos.person;

import org.springframework.data.domain.Page;
import vdsale.model.entities.PersonEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.page.PageConverter;
import vdsale.model.vos.page.PageVO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonConverter implements IBasicConverter<PersonEntity, PersonVO> {

    private static PersonConverter INSTANCE;

    public PersonConverter(){
    }

    public static PersonConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new PersonConverter();
        }
        return INSTANCE;
    }

    @Override
    public List<PersonVO> toVO(List<PersonEntity> all) {
        return Optional.ofNullable(all)
                .map(p -> p.stream()
                        .map(INSTANCE::fromEntityToVO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageVO<PersonVO> toVO(Page<PersonEntity> page, Function<PersonEntity, PersonVO> function) {
        return PageConverter.toVO(page, function);
    }

    @Override
    public PersonVO fromEntityToVO(PersonEntity entity){
        Objects.requireNonNull(entity, "person entity can't be null");

        var vo = new PersonVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());

        return vo;
    }

    @Override
    public PersonEntity toEntity(PersonVO vo) {
        Objects.requireNonNull(vo, "person vo can't be null");

        var entity = new PersonEntity();
        entity.setId(vo.getId());
        entity.setName(vo.getName());

        return entity;
    }

}
