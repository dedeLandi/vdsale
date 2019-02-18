package vdsale.model.vos.cashback;

import org.springframework.data.domain.Page;
import vdsale.model.entities.CashbackEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.category.CategoryConverter;
import vdsale.model.vos.page.PageConverter;
import vdsale.model.vos.page.PageVO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CashbackConverter implements IBasicConverter<CashbackEntity, CashbackVO> {

    private static CashbackConverter INSTANCE;

    private CashbackConverter(){
    }

    public static CashbackConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new CashbackConverter();
        }
        return INSTANCE;
    }

    @Override
    public List<CashbackVO> toVO(List<CashbackEntity> all) {
        return Optional.ofNullable(all)
                .map(c -> c.stream()
                        .map(INSTANCE::fromEntityToVO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageVO<CashbackVO> toVO(Page<CashbackEntity> page, Function<CashbackEntity, CashbackVO> function) {
        return PageConverter.toVO(page, function);
    }

    @Override
    public CashbackVO fromEntityToVO(CashbackEntity entity){
        Objects.requireNonNull(entity, "cashback entity can't be null");

        var vo = new CashbackVO();
        vo.setId(entity.getId());
        vo.setCategory(CategoryConverter.getInstance().fromEntityToVO(entity.getCategory()));
        vo.setWeekday(entity.getWeekday());
        vo.setCashback(entity.getCashback());

        return vo;
    }

    @Override
    public CashbackEntity toEntity(CashbackVO vo) {
        Objects.requireNonNull(vo, "cashback vo can't be null");

        var entity = new CashbackEntity();
        entity.setId(vo.getId());
        entity.setCategory(CategoryConverter.getInstance().toEntity(vo.getCategory()));
        entity.setWeekday(vo.getWeekday());
        entity.setCashback(vo.getCashback());

        return entity;
    }


}
