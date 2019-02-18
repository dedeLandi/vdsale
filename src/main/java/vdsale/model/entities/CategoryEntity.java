package vdsale.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static vdsale.model.entities.CategoryEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class CategoryEntity {

    public static final String ENTITY_NAME = "CategoryEntity";
    protected static final String TABLE_NAME = "CATEGORY";

    @Id
    @Column(name = "ID_CATEGORY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ID_CATEGORY_SPOTIFY", length = 100, nullable = false)
    private String spotifyId;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

}
