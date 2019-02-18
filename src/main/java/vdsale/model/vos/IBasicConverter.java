package vdsale.model.vos;

import org.springframework.data.domain.Page;
import vdsale.model.vos.page.PageVO;

import java.util.List;
import java.util.function.Function;

public interface IBasicConverter<ENTITY, VO> {

    List<VO> toVO(List<ENTITY> all) ;
    
    PageVO<VO> toVO(Page<ENTITY> page, Function<ENTITY, VO> function) ;
    
    VO fromEntityToVO(ENTITY entity);

    ENTITY toEntity(VO data);
}
