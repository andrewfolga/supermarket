package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.Formatter;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Receipt {
    public static final String TEMPLATE = "%-20s %5.2f\n";

    private final Basket basket;
    private final BigDecimal subTotal;
    private final BigDecimal totalSavings;
    private final BigDecimal totalToPay;

    public Receipt(Basket basket, BigDecimal subTotal, BigDecimal totalToPay) {
        this.basket = basket;
        this.subTotal = subTotal;
        this.totalToPay = totalToPay;
        this.totalSavings = totalToPay.subtract(subTotal);
    }

    public BigDecimal getTotalToPay() {
        return totalToPay;
    }

    public String printOut() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        sb.append(basket.printAllItemsNoPromotions());
        formatter.format(TEMPLATE, "Sub-total", subTotal);
        sb.append("Savings\n");
        sb.append(basket.printSavings());
        formatter.format(TEMPLATE, "Total savings", totalSavings);
        sb.append("--------------------------\n");
        formatter.format(TEMPLATE, "Total to Pay", totalToPay);
        return sb.toString();
    }
}
