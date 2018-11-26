package semantic;

import AST.RegularExpression;

public class SemanticAnalyzerRegExp implements SemanticAnalyzer {

    RegularExpression regExp;

    public SemanticAnalyzerRegExp(RegularExpression regExp) {
        this.regExp = regExp;
    }

    @Override
    public void validator()throws Exception {
        String reg[] = regExp.value().split(" ");
        for (int i = 0; i < reg.length; i++) {
            if(reg[i].equals("*") || reg[i].equals(".") || reg[i].equals(")") || reg[i].equals(null)) throw new Exception("Pattern of regExp isnt right");
        }
        System.out.println("Semantic is OK!");
        System.out.println();
    }
}
