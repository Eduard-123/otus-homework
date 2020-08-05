import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private void testInit(List<Method> listBefore, List<Method> listTest, List<Method> listAfter, Class<?> clazz) {

        if (!listBefore.isEmpty() || !listTest.isEmpty() || !listAfter.isEmpty()) {
            listBefore.clear();
            listTest.clear();
            listAfter.clear();
        }
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Before.class)) {
                    listBefore.add(m);
                } else if (m.isAnnotationPresent(Test.class)) {
                    listTest.add(m);
                } else if (m.isAnnotationPresent(After.class)) {
                    listAfter.add(m);
                }
            }
    }

    public void testRun(Class<?>... classes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Method> listBefore = new ArrayList<>();
        List<Method> listTest = new ArrayList<>();
        List<Method> listAfter = new ArrayList<>();

        for (int i = 0; i < classes.length; i++) {
            Object obj = classes[i].getConstructor().newInstance();
            testInit(listBefore, listTest, listAfter, classes[i]);
            invokeMethods(obj, listBefore, listTest, listAfter);
        }

    }

    private void invokeMethods(Object object, List<Method> listBefore, List<Method> listTest, List<Method> listAfter) throws InvocationTargetException, IllegalAccessException {
        int testsTotal = 0;
        int passed = 0;

        try {
            for (Method value : listBefore) {
                value.invoke(object);
            }
            for (Method method : listTest) {
                method.invoke(object);
                passed++;
            }
            for (Method item : listAfter) {
                item.invoke(object);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        testsTotal++;

        System.out.println("Tests: " + testsTotal + " Passed: " + passed + " Failed: " + (testsTotal - passed));
    }
}
