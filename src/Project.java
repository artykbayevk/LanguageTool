import util.Config;
import java.io.File;

public class Project {
    public static void main(String[] args) {
        Config config = new Config();
        File baseDirectory  = new File(config.abs_path);
        boolean debug = true;

//        UNCOMMENT TYPE AND PATH FOR FILE FOR RUNNING ALL PROCESSES


//        String type = "DFA";
//        File file = new File(baseDirectory, "DFA_TEST_1.txt");
//        File file = new File(baseDirectory, "DFA_TEST_2.txt");
//        File file = new File(baseDirectory, "DFA_TEST_3.txt");



//        String type = "NFA";
//        File file = new File(baseDirectory, "NFA_TEST_1.txt");
//        File file = new File(baseDirectory, "NFA_TEST_2.txt");
//        File file = new File(baseDirectory, "NFA_TEST_3.txt");
//        File file = new File(baseDirectory, "NFA_TEST_4.txt");





        String type = "REG";
        File file = new File(baseDirectory, "REG_TEST_1.txt");
//        File file = new File(baseDirectory, "REG_TEST_2.txt");
//        File file = new File(baseDirectory, "REG_TEST_3.txt");
//        File file = new File(baseDirectory, "REG_TEST_4.txt");
//        File file = new File(baseDirectory, "REG_TEST_5.txt");
//        File file = new File(baseDirectory, "REG_TEST_6.txt");
//        File file = new File(baseDirectory, "REG_TEST_7.txt");
//        File file = new File(baseDirectory, "REG_TEST_8.txt");

        if(file.exists()){
            System.out.println();
            System.out.println("Your file is exist!\n");
            System.out.println("--- Lexing and Parsing process ---\n");

            try{
                LanguageTool tool = new LanguageTool(file.toString(), type,debug);
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


                    //RUNNING WORD ON DFA
                    String word = "a";
                    System.out.print("Running DFA on word "+ word+ ", result: ");
                    tool.executeDFA(word);


                    //CONVERT DFA TO NFA and PRINT THIS NFA
                    tool.convertDFAtoNFA();
                    System.out.println("--- CONVERTED NFA ---");
                    tool.getNFA().printAutomataAST();



                    //CONVERT DFA TO REGULAR EXPRESSION - NOT FINISHED
                    System.out.println("--- CONVERTING DFA TO REGEXP ---");
                    tool.convertDFAtoRegExp(); // THIS PART NOT FINISHED, SHOW JUST GNFA
//                    tool.getREGEXP().printElements();




                }else if(type.equals("NFA")){



                    //CONVERT NFA TO DFA and PRINT THIS DFA
                    System.out.println("--- CONVERTED DFA ---");
                    tool.convertNFAtoDFA();
                    tool.getDFA().printAutomataAST();



                    //RUN WORD ON DFA, THAT CONVERTED FROM NFA
                     String word = "ab";
                     System.out.print("Running DFA on word "+ word+ ", result: ");
                     tool.executeDFA(word);


                    //CONVERT DFA TO REGULAR EXPRESSION - NOT FINISHED
                    System.out.println("--- CONVERTING NFA TO REGEXP ---");
                    tool.convertNFAtoRegExp(); // THIS PART NOT FINISHED, SHOW JUST GNFA
                    tool.getREGEXP().printElements();




                }else if(type.equals("REG")){



                    //CONVERTING REGEXP TO NFA AND PRINT THIS NFA
                    System.out.println("--- CONVERTED NFA ---");
                    tool.fromRegExpToNFA();
                    tool.getNFA().printAutomataAST();



                    //CONVERTING NFA TO DFA
                    System.out.println("--- CONVERTED DFA ---");
                    tool.convertNFAtoDFA();
                    tool.getDFA().printAutomataAST();



                    //RUNNING WORD ON DFA
                    String word = "a";
                    System.out.print("Running DFA on word "+ word+ ", result: ");
                    tool.executeDFA(word);




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