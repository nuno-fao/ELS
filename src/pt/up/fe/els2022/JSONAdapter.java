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
    static boolean getNull = false;
    public static Pair parseFile(String filename, List<String> headers, List<String> elements, List<String> parentElements, boolean root) throws IOException {
        ArrayList<HashMap<String, String>> entry = new ArrayList<>();
        HashMap<String, HashMap<String, String>> elems = new HashMap<>();
        ArrayList<String> order = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Path.of(filename));
        JsonElement jsonElement = JsonParser.parseReader(reader);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        HashMap<String, String> elemList = new HashMap<>();
        entry.add(new HashMap<>());

        // get root children
        if(getNull) {
            if (root) {
                for (Map.Entry<String, JsonElement> obj : jsonObject.entrySet()) {
                    if (obj.getValue().isJsonPrimitive() || obj.getValue().isJsonNull()) {
                        elemList.put(obj.getKey(), obj.getValue().getAsString());
                        order.add(obj.getKey());
                    } else if (obj.getValue().isJsonNull()) {
                        elemList.put(obj.getKey(), null);
                        order.add(obj.getKey());
                    }

                }
            }

            // get children of parents
            for (Map.Entry<String, JsonElement> obj : jsonObject.entrySet()) {
                if (parentElements.contains(obj.getKey())) {
                    for (Map.Entry<String, JsonElement> child : obj.getValue().getAsJsonObject().entrySet()) {
                        if (child.getValue().isJsonPrimitive()) {
                            elemList.put(child.getKey(), child.getValue().getAsString());
                            order.add(child.getKey());
                        } else if (child.getValue().isJsonNull()) {
                            elemList.put(child.getKey(), null);
                            order.add(child.getKey());
                        }
                    }
                }
            }
        }else{
            if (root) {
                for (Map.Entry<String, JsonElement> obj : jsonObject.entrySet()) {
                    if (obj.getValue().isJsonPrimitive()) {
                        elemList.put(obj.getKey(), obj.getValue().getAsString());
                        order.add(obj.getKey());
                    }

                }
            }

            // get children of parents
            for (Map.Entry<String, JsonElement> obj : jsonObject.entrySet()) {
                if (parentElements.contains(obj.getKey())) {
                    for (Map.Entry<String, JsonElement> child : obj.getValue().getAsJsonObject().entrySet()) {
                        if (child.getValue().isJsonPrimitive()) {
                            elemList.put(child.getKey(), child.getValue().getAsString());
                            order.add(child.getKey());
                        }
                    }
                }
            }
        }

        // put all header-value pairs in table entry with same names
        if (headers.isEmpty() && elements.isEmpty()) {
            for (var e : elemList.entrySet()){
                entry.get(0).put(e.getKey(),e.getValue());
            }
        } else { // put only the header-value pairs specified
            for(int i = 0;i < headers.size();i++){
                if(elemList.containsKey(headers.get(i))){
                    entry.get(0).put(elements.get(i),elemList.get(headers.get(i)));
                }
            }
            order = (ArrayList<String>) elements;
        }
        return new Pair(entry, order);
    }
}
