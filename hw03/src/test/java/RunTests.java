import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

    public static void main(String[] args) throws Exception {
        Class<?> testClass = TestCase.class;
        Class<?> aClass = TestRunner.class;
        TestRunner runner = (TestRunner) aClass.getConstructor().newInstance();
        Method method = aClass.getDeclaredMethod("testRun", Class.class);
        method.setAccessible(true);
        try {
            method.invoke(runner, testClass);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
