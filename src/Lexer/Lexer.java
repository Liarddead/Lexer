package Lexer;
import java.util.*;
import java.util.regex.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class Lexer {
    public List ReadFileUsingFiles() {
            try {
                List allLines = Files.readAllLines(Paths.get("C:\\Users\\alexa\\IdeaProjects\\Lexer\\src\\Lexer\\code.txt"));
                return allLines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }
    private static  Map<String,Pattern> lexems = new HashMap<>();
    static {
        lexems.put("VAR",Pattern.compile("[a-z][a-z0-9]*"));
        lexems.put("ASSIGN_OP",Pattern.compile("="));
        lexems.put("DIGIT",Pattern.compile("0|[1-9][0-9]*"));
        lexems.put("OP",Pattern.compile("-|\\+|/|\\*"));
        lexems.put("STAPLES",Pattern.compile("\\(|\\)"));
        lexems.put("LOGIG_SB",Pattern.compile("==|>|<|=>|<="));
        lexems.put("LOGIG_OP",Pattern.compile("&|\\|"));
        lexems.put("RESTRICTED",Pattern.compile("Ё"));

    }
    private static ArrayList<String> restricted_list = new ArrayList<>();
    static {
        restricted_list.add("print");
        restricted_list.add("else");
        restricted_list.add("if");
        restricted_list.add("for");
        restricted_list.add("while");
        restricted_list.add("int");
        restricted_list.add("string");
        restricted_list.add("list");
        restricted_list.add("hashmap");
    }
    public static ArrayList<Token> Start(String srcExample) {
        char[] strings = srcExample.toCharArray();
        ArrayList<Token> tokens = new ArrayList<>();
        char[] lexem = new char[20];
        int count = 0;
        int notfound = 0;
        String final_lexem = " ";
        for (int i = 0; i < strings.length; i++) {
            lexem[count] = strings[i];
            char[] temp1 = new char[count + 1];
            for (int j = 0; j < count + 1; j++) {
                temp1[j] = lexem[j];
            }
            String temp2 = String.valueOf(temp1);
            for (String lexemeName : lexems.keySet()) {
                Matcher m = lexems.get(lexemeName).matcher(temp2);
                boolean matches = m.matches();
                if (matches == true) {
                    final_lexem = lexemeName;
                } else {
                    notfound++;
                }
            }
            if (notfound == lexems.size()) {
                if ((temp1[count] == ' ') || (temp1[count] == ';')) {
                    i++;
                }

                char[] temp3 = new char[count];
                for (int j = 0; j < count; j++) {
                    temp3[j] = temp1[j];
                }
                String final_name = String.valueOf(temp3);
                if (final_lexem == "VAR") {
                    for (int j = 0; j < restricted_list.size(); j++) {
                        boolean match = Pattern.matches(restricted_list.get(j), final_name);
                        if (match == true) {
                            final_lexem = "RESTRICTED";
                        }
                    }
                }
                for (String lexemeName : lexems.keySet()) {
                    if (final_lexem == lexemeName) {
                        Token temp = new Token(final_name, final_lexem);
                        tokens.add(temp);
                    }
                }
                if ((temp1[count] == '}') || (temp1[count] == '{')) {
                    char stamp_temp=temp1[count];
                    String stamp=String.valueOf(stamp_temp);
                    Token stp = new Token(stamp, "MAIN_STAPLES");
                    tokens.add(stp);
                    i++;
                }
                notfound = 0;
                count = 0;
                for (int j = 0; j < 20; j++) {
                    lexem[j] = ' ';
                }
                i--;
            } else {
                count++;
                notfound = 0;
            }
        }
        return tokens;
    }

}
