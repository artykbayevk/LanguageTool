package util;

public class Config {
    private static int global_counter = 0;
    public String abs_path = "/Users/kamalsdu/Documents/NU/software/LanguageTool/files"; // MAC PATH
//    public String abs_path = "C:\\Users\\home\\Desktop\\LanguageTool\\files"; // WINDOWS PATH

    public static int getGlobal_counter(){
        global_counter++;
        return global_counter;
    }
}
