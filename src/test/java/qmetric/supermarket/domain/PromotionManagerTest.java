package qmetric.supermarket.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;
import qmetric.supermarket.domain.promotion.TwoForPricePromotion;
import qmetric.supermarket.domain.promotion.WeightForPricePromotion;
import qmetric.supermarket.ports.PromitionRepositoryPort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static qmetric.supermarket.domain.ItemType.*;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PromotionManagerTest {

    @Mock
    private PromitionRepositoryPort promitionRepositoryPort;

    @InjectMocks
    private PromotionManager promotionManager;

    @Before
    public void setUp() throws Exception {
        promotionManager = new PromotionManager(promitionRepositoryPort);
    }

    @Test
    public void shouldApplyThreeForTwoPromotion() throws Exception {
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new BigDecimal("0.5"), new BigDecimal(3)));
        basket.add(new Item(BEANS, new BigDecimal("0.5"), new BigDecimal(3)));
        basket.add(new Item(BEANS, new BigDecimal("0.5"), BigDecimal.ONE));
        List<Promotion> promotions = Arrays.asList(new ThreeForTwoPromotion(BEANS));
        Mockito.when(promitionRepositoryPort.findPromotions(basket)).thenReturn(promotions);

        Receipt receipt = promotionManager.applyPromotions(basket);

        assertThat(receipt.getFinalPrice(), is(new BigDecimal("2.50")));
    }

    @Test
    public void shouldNotApplyThreeForTwoPromotionIfNoQuantity() throws Exception {
        Basket basket = new Basket();
        basket.add(new Item(BEANS, new BigDecimal("0.5"), new BigDecimal(2)));
        List<Promotion> promotions = Arrays.asList(new ThreeForTwoPromotion(BEANS));
        Mockito.when(promitionRepositoryPort.findPromotions(basket)).thenReturn(promotions);

        Receipt receipt = promotionManager.applyPromotions(basket);

        assertThat(receipt.getFinalPrice(), is(new BigDecimal("1.00")));
    }

    @Test
    public void shouldApplyTwoForPricePromotion() throws Exception {
        Basket basket = new Basket();
        basket.add(new Item(COKE, new BigDecimal("0.7"), new BigDecimal(2)));
        basket.add(new Item(COKE, new BigDecimal("0.7"), new BigDecimal(2)));
        basket.add(new Item(COKE, new BigDecimal("0.7"), BigDecimal.ONE));
        List<Promotion> promotions = Arrays.asList(new TwoForPricePromotion(COKE, new BigDecimal("1.0")));
        Mockito.when(promitionRepositoryPort.findPromotions(basket)).thenReturn(promotions);

        Receipt receipt = promotionManager.applyPromotions(basket);

        assertThat(receipt.getFinalPrice(), is(new BigDecimal("2.70")));
    }

    @Test
    public void shouldNotApplyTwoForPricePromotionIfNoQuantity() throws Exception {
        Basket basket = new Basket();
        basket.add(new Item(COKE, new BigDecimal("0.7"), BigDecimal.ONE));
        List<Promotion> promotions = Arrays.asList(new TwoForPricePromotion(COKE, new BigDecimal("1.0")));
        Mockito.when(promitionRepositoryPort.findPromotions(basket)).thenReturn(promotions);

        Receipt receipt = promotionManager.applyPromotions(basket);

        assertThat(receipt.getFinalPrice(), is(new BigDecimal("0.70")));
    }


    @Test
    public void shouldApplyWeightForPricePromotion() throws Exception {
        Basket basket = new Basket();
        basket.add(new Item(ORANGES, new BigDecimal("0.2"), new BigDecimal("0.2")));
        List<Promotion> promotions = Arrays.asList(new WeightForPricePromotion(ORANGES, new BigDecimal("1.99")));
        Mockito.when(promitionRepositoryPort.findPromotions(basket)).thenReturn(promotions);

        Receipt receipt = promotionManager.applyPromotions(basket);

        assertThat(receipt.getFinalPrice(), is(new BigDecimal("0.40")));
    }
}