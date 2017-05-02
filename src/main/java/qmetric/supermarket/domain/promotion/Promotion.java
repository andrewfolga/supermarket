package qmetric.supermarket.domain.promotion;

import org.apache.commons.lang3.Validate;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public abstract class Promotion {

    protected static final String FORMAT_PROMOTION_DISPLAY = "%-20s";

    protected final BigDecimal triggerQuantity;
    protected final Optional<BigDecimal> targetQuantity;
    protected final Optional<BigDecimal> targetPrice;
    private final ItemType itemType;

    public Promotion(BigDecimal triggerQuantity, Optional<BigDecimal> targetQuantity, Optional<BigDecimal> targetPrice, ItemType itemType) {
        Validate.notNull(triggerQuantity, "Trigger quantity must be provided");
        Validate.isTrue(targetQuantity.isPresent() || targetPrice.isPresent(), "Target quantity or target price must be provided");
        Validate.isTrue(triggerQuantity.compareTo(targetQuantity.get())<0, "Trigger quantity must greater than target quantity");
        this.triggerQuantity = triggerQuantity;
        this.targetQuantity = targetQuantity;
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
        BigDecimal priceToPay = BigDecimal.ZERO;
        BigDecimal itemQuantity = item.getQuantityPerUnit();
        if (item.getItemType().equals(itemType) && itemQuantity.compareTo(triggerQuantity) >= 0) {
            BigDecimal applyTimes = itemQuantity.divide(triggerQuantity, 0, BigDecimal.ROUND_DOWN);
            BigDecimal applyReminder = itemQuantity.remainder(triggerQuantity);

            priceToPay = getPromotionPrice(item).multiply(getPromotionQuantity(item));

            priceToPay = priceToPay.multiply(applyTimes);
            priceToPay = priceToPay.add(item.getPriceDefinition().getAmountPerUnit().multiply(applyReminder));
        }
        return priceToPay.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private BigDecimal getPromotionPrice(Item item) {
        return targetPrice.orElseGet(() -> item.getPriceDefinition().getAmountPerUnit());
    }

    public BigDecimal getPromotionQuantity(Item item) {
        return targetQuantity.orElseGet(() -> BigDecimal.ONE);
    }

    public abstract PromotionType getPromotionType();
}
