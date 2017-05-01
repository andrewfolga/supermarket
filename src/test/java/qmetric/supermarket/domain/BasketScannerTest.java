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
import qmetric.supermarket.ports.primary.PromitionRepositoryPort;

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
public class BasketScannerTest {

    public static final PriceDefinition HALF_POUND_PER_ITEM = new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM);
    public static final PriceDefinition SEVENTY_PENCE_PER_ITEM = new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM);
    public static final PriceDefinition POUND_NINETY_NINE_PENCE_PER_KG = new PriceDefinition(new BigDecimal("1.99"), Unit.KG);
    @Mock
    private PromitionRepositoryPort promitionRepositoryPort;
    @InjectMocks
    private BasketScanner basketScanner;

    @Before
    public void setUp() throws Exception {
        basketScanner = new BasketScanner(promitionRepositoryPort);
    }

    @Test
    public void shouldApplyThreeForTwoPromotion() throws Exception {
        Basket basket = new Basket(Arrays.asList(new ThreeForTwoPromotion(BEANS)));
        basket.add(new Item(BEANS, HALF_POUND_PER_ITEM, new BigDecimal(3)));
        basket.add(new Item(BEANS, HALF_POUND_PER_ITEM, new BigDecimal(3)));
        basket.add(new Item(BEANS, HALF_POUND_PER_ITEM, BigDecimal.ONE));

        Receipt receipt = basketScanner.scan(basket);

        assertThat(receipt.getTotalToPay(), is(new BigDecimal("2.50")));
    }

    @Test
    public void shouldNotApplyThreeForTwoPromotionIfNoQuantity() throws Exception {
        Basket basket = new Basket(Arrays.asList(new ThreeForTwoPromotion(BEANS)));
        basket.add(new Item(BEANS, HALF_POUND_PER_ITEM, new BigDecimal(2)));

        Receipt receipt = basketScanner.scan(basket);

        assertThat(receipt.getTotalToPay(), is(new BigDecimal("1.00")));
    }

    @Test
    public void shouldApplyTwoForPricePromotion() throws Exception {
        Basket basket = new Basket(Arrays.asList(new TwoForPricePromotion(COKE, new BigDecimal("1.0"))));
        basket.add(new Item(COKE, SEVENTY_PENCE_PER_ITEM, new BigDecimal(2)));
        basket.add(new Item(COKE, SEVENTY_PENCE_PER_ITEM, new BigDecimal(2)));
        basket.add(new Item(COKE, SEVENTY_PENCE_PER_ITEM, BigDecimal.ONE));

        Receipt receipt = basketScanner.scan(basket);

        assertThat(receipt.getTotalToPay(), is(new BigDecimal("2.70")));
    }

    @Test
    public void shouldNotApplyTwoForPricePromotionIfNoQuantity() throws Exception {
        Basket basket = new Basket(Arrays.asList(new TwoForPricePromotion(COKE, new BigDecimal("1.0"))));
        basket.add(new Item(COKE, SEVENTY_PENCE_PER_ITEM, BigDecimal.ONE));

        Receipt receipt = basketScanner.scan(basket);

        assertThat(receipt.getTotalToPay(), is(new BigDecimal("0.70")));
    }

    @Test
    public void shouldApplyWeightForPriceDefinition() throws Exception {
        Basket basket = new Basket(Arrays.asList());
        basket.add(new Item(ORANGES, POUND_NINETY_NINE_PENCE_PER_KG, new BigDecimal("0.20")));

        Receipt receipt = basketScanner.scan(basket);

        assertThat(receipt.getTotalToPay(), is(new BigDecimal("0.40")));
    }
}