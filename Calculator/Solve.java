import java.lang.Math;
import java.util.*;

public class Solve {

    private String expression;
    private int result;

    private static int priority(char operation) {
        int priorityIndex;
        switch (operation) {
            case '+':
            case '-': priorityIndex = 0; break;
            case '*':
            case '/': priorityIndex = 1; break;
            case '^': priorityIndex = 2; break;
            default: priorityIndex = -1;
        }
        return priorityIndex;
    }
    private static int reverse(int n, int zero_index) {
        int reversedNumber = 0, remainder;

        while(n != 0)
        {
            remainder = n%10;
            reversedNumber = reversedNumber*10 + remainder;
            n /= 10;
        }

        for(int i=0; i<zero_index; i++) {
            reversedNumber *= 10;
        }

        return reversedNumber;
    }


    public Solve(String expr) {
        expression = expr;
        result = 0;
    }

    public String getExpression() {
        return expression;
    }

    public int getResult() {
        return result;
    }

    public boolean balancedBrackets(String expr) {
        Stack stack = new Stack();

        for(int i=0; i<expr.length(); i++) {
            switch (expr.charAt(i)) {
                case '(': stack.push(expr.charAt(i)); break;
                case ')':
                    if(stack.empty()) {
                        return false;
                    }
                    else {  stack.pop(); }
                    break;
            }
        }
        return stack.empty();
    }

    public boolean checkExpression(String expr) {
        return balancedBrackets(expr);
    }

    public String translate(String expr) {

        StringBuilder newExpr = new StringBuilder();

        Stack stack = new Stack();
        stack.push('#'); //stack bottom

        for(int i = 0; i < expr.length(); i++) {
            if (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                newExpr.append(expr.charAt(i));
            } else {
                if (expr.charAt(i) == '(') {
                    stack.push('(');
                } else {
                    if (expr.charAt(i) == ')') {
                        char x = (char) stack.pop();
                        while (x != '(') {
                            newExpr.append(x);
                            x = (char) stack.pop();
                        }
                    } else {
                        if (expr.charAt(i) == '+' || expr.charAt(i) == '-' || expr.charAt(i) == '*' || expr.charAt(i) == '/' || expr.charAt(i) == '^') {
                            char x = (char) stack.pop();
                            while (x != '(' && priority(x) >= priority(expr.charAt(i))) {
                                newExpr.append(x);
                                x = (char) stack.pop();
                            }
                            newExpr.append(' ');
                            stack.push(x);
                            stack.push(expr.charAt(i));
                        }
                    }
                }
            }

        }

        char x = (char)stack.pop();
        while(x != '#') {
            newExpr.append(x);
            x = (char)stack.pop();
        }

        return newExpr.toString();
    }

    public void solveExpression(String expr) {

        String newExpr = translate(expr);

        Stack stack = new Stack();

        int temp = 0, index = 1,zero_index = 0;
        int x=0, y=0, z=0;
        for(int i=0; i<newExpr.length(); i++) {
            if(newExpr.charAt(i) >= '0' && newExpr.charAt(i) <= '9') {
                if(newExpr.charAt(i) == '0') { zero_index++;}
                temp += index*((int)newExpr.charAt(i) - (int)'0');
                index *= 10;
            }
            else if(newExpr.charAt(i) == ' ' && (newExpr.charAt(i-1) >= '0' && newExpr.charAt(i-1) <= '9')) {
                stack.push(reverse(temp,zero_index));
                temp = 0;
                index = 1;
            }
            else {
                if(newExpr.charAt(i) == '+' || newExpr.charAt(i) == '-' || newExpr.charAt(i) == '*' || newExpr.charAt(i) == '/' || newExpr.charAt(i) == '^') {
                    if(newExpr.charAt(i-1) >= '0' && newExpr.charAt(i-1) <= '9') {
                        stack.push(reverse(temp,zero_index));
                        temp = 0;
                        index = 1;
                    }

                    y = (int)stack.pop();
                    x = (int)stack.pop();
                    switch (newExpr.charAt(i)) {
                        case '+':
                            z = x + y; break;
                        case '-':
                            z = x - y; break;
                        case '*':
                            z = x * y; break;
                        case '/':
                            z = x / y; break;
                        case '^':
                            z = (int)Math.pow(x,y); break;
                    }
                    stack.push(z);
                }
            }
        }

        result = (int) stack.pop();
    }

}
