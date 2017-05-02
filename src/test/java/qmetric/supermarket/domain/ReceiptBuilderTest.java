package qmetric.supermarket.domain;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static qmetric.supermarket.domain.ItemType.BEANS;
import static qmetric.supermarket.domain.ItemType.ORANGES;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilderTest {

    public static final BigDecimal ZERO_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    private ReceiptBuilder receiptBuilder = new ReceiptBuilder();;

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        Basket basket = new Basket(Arrays.asList());
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket);

        Assert.assertThat(receipt.getItems(), hasItems(item));
        Assert.assertThat(receipt.getPromotions(), empty());
        Assert.assertThat(receipt.getPromotionSavings().entrySet(), Matchers.hasSize(0));
        Assert.assertThat(receipt.getSubTotal(), is(equalTo(new BigDecimal("1.50"))));
        Assert.assertThat(receipt.getTotalSavings(), is(equalTo(ZERO_AMOUNT)));
        Assert.assertThat(receipt.getTotalToPay(), is(equalTo(new BigDecimal("1.50"))));
    }

    @Test
    public void shouldBuildReceiptWithPromotions() throws Exception {
        ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(BEANS);
        List<Promotion> availablePromotions = Arrays.asList(threeForTwoPromotion);
        Basket basket = new Basket(availablePromotions);
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket);

        Assert.assertThat(receipt.getItems(), hasItems(item));
        Assert.assertThat(receipt.getPromotions(), hasItems(threeForTwoPromotion));
        Assert.assertThat(receipt.getPromotionSavings(), hasEntry(threeForTwoPromotion.getPromotionType(), new BigDecimal("-0.50")));
        Assert.assertThat(receipt.getSubTotal(), is(equalTo(new BigDecimal("1.50"))));
        Assert.assertThat(receipt.getTotalSavings(), is(equalTo(new BigDecimal("-0.50"))));
        Assert.assertThat(receipt.getTotalToPay(), is(equalTo(new BigDecimal("1.00"))));
    }

    @Test
    public void shouldBuildReceiptWithPromotionsAndPriceDefinitions() throws Exception {
        ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(BEANS);
        List<Promotion> availablePromotions = Arrays.asList(threeForTwoPromotion);
        Basket basket = new Basket(availablePromotions);
        Item beans = new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3));
        basket.add(beans);
        Item oranges = new Item(ORANGES, new PriceDefinition(new BigDecimal("1.99"), Unit.KG), new BigDecimal("0.2"));
        basket.add(oranges);

        Receipt receipt = receiptBuilder.build(basket);

        Assert.assertThat(receipt.getItems(), hasItems(beans, oranges));
        Assert.assertThat(receipt.getPromotions(), hasItems(threeForTwoPromotion));
        Assert.assertThat(receipt.getPromotionSavings(), hasEntry(threeForTwoPromotion.getPromotionType(), new BigDecimal("-0.50")));
        Assert.assertThat(receipt.getSubTotal(), is(equalTo(new BigDecimal("1.90"))));
        Assert.assertThat(receipt.getTotalSavings(), is(equalTo(new BigDecimal("-0.50"))));
        Assert.assertThat(receipt.getTotalToPay(), is(equalTo(new BigDecimal("1.40"))));
    }
}