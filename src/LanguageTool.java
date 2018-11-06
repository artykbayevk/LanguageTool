import lexing.DFA.DFALexer;
import lexing.Lexer;

public class LanguageTool {
    public String path;
    public String type;
    public String out_dir;

    private Lexer lexer;

    public LanguageTool(String path, String type, String output_directory){
        this.path = path;
        this.type = type;
        this.out_dir = output_directory;


        if(type.equals("DFA")) lexer = new DFALexer(path);
    }
}
