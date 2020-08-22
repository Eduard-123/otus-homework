import annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static List<Method> logMethods = new ArrayList<>();

    private static  List<Method>  getLoggedMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                logMethods.add(method);
            }
        }
        return logMethods;
    }

    public static ITestLogging create(Class<?> clazz) {
        logMethods = getLoggedMethods(clazz);
        return (ITestLogging) Proxy.newProxyInstance(TestLogging.class.getClassLoader(),
                TestLogging.class.getInterfaces(),
                new Invocation(new TestLogging()));
    }

    private static class Invocation implements InvocationHandler {
        private final ITestLogging iTestLogging;

        public Invocation(TestLogging testLogging) {
            this.iTestLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logMethods.contains(method)) {
                System.out.println("[LOG] invoking method:" + method + " with args: " + args[0]);
            }
            return method.invoke(iTestLogging, args);
        }

    }

}
