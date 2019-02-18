package vdsale.model.entities;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

import static vdsale.model.entities.OrderEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class OrderEntity {

    public static final String ENTITY_NAME = "OrderEntity";
    protected static final String TABLE_NAME = "VD_ORDER";

    @Id
    @Column(name = "ID_ORDER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_PERSON", nullable = false)
    private PersonEntity person;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderItemEntity.class)
    private Set<OrderItemEntity> items;

    @Column(name = "TOTAL", nullable = false)
    private double total;

    @Column(name = "CASHBACK_TOTAL", nullable = false)
    private double cashbackTotal;

    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

}
