package lexing.tokenizer;

public class Token {

    private char[] lexList;
    private int[][] pos;

    public Token(){
    }

    public Token(char[] lexList, int[][] pos){
        this.lexList = lexList;
        this.pos = pos;
    }

}
