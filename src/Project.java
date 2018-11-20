import AST.AutomataAST;
import AST.RegularExpression;
import util.Config;
import java.io.File;

public class Project {
    public static void main(String[] args) {
        Config config = new Config();
        File baseDirectory  = new File(config.abs_path);
        AutomataAST DFA;
        AutomataAST NFA;
        RegularExpression REGEXP;


        String type = "DFA";
        File file = new File(baseDirectory, "structure_DFA.txt");


//        String type = "NFA";
//        File file = new File(baseDirectory, "structure_NFA.txt");


//        String type = "REG";
//        File file = new File(baseDirectory, "structure_REG.txt");

        if(file.exists()){
            System.out.println("File exists");

            LanguageTool tool = new LanguageTool(file.toString(), type, baseDirectory.toString());

            boolean validated = false;
            try {
                validated = tool.checkValidation();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Our AST : validation: "+validated);



        }else{
            System.out.println("Not here ");
        }
    }
}
