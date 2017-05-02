package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    public Receipt build(Basket basket, List<Promotion> promotions) {
        BigDecimal totalToPay = basket.calculatePromotions(promotions);
        BigDecimal totalToPayLessPromotions = basket.calculateNonPromotions();
        totalToPay = totalToPay.add(totalToPayLessPromotions);
        BigDecimal subTotal = basket.calculateSubTotal();
        return new Receipt(basket, new ReceiptSummary(subTotal, totalToPay));
    }

}
