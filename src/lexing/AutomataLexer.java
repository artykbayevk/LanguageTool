package lexing;

import util.Token;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AutomataLexer extends Lexer {

    public AutomataLexer(String path){
        super(path);
    }
    @Override
    public List<Token> getTokens() throws Exception {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean valid = false;
            int row = 0;


            while ((line = reader.readLine())!= null) {
                if (!line.trim().equals("")) {
                    valid = true;
                    if(!line.trim().equals("-")){
                        line = line.trim().replaceAll("\\s", "");
                        boolean validation = LineToTokenChecker(line, row);
                        if(!validation){
                            LexerCharList.clear();
                            break;
                        }
                    }
                    row++;
                }
            }

            if(!valid){
                throw new IOException("File is empty");
            }
        return LexerCharList;
    }

    private boolean LineToTokenChecker(String line, int row) throws Exception{
        String token = "";
        boolean valid = true;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if(!Character.toString(c).matches("^[a-zA-P0-9,=;>$]*$")){
                valid=false;
                throw new Exception("You have a specific char that not valid at position: "+row+' '+i);
            }else{
                if(c == ',' || c == '>' || c == '=' || c==';'){
                    LexerCharList.add(new Token(token, new int[]{row, i-token.length()}));
                    LexerCharList.add(new Token(Character.toString(c), new int[]{row, i-token.length()+1}));
                    token = "";
                }else{
                    token+=c;
                }
            }
        }
        return valid;
    }
}