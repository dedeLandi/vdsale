package vdsale.model.vos.disc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.model.vos.BasicVO;

@Data
@EqualsAndHashCode(callSuper=true)
public class ArtistVO extends BasicVO {

    private static final long serialVersionUID = 27003723567062422L;

    private int id;
    private String spotifyId;
    private String name;
}
