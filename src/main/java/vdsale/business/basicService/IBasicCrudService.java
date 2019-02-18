package vdsale.business.basicService;

import org.springframework.data.domain.Pageable;
import vdsale.model.vos.page.PageVO;

import java.util.List;

public interface IBasicCrudService<T> {

    List<T> getAll();

    PageVO<T> getAll(Pageable pageRequest);

    T get(int id);

    T save(T data);

    T update(T data);

    void delete(int id);
}