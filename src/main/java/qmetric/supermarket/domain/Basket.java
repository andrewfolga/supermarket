package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.*;

import static qmetric.supermarket.domain.Unit.ITEM;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Basket {

    public static final String TEMPLATE = "%-20s %5.2f\n";

    private final Map<ItemType, Item> items = new HashMap<>();
    private final List<Promotion> promotions;
    private final Map<ItemType, Promotion> appliedPromotions = new HashMap<>();
    private final Map<PromotionType, BigDecimal> promotionSavings = new HashMap<>();

    public Basket(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(new ArrayList<>(appliedPromotions.values()));
    }

    public Map<PromotionType, BigDecimal> getSavings() {
        return Collections.unmodifiableMap(new HashMap<>(promotionSavings));
    }

    public BigDecimal calculatePromotions() {
        BigDecimal totalToPayForPromotions = BigDecimal.ZERO;
        for (Promotion promotion : promotions) {
            Item item = findItemForType(promotion.getItemType());
            if (item != null) {
                totalToPayForPromotions = totalToPayForPromotions.add(promotion.apply(item));
                promotionApplied(promotion, item, totalToPayForPromotions);
            }
        }
        return totalToPayForPromotions;
    }

    public void add(Item item) {
        items.merge(item.getItemType(), item, (a, b) -> new Item(a.getItemType(), a.getPriceDefinition(), a.getQuantity().add(b.getQuantity())));
    }

    public void promotionApplied(Promotion promotion, Item item, BigDecimal amountToPay) {
        appliedPromotions.put(promotion.getItemType(), promotion);
        promotionSavings.put(promotion.getPromotionType(), amountToPay.subtract(item.getQuantity().multiply(item.getPriceDefinition().getAmountPerUnit())));
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
            if (hasNoPromotion(item)) {
                totalAfterPromotions = totalAfterPromotions.add(item.getPriceDefinition().getAmountPerUnit().multiply(item.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }
        }
        return totalAfterPromotions;
    }

    private boolean hasNoPromotion(Item item) {
        return !appliedPromotions.containsKey(item.getItemType());
    }
}
