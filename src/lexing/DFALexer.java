package lexing;

import util.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DFALexer extends Lexer {

    private List<Token> tokenList;

    public DFALexer(String path){
        super(path);
        System.out.println(file.toString());
    }

    // TODO: 11/7/18 read file from this path , read all chars, divide into tokens and return token list
    public List<Token> getTokens() throws IOException {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int ch;
            int row = 0, col = 0;
            String val = "";

            do {
                ch = reader.read();
                if(ch == -1){
                    System.out.println("File is empty");
                    break;
                }else{
                    System.out.println(">>" + (char) ch);
                }

            }while (ch != -1);
        }catch (IOException e){
            System.out.println("Problem in reading file!");
        }
        return null;
    }
}
