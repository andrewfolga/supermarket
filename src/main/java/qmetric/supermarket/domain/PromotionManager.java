package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.ports.PromitionRepositoryPort;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class PromotionManager {

    private PromitionRepositoryPort promitionRepositoryPort;

    public PromotionManager(PromitionRepositoryPort promitionRepositoryPort) {
        this.promitionRepositoryPort = promitionRepositoryPort;
    }

    public Receipt applyPromotions(Basket basket) {
        List<Promotion> availablePromotions = promitionRepositoryPort.findPromotions(basket);
        ReceiptBuilder receiptBuilder = new ReceiptBuilder(availablePromotions, basket);
        return receiptBuilder.build();
    }
}
