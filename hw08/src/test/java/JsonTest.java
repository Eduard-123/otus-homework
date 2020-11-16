import json.JsonObject;
import json.JsonObjectWriter;
import model.TestPojo;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonTest {

    @Test
    public void jsonTest() {

        TestPojo testPojo = new TestPojo();
        testPojo.setInteger(5);
        testPojo.setString("Cat");
        testPojo.setInts(new int[]{1, 2, 3});
        testPojo.setStrings(new String[]{"A", "B"});

        Map<Object, Object> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        testPojo.setMap(map);

        JsonObject object = JsonObjectWriter.toJsonObject(testPojo);
        System.out.println(object.toJsonString());

    }

}
