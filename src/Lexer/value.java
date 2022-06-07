package Lexer;

public class value {
    private String name;
    private int value;
    public value(String Name, int Value) {
        name = Name;
        value = Value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "value{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
