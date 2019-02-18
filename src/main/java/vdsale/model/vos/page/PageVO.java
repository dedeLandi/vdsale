package vdsale.model.vos.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vdsale.model.vos.BasicVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageVO<E> extends BasicVO {

    private static final long serialVersionUID = 372287022056382011L;

    private List<E> contents;
    private Long totalElements;
    private int totalPages;

}
