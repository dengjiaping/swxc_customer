package com.shiwaixiangcun.customer.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtil {
    public static String toJson(Object object) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        try {
            return gson.fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        try {
            return gson.fromJson(json, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }


}