package qmetric.supermarket.domain;

import qmetric.supermarket.ports.primary.PromitionRepositoryPort;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class BasketScanner {

    private ReceiptBuilder receiptBuilder;

    public BasketScanner(ReceiptBuilder receiptBuilder) {
        this.receiptBuilder = receiptBuilder;
    }

    public Receipt scan(Basket basket) {
        return receiptBuilder.build(basket);
    }
}
