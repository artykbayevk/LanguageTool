import util.Config;
import java.io.File;

public class Project {
    public static void main(String[] args) throws Exception{
        Config config = new Config();
        File baseDirectory  = new File(config.abs_path);
        File file = new File(baseDirectory, "structure_DFA.txt");

        if(file.exists()){
            System.out.println("File here");
        }else{
            System.out.println("Not here ");
        }


        LanguageTool tool = new LanguageTool(file.toString(), "DFA", baseDirectory.toString());
    }
}
