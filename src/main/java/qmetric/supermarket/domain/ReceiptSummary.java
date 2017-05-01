package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptSummary {

    private final BigDecimal subTotal;
    private final BigDecimal totalSavings;
    private final BigDecimal totalToPay;

    public ReceiptSummary(BigDecimal subTotal, BigDecimal totalToPay) {
        this.subTotal = subTotal;
        this.totalToPay = totalToPay;
        this.totalSavings = totalToPay.subtract(subTotal);
    }

    public BigDecimal getTotalToPay() {
        return totalToPay;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public BigDecimal getTotalSavings() {
        return totalSavings;
    }
}
