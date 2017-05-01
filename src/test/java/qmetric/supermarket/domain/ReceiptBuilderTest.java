package qmetric.supermarket.domain;

import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static qmetric.supermarket.domain.ItemType.BEANS;
import static qmetric.supermarket.domain.ItemType.ORANGES;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilderTest {

    private ReceiptBuilder receiptBuilder;

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

    private static final String PROMOTION_AND_PRICE_DEFINITION_RECEIPT =
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

        List<Promotion> availablePromotions = Arrays.asList();
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3)));

        receiptBuilder = new ReceiptBuilder(availablePromotions, basket);
        Receipt receipt = receiptBuilder.build();

        String receiptPrintOut = receipt.printOut();
        Assert.assertThat(receiptPrintOut, is(BASIC_RECEIPT));
    }

    @Test
    public void shouldBuildReceiptWithPromotions() throws Exception {

        List<Promotion> availablePromotions = Arrays.asList(new ThreeForTwoPromotion(BEANS));
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3)));
        receiptBuilder = new ReceiptBuilder(availablePromotions, basket);
        Receipt receipt = receiptBuilder.build();

        String receiptPrintOut = receipt.printOut();
        System.out.println(receiptPrintOut);
        Assert.assertThat(receiptPrintOut, is(PROMOTION_RECEIPT));
    }

    @Test
    public void shouldBuildReceiptWithPromotionsAndPriceDefinitions() throws Exception {

        List<Promotion> availablePromotions = Arrays.asList(new ThreeForTwoPromotion(BEANS));
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3)));
        basket.add(new Item(ORANGES, new PriceDefinition(new BigDecimal("1.99"), Unit.KG), new BigDecimal("0.2")));
        receiptBuilder = new ReceiptBuilder(availablePromotions, basket);
        Receipt receipt = receiptBuilder.build();

        String receiptPrintOut = receipt.printOut();

        Assert.assertThat(receiptPrintOut, is(PROMOTION_AND_PRICE_DEFINITION_RECEIPT));
    }
}