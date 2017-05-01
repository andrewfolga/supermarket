package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class WeightForPricePromotion extends Promotion {

    public WeightForPricePromotion(ItemType itemType, BigDecimal price) {
        super(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(price), itemType);
    }

    @Override
    public PromotionType getPromotionType() {
        return PromotionType.WEIGHT_FOR_PRICE;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.ORANGES;
    }

}
