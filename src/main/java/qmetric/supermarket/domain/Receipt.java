package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Receipt {

    private final List<Item> items;
    private final List<Promotion> promotions;
    private final Map<PromotionType, BigDecimal> promotionSavings;
    private final ReceiptSummary receiptSummary;

    public Receipt(Basket basket, ReceiptSummary receiptSummary) {
        this.items = basket.getItems();
        this.promotions = basket.getPromotions();
        this.promotionSavings = basket.getSavings();
        this.receiptSummary = receiptSummary;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public Map<PromotionType, BigDecimal> getPromotionSavings() {
        return promotionSavings;
    }

    public ReceiptSummary getReceiptSummary() {
        return receiptSummary;
    }

    public BigDecimal getSubTotal() {
        return receiptSummary.getSubTotal();
    }

    public BigDecimal getTotalSavings() {
        return receiptSummary.getTotalSavings();
    }

    public BigDecimal getTotalToPay() {
        return receiptSummary.getTotalToPay();
    }
}