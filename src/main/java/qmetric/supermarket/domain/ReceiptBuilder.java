package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    private final List<Promotion> availablePromotions;
    private final Basket basket;

    public ReceiptBuilder(List<Promotion> availablePromotions, Basket basket) {

        this.availablePromotions = availablePromotions;
        this.basket = basket;
    }

    public Receipt build() {
        BigDecimal overallPrice = BigDecimal.ZERO;
        for (Promotion promotion : availablePromotions) {
            Item item = basket.findItemForType(promotion.getItemType());
            overallPrice = overallPrice.add(promotion.apply(item));
        }
        return new Receipt(basket, overallPrice);
    }

}
