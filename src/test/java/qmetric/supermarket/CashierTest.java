package qmetric.supermarket;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.domain.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
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
    private BasketScanner basketScanner;
    @Mock
    private ReceiptPrinter receiptPrinter;
    @InjectMocks
    private Cashier cashier;

    @Test
    public void shouldProduceReceipt() throws Exception {
        when(basketScanner.scan(basket)).thenReturn(receipt);
        String receiptPrintout = "receipt";
        when(receiptPrinter.print(receipt)).thenReturn(receiptPrintout);

        String result = cashier.process(basket);

        verify(basketScanner).scan(basket);
        verify(receiptPrinter).print(receipt);
        assertThat(result, is(equalTo(receiptPrintout)));
    }
}
