package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import static qmetric.supermarket.domain.Unit.ITEM;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
// TODO: This class has 2 responsibilities: managing amount definition and promotion calculations and printing items -> decouple
public class Basket {

    public static final String TEMPLATE = "%-20s %5.2f\n";

    private final Map<ItemType, Item> items = new HashMap<>();
    private final Map<ItemType, Promotion> appliedPromotions = new HashMap<>();
    private final Map<PromotionType, BigDecimal> promotionSavings = new HashMap<>();

    public void add(Item item) {
        items.merge(item.getItemType(), item, (a, b) -> new Item(a.getItemType(), a.getPriceDefinition(), a.getQuantity().add(b.getQuantity())));
    }

    public void promotionApplied(Promotion promotion, Item item, BigDecimal amountToPay) {
        appliedPromotions.put(promotion.getItemType(), promotion);
        promotionSavings.put(promotion.getPromotionType(), amountToPay.subtract(item.getQuantity().multiply(item.getPriceDefinition().getAmountPerUnit())));
    }

    public BigDecimal findQuantityForType(ItemType type) {
        return items.get(type).getQuantity();
    }

    public Item findItemForType(ItemType type) {
        return items.get(type);
    }

    public String printAllItemsNoPromotions() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        for (Item item : items.values()) {
            BigDecimal quantity = item.getQuantity();
            Unit itemUnit = item.getPriceDefinition().getUnit();
            BigDecimal itemAmountPerUnit = item.getPriceDefinition().getAmountPerUnit();
            if (itemUnit == ITEM) {
                for (int i = 0; i < quantity.intValue(); i++) {
                    formatter.format(TEMPLATE, item.getItemType().getName(), itemAmountPerUnit);
                }
            } else {
                BigDecimal itemSubTotal = itemAmountPerUnit.multiply(quantity);
                formatter.format(TEMPLATE,
                        String.format(itemUnit.getDisplayFormat(), item.getItemType().getName(), quantity, itemAmountPerUnit),
                        itemSubTotal);
            }
        }
        return sb.toString();
    }

    public String printSavings() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        for (Promotion promotion : appliedPromotions.values()) {
            formatter.format(TEMPLATE, promotion.getDescription(), promotionSavings.get(promotion.getPromotionType()));
        }
        return sb.toString();
    }


    public BigDecimal calculateSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (Item item : items.values()) {
            subTotal = subTotal.add(item.getPriceDefinition().getAmountPerUnit().multiply(item.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
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
