package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.ports.primary.PromotionRepositoryPort;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Cashier {

    private PromotionRepositoryPort promotionRepositoryPort;
    private ReceiptBuilder receiptBuilder;
    private ReceiptPrinter receiptPrinterPort;

    public Cashier(PromotionRepositoryPort promotionRepositoryPort, ReceiptBuilder receiptBuilder, ReceiptPrinter receiptPrinter) {
        this.promotionRepositoryPort = promotionRepositoryPort;
        this.receiptBuilder = receiptBuilder;
        this.receiptPrinterPort = receiptPrinter;
    }

    public String process(Basket basket) {
        List<Promotion> promotions = promotionRepositoryPort.findPromotions(basket);
        Receipt receipt = receiptBuilder.build(basket, promotions);
        return receiptPrinterPort.print(receipt);
    }

}
