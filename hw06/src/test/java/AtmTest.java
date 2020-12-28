import atm.ATM;
import atm.Cell;
import atm.Denomination;
import atm.exception.AtmException;
import atm.operation.WithdrawStrategy;
import atm.operation.WithdrawStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmTest {

    private ATM testATM;

    @BeforeEach
    public void setup() {
        List<Cell> cells = List.of(
                new Cell(Denomination.b1, 1),
                new Cell(Denomination.b2, 2),
                new Cell(Denomination.b5, 3),
                new Cell(Denomination.b50, 4),
                new Cell(Denomination.b100, 5),
                new Cell(Denomination.b500, 6),
                new Cell(Denomination.b1000, 7),
                new Cell(Denomination.b5000, 8)
        );
        testATM = new ATM(cells);
    }

    @Test
    public void checkAtmBalance() {
        assertEquals(50720, testATM.getATMBalance());
    }

    @Test
    public void insertMoney() {
        Map<Denomination, Long> payment = Map.of(Denomination.b1000, 2L);
        assertEquals(50720, testATM.getATMBalance());
        testATM.putMoney(payment);
        assertEquals(52720, testATM.getATMBalance());
    }

    @Test
    public void withdrawMoney() throws AtmException {
        WithdrawStrategy withdrawStrategy = new WithdrawStrategyImpl();
        assertEquals(50720, testATM.getATMBalance());
        Map<Denomination, Long> money = testATM.withdrawMoney(withdrawStrategy, 12700);
        assertEquals(38020, testATM.getATMBalance());
        assertEquals(Map.of(Denomination.b5000, 2L, Denomination.b1000, 2L, Denomination.b500, 1L, Denomination.b100, 2L), money);

        Exception exceptionFirst = assertThrows(AtmException.class,
                () -> testATM.withdrawMoney(withdrawStrategy, 10543));
        assertEquals("Запрошенная сумма не может быть выдана", exceptionFirst.getMessage());
        assertEquals(38020, testATM.getATMBalance());
    }

    @Test
    public void withdrawMoneyFailed() {
        WithdrawStrategy withdrawStrategy = new WithdrawStrategyImpl();

        Exception exception = assertThrows(AtmException.class, () -> testATM.withdrawMoney(withdrawStrategy, 100000));
        assertEquals("Запрошенная сумма не может быть выдана", exception.getMessage());
        assertEquals(50720, testATM.getATMBalance());
    }

}
