package handlers;

import Services.RegisterResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.catalog.Catalog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ObjectConverter {

    public static <T> T deserialize(String value, Class<T> returnType) { // untested from powerpoint
        return (new Gson()).fromJson(value, returnType);
    }

    public static <T> String  serialize(T inputType/*RegisterResult inputType*/) throws IOException { //untested
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(inputType);
        System.out.println(jsonString); // don't need this for now
        return jsonString;
    }
}
