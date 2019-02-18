package vdsale.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

import static vdsale.model.entities.OrderItemEntity.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class OrderItemEntity {

    public static final String ENTITY_NAME = "OrderItemEntity";
    protected static final String TABLE_NAME = "ORDER_ITEM";

    @Id
    @Column(name = "ID_ORDER_ITEM")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_ORDER", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "ID_DISC", nullable = false)
    private DiscEntity disc;

    @Column(name = "QTD", nullable = false)
    private int qtd;

    @Column(name = "UNIT_PRICE", nullable = false)
    private double unitPrice;

    @Column(name = "TOTAL", nullable = false)
    private double total;

    @Column(name = "CASHBACK", nullable = false)
    private double cashback;

    @Column(name = "CASHBACK_TOTAL", nullable = false)
    private double cashbackTotal;

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", order=" + order.getId() +
                ", disc=" + disc +
                ", qtd=" + qtd +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", cashback=" + cashback +
                ", cashbackTotal=" + cashbackTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return id == that.id &&
                (order != null && order.getId() == that.getOrder().getId()) &&
                qtd == that.qtd &&
                Double.compare(that.unitPrice, unitPrice) == 0 &&
                Double.compare(that.total, total) == 0 &&
                Double.compare(that.cashback, cashback) == 0 &&
                Double.compare(that.cashbackTotal, cashbackTotal) == 0 &&
                disc.equals(that.disc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                order != null ? order.getId() : null,
                disc,
                qtd,
                unitPrice,
                total,
                cashback,
                cashbackTotal);
    }
}
