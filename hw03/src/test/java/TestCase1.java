import annotations.After;
import annotations.Before;
import annotations.Test;
import org.junit.Assert;

import java.util.Arrays;

public class TestCase1 {
    @Before
    public void method1() {
        System.out.println("@Before-test: setting up the environment for class " + this.getClass().getName());
    }

    @Test
    public void method2() {
        Assert.assertEquals(42, Integer.sum(21, 21));
        System.out.println("equals");
    }

    @After
    public void method3() {
        System.out.println("@After-test: clearing the environment " + this.getClass().getName());
    }
}
