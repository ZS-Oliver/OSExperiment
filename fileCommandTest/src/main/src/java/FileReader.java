import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String> readFile() {
        String filename = ("D:\\test.txt");
        List<String> lines = new ArrayList();
        java.io.File newFile = new java.io.File(filename);
        try {
            lines = Files.readLines(newFile, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void write(String list){
        java.io.File file = new java.io.File("D:\\test.txt");
        try {
            Files.append(list,file,Charsets.UTF_8);
            Files.append("\n",file,Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
