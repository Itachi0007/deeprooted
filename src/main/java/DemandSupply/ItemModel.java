package DemandSupply;

import java.time.LocalDateTime;
import java.util.Date;

public class ItemModel {
    private Integer id;
    private String name;
    private Integer price;
    private LocalDateTime timestamp;
    private Integer quantity;

    public ItemModel(Integer id, String name, Integer price, LocalDateTime timestamp, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.timestamp = timestamp;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
