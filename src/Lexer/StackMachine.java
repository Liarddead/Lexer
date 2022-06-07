package Lexer;

import java.util.*;
import java.util.regex.Pattern;

public class StackMachine {
    private List<Token> tokenList;
    private Token currentToken;
    private ListIterator<Token> iterator;
    private  Map<String,value> var_list= new HashMap<>();
    private static ArrayList<value> var_dictionary = new ArrayList<>();
    public void set_all(List<Token> TokenList) {
        tokenList = TokenList;
        iterator = tokenList.listIterator();
        currentToken = iterator.next();
    }
    public void start() {
        polskay();
        assign();
        if(currentToken.getName().equals("print")){
            next();
            if (currentToken.getType().equals("DIGIT")){
                System.out.println(currentToken.getName());
            }
            else {
                System.out.println(var_list.get(currentToken.getName()).getValue());
            }
        }
    }
    public void assign(){
        if(currentToken.getType().equals("ASSIGN_OP")) {
            value operand1 = new value("zero", 0);
            next();
            operand1=var_dictionary.get(0);
            var_dictionary.remove(0);
            value operand2 = new value("zero", 0);
            next();
            if (currentToken.getType().equals("OP")){
                operand2=operation(currentToken);
            }
            else{
                operand2=var_dictionary.get(0);
                var_dictionary.remove(0);
            }

            value res = new value(operand1.getName(),operand2.getValue());
            var_list.put(res.getName(),res);

        }
    }
    public value operation(Token token) {
        Token operation=currentToken;
        value operand1 = new value("zero", 0);
        next();
        operand1=var_dictionary.get(0);
        var_dictionary.remove(0);
        value operand2 = new value("zero", 0);
        next();
        if (currentToken.getType().equals("OP")){
            operand2=operation(currentToken);
        }
        else {
            operand2 = var_dictionary.get(0);
            var_dictionary.remove(0);
        }
            if (operation.getName().equals("+")){
                int val=operand1.getValue()+operand2.getValue();
                String name = Integer.toString (val);
                value res = new value(name,val);
            return res;
        }
            if (operation.getName().equals("-")){
                int val=operand1.getValue()-operand2.getValue();
                String name = Integer.toString (val);
                value res = new value(name,val);
                return res;
            }
            if (operation.getName().equals("*")){
                int val=operand1.getValue()*operand2.getValue();
                String name = Integer.toString (val);
                value res = new value(name,val);
                return res;
            }
            if (operation.getName().equals("/")){
                int val=operand1.getValue()/operand2.getValue();
                String name = Integer.toString (val);
                value res = new value(name,val);
                return res;
            }

        System.out.println("What ta fuck");
        value res =new value("zero",0);
        return res;
    }
        void next() {
            currentToken = iterator.next();
        }
        void previous () {
            currentToken = iterator.previous();
        }

        public void polskay(){
        for (int i=0;i<tokenList.size()-1;i++) {
            boolean res0 = tokenList.get(i).getName().equals("print");
            if (res0==true){
                break;
            }
            boolean res1 = tokenList.get(i).getName().equals("=");
            boolean res2 = tokenList.get(i).getName().equals("+");
            boolean res3 = tokenList.get(i).getName().equals("-");
            boolean res4 = tokenList.get(i).getName().equals("*");
            boolean res5 = tokenList.get(i).getName().equals("/");
            if ((res1 = true) || (res2 = true) || (res3 = true) || (res4 = true) || (res5 = true)) {
                Token temp = tokenList.get(i);
                Token temp1 = tokenList.get(i + 1);
                tokenList.set(i, temp1);
                tokenList.set(i + 1, temp);
                i++;
            }
        }
            for (int i=0;i<tokenList.size();i++){
            boolean res6 = tokenList.get(i).getType().equals("VAR");
            if (res6==true){
                boolean inz=false;
                for (String Name : var_list.keySet()){
                    if (tokenList.get(i).getName().equals(Name)){
                        var_dictionary.add(var_list.get(Name));
                        inz=true;
                    }
                }
                if(inz==false) {
                    value temp = new value(tokenList.get(i).getName(), 0);
                    var_dictionary.add(temp);
                    }
                }
            boolean res7 = tokenList.get(i).getType().equals("DIGIT");
            if (res7==true){
                int val = Integer.parseInt (tokenList.get(i).getName().trim ());
                value temp = new value(tokenList.get(i).getName(),val);
                var_dictionary.add(temp);
                }
            }
            iterator=tokenList.listIterator();
            currentToken=iterator.next();
        }
    }
