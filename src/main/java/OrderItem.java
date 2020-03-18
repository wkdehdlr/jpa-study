import javax.persistence.*;

@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seq_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "seq_item")
    private Item item;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
