import annotations.After;
import annotations.Before;
import annotations.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


public class TestFramework {

    private TestResults testResults = new TestResults();

    public TestFramework() {
        runBeforeSteps();
    }

    public void run() {
        int totalClasses = 0;
        for (Class<?> clazz : getTestClasses()) {
            totalClasses++;
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        method.invoke(clazz.getConstructor().newInstance());
                        testResults.setSuccessCount();
                    } catch (Exception exception) {
                        testResults.setFailedCount();
                    }
                }
            }
            if (totalClasses == getTestClasses().size() - 1) {
                runAfterSteps();
            }
        }
    }

    private Set<Class<?>> getTestClasses() {

        final ConfigurationBuilder config = new ConfigurationBuilder()
                .setScanners(new ResourcesScanner(), new MethodAnnotationsScanner(), new SubTypesScanner(false))
                .setUrls(ClasspathHelper.forPackage(this.getClass().getPackageName()));

        final Reflections reflect = new Reflections(config);

        Set<Class<?>> testClasses = new HashSet<>();

        reflect.getSubTypesOf(Object.class).forEach(clazz -> {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    testClasses.add(clazz);
                }
            }
        });

        return testClasses;
    }

    private void runBeforeSteps() {
        try {
            Class<?> clazz = Class.forName("BeforeAndAfterSteps");
            Object beforeAndAfterSteps = clazz.getDeclaredConstructor().newInstance();

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Before.class)) {
                    method.invoke(beforeAndAfterSteps);
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }

    private void runAfterSteps() {
        try {
            Class<?> clazz = Class.forName("BeforeAndAfterSteps");
            Object beforeAndAfterSteps = clazz.getDeclaredConstructor().newInstance();

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(After.class)) {
                    method.invoke(beforeAndAfterSteps, testResults);
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }
}
