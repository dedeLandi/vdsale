package vdsale.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static vdsale.model.entities.ArtistEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class ArtistEntity {

    public static final String ENTITY_NAME = "ArtistEntity";
    protected static final String TABLE_NAME = "ARTIST";

    @Id
    @Column(name = "ID_ARTIST")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ID_ARTIST_SPOTIFY", length = 100, nullable = false)
    private String spotifyId;

    @Column(name = "NAME", nullable = false)
    private String name;
}
