package atm;

import atm.model.Denomination;
import atm.model.Cassette;

public class Main {

    public static void main(String... args) {

        Atm cashBox = Atm.builder().addCassettes(new Cassette(Denomination.b1, 20L)).build();

        System.out.println(cashBox.getCurrentAmount());
    }

}
