package vdsale.model.vos.disc;

import com.wrapper.spotify.enums.AlbumType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.constants.ValidationConstants;
import vdsale.model.vos.BasicVO;
import vdsale.model.vos.category.CategoryVO;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiscVO extends BasicVO {

    private static final long serialVersionUID = 7055567358638804512L;

    private int id;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String spotifyId;
    private AlbumType type;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private CategoryVO category;
    private ArtistVO artist;
    private String name;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private double price;
}
