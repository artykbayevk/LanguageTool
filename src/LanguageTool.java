import util.Config;
import java.io.File;
import java.util.Scanner;
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

        System.out.println("Language Tool Interface:\n");
        Scanner reader = new Scanner(System.in);
        System.out.println("1 - Load DFA");
        System.out.println("2 - Load NFA");
        System.out.println("3 - Load RegExp\n");
        int n = reader.nextInt();
    }
}
