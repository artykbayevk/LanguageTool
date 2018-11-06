import util.Config;
import java.io.File;
import java.io.IOException;


public class LanguageTool {

    public static void main(String[] args) {
        Config config = new Config();
        File baseDirectory  = new File(config.abs_path);
        File file = new File(baseDirectory, "structure_DFA.txt");


        if(file.exists()){
            System.out.println("File here");
        }else{
            System.out.println("Not here ");
        }
    }
}
