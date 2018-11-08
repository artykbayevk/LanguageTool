package lexing;

import util.Token;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DFALexer extends Lexer {

    public DFALexer(String path){
        super(path);
    }

    public List<Token> getTokens() throws IOException {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean valid = false;
            while ((line = reader.readLine())!= null) {
                if (!line.trim().equals("")) {
                    valid = true;
                    LineToToken(line.replaceAll("\\s", ""));
                }
            }

            if(!valid){
                System.out.println("File is empty");
            }

        }catch (IOException e){
            throw e;
        }
        return LexerCharList;
    }


    private void LineToToken(String line){
        System.out.println(line);
    }
}