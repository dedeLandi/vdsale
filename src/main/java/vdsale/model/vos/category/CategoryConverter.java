package vdsale.model.vos.category;

import com.wrapper.spotify.model_objects.specification.Category;
import org.springframework.data.domain.Page;
import vdsale.model.entities.CategoryEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.page.PageConverter;
import vdsale.model.vos.page.PageVO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryConverter implements IBasicConverter<CategoryEntity, CategoryVO> {

    private static CategoryConverter INSTANCE;

    private CategoryConverter(){

    }

    public static CategoryConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new CategoryConverter();
        }
        return INSTANCE;
    }

    public static CategoryEntity toEntity(Category category) {
        Objects.requireNonNull(category, "Spotify category can't be null");

        CategoryEntity entity = new CategoryEntity();
        entity.setSpotifyId(category.getId());
        entity.setName(category.getName());

        return entity;
    }

    @Override
    public List<CategoryVO> toVO(List<CategoryEntity> allCategories) {
        return Optional.ofNullable(allCategories)
                .map(c -> c.stream()
                        .map(INSTANCE::fromEntityToVO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageVO<CategoryVO> toVO(Page<CategoryEntity> page, Function<CategoryEntity, CategoryVO> function) {
        return PageConverter.toVO(page, function);
    }

    @Override
    public CategoryVO fromEntityToVO(CategoryEntity entity){
        Objects.requireNonNull(entity, "Category entity can't be null");

        var vo = new CategoryVO();
        vo.setId(entity.getId());
        vo.setSpotifyId(entity.getSpotifyId());
        vo.setName(entity.getName());

        return vo;
    }

    @Override
    public CategoryEntity toEntity(CategoryVO vo) {
        Objects.requireNonNull(vo, "Category vo can't be null");

        var entity = new CategoryEntity();
        entity.setId(vo.getId());
        entity.setSpotifyId(vo.getSpotifyId());
        entity.setName(vo.getName());

        return entity;
    }

    public CategoryVO toVO(Category category) {
        Objects.requireNonNull(category, "Spotify category can't be null");

        CategoryVO entity = new CategoryVO();
        entity.setSpotifyId(category.getId());
        entity.setName(category.getName());

        return entity;
    }

}
