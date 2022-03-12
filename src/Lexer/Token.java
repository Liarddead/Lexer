package Lexer;

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
}
