package util;

public class Token {

    public String val;
    public int[] pos;

    public Token(String lexList, int[] pos){
        this.val = lexList;
        this.pos = pos;
    }



}
