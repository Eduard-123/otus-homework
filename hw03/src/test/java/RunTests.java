import org.junit.runners.model.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

    public static void main(String[] args) throws Exception {
        Class<?> testClass1 = TestCase1.class;
        Class<?> testClass2 = TestCase2.class;
        Class<?> testClass3 = TestCase3.class;

        TestRunner runner = new TestRunner();
        runner.testRun(testClass1,testClass2,testClass3);
    }

}
