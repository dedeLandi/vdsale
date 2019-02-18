package vdsale.model.vos.page;

import org.springframework.data.domain.Page;

import java.util.Objects;
import java.util.function.Function;

public class PageConverter {

    private PageConverter(){
    }

    public static <VO> PageVO<VO> toVO(Page<VO> page) {
        Objects.requireNonNull(page, "page can't be null");

        return new PageVO<VO>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    public static <ENTITY, VO> PageVO<VO> toVO(Page<ENTITY> page, Function<ENTITY, VO> mapping) {
        Objects.requireNonNull(page, "all data can't be null");
        Objects.requireNonNull(mapping, "mapping can't be null");

        return new PageVO<VO>(page.map(mapping::apply).getContent(), page.getTotalElements(), page.getTotalPages());

    }
}
