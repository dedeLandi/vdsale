package vdsale.model.entities;

import lombok.Data;
import vdsale.model.enums.Weekday;

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

import static vdsale.model.entities.CashbackEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class CashbackEntity {

    public static final String ENTITY_NAME = "CashbackEntity";
    public static final String TABLE_NAME = "CASHBACK";

    @Id
    @Column(name = "ID_CASHBACK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORY", nullable = false)
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    @Column(name = "WEEKDAY", length = 15, nullable = false)
    private Weekday weekday;

    @Column(name = "CASHBACK_VALUE", nullable = false)
    private double cashback;


}
