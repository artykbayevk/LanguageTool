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
            int ch,col=0,row=0;
            boolean break_stm = false;
            String line = "";
            do {
                ch = reader.read();

                //if our file is empty, end we just write this is empty file
                if(ch == -1 && line.length()==0 && col == 0 && row == 0){
                    System.out.println("File is empty");
                }else if(ch == -1 && line.length()!=0){ // or we take last line and split it
                    String[] splited = line.split("\\s+");
                    LineToToken(splited,break_stm);
                }



                if((char)ch == '\n'){
                    String[] splited = line.split("\\s+");
                    //not space
                    if (splited.length == 1 && splited[0].equals("-")) break_stm = true;


                    if(splited.length != 1){
                        LineToToken(splited,break_stm);
                    }
                    line="";
                    row++;
                }else{
                    line+=(char)ch;
                }
            }while (ch != -1);
        }catch (IOException e){
            System.out.println("Problem in reading file!");
        }
        return LexerCharList;
    }


    public void LineToToken(String[] lineArr,boolean break_stm){
        String line = "";
        for (int i = 0; i < lineArr.length; i++)line+=lineArr[i];
        System.out.println(line + ' ' + lineArr.length + ' ' + break_stm);
    }
}