package lexing;

import util.Token;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DFALexer extends Lexer {

    public DFALexer(String path){
        super(path);
    }

    public List<Token> getTokens() throws IOException {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int ch;
            String line = "";
            do {
                ch = reader.read();
                if(ch == -1){

                }
                if((char)ch == '\n'){
                    String[] splited = line.split("\\s+");
                    if(splited.length != 1){
                        System.out.println("Lenght of splitted + "+splited.length);
                        for (int i = 0; i < splited.length; i++) {
                            System.out.print(splited[i]);
                        }
                        System.out.println('\n');
                    }

                    line="";
                }else{
                    line+=(char)ch;
                }
            }while (ch != -1);
        }catch (IOException e){
            System.out.println("Problem in reading file!");
        }
        return LexerCharList;
    }
}