package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Item {
    private final ItemType itemType;
    private final BigDecimal price;
    private final BigDecimal quantity;

    public Item(ItemType itemType, BigDecimal price, BigDecimal quantity) {
        this.itemType = itemType;
        this.price = price;
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
