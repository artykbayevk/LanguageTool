package util;

public class Token {

    private String val;
    private int[] pos;

    public Token(){
    }

    public Token(String lexList, int[] pos){
        this.val = lexList;
        this.pos = pos;
    }

}
