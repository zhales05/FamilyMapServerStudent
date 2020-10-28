package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.catalog.Catalog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ObjectConverter {

    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }

    private void serialize(Catalog catalog, File file) throws IOException { //needs all the work
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(catalog);

        try(FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(jsonString);
        }

        System.out.println(jsonString);
    }
}
