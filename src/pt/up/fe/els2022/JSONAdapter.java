package pt.up.fe.els2022;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class JSONAdapter {
    public static HashMap<String, String> parseFile(String filename) throws IOException {
        HashMap<String, String> entry = new HashMap<>();

        Reader reader = Files.newBufferedReader(Path.of(filename));
        JsonElement jsonElement = JsonParser.parseReader(reader);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        for (String key: jsonObject.keySet()) {
            JsonElement obj = jsonObject.get(key);
            if (obj.isJsonPrimitive()) {
                entry.put(key, obj.getAsString());
            } else if (key == "params") {
                JsonObject params = obj.getAsJsonObject();
                for (String k: params.keySet()) {
                    entry.put(key, params.get(k).getAsString());
                }
            }
        }

        return entry;
    }
}
