package com.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.people.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;

/**
 * Operates the file for saving/loading collection.
 */
public class FileManager {
    private final String fileName;
    private final static Gson converter = new Gson();

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Writes collection to a file.
     *
     * @param personCollection Collection to write.
     */
    public void writeCollection(ArrayDeque<Person> personCollection) {
        try {
            if (fileName != null) {
                File file = new File(fileName);
                if (!file.canRead() || !file.canWrite())
                    throw new IOException();
                String result = converter.toJson(personCollection);
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                writer.write(result);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            ConsoleManager.printErr("Нет доступа к файлу!");
            System.exit(0);
        }

    }

    /**
     * Reads collection from a file.
     *
     * @return Readed collection.
     */
    public ArrayDeque<Person> readCollection() {
        try {
            if (fileName != null) {
                File file = new File(fileName);
                if (!file.canRead())
                    throw new IOException();
                InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
                StringBuilder jsonHolder = new StringBuilder();
                char[] buffer = new char[1024];
                int length = reader.read(buffer);
                while (length != -1) {
                    jsonHolder.append(buffer, 0, length);
                    length = reader.read(buffer);
                }
                reader.close();
                Type listType = new TypeToken<ArrayDeque<Person>>() {
                }.getType();
                ArrayDeque<Person> convertedCollection = converter.fromJson(jsonHolder.toString(), listType);
                if (convertedCollection == null)
                    convertedCollection = new ArrayDeque<>();

                return convertedCollection;
            }
        } catch (IOException e) {
            ConsoleManager.printErr("Нет доступа к файлу!");
            System.exit(0);
        }
        return new ArrayDeque<>();
    }
}
