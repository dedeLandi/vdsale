package vdsale.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static vdsale.model.entities.PersonEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class PersonEntity {

    public static final String ENTITY_NAME = "PersonEntity";
    public static final String TABLE_NAME = "PERSON";

    @Id
    @Column(name = "ID_PERSON")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;
}
