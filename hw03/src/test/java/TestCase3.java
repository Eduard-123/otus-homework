import annotations.After;
import annotations.Before;
import annotations.Test;
import org.junit.Assert;

public class TestCase3 {

    @Test
    public void method1() {
        Assert.assertEquals("1","2");
    }

    @Test
    public void method2() {
        Assert.assertEquals(3,3);
    }

    @Test
    public void method3() {
        Assert.fail();
    }

}