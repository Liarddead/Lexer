package Lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
    private String name;
    private String type;
     public Token(String Name,String Type){
          name = Name;
          type = Type;
      }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static void main(String[] args) {

        Matcher m = Pattern.compile("[a-z][a-z0-9]*").matcher("si");
        boolean matches = m.matches();
        System.out.println(matches);

    }
}
