package atm.model;

import java.util.Optional;

public enum Denomination {
    b1(1),
    b2(2),
    b5(5),
    b10(10),
    b50(50),
    b100(100),
    b500(500),
    b1000(1000),
    b5000(5000);

    private final int amount;

    Denomination(int amount) {
        this.amount = amount;
    }

    public static int compare(Denomination d1, Denomination d2) {
        return Integer.compare(getAmount(d1), getAmount(d2));
    }

    private static int getAmount(Denomination d) {
        return d == null ? -1 : d.amount;
    }

    public Long getAmount() {
        return (long) amount;
    }

    public static Denomination fromName(String name) {
        for (Denomination denomination : Denomination.values()) {
            if (denomination.name().equals(name)) {
                return denomination;
            }
        }
        return null;
    }
}
