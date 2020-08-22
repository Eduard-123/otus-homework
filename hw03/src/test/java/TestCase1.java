import annotations.After;
import annotations.Before;
import annotations.Test;
import org.junit.Assert;

import java.util.Arrays;

public class TestCase1 {
    @Test
    public void method1() {
        Assert.assertEquals(this, new TestCase1());
    }

    @Test
    public void method2() {
        Assert.assertEquals(42, Integer.sum(21, 21));
    }

    @Test
    public void method3() {
        Assert.assertNull(null);
    }
}
