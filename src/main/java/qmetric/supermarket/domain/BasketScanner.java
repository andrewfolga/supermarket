package qmetric.supermarket.domain;

import qmetric.supermarket.ports.primary.PromitionRepositoryPort;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class BasketScanner {

    private PromitionRepositoryPort promitionRepositoryPort;
    private ReceiptBuilder receiptBuilder;

    public BasketScanner(PromitionRepositoryPort promitionRepositoryPort, ReceiptBuilder receiptBuilder) {
        this.promitionRepositoryPort = promitionRepositoryPort;
        this.receiptBuilder = receiptBuilder;
    }

    public Receipt scan(Basket basket) {
        return receiptBuilder.build(basket);
    }
}
