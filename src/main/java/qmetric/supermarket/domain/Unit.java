package qmetric.supermarket.domain;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public enum Unit {
    ITEM("%s"), KG("%s\n%5.3f kg @ £%s/kg ");

    private String format;

    Unit(String format) {
        this.format = format;
    }

    public String getDisplayFormat() {
        return format;
    }
}
