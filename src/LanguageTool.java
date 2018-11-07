import lexing.DFALexer;
import lexing.Lexer;
import lexing.Token;

import java.io.IOException;
import java.util.List;

public class LanguageTool {
    public String path;
    public String type;
    public String out_dir;

    private Lexer lexer;

    public LanguageTool(String path, String type, String output_directory){
        this.path = path;
        this.type = type;
        this.out_dir = output_directory;

        if(type.equals("DFA")) {
            lexer = new DFALexer(path);

        }

        // TODO: 11/7/18 this token will be use for parsing
    }
}
