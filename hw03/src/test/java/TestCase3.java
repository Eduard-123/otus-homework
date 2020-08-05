import annotations.After;
import annotations.Before;
import annotations.Test;
import org.junit.Assert;

public class TestCase3 {

    @Before
    public void method1() {
        System.out.println("@Before-test: setting up the environment for class " + this.getClass().getName());
    }

    @Test
    public void method2() {
        Assert.assertEquals(3,3);
        System.out.println("equals");
    }

    @After
    public void method3() {
        System.out.println("@After-test: clearing the environment " + this.getClass().getName());
    }

}