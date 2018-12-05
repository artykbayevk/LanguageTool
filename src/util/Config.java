package util;

public class Config {
    private static int global_counter = 0;
    public String abs_path = "/Users/kamalsdu/Documents/NU/software/LanguageTool/files"; // MAC PATH
//    public String abs_path = "C:\\Users\\home\\Desktop\\LanguageTool\\files"; // WINDOWS PATH

    //Please write here absolute path for folder with input files
    //public String abs_path = "";

    public static int getGlobal_counter(){
        global_counter++;
        return global_counter;
    }
}
