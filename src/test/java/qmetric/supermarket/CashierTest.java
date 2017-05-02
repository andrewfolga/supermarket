package qmetric.supermarket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.domain.*;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.ports.primary.PromotionRepositoryPort;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CashierTest {

    @Mock
    Basket basket;
    @Mock
    Receipt receipt;
    @Mock
    List<Promotion> promotions;
    @Mock
    private ReceiptPrinter receiptPrinter;
    @Mock
    private ReceiptBuilder receiptBuilder;
    @Mock
    private PromotionRepositoryPort promotionRepositoryPort;
    @InjectMocks
    private Cashier cashier;

    @Test
    public void shouldProduceReceipt() throws Exception {
        String receiptPrintout = "receipt";
        when(promotionRepositoryPort.findPromotions(basket)).thenReturn(promotions);
        when(receiptBuilder.build(basket, promotions)).thenReturn(receipt);
        when(receiptPrinter.print(receipt)).thenReturn(receiptPrintout);

        String result = cashier.process(basket);

        verify(promotionRepositoryPort).findPromotions(basket);
        verify(receiptPrinter).print(receipt);
        assertThat(result, is(equalTo(receiptPrintout)));
    }
}
