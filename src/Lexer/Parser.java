package Lexer;
import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private int stopsignal =0;
    private List <Token> tokenList;
    private Token currentToken;
    private ListIterator<Token> iterator;

    public Parser(List <Token> TokenList){
        tokenList =TokenList;
        iterator = tokenList.listIterator();
        currentToken = iterator.next();
    }
    public void start()
    {
        lang();
    }
    void lang(){
            expr();
        System.out.println("lang check");
    }
    void expr(){
        boolean res =assign_expr();
        if (res==false){
            try {
                throw new ParseException("expr expected",1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("expr check");
    }
    boolean assign_expr(){
        boolean res1 = var();
        if((res1==false)){
            try {
                throw new ParseException("Var expected",1);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

        }
        boolean res2 = assign_op();
        if((res2==false)){
            try {
                throw new ParseException("assign_op expected",1);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

        }
        boolean res3 = expr_value();
        if((res3==false)){
            try {
                throw new ParseException("expr_value expected",1);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }
    boolean expr_value(){
        boolean res = value();
        if((res==false)){
            try {
                throw new ParseException("value expected",1);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

        }
        System.out.println("first value check");
        boolean res1=true;
        boolean res2=true;
        while (((res1==true)&&(res2==true))) {
            if (stopsignal==1){
                break;
            }
            res1 = op();
            res2 = value();
        }
        if((res1==false)||(res2==false)){
            try {
                throw new ParseException("value expected",1);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

        }

        System.out.println("expr_value check");
        return true;
    }
    boolean value()  {
        boolean res1=digit();
        boolean res2=var();
        if ((res1==false)&&(res2==false)){
                return false;
        }
        System.out.println("value check");
        return true;
    }
    boolean op(){
        boolean res = checkToken("OP");
        System.out.println("OP check");
        return res;
    }
    boolean var(){
       boolean res =checkToken("VAR");
        System.out.println("VAR check");
        return res;
    }
    boolean digit(){
        boolean res =checkToken("DIGIT");
        System.out.println("DIGIT check");
        return res;
    }
    boolean assign_op(){
        boolean res = checkToken("ASSIGN_OP");
        System.out.println("ASSIGN_OP check");
        return  res;
    }
    boolean checkToken(String type){
        boolean result = this.currentToken.getType().equals(type);
        if(result==false){
            prev();
            next();
            return result;
        }
        if (iterator.hasNext()==false){
            stopsignal =1;
            return result;
        }
        next();
        return result;
    }
    void next(){
        currentToken=iterator.next();
    }
    void prev(){
        currentToken= iterator.previous();
    }
}
