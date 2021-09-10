import com.google.gson.*;

public class JsonUtil {

    public static String toJson(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public static JsonArray deserializeJson(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(jsonString).getAsJsonArray();
        return jsonArray;
    }
}
