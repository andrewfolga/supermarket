package qmetric.supermarket.domain;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Cashier {

    private BasketScanner basketScanner;
    private ReceiptPrinter receiptPrinter;

    public Cashier(BasketScanner basketScanner, ReceiptPrinter receiptPrinter) {
        this.basketScanner = basketScanner;
        this.receiptPrinter = receiptPrinter;
    }

    public String process(Basket basket) {
        Receipt receipt = basketScanner.scan(basket);
        String printOut = receiptPrinter.print(receipt);
        return printOut;
    }

}
