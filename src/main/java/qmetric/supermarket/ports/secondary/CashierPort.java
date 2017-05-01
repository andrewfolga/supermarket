package qmetric.supermarket.ports.secondary;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public interface CashierPort {
    boolean credit(BigDecimal totalToPay);
}
