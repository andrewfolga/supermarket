package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public abstract class Promotion {

    protected static final String FORMAT_PROMOTION_DISPLAY = "%-20s";

    protected final Optional<BigDecimal> triggerQuantity;
    protected final Optional<BigDecimal> targetQuantity;
    protected final Optional<BigDecimal> triggerPrice;
    protected final Optional<BigDecimal> targetPrice;
    private final ItemType itemType;

    public Promotion(Optional<BigDecimal> triggerQuantity, Optional<BigDecimal> targetQuantity, Optional<BigDecimal> triggerPrice, Optional<BigDecimal> targetPrice, ItemType itemType) {
        this.triggerQuantity = triggerQuantity;
        this.targetQuantity = targetQuantity;
        this.triggerPrice = triggerPrice;
        this.targetPrice = targetPrice;
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getDescription() {
        return String.format(FORMAT_PROMOTION_DISPLAY, getItemType().getName()+ " " + getPromotionType().getDisplayFormat());
    }

    public BigDecimal apply(Item item) {
        BigDecimal price = item.getPriceDefinition().getAmountPerUnit().multiply(item.getQuantity()).setScale(2);
        BigDecimal itemQuantity = item.getQuantity();
        if (!triggerQuantity.isPresent() || itemQuantity.compareTo(triggerQuantity.get()) >= 0) {
            int itemQuantityValue = itemQuantity.intValue();
            BigDecimal applyTimes = triggerQuantity.isPresent() ?
                    new BigDecimal(itemQuantityValue / triggerQuantity.get().intValue()) : BigDecimal.ONE;
            BigDecimal applyReminder = triggerQuantity.isPresent() ?
                    new BigDecimal(itemQuantityValue % triggerQuantity.get().intValue()) : BigDecimal.ZERO;

            price = getPromotionPrice(item).multiply(getPromotionQuantity(item)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

            price = price.multiply(applyTimes);
            price = price.add(item.getPriceDefinition().getAmountPerUnit().multiply(applyReminder));
        }
        return price;
    }

    private BigDecimal getPromotionPrice(Item item) {
        return targetPrice.orElseGet(() -> item.getPriceDefinition().getAmountPerUnit());
    }

    public BigDecimal getPromotionQuantity(Item item) {
        return targetQuantity.orElseGet(() -> item.getQuantity());
    }

    public abstract PromotionType getPromotionType();
}
