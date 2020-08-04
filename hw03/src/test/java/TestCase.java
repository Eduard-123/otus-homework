import annotations.After;
import annotations.Before;
import annotations.Test;
import org.junit.Assert;

import java.util.Arrays;

public class TestCase {
    @Before
    public void method1() {
        System.out.println("@Before-test: setting up the environment.");
    }

    @Before
    public void method2() {
        Assert.assertTrue(true);
    }

    @Test
    public void method3() {
        Assert.assertEquals(42, Integer.sum(21, 21));
    }

    @Test
    public void method4() {
        Assert.assertFalse(false);
    }

    @After
    public void method5() {
        Assert.assertTrue(Boolean.getBoolean("true"));
    }

    @After
    public void method6() {
        System.out.println("@After-test: clearing the environment.");
    }
}
