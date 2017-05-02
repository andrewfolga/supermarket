package qmetric.supermarket.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;
import qmetric.supermarket.domain.promotion.TwoForPricePromotion;
import qmetric.supermarket.ports.primary.PromitionRepositoryPort;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static qmetric.supermarket.domain.ItemType.*;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BasketScannerTest {

    @Mock
    private Basket basket;
    @Mock
    private Receipt receipt;
    @Mock
    private ReceiptBuilder receiptBuilder;
    @InjectMocks
    private BasketScanner basketScanner;

    @Before
    public void setUp() throws Exception {
        basketScanner = new BasketScanner(receiptBuilder);
    }

    @Test
    public void shouldScan() throws Exception {
        Mockito.when(receiptBuilder.build(basket)).thenReturn(receipt);

        Receipt receipt = basketScanner.scan(basket);

        Assert.assertThat(receipt, is(equalTo(receipt)));
    }

}