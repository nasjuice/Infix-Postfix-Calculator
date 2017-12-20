package com.naasirjusab.infixpostfixcalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class is the Calculator class which can do 4 operations addition,
 * subtraction, multiplication and division. It even supports parentheses
 * through its algorithm. The class has three fields. The first one is received
 * by a user, an infixQueue which will be converted to a queuePostFix. The
 * operatorStack is a mechanism used by the calculator to keep track of the
 * operators to get its algorithm to work.
 *
 * @author Naasir Jusab
 */
public class Calculator {

    private MyQueue<String> queueInfix;
    private MyQueue<String> queuePostfix;
    private MyStack<String> operatorStack;
    private String[] operators;

    /**
     * This constructor instantiates a Calculator object with three fields. The
     * first field is a queueInfix which is given by the user. The second field
     * is a queuePostFix and the third field is an operatorStack. The last two
     * fields are used to perform an algorithm to get the result of an equation.
     *
     * @param queueInfix
     */
    public Calculator(MyQueue<String> queueInfix) {
        this.queueInfix = queueInfix;
        this.queuePostfix = new MyQueue<String>(10);
        this.operatorStack = new MyStack<String>(10);
        this.operators = new String[4];
        this.operators[0] = "*";
        this.operators[1] = "/";
        this.operators[2] = "+";
        this.operators[3] = "-";

    }

    /**
     * This method takes care of getting the result of the equation. It first
     * converts the infix to postfix. Then, it loops through the queuePostfix to
     * get the result.
     *
     * @return MyQueue size 1 contains a result
     */
    public MyQueue getResult() {
        convertInfixToPostfix();

        MyQueue<String> equationQueue = new MyQueue<>(3);
        MyStack<String> operandStack = new MyStack<>(2);

        String data;
        String result = "";
        //theres a value inside
        while (!queuePostfix.isEmpty()) {
            //get the value
            data = queuePostfix.pop();
            //if the data is an operand then start performing the arithmetic
            //because there should be two values in the operandStack by then
            //then push the result of the two to the operand stack.

            if (data.equals(operators[0]) || data.equals(operators[1]) || data.equals(operators[2]) || data.equals(operators[3])) {
                equationQueue.push(operandStack.pop());
                equationQueue.push(data);
                equationQueue.push(operandStack.pop());

                result = performArithmetic(equationQueue);

                operandStack.push(result);
            } //this means that it is an operand so you just push it to the stack
            else {
                operandStack.push(data);
            }

        }
        
        
        return formatResult(result);

    }

    /**
     * This method will go through each value in a infix queue to validate it
     * and then add it to a postfix queue.
     * @return MyQueue contain the postfix equation
     */
    public MyQueue<String> convertInfixToPostfix() {
              
        // gets previous item in the queueInfix
        String previous = getPrevious();
        //this counter helps to determine how many opening and closing 
        //parentheses there were in the equation
        int parenthesesCtr = addPrevious(previous);

        String current = "";
        
        //looping till its empty
        while (!queueInfix.isEmpty()) {
            //gets the current item in the queueInfix
            current = queueInfix.pop();
            //validate both values to see if they are proper
            validateCurrent(previous, current);
            //perform the necessary action
            parenthesesCtr = performAction(current, parenthesesCtr);

            //check if a closing parentheses was started and no open parentheses was created
            if (parenthesesCtr < 0) {
                throw new IllegalArgumentException("Cannot have closing parentheses there");
            }

            previous = current;
        }

        finalValidation(current,parenthesesCtr);

        //once you have gone through the queueInfix, you have to check if you
        //have operators left in your stack, if you do then you must push them
        while (!operatorStack.isEmpty()) {
            queuePostfix.push(operatorStack.pop());
        }
        
        return queuePostfix;

    }
    
    private void finalValidation(String current, int parenthesesCtr)
    {
        //check last item in equation i.e. 4+6+ should be invalid
        for (String op : operators) {
            if (current.equals(op)) {
                throw new IllegalArgumentException("Invalid last value");
            }
        }
        //this would mean that the number of closing and opening parentheses are not the same
        if (parenthesesCtr != 0) {
            throw new IllegalArgumentException("Parentheses have been misplaced");
        }
       
    }
    
    private int addPrevious(String previous)
    {
        int parenthesesCtr = 0;
        //push previous to its rightful data structure
        if (previous.equals("(")) {
            operatorStack.push(previous);
            parenthesesCtr++;
            
        } else {
            //no exceptions are thrown then it is a valid double
            double value = Double.parseDouble(previous);
            queuePostfix.push(previous);

        }
        
        return parenthesesCtr;
     
        
    }
       
    private MyQueue<String> formatResult(String result)
    {
        /**
         *  the problem with this was that divisions and multiplications have
         *  many zeros following the decimal point, by forcing two numbers after 
         *  the decimal point we are losing accuracy, but it is easier to test
         *  because we know that the results are correct. Ex: Calculator
         *  returns 5.766666666. Then, I would need my CalculatorTest to have a
         *  result of 5.766666666. Therefore, rather than comparing two huge 
         *  decimal numbers, I am limiting it to 5.76 and just comparing it to 
         *  5.76. We lose precision but, if the client ever needs precision we
         *  can just take out this format method.
           */
        
        //formatting result, should always have two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        double value = Double.parseDouble(result);

        String formattedResult = df.format(value);

        //adding the result to the Queue since the result returned needs to be
        //a queue
        MyQueue<String> resultQueue = new MyQueue<>(1);
        resultQueue.push(formattedResult);
        
        return resultQueue;
        
    }

  
    private int performAction(String current, int parenthesesCtr) {
        //This method will take care of performing necessary actions for the
        //current value and push them onto their respective data structures.
        
        double value;
        switch (current) {
            //nothing much to do with open parentheses just add it and inc ctr
            case "(":
                operatorStack.push(current);
                parenthesesCtr++;
                break;
            //pop everything from stack and dec ctr
            case ")":
                getOpenParentheses();
                parenthesesCtr--;
                break;
            //add to stack
            case "*":
            case "/":
                checkStackForMultiplicationAndDivision(current);
                break;
            //add to stack
            case "+":
            case "-":
                checkStackForAdditionAndSub(current);
                break;
            //with double you just add them to the queuePostfix, if it parses then it is a valid double
            default:
                value = Double.parseDouble(current);
                queuePostfix.push(current);
                break;

        }

        return parenthesesCtr;
    }


    private String getPrevious() {
        //This method gets the first item on the infix queue. It validates it and then
        //proceeds to add it to its respective data structure.
        
        /** checking if the size is at least 3 and there is is at least 1 operator 
         * otherwise it can't be an equation
         * doing it this way because we can't have a size method for the queue
         * so i am emptying the queue then re-populating it to see how many data
         * items it has
         * */
        int counter = 0;
        int operatorCounters = 0;
        ArrayList<String> content = new ArrayList<String>();
        while(!queueInfix.isEmpty())
        {
            String data = queueInfix.pop();
            content.add(data);
            counter++;
            if (data.equals(operators[0]) || data.equals(operators[1]) || data.equals(operators[2]) || data.equals(operators[3]))
                operatorCounters++;
            
            
        }
        if(counter <3 || operatorCounters < 1)
            throw new IllegalArgumentException("Not a valid equation");
        
        for(String data : content)
        {
            queueInfix.push(data);
            
        }
        
        //get first item in list
        String previous = queueInfix.pop();
        //check if previous is invalid char
        for (String op : operators) {
            if (previous.equals(")") || previous.equals(op)) {
                throw new IllegalArgumentException("Start of equation is illegal");
            }
        }

        
        return previous;
    }

    
    private void validateCurrent(String previous, String current) {
        //This method goes through a set of rules that an equation should follow to be valid.
        
        //if previous was a open parentheses then current cannot be an operator
        if (previous.equals("(")) {
            for (String op : operators) {
                if (current.equals(op)) {
                    throw new IllegalArgumentException("Operator cannot follow open parentheses");
                }
            }
          //if previous item was a parentheses parentheses then current cannot be an opening parentheses
        } else if (previous.equals(")")) {
            if (current.equals("(")) {
                throw new IllegalArgumentException("Need an operand between parentheses");
            }
            //need to check if it is not a number
            checkCurrent(current);            

            //check if current is not an open bracket if previous was an operator
        } else if (previous.equals(operators[0]) || previous.equals(operators[1]) || previous.equals(operators[2]) || previous.equals(operators[3])) {
            if (current.equals(")")) {
                throw new IllegalArgumentException("Cannot have closing parentheses here");
            }

        } //if we reach here then previous was a number must check that
        //current is not open parentheses
        //parsing value to see whether it is a valid double
        else {
            if (current.equals("(")) {
                throw new IllegalArgumentException("Must have operator after open parentheses");
            }
        }

    }
    
    private void checkCurrent(String current)
    {
        //this method will help determine wether the current value is a double
        boolean isEqual = true;

        //if its not an operator or a closing bracket then it is a number
        for(String op: operators)
            if(current.equals(op))
               isEqual = false;

        //check to see if it is not a closing bracket
        if(current.equals(")"))
            isEqual = false;

        //cannot have an double after a closing bracket
        //parsing value to see whether it is a valid double
        //if it is a valid double then it will throw another error message
        //saying you need to have an operator there
        if (isEqual) {
                double currentValue = Double.parseDouble(current);
                throw new IllegalArgumentException("Must have operator after closing parentheses");
            }
    }

    /**
     * This method takes care of performing the arithmetic between the operands
     * in the stack and the operator.
     *
     * @param equationQueue
     * @return
     */
    private String performArithmetic(MyQueue<String> equationQueue) {
        //get the values from the stack
        String lastOperand = equationQueue.pop();
        String operator = equationQueue.pop();
        String firstOperand = equationQueue.pop();
        Double firstInt = Double.parseDouble(firstOperand);
        Double lastInt = Double.parseDouble(lastOperand);

        String result = "";

        //check what operator it is then perform the proper arithmetic
        if (operator.equals("*")) {
            result = String.valueOf(firstInt * lastInt);
        } else if (operator.equals("/")) {
            result = String.valueOf(firstInt / lastInt);
        } else if (operator.equals("+")) {
            result = String.valueOf(firstInt + lastInt);
        } else if (operator.equals("-")) {
            result = String.valueOf(firstInt - lastInt);
        }

        return result;

    }

    /**
     * When you hit a close parentheses then you have to pop all the operators inside
     * the parentheses until you reach the open parentheses. Then, pop the open parentheses
     * because you do not need it anymore.
     */
    private void getOpenParentheses() {
        String data;
        if (!operatorStack.isEmpty()) {
            while (!operatorStack.peek().equals("(")) {
                queuePostfix.push(operatorStack.pop());
            }
            
            //pop out the open parentheses
            operatorStack.pop();
        }
        
        else 
            throw new IllegalArgumentException("No open parentheses for the"
            + " closing parentheses");
        
        

        
    }

    /**
     * This method will handle the data if it is a multiplication sign or a
     * division sign. You first check if the operator stack is not empty if it
     * is then you just push your value onto the stack. The other check peeks to
     * see if the operator is not a parentheses because you should not take them
     * out until you reach a closing parentheses.
     *
     * @param data * /
     */
    private void checkStackForMultiplicationAndDivision(String data) {

        if (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
            String operatorInStack = operatorStack.peek();
            if (operatorInStack.equals(operators[1]) || operatorInStack.equals(operators[0])) {
                queuePostfix.push(operatorStack.pop());
            }

        }

        operatorStack.push(data);

    }

    /**
     * This method will handle the data if it is a addition sign or a
     * subtraction sign. You first check if the operator stack is not empty if
     * it is then you just push your value onto the stack. The other check peeks
     * to see if the operator is not a parentheses because you should not take
     * them out until you reach a closing parentheses.
     *
     * @param data + -
     */
    private void checkStackForAdditionAndSub(String data) {
        while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
            queuePostfix.push(operatorStack.pop());
        }

        operatorStack.push(data);

    }

}
