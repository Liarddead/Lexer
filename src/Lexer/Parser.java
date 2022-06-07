package Lexer;
import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private List <Token> tokenList;
    private Token currentToken;
    private ListIterator<Token> iterator;
    private int number_of_string;
    private String error_message;
    private int column_size=1;
    private int main_staple_count=0;
    private int lock_error_message=0;
    public Parser(){

    }
    public void finish(){
        if ((main_staple_count>0))
            try {
                throw new ParseException("Miss main_staple",1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }
    public void set_all(List <Token> TokenList,int Number_of_string){
        tokenList =TokenList;
        iterator = tokenList.listIterator();
        currentToken = iterator.next();
        number_of_string=Number_of_string+1;
        column_size=1;
    }
    public void start()
    {
        lang();
    }
    void lang(){
           boolean res =expr();
            if((res==false)){
                try {
                    throw new ParseException(error_message,1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
    }
    boolean expr(){
        boolean res1 =assign_expr();
        boolean res2 =cycle_expr();
        boolean res3=main_staple_rigt();
        boolean res4=logic_expr_if();
        boolean res5=logic_expr_else();
        boolean res6=print_expr();
        if (res3==true){
            main_staple_count--;
        }
        if ((res1==false)&&(res2==false)&&(res3==false)&&(res4==false)&&(res5==false)&&(res6==false)){
            return false;
            }
        else{
            return true;
        }
    }
    boolean print_expr(){
        boolean res1= restricted("print");
        if((res1==false)){
            if (lock_error_message==0){
                error_message="In string "+number_of_string+ " In column "+column_size+" expected key word \"print\" ";}
            return false;
        }
        lock_error_message=7;
        boolean res2 =value();
        if((res2==false)){
            if (lock_error_message==7){
                error_message="In string "+number_of_string+ " In column "+column_size+" value expected  ";}
            return false;
        }
        return true;
    }
    boolean logic_expr_if(){
        boolean res1= restricted("if");
        if((res1==false)){
            if (lock_error_message==0){
            error_message="In string "+number_of_string+ " In column "+column_size+" expected key word \"if\" ";}
            return false;
        }
        lock_error_message=1;
        boolean res2=logic_cond();
        if((res2==false)){
            return false;
        }
        boolean res3 = main_staple_left();
        if((res3==false)){
            if (lock_error_message==1){
            error_message="In string "+number_of_string+ " In column "+column_size+" left_main_staple expected";}
            return false;
        }
        main_staple_count++;
        return true;
    }
    boolean logic_expr_else(){
        boolean res1= restricted("else");
        if((res1==false)){
            if (lock_error_message==0){
            error_message="In string "+number_of_string+ " In column "+column_size+" expected key word \"else\" ";}
            return false;
        }
        lock_error_message=2;
        boolean res3 = main_staple_left();
        if((res3==false)){
            if (lock_error_message==2){
            error_message="In string "+number_of_string+ " In column "+column_size+" left_main_staple expected";}
            return false;
        }
        main_staple_count++;
        return true;
    }
    boolean cycle_expr(){
        boolean res1= restricted("while");
        if((res1==false)){
            if (lock_error_message==0){
                error_message="In string "+number_of_string+ " In column "+column_size+" expected key word \"while\" ";}
                return false;
            }
        lock_error_message=3;
        boolean res2 = logic_cond();
        if((res2==false)){
            return false;
        }
        boolean res3 = main_staple_left();
        if((res3==false)){
            if (lock_error_message==3){
            error_message="In string "+number_of_string+ " In column "+column_size+" left_main_staple expected";}
            return false;
        }
        main_staple_count++;
        return true;
        }

    boolean assign_expr(){
        boolean res1 = var();
        if((res1==false)){
             {
                 if (lock_error_message==0){
                error_message="In string "+number_of_string+ " In column "+column_size+" Var expected";}
                return false;
            }

        }
        lock_error_message=4;
        boolean res2 = assign_op();
        if((res2==false)){
             { if (lock_error_message==4){
                error_message="In string "+number_of_string+ " In column "+column_size+" assign_op expected";}
                return false;
            }

        }
        boolean res3 = expr_value();
        if((res3==false)){
                return false;
            }
        else {
            return true;
        }
    }
    boolean type(){
        boolean res1=restricted("int");
        if (res1==true){
            return true;
        }
        boolean res2=restricted("string");
        if (res2==true){
            return true;
        }
        boolean res3=restricted("hashmap");
        if (res3==true){
            return true;
        }
        boolean res4=restricted("list");
        if (res4==true){
            return true;
        }
        return false;

    }
    boolean expr_value(){
        boolean res = value();
        if((res==false)){
             {if (lock_error_message==4){
                error_message="In string "+number_of_string+ " In column "+column_size+" value expected";}
                return false;
            }

        }
        boolean res1=true;
        boolean res2=true;
        while (((res1==true)&&(res2==true))) {
            res1 = op();
            if(res1==false) {
                if (iterator.hasNext()==false){
                    return true;
                }
                if ((lock_error_message==4)){
                    error_message="In string "+number_of_string+ " In column "+column_size+" value expected";}
                return false;
            }
            res2 = value();
            if(res2==false) {
                if ((lock_error_message==4)){
                    error_message="In string "+number_of_string+ " In column "+column_size+" value expected";}
                return false;
            }

        }
        if((res1==false)||(res2==false)){
            {   if (lock_error_message==4){
                error_message="In string "+number_of_string+ " In column "+column_size+" value expected";}
                return false;
            }
        }
        return true;
    }
    boolean logic_cond(){
        boolean res1=cond();
        if((res1==false)){
            {
                return false;
            }
        }
        boolean res2=true;
        boolean res3=true;
        while (((res2==true)&&(res3==true))) {
            res2 = logic_op();
            if(res2==false) {
                if (currentToken.getName().equals("{")){
                    return true;
                }
                if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" cond expected";}
                return false;
            }
            res3 = cond();
            if(res3==false) {
                if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" cond expected";}
                return false;
            }
        }
        return true;
    }
    boolean cond(){
        boolean res1 = staple_left();
        if((res1==false)){
            { if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" left_staple expected";}
                return false;
            }
        }
        boolean res2=var();
        if((res2==false)){
            { if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" Var expected";}
                return false;
            }
        }
        boolean res3=logic_sb();
        if((res3==false)){
            {    if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" logic_sb expected";}
                return false;
            }
        }
        boolean res4=value();
        if((res4==false)){
            {    if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" value expected";}
                return false;
            }
        }
        boolean res5=staple_right();
        if((res5==false)){
            { if ((lock_error_message==1)||(lock_error_message==3)){
                error_message="In string "+number_of_string+ " In column "+column_size+" right_staple expected";}
                return false;
            }
        }
        return true;
    }
    boolean value()  {
        boolean res1=digit();
        boolean res2=var();
        if ((res1==false)&&(res2==false)){
                return false;
        }
        return true;
    }
    boolean op(){
        boolean res = checkToken("OP");
        return res;
    }
    boolean var(){
       boolean res =checkToken("VAR");
        return res;
    }
    boolean digit(){
        boolean res =checkToken("DIGIT");
        return res;
    }
    boolean assign_op(){
        boolean res = checkToken("ASSIGN_OP");
        return  res;
    }
    boolean logic_sb(){
        boolean res = checkToken("LOGIG_SB");
        return  res;
    }
    boolean logic_op(){
        boolean res = checkToken("LOGIG_OP");
        return  res;
    }
    boolean staple_right(){
        boolean res = this.currentToken.getName().equals(")");
        if(res==true){
            boolean result = checkToken("STAPLES");
            return result;
        }
        else{
            return false;
        }
    }
    boolean staple_left(){
        boolean res = this.currentToken.getName().equals("(");
        if(res==true){
            boolean result = checkToken("STAPLES");
            return result;
        }
        else{
            return false;
        }
    }
    boolean main_staple_left(){
        boolean res = this.currentToken.getName().equals("{");
        if(res==true){
            boolean result = checkToken("MAIN_STAPLES");
            return result;
        }
        else{
            return false;
        }
    }
    boolean main_staple_rigt(){
        boolean res = this.currentToken.getName().equals("}");
        if(res==true){
            boolean result = checkToken("MAIN_STAPLES");
            return result;
        }
        else{
            return false;
        }
    }
    boolean restricted(String Name){
        boolean res = this.currentToken.getName().equals(Name);
        if(res==true){
            boolean result = checkToken("RESTRICTED");
            return result;
        }
        else{
            return false;
        }
    }
    boolean checkToken(String type){
        boolean result = this.currentToken.getType().equals(type);
        if(result==false){
            return result;
        }
        if (iterator.hasNext()==false){
            column_size=column_size+currentToken.getToken_size();
            return result;
        }
        next();
        return result;
    }
    void next(){
        column_size=column_size+currentToken.getToken_size();
        currentToken=iterator.next();
    }
    void previous(){
        column_size=column_size-currentToken.getToken_size();
        currentToken=iterator.previous();
    }
}
