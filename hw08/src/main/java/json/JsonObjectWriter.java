package json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class JsonObjectWriter {

    @SuppressWarnings("unchecked")
    public static JsonObject toJsonObject(Object object) {
        JsonObject jsonObject = new JsonObject();
        List<JsonComponent> componentList = new ArrayList<>();
        try {
            writeObject(componentList, object);
            jsonObject.setJsonComponents(componentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static JsonComponent createSubObject(String objectName, Object object) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.setObjectName(objectName);
        List<JsonComponent> componentList = new ArrayList<>();
        try {
            writeObject(componentList, object);
            jsonObject.setJsonComponents(componentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static void writeObject(List<JsonComponent> componentList, Object object) throws IllegalAccessException {
        if (object == null) {
            componentList.add(null);
        } else if (isPrimitiveOrWrapper(object.getClass()) || String.class.isAssignableFrom(object.getClass())
                || object.getClass().isArray()
                || Collection.class.isAssignableFrom(object.getClass())) {

            componentList.add(new JsonElement(object));
        } else {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (isEmpty(field.get(object)))
                    continue;

                if (field.getType().isArray()) {
                    componentList.add(JsonParser.parseArray(field, object));
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    componentList.add(JsonParser.parseMap(field, object));
                } else if (isPrimitiveOrWrapper(field.getType()) || String.class.isAssignableFrom(field.getType())) {
                    componentList.add(JsonParser.parsePrimitiveOrString(field, object));
                } else {
                    componentList.add(createSubObject(field.getName(), field.get(object)));
                }
            }
        }
    }
}