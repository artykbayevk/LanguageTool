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

//        String type = "DFA";
//        File file = new File(baseDirectory, "structure_DFA.txt");

//        String type = "NFA";
//        File file = new File(baseDirectory, "structure_NFA.txt");

        String type = "REG";
        File file = new File(baseDirectory, "structure_REG.txt");

        if(file.exists()){
            System.out.println("File exists");

            try{
                LanguageTool tool = new LanguageTool(file.toString(), type, baseDirectory.toString());
                tool.checkValidation();

                if(type.equals("DFA")){
                    System.out.println("----");
                    String word = "abab";
                    tool.executeDFA(word);
                }

                if(type.equals("NFA")){
                    System.out.println("----");
                    System.out.println("Converting");
                    tool.setDFA(tool.convertNFAtoDFA());
                    System.out.println("----");
                    tool.changeType("DFA");
                    tool.executeDFA("bcbca");
                }

                if(type.equals("REG")){
                    System.out.println("----");
                    System.out.print("Regular Expression: ");
                    tool.printRegExp();
                    System.out.println();
                    System.out.println("----\nConverting to NFA:");
                    tool.setNFA(tool.fromRegExpToNFA());
                    tool.printNFAandDFA(tool.getNFA());

                    tool.setDFA(tool.convertNFAtoDFA());
                    tool.printNFAandDFA(tool.getDFA());
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            System.out.println("File doesn't exist");
        }
    }
}
