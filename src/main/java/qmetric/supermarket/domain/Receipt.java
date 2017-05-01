package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.Formatter;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Receipt {
    public static final String TEMPLATE = "%-15s %-4.2f\n";

    private final Basket basket;
    private final BigDecimal finalPrice;

    public Receipt(Basket basket, BigDecimal finalPrice) {
        this.basket = basket;
        this.finalPrice = finalPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        sb.append(basket.toString());
        formatter.format(TEMPLATE, "Sub-total", finalPrice);
        sb.append("Savings\n");
        formatter.format(TEMPLATE, "Total savings", BigDecimal.ZERO);
        sb.append("--------------------\n");
        formatter.format(TEMPLATE, "Total to Pay", finalPrice);
        return sb.toString();
    }
}
