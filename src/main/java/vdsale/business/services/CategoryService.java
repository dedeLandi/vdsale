package vdsale.business.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vdsale.business.basicService.BasicCrudService;
import vdsale.exceptions.MethodNotImplementedException;
import vdsale.model.entities.CategoryEntity;
import vdsale.model.repositories.CategoryRepository;
import vdsale.model.vos.category.CategoryConverter;
import vdsale.model.vos.category.CategoryVO;

import java.util.List;
import java.util.Optional;

import static vdsale.business.services.CategoryService.QUALIFIER;

@Slf4j
@Service(QUALIFIER)
public class CategoryService extends BasicCrudService<CategoryVO,
        CategoryEntity,
        CategoryConverter,
        CategoryRepository> {

    public static final String QUALIFIER = "CategoryService";

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository, CategoryConverter.getInstance());
    }


    @Override
    @Deprecated
    public CategoryVO update(CategoryVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    @Deprecated
    public void delete(int id) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    protected String getEntitySimpleName() {
        return CategoryEntity.class.getSimpleName();
    }

    @Override
    @Deprecated
    protected void updateData(CategoryVO categoryVO, CategoryEntity categoryEntity) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    public List<CategoryEntity> findAll() {
        log.info("M=findAll, message=Searching all categories");
        return repository.findAll();
    }

    public Optional<CategoryEntity> findById(int id) {
        log.info("M=getById, categoryId={}, message=Searching for a category", id);
        return repository.findById(id);
    }

    public int countBySpotifyID(String id) {
        log.info("M=countBySpotifyID, categoryId={}, message=Counting by spotify id", id);
        return repository.countBySpotifyID(id);
    }

    public Optional<CategoryEntity> findBySpotifyID(String spotifyCategoryID) {
        log.info("M=findBySpotifyID, spotifyCategoryID={}, message=Searching category by spotify id", spotifyCategoryID);
        return repository.findBySpotifyID(spotifyCategoryID);
    }
}
