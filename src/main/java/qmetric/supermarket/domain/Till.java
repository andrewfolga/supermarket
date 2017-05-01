package qmetric.supermarket.domain;

import qmetric.supermarket.ports.secondary.CashierPort;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Till {

    private BasketScanner basketScanner;
    private CashierPort cashierPort;

    public String processCustomer(Customer customer, Basket basket) {
        Receipt receipt = basketScanner.scan(basket);
        cashierPort.credit(receipt.getReceiptSummary().getTotalToPay());
        customer.debit(receipt.getReceiptSummary().getTotalToPay());
        return new ReceiptPrinter(receipt).print();
    }

}
