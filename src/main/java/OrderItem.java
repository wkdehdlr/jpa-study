import javax.persistence.*;

@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq")
    private Long id;

    @Column(name = "seq_order")
    private Long orderId;

    @Column(name = "seq_item")
    private Long itemId;

    @Column(name = "orderPrice")
    private Long orderPrice;

    @Column(name = "count")
    private Long count;

    public OrderItem(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
