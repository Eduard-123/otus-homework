import annotations.After;
import annotations.Before;
import annotations.Test;
import org.junit.Assert;

public class TestCase2 {

    @Test
    public void method1() {
       Assert.assertEquals(1,2);
    }

    @Test
    public void method2() {
        Assert.assertEquals(42, Integer.sum(21, 25));
    }
    @Test
    public void method3() {
       Assert.assertEquals("String 1", "String 2");
    }
}
