package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    public Receipt build(Basket basket) {
        BigDecimal totalToPay = basket.calculatePromotions();
        BigDecimal afterPromotionsSubTotal = basket.calculateRemainder();
        totalToPay = totalToPay.add(afterPromotionsSubTotal);
        BigDecimal subTotal = basket.calculateSubTotal();
        return new Receipt(basket, new ReceiptSummary(subTotal, totalToPay));
    }

}
