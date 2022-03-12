package Lexer;
import java.util.*;
import java.util.regex.*;
public class Lexer {
    private static  Map<String,Pattern> lexems = new HashMap<>();
    static {
        lexems.put("VAR",Pattern.compile("[a-z][a-z0-9]{0,}"));
        lexems.put("ASSIGN_OP",Pattern.compile("="));
        lexems.put("DIGIT",Pattern.compile("0|[1-9][0-9]*"));
        lexems.put("OP",Pattern.compile("-|\\+|/|\\*"));


    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\s");
        String srcExample = "size = 100 + 1 + 300 * 400 ";
        String[] strings = pattern.split(srcExample);
        ArrayList<Token> tokens = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
        for (String lexemeName : lexems.keySet()) {
                Matcher m = lexems.get(lexemeName).matcher(strings[i]);
                if (m.find()) {
                    Token temp = new Token(strings[i],lexemeName);
                    tokens.add(temp);
                }
            }
        }
        for (int i =0;i<tokens.size();i++){
            System.out.println(tokens.get(i).toString());
        }
    }
}
