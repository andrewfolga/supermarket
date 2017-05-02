package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Basket {

    private final SortedMap<ItemType, Item> items = new TreeMap<>();
    private final List<Promotion> appliedPromotions = new ArrayList<>();
    private final Map<PromotionType, BigDecimal> promotionSavings = new HashMap<>();

    public List<Item> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public List<Promotion> getAllPromotions() {
        return Collections.unmodifiableList(new ArrayList<>(appliedPromotions));
    }

    public Map<PromotionType, BigDecimal> getSavings() {
        return Collections.unmodifiableMap(new HashMap<>(promotionSavings));
    }

    public BigDecimal calculatePromotions(List<Promotion> promotions) {
        BigDecimal totalToPay = BigDecimal.ZERO;
        for (Promotion promotion : promotions) {
            Item item = items.get(promotion.getItemType());
            totalToPay = totalToPay.add(promotion.apply(item));
            appliedPromotions.add(promotion);
            promotionSavings.put(promotion.getPromotionType(), totalToPay.subtract(item.getQuantityPerUnit().multiply(item.getPriceDefinition().getAmountPerUnit())));
        }
        return totalToPay;
    }

    public void add(Item item) {
        items.merge(item.getItemType(), item, (a, b) -> new Item(a.getItemType(), a.getPriceDefinition(), a.getQuantityPerUnit().add(b.getQuantityPerUnit())));

    }

    public BigDecimal calculateSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (Item item : items.values()) {
            BigDecimal amountPerUnit = item.getPriceDefinition().getAmountPerUnit();
            BigDecimal quantity = item.getQuantityPerUnit();
            subTotal = subTotal.add(amountPerUnit.multiply(quantity)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        return subTotal;
    }

    public BigDecimal calculateNonPromotions() {
        BigDecimal totalAfterPromotions = BigDecimal.ZERO;
        for (Item item : items.values()) {
            boolean wasPromotionApplied = appliedPromotions.stream().filter(e -> e.getItemType().equals(item.getItemType())).findFirst().isPresent();
            if (!wasPromotionApplied) {
                totalAfterPromotions = totalAfterPromotions.add(item.getPriceDefinition().getAmountPerUnit().multiply(item.getQuantityPerUnit())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }
        }
        return totalAfterPromotions;
    }

}
