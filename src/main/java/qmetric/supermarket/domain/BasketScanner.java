package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.ports.primary.PromitionRepositoryPort;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class BasketScanner {

    private PromitionRepositoryPort promitionRepositoryPort;

    public BasketScanner(PromitionRepositoryPort promitionRepositoryPort) {
        this.promitionRepositoryPort = promitionRepositoryPort;
    }

    public Receipt scan(Basket basket) {
        List<Promotion> availablePromotions = promitionRepositoryPort.findPromotions(basket);
        ReceiptBuilder receiptBuilder = new ReceiptBuilder(availablePromotions, basket);
        return receiptBuilder.build();
    }
}
