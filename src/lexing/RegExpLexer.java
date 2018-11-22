package lexing;

import util.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class RegExpLexer extends Lexer {

    public RegExpLexer(String path){
        super(path);
    }

    @Override
    public List<Token> getTokens() throws Exception{
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int ch, el = 0;
            String line = "";
            do {
                ch = reader.read();

                if(ch == -1 && el == 0){
                    throw new Exception("File is empty");
                }
                el++;
                if(ch != -1) line+=(char)ch;
            }while (ch != -1);
            line = line.trim();
            line = line.replaceAll(" ", "");
            for (int i = 0; i < line.length() ; i++) {
                LexerCharList.add(new Token(line.substring(i,i+1),new int[]{0, i} ));
            }
        }catch (Exception e){
            throw new Exception("Problems in reading file");
        }
        return LexerCharList;
    }

}
