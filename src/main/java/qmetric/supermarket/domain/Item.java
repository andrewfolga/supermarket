package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Item {
    private final ItemType itemType;
    private final PriceDefinition priceDefinition;
    private final BigDecimal quantityPerUnit;

    public Item(ItemType itemType, PriceDefinition priceDefinition, BigDecimal quantityPerUnit) {
        this.itemType = itemType;
        this.priceDefinition = priceDefinition;
        this.quantityPerUnit = quantityPerUnit;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public PriceDefinition getPriceDefinition() {
        return priceDefinition;
    }

    public BigDecimal getQuantityPerUnit() {
        return quantityPerUnit;
    }
}
