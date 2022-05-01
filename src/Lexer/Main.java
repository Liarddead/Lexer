package Lexer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lex = new Lexer();

        List<String> code_strings = lex.ReadFileUsingFiles();
        for (int j = 0; j < code_strings.size(); j++) {
            System.out.println("String " + (j+1));
            ArrayList<Token> tokens = Lexer.Start(code_strings.get(j));
            for (int i = 0; i < tokens.size(); i++) {
                System.out.println(tokens.get(i).toString());
            }
            Parser pas = new Parser(tokens);
            pas.start();
        }
    }
}