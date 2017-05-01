package qmetric.supermarket.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import qmetric.supermarket.domain.promotion.Promotion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static qmetric.supermarket.domain.ItemType.BEANS;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilderTest {

    private ReceiptBuilder receiptBuilder;

    private static final String BASIC_RECEIPT = "BEANS           0.50\n" +
            "BEANS           0.50\n" +
            "BEANS           0.50\n" +
            "Sub-total       0.00\n" +
            "Savings\n" +
            "Total savings   0.00\n" +
            "--------------------\n" +
            "Total to Pay    0.00\n";

    @Test
    public void shouldBuildBasicReceipt() throws Exception {

        List<Promotion> availablePromotions = Arrays.asList();
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new BigDecimal("0.5"), new BigDecimal(3)));
        receiptBuilder = new ReceiptBuilder(availablePromotions, basket);
        Receipt receipt = receiptBuilder.build();

        String receiptPrintOut = receipt.print();
        System.out.println(receiptPrintOut);
        Assert.assertThat(receiptPrintOut, is(BASIC_RECEIPT));
    }
}