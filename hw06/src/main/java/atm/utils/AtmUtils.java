package atm.utils;

import atm.model.exception.AtmException;
import atm.model.operation.Empty;

import java.util.function.Supplier;

public class AtmUtils {

    public static boolean isNullOrEmpty(Empty empty) {
        return empty == null || empty.isEmpty();
    }

    public static <T extends AtmException> void throwExceptionIfTrue(boolean check, Supplier<T> supplier) {
        if (check) throw supplier.get();
    }

}
