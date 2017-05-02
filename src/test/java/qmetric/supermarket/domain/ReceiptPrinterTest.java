package qmetric.supermarket.domain;

import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.ReceiptPrinter;
import qmetric.supermarket.domain.*;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static qmetric.supermarket.domain.ItemType.BEANS;
import static qmetric.supermarket.domain.ItemType.ORANGES;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptPrinterTest {

    private ReceiptPrinter receiptPrinterAdapter = new ReceiptPrinter();

    private static final String BASIC_RECEIPT =
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Sub-total             1.50\n" +
            "Savings\n" +
            "Total savings         0.00\n" +
            "--------------------------\n" +
            "Total to Pay          1.50\n";

    private static final String PROMOTION_RECEIPT =
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Sub-total             1.50\n" +
            "Savings\n" +
            "Beans 3 for 2        -0.50\n" +
            "Total savings        -0.50\n" +
            "--------------------------\n" +
            "Total to Pay          1.00\n";

    private static final String PROMOTION_AND_KG_PRICE_DEFINITION_RECEIPT =
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Oranges\n" +
            "0.200 kg @ £1.99/kg   0.40\n" +
            "Sub-total             1.90\n" +
            "Savings\n" +
            "Beans 3 for 2        -0.50\n" +
            "Total savings        -0.50\n" +
            "--------------------------\n" +
            "Total to Pay          1.40\n";

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3)));
        Receipt receipt = new Receipt(basket, new ReceiptSummary(new BigDecimal("1.5"), new BigDecimal("1.5")));

        String receiptPrintout = receiptPrinterAdapter.print(receipt);

        Assert.assertThat(receiptPrintout, is(BASIC_RECEIPT));
    }

    @Test
    public void shouldBuildReceiptWithPromotions() throws Exception {
        Basket basket = new Basket();
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);
        basket.calculatePromotions(Arrays.asList(new ThreeForTwoPromotion(BEANS)));
        Receipt receipt = new Receipt(basket, new ReceiptSummary(new BigDecimal("1.5"), new BigDecimal("1.0")));

        String receiptPrintout = receiptPrinterAdapter.print(receipt);

        Assert.assertThat(receiptPrintout, is(PROMOTION_RECEIPT));
    }

    @Test
    public void shouldBuildReceiptWithPromotionsAndPriceDefinitions() throws Exception {

        ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(BEANS);
        Basket basket = new Basket();
        Item beans = new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3));
        basket.add(beans);
        Item oranges = new Item(ORANGES, new PriceDefinition(new BigDecimal("1.99"), Unit.KG), new BigDecimal("0.2"));
        basket.add(oranges);
        basket.calculatePromotions(Arrays.asList(threeForTwoPromotion));
        Receipt receipt = new Receipt(basket, new ReceiptSummary(new BigDecimal("1.90"), new BigDecimal("1.40")));

        String receiptPrintout = receiptPrinterAdapter.print(receipt);

        Assert.assertThat(receiptPrintout, is(PROMOTION_AND_KG_PRICE_DEFINITION_RECEIPT));
    }
}