package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Basket {

    private final Map<ItemType, Item> items = new HashMap<>();
    private final List<Promotion> allPromotions;
    private final List<Promotion> appliedPromotions = new ArrayList<>();
    private final Map<PromotionType, BigDecimal> promotionSavings = new HashMap<>();

    public Basket(List<Promotion> allPromotions) {
        this.allPromotions = allPromotions;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public List<Promotion> getAllPromotions() {
        return Collections.unmodifiableList(new ArrayList<>(appliedPromotions));
    }

    public Map<PromotionType, BigDecimal> getSavings() {
        return Collections.unmodifiableMap(new HashMap<>(promotionSavings));
    }

    public BigDecimal calculatePromotions() {
        BigDecimal totalToPayForPromotions = BigDecimal.ZERO;
        for (Promotion promotion : allPromotions) {
            Item item = findItemForType(promotion.getItemType());
            if (item != null) {
                totalToPayForPromotions = totalToPayForPromotions.add(promotion.apply(item));
                appliedPromotions.add(promotion);
                promotionSavings.put(promotion.getPromotionType(), totalToPayForPromotions.subtract(item.getQuantity().multiply(item.getPriceDefinition().getAmountPerUnit())));
            }
        }
        return totalToPayForPromotions;
    }

    public void add(Item item) {
        items.merge(item.getItemType(), item, (a, b) -> new Item(a.getItemType(), a.getPriceDefinition(), a.getQuantity().add(b.getQuantity())));

    }

    public Item findItemForType(ItemType type) {
        return items.get(type);
    }


    public BigDecimal calculateSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (Item item : items.values()) {
            BigDecimal amountPerUnit = item.getPriceDefinition().getAmountPerUnit();
            BigDecimal quantity = item.getQuantity();
            subTotal = subTotal.add(amountPerUnit.multiply(quantity)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        return subTotal;
    }

    public BigDecimal calculateRemainder() {
        BigDecimal totalAfterPromotions = BigDecimal.ZERO;
        for (Item item : items.values()) {
            boolean wasPromotionApplied = appliedPromotions.stream().filter(e -> e.getItemType().equals(item.getItemType())).findFirst().isPresent();
            if (!wasPromotionApplied) {
                totalAfterPromotions = totalAfterPromotions.add(item.getPriceDefinition().getAmountPerUnit().multiply(item.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }
        }
        return totalAfterPromotions;
    }

}
