package com.tempce.hideandseek.utils;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Path;

public class JsonConfig {
    private final Path path;
    private JsonObject root;

    public JsonConfig(Path path) throws Exception {
        this.path = path;
        load();
    }

    private void load() throws Exception {
        File file = path.toFile();
        if (!file.exists()) {
            root = new JsonObject();
            save();
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonElement parsed = JsonParser.parseReader(reader);
            root = parsed.isJsonObject() ? parsed.getAsJsonObject() : new JsonObject();
        }
    }

    public void reload() throws Exception {
        load();
    }

    public JsonObject getRoot() {
        return root;
    }

    public JsonElement get(String path) {
        String[] parts = path.split("\\.");
        JsonElement current = root;
        for (String part : parts) {
            if (!current.isJsonObject()) return null;
            current = current.getAsJsonObject().get(part);
            if (current == null) return null;
        }
        return current;
    }

    public int getInt(String path, int fallback) {
        JsonElement val = get(path);
        return val != null && val.isJsonPrimitive() && val.getAsJsonPrimitive().isNumber()
                ? val.getAsInt()
                : fallback;
    }

    public double getDouble(String path, double fallback) {
        JsonElement val = get(path);
        return val != null && val.isJsonPrimitive() && val.getAsJsonPrimitive().isNumber()
                ? val.getAsDouble()
                : fallback;
    }

    public boolean getBoolean(String path, boolean fallback) {
        JsonElement val = get(path);
        return val != null && val.isJsonPrimitive() && val.getAsJsonPrimitive().isBoolean()
                ? val.getAsBoolean()
                : fallback;
    }

    public String getString(String path, String fallback) {
        JsonElement val = get(path);
        return val != null && val.isJsonPrimitive()
                ? val.getAsString()
                : fallback;
    }

    public int getInt(String path) {
        JsonElement val = get(path);
        if (val != null && val.isJsonPrimitive() && val.getAsJsonPrimitive().isNumber()) {
            return val.getAsInt();
        }
        throw new IllegalArgumentException("Expected integer at path '" + path + "'");
    }

    public double getDouble(String path) {
        JsonElement val = get(path);
        if (val != null && val.isJsonPrimitive() && val.getAsJsonPrimitive().isNumber()) {
            return val.getAsDouble();
        }
        throw new IllegalArgumentException("Expected double at path '" + path + "'");
    }

    public boolean getBoolean(String path) {
        JsonElement val = get(path);
        if (val != null && val.isJsonPrimitive() && val.getAsJsonPrimitive().isBoolean()) {
            return val.getAsBoolean();
        }
        throw new IllegalArgumentException("Expected boolean at path '" + path + "'");
    }

    public String getString(String path) {
        JsonElement val = get(path);
        if (val != null && val.isJsonPrimitive()) {
            return val.getAsString();
        }
        throw new IllegalArgumentException("Expected string at path '" + path + "'");
    }

    public boolean contains(String path) {
        return get(path) != null;
    }

    /**
     * 指定したパスに値をセットします。存在しないオブジェクト階層は自動生成します。
     */
    public void set(String path, JsonElement value) {
        String[] parts = path.split("\\.");
        JsonObject current = root;
        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            JsonElement next = current.get(part);
            if (next == null || !next.isJsonObject()) {
                JsonObject obj = new JsonObject();
                current.add(part, obj);
                current = obj;
            } else {
                current = next.getAsJsonObject();
            }
        }
        current.add(parts[parts.length - 1], value);
    }

    public void set(String path, String value) {
        set(path, new JsonPrimitive(value));
    }

    public void set(String path, int value) {
        set(path, new JsonPrimitive(value));
    }

    public void set(String path, double value) {
        set(path, new JsonPrimitive(value));
    }

    public void set(String path, boolean value) {
        set(path, new JsonPrimitive(value));
    }

    /**
     * 変更した内容をファイルに保存します
     */
    public void save() throws IOException {
        try (Writer writer = new FileWriter(path.toFile())) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        }
    }
}
