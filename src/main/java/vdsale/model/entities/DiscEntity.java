package vdsale.model.entities;

import com.wrapper.spotify.enums.AlbumType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static vdsale.model.entities.DiscEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class DiscEntity {

    public static final String ENTITY_NAME = "DiscEntity";
    protected static final String TABLE_NAME = "DISC";

    @Id
    @Column(name = "ID_DISC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ID_DISC_SPOTIFY", nullable = false)
    private String spotifyId;

    @Column(name = "TYPE", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private AlbumType type;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORY", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "ID_ARTIST", nullable = false)
    private ArtistEntity artist;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private double price;

}
