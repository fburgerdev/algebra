package algebra;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    // isNumeric
    private static boolean isNumeric(String str) { 
        try {  
            Double.valueOf(str);
            return true;
        }
        catch(NumberFormatException e) {  
            return false;  
        }
    }

    // tokenize
    private static List<String> tokenize(String source) {
        source = source.replaceAll(" ", "");
        source = source.replaceAll("\\^", ";\\^;");
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
    // removeBrackets
    private static void removeBrackets(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i + 3 < list.size()) {
                Object left = list.get(i + 0);
                Object right = list.get(i + 2);
                if (left instanceof String leftString && right instanceof Expr rightExpr) {
                    if (list.get(i + 1) instanceof String openBracket && openBracket.equals("(")) {
                        if (list.get(i + 3) instanceof String closeBracket && closeBracket.equals(")")) {
                            if (leftString.equals("exp")) {
                                list.remove(i);
                                list.remove(i);
                                list.remove(i);
                                list.remove(i);
                                list.add(i, new Exp(rightExpr));
                                removeBrackets(list);
                                return;
                            }
                            else if (leftString.equals("log")) {
                                list.remove(i);
                                list.remove(i);
                                list.remove(i);
                                list.remove(i);
                                list.add(i, new Log(rightExpr));
                                removeBrackets(list);
                                return;
                            }
                        }
                    }
                }
            }
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
    // parseBinary
    private static void parseBinary(List<Object> list, String operator) {
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
                                case "^" -> list.add(i, new Exp(new Mul(new Log(leftExpr), rightExpr)));
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
    
    // parse
    public static Expr parse(String string) {
        List<Object> list = new ArrayList<>(tokenize(string));
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof String str) {
                // Number
                if (isNumeric(str)) {
                    list.set(i, new Num(Double.parseDouble(str)));
                }
                // Variable
                else if ((str.chars()).allMatch(Character::isAlphabetic)) {
                    if (!str.equals("exp") && !str.equals("log")) {
                        list.set(i, new Var(str));
                    }
                }
            }
        }
        for (int i = 0; i < 100; ++i) {
            if (list.size() == 1) {
                break;
            }
            removeBrackets(list);
            parseBinary(list, "^");
            parseBinary(list, "*");
            parseBinary(list, "/");
            parseBinary(list, "+");
            parseBinary(list, "-");
        }
        if (list.size() == 1) {
            return (Expr)list.get(0);
        }
        return null;
    }
}