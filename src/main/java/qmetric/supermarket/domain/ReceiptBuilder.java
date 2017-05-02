package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    public Receipt build(Basket basket) {
        BigDecimal totalToPay = basket.calculatePromotions();
        BigDecimal totalToPayLessPromotions = basket.calculateNonPromotions();
        totalToPay = totalToPay.add(totalToPayLessPromotions);
        BigDecimal subTotal = basket.calculateSubTotal();
        return new Receipt(basket, new ReceiptSummary(subTotal, totalToPay));
    }

}
