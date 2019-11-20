package AlertClass;
import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    private static Journal journal = new Journal();

private static void Save(String path){
    Gson gson  = new Gson();
    try {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(gson.toJson(journal));
        fileWriter.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
private static void Load(String path){
    Gson gson = new Gson();
    try {
        FileReader fileReader = new FileReader(path);
        journal = gson.fromJson(fileReader,Journal.class);
        fileReader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
