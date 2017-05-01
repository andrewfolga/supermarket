package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Receipt {
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
        sb.append(basket.toString());
        sb.append("\t\t\t\t---\n");
        sb.append("Sub-total\t\t");
        sb.append(finalPrice);
        sb.append("\n\n");
        sb.append("Savings");
        sb.append("Total savings\t\t\t");
        sb.append(BigDecimal.ZERO);
        sb.append("-----------------------");
        sb.append("Total to Pay\t\t\t");
        sb.append(finalPrice);
        return sb.toString();
    }
}
