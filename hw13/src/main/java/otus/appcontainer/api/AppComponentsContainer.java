package otus.appcontainer.api;

public interface AppComponentsContainer {
    <C> C getAppComponent(Class<C> componentClass) throws ClassNotFoundException;
    <C> C getAppComponent(String componentName);
}
