import AST.AutomataAST;
import AST.RegularExpression;
import util.Config;
import java.io.File;

public class Project {
    public static void main(String[] args) {
        Config config = new Config();
        File baseDirectory  = new File(config.abs_path);
        boolean debug = true;

        AutomataAST DFA;
        AutomataAST NFA;
        RegularExpression REGEXP;

        String type = "DFA";
        File file = new File(baseDirectory, "DFA.txt");

//        String type = "NFA";
//        File file = new File(baseDirectory, "NFA_3.txt");

//        String type = "REG";
//        File file = new File(baseDirectory, "REG_4.txt");

        if(file.exists()){
            System.out.println();
            System.out.println("Your file is exist!\n");
            System.out.println("--- Lexing and Parsing process ---\n");

            try{
                LanguageTool tool = new LanguageTool(file.toString(), type, baseDirectory.toString(), debug);
                System.out.println(" --- Pattern ---");


                //printing patterns
                if(type.equals("DFA")) {
                    System.out.println("DFA");
                    tool.getDFA().printAutomataAST();
                }
                else if(type.equals("NFA")) {
                    System.out.println("NFA");
                    tool.getNFA().printAutomataAST();
                }
                else if(type.equals("REG")) {
                    System.out.println("Regular Expression: ");
                    tool.getREGEXP().printElements();
                }
                System.out.println();

                // checking validation of pattern
                tool.checkValidation();


                System.out.println("--- Compiling and Converting Patterns ---");
                //running compiler on pattern

                if(type.equals("DFA")){
//                    String word = "a";
//                    System.out.print("Running on word "+word+" : ");
//                    tool.getDFA().execute(word);
                    tool.convertDFAtoNFA();
                    tool.getNFA().printAutomataAST();
                    tool.convertDFAtoRegExp();
                }else if(type.equals("NFA")){
                    String word = "01";
                    tool.convertNFAtoDFA();
                    tool.getDFA().printAutomataAST();
                    System.out.print("Running on word "+word+" : ");
                    tool.getDFA().execute(word);
                }else if(type.equals("REG")){
                    String word = "de";
                    tool.fromRegExpToNFA();
                    tool.convertNFAtoDFA();
                    tool.getNFA().printAutomataAST();
                    tool.getDFA().printAutomataAST();
                    System.out.print("Running on word "+word+" : ");
                    tool.getDFA().execute(word);
                }else{
                    System.out.println("Nothing");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("File doesn't exist");
        }
    }
}