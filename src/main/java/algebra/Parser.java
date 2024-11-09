package algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);
            return true;
        }
        catch(Exception e) {  
            return false;  
        }
    }  
    
    public static ArrayList<String> split(String source, String delimiters) {
        ArrayList<String> out = new ArrayList<>();
        String current = new String();
        int level = 0;
        for (char ch : source.toCharArray()) {
            if (ch == '(') {
                ++level;
            }
            if (level == 0 && delimiters.indexOf(ch) >= 0) {
                if (!current.isEmpty()) {
                    out.add(current);
                    current = new String();
                }
                out.add(String.valueOf(ch));
            }
            else {
                current += ch;
            }
            if (ch == ')') {
                --level;
            }
        }
        if (!current.isEmpty()) {
            out.add(current);
        }
        return out;
    } 
    public static List<String> tokenize(String source) {
        source = source.replaceAll(" ", "");
        source = source.replaceAll("\\+", ";\\+;");
        source = source.replaceAll("\\-", ";\\-;");
        source = source.replaceAll("\\*", ";\\*;");
        source = source.replaceAll("\\/", ";\\/;");
        source = source.replaceAll("\\(", ";\\(;");
        source = source.replaceAll("\\)", ";\\);");
        List<String> list = new ArrayList<>(Arrays.asList(source.split(";")));
        list.removeIf(str -> str.isBlank());
        return list;
    }
    public static void removeBrackets(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i + 2 < list.size()) {
                Object left = list.get(i + 0);
                Object middle = list.get(i + 1);
                Object right = list.get(i + 2);
                if (left instanceof String leftBracket && right instanceof String rightBracket) {
                    if (middle instanceof Expr expr) {
                        if (leftBracket.equals("(") && rightBracket.equals(")")) {
                            list.remove(i);
                            list.remove(i);
                            list.remove(i);
                            list.add(i, expr);
                            removeBrackets(list);
                            return;
                        }
                    }
                }
            }
        }   
    }
    public static void parseBinary(List<Object> list, String operator) {
        for (int i = 0; i < list.size(); i++) {
            if (i + 2 < list.size()) {
                Object left = list.get(i + 0);
                Object middle = list.get(i + 1);
                Object right = list.get(i + 2);
                if (left instanceof Expr leftExpr && right instanceof Expr rightExpr) {
                    if (middle instanceof String middleString) {
                        if (middleString.equals(operator)) {
                            list.remove(i);
                            list.remove(i);
                            list.remove(i);
                            switch (operator) {
                                case "+" -> list.add(i, new Add(leftExpr, rightExpr));
                                case "-" -> list.add(i, new Add(leftExpr, new Mul(new Num(-1.0), rightExpr)));
                                case "*" -> list.add(i, new Mul(leftExpr, rightExpr));
                                case "/" -> list.add(i, new Mul(leftExpr, new Exp(new Mul(new Log(rightExpr), new Num(-1.0)))));
                                default -> {
                                }
                            }
                            parseBinary(list, operator);
                            return;
                        }
                    }
                }
            }
        }
    }
    public static List<Object> parse(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof String str) {
                // Number
                if (isNumeric(str)) {
                    list.set(i, new Num(Double.parseDouble(str)));
                }
                // Variable
                else if ((str.chars()).allMatch(Character::isAlphabetic)) {
                    list.set(i, new Var(str));
                }
            }
        }
        for (int i = 0; i < 100; ++i) {
            if (list.size() == 1) {
                break;
            }
            removeBrackets(list);
            parseBinary(list, "*");
            parseBinary(list, "/");
            parseBinary(list, "+");
            parseBinary(list, "-");
        }
        return list;
    }
    // parse
    public static Expr parse(String string) {
        List<Object> list = parse(new ArrayList<>(tokenize(string)));
        if (list.size() == 1) {
            return (Expr)list.get(0);
        }
        return null;
    }
}