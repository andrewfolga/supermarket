package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class TwoForPricePromotion extends Promotion {

    private static final BigDecimal TRIGGER_QUANTITY = new BigDecimal(2);
    private static final BigDecimal TARGET_QUANTITY = new BigDecimal(1);

    public TwoForPricePromotion(ItemType itemType, BigDecimal price) {
        super(Optional.of(TRIGGER_QUANTITY), Optional.of(TARGET_QUANTITY), Optional.empty(), Optional.of(price), itemType);
    }

    @Override
    public PromotionType getPromotionType() {
        return PromotionType.TWO_FOR_PRICE;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.COKE;
    }

}
