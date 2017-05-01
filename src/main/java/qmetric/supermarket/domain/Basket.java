package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Basket {

    private final Map<ItemType, Item> items = new HashMap<>();

    public void add(Item item) {
        items.merge(item.getItemType(), item, (a, b) -> new Item(a.getItemType(), a.getPrice(), a.getQuantity().add(b.getQuantity())));
    }

    public BigDecimal findQuantityForType(ItemType type) {
        return items.get(type).getQuantity();
    }

    public Item findItemForType(ItemType type) {
        return items.get(type);
    }

}
