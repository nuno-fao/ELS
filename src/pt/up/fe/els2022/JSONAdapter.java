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
import java.util.*;

public class JSONAdapter {
    public static ArrayList<HashMap<String, String>> parseFile(String filename, List<String> headers, List<String> elements, List<String> parentElements, boolean root) throws IOException {
        ArrayList<HashMap<String, String>> entry = new ArrayList<>();
        HashMap<String, HashMap<String, String>> elems = new HashMap<>();

        Reader reader = Files.newBufferedReader(Path.of(filename));
        JsonElement jsonElement = JsonParser.parseReader(reader);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // get root children
        if (root) {
            HashMap<String, String> elemList = new HashMap<>();
            for (Map.Entry<String, JsonElement> obj: jsonObject.entrySet()) {
                if (obj.getValue().isJsonPrimitive()) {
                    elemList.put(obj.getKey(), obj.getValue().getAsString());
                }
            }
            elems.put("ROOT", elemList);
        }

        // get children of parents
        for (Map.Entry<String, JsonElement> obj: jsonObject.entrySet()) {
            if (parentElements.contains(obj.getKey())) {
                HashMap<String, String> elemList = new HashMap<>();
                for (Map.Entry<String, JsonElement> child: obj.getValue().getAsJsonObject().entrySet()) {
                    if (child.getValue().isJsonPrimitive()) {
                        elemList.put(child.getKey(), child.getValue().getAsString());
                    }
                }
                elems.put(obj.getKey(), elemList);
            }
        }

        // put all header-value pairs in table entry with same names
        if (headers.isEmpty() && elements.isEmpty()) {
            for (HashMap<String, String> elem: elems.values()) {
                entry.add(elem);
            }
        } else { // put only the header-value pairs specified
            for (HashMap<String, String> elem: elems.values()) {
                HashMap<String, String> newEntryMap = new HashMap<>();
                for (Map.Entry<String, String> elemValue: elem.entrySet()) {
                    int i = headers.indexOf(elemValue.getKey());
                    if (i != -1) {
                        newEntryMap.put(elements.get(i), elemValue.getValue());
                    }
                }
                entry.add(newEntryMap);
            }
        }

        return entry;
    }
}
