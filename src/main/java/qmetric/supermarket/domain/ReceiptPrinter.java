package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.math.BigDecimal;
import java.util.Formatter;

import static qmetric.supermarket.domain.Unit.ITEM;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class ReceiptPrinter {

    public static final String TEMPLATE = "%-20s %5.2f\n";

    public String print(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        sb.append(printSubTotal(receipt));
        formatter.format(TEMPLATE, "Sub-total", receipt.getSubTotal());
        sb.append("Savings\n");
        sb.append(printSavings(receipt));
        formatter.format(TEMPLATE, "Total savings", receipt.getTotalSavings());
        sb.append("--------------------------\n");
        formatter.format(TEMPLATE, "Total to Pay", receipt.getTotalToPay());
        return sb.toString();
    }

    private String printSubTotal(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        for (Item item : receipt.getItems()) {
            BigDecimal quantity = item.getQuantityPerUnit();
            Unit itemUnit = item.getPriceDefinition().getUnit();
            BigDecimal itemAmountPerUnit = item.getPriceDefinition().getAmountPerUnit();
            if (itemUnit == ITEM) {
                for (int i = 0; i < quantity.intValue(); i++) {
                    formatter.format(TEMPLATE, item.getItemType().getName(), itemAmountPerUnit);
                }
            } else {
                BigDecimal itemSubTotal = itemAmountPerUnit.multiply(quantity);
                formatter.format(TEMPLATE,
                        String.format(itemUnit.getDisplayFormat(), item.getItemType().getName(), quantity, itemAmountPerUnit),
                        itemSubTotal);
            }
        }
        return sb.toString();
    }

    private String printSavings(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        for (Promotion promotion : receipt.getPromotions()) {
            formatter.format(TEMPLATE, promotion.getDescription(), receipt.getPromotionSavings().get(promotion.getPromotionType()));
        }
        return sb.toString();
    }

}
