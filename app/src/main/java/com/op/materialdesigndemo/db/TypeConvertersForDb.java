package com.op.materialdesigndemo.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeConvertersForDb {
    @TypeConverter
    public static String stringListToString(List<String> images) {
        if (images == null || images.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String image : images) {
            stringBuilder.append(image).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @TypeConverter
    public static List<String> stringToStringList(String str) {
        String[] imageArray = str.split(",");
        return new ArrayList<>(Arrays.asList(imageArray));
    }
}
