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

            try{
                LanguageTool tool = new LanguageTool(file.toString(), type, baseDirectory.toString());
                try {
                    tool.checkValidation();

                    if(type.equals("DFA")){
                        String word = "abab";
                        tool.executeDFA(word);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            System.out.println("File doesn't exist");
        }
    }
}
