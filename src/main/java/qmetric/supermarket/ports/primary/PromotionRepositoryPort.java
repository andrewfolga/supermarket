package qmetric.supermarket.ports.primary;

import qmetric.supermarket.domain.Basket;
import qmetric.supermarket.domain.promotion.Promotion;

import java.util.Collection;
import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public interface PromotionRepositoryPort {

    Collection<Promotion> getPromotions();

    List<Promotion> findPromotions(Basket basket);
}
