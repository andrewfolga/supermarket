package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Item {
    private final ItemType itemType;
    private final PriceDefinition priceDefinition;
    private final BigDecimal quantity;

    public Item(ItemType itemType, PriceDefinition priceDefinition, BigDecimal quantity) {
        this.itemType = itemType;
        this.priceDefinition = priceDefinition;
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public PriceDefinition getPriceDefinition() {
        return priceDefinition;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
