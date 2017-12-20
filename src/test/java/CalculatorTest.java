

import com.naasirjusab.infixpostfixcalculator.Calculator;
import com.naasirjusab.infixpostfixcalculator.MyQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;




/**
 *
 * @author Naasir Jusab
 */
@RunWith(Parameterized.class)
public class CalculatorTest {
    private String strInfix;
    private MyQueue<String> postFix;
    private MyQueue<String> result;
  
    
    
    
    public CalculatorTest(String strInfix, MyQueue<String> postFix, MyQueue<String> result) {
        
        this.strInfix=strInfix;
        this.postFix = postFix;
        this.result=result;
    }
    
    
    @Parameterized.Parameters 
    public static Collection<Object[]> data()
    {       
        //5 cases are with Integers/No Parentheses
        //5-10 cases are with Integers/With Parentheses
        //10-15 cases are with Doubles/No Parentheses
        //15-20 cases are with Doubles/With Parentheses
        //20-25 mix of both
        Object[][] datas = new Object[][]{
            
              //CASE 1
              //1+2/4
              //1 2 4/+
              //answer : 1.50
            { new String("1 + 2 / 4"),
              new MyQueue<String>(5){{push("1");push("2");push("4");push("/");push("+");}},
              new MyQueue<String>(1){{push("1.50");}}      
            },
              //CASE 2
              //2+3*4
              //2 3 4*+
              //answer : 14.00
            { new String("2 + 3 * 4"),
              new MyQueue<String>(5){{push("2");push("3");push("4");push("*");push("+");}},
              new MyQueue<String>(1){{push("14.00");}}   
            },
              //CASE 3
              //3*4+2*5  
              //3 4*2 5*+
              //answer : 22.00
            { new String("3 * 4 + 2 * 5"),
              new MyQueue<String>(7){{push("3");push("4");push("*");push("2");push("5");push("*");push("+");}},
              new MyQueue<String>(1){{push("22.00");}}   
            },
              //CASE 4
              //3+4*5/6
              //3 4 5*6/+
              //answer : 6.33
            { new String("3 + 4 * 5 / 6"),
              new MyQueue<String>(7){{push("3");push("4");push("5");push("*");push("6");push("/");push("+");}},
              new MyQueue<String>(1){{push("6.33");}}   
            },
            
              //CASE 5
              //7+8-2*6/2
              //7 8+2 6*2/-
              //answer : 9
            { new String("7 + 8 - 2 * 6 / 2"),
              new MyQueue<String>(9){{push("7");push("8");push("+");push("2");push("6");push("*");push("2");push("/");push("-");}},
              new MyQueue<String>(1){{push("9.00");}}   
            },
              //CASE 6
              //(10+20*(4-5)/6)
              //10 20 4 5 - * 6 / +
              //answer : 6.67
            { new String("( 10 + 20 * ( 4 - 5 ) / 6 )"),
              new MyQueue<String>(9){{push("10");push("20");push("4");push("5");push("-");push("*");push("6");push("/");push("+");}},
              new MyQueue<String>(1){{push("6.67");}}   
            },
             //CASE 7
             //( 5 * ( 6 + 7) / 8 )
             //5 6 7 + * 8 /
             //answer : 8.12
            { new String("( 5 * ( 6 + 7 ) / 8 )"),
              new MyQueue<String>(7){{push("5");push("6");push("7");push("+");push("*");push("8");push("/");}},
              new MyQueue<String>(1){{push("8.12");}}   
            },
            
            
             //CASE 8
             //( ( 3 * 4 ) + (8 / 9 ) )
             //3 4 * 8 9 / +
             //answer : 12.89
            { new String("( ( 3 * 4 ) + ( 8 / 9 ) )"),
              new MyQueue<String>(7){{push("3");push("4");push("*");push("8");push("9");push("/");push("+");}},
              new MyQueue<String>(1){{push("12.89");}}   
            },
            
            
             //CASE 9
             //(5 / ( 6 - 7 + 8 ) ) * ( 9 - 5 ) * 7
             //5 6 7 - 8 + / 9 5 - * 7 *
             //answer : 20.00
            { new String("( 5 / ( 6 - 7 + 8 ) ) * ( 9 - 5 ) * 7"),
              new MyQueue<String>(13){{push("5");push("6");push("7");push("-");push("8");push("+");push("/");push("9");push("5");push("-");push("*");push("7");push("*");}},
              new MyQueue<String>(1){{push("20.00");}}   
            },
                
             //CASE 10
             //( ( -6 ) * 8 + 40 ) * 30
             //-6 8 * 40 + 30 *
             //answer : -240.00
            { new String("( ( -6 ) * 8 + 40 ) * 30"),
              new MyQueue<String>(7){{push("-6");push("8");push("*");push("40");push("+");push("30");push("*");}},
              new MyQueue<String>(1){{push("-240.00");}}   
            },
            
            //CASE 11
            //1.00 + 2.00 * 3.00 * 0.25
            //1.00 2.00 3.00 * 0.25 * +
            //answer : 2.50
            {
                new String("1.00 + 2.00 * 3.00 * 0.25"),
                new MyQueue<String>(7){{push("1.00");push("2.00");push("3.00");push("*");push("0.25");push("*");push("+");}},
                new MyQueue<String>(1){{push("2.50");}}
            },
                
            //CASE 12
            //25.00 - 30.00 * 2.00 / 0.50
            //25.00 30.00 2.00 * 0.50 / -
            //answer : -95.00
            {
                new String("25.00 - 30.00 * 2.00 / 0.50"),
                new MyQueue<String>(7){{push("25.00");push("30.00");push("2.00");push("*");push("0.50");push("/");push("-");}},
                new MyQueue<String>(1){{push("-95.00");}}
            },
            
            //CASE 13
            //25.00 / 2.00 * 7.00 - 500.00
            //25.00 2.00 / 7.00 * 500.00 -
            //answer : -412.50
            {
                new String("25.00 / 2.00 * 7.00 - 500.00"),
                new MyQueue<String>(7){{push("25.00");push("2.00");push("/");push("7.00");push("*");push("500.00");push("-");}},
                new MyQueue<String>(1){{push("-412.50");}}
            },
            
            //CASE 14
            //45.00 * 6.00 + 3.50 / 200.00 + 50.00 * 25.00
            //45.00 6.00 * 3.50 200.00 / + 50.00 25.00 * +
            //answer : 1520.02
            {
                new String("45.00 * 6.00 + 3.50 / 200.00 + 50.00 * 25.00"),
                new MyQueue<String>(11){{push("45.00");push("6.00");push("*");push("3.50");push("200.00");push("/");push("+");push("50.00");push("25.00");push("*");push("+");}},
                new MyQueue<String>(1){{push("1520.02");}}
            },
            
            //CASE 15
            //2.00 + 7000.00 / 350.00 * 900.00  - 2000.00 + 50.00
            //2.00 7000.00 350.00 / 900.00 * + 2000.00 - 50.00 +
            //answer : 16052.00
            {
                new String("2.00 + 7000.00 / 350.00 * 900.00 - 2000.00 + 50.00"),
                new MyQueue<String>(11){{push("2.00");push("7000.00");push("350.00");push("/");push("900.00");push("*");push("+");push("2000.00");push("-");push("50.00");push("+");}},
                new MyQueue<String>(1){{push("16052.00");}}
            },
            
            //CASE 16
            //( 2.00 + 7000.00 ) / ( 350.00 * ( 900.00  - 2000.00 ) ) + 50.00
            //2.00 7000.00 + 350.00 900.00 2000.00 - * / 50.00 +
            //answer : 49.98
            {
                new String("( 2.00 + 7000.00 ) / ( 350.00 * ( 900.00 - 2000.00 ) ) + 50.00"),
                new MyQueue<String>(11){{push("2.00");push("7000.00");push("+");push("350.00");push("900.00");push("2000.00");push("-");push("*");push("/");push("50.00");push("+");}},
                new MyQueue<String>(1){{push("49.98");}}
            },
            
            
            //CASE 17
            //( 7.00 + 8.00 * ( 56.00 / 9.00 ) ) / ( ( 250.00 + 25.00 ) * 3.00 ) + 0.0322
            //7.00 8.00 56.00 9.00 / * + 250.00 25.00 + 3.00 * / 0.0322 +
            //answer : 49.98
            {
                new String("( 7.00 + 8.00 * ( 56.00 / 9.00 ) ) / ( ( 250.00 + 25.00 ) * 3.00 ) + 0.0322"),
                new MyQueue<String>(15){{push("7.00");push("8.00");push("56.00");push("9.00");push("/");push("*");push("+");push("250.00");push("25.00");push("+");push("3.00");push("*");push("/");push("0.0322");push("+");}},
                new MyQueue<String>(1){{push("0.10");}}
            },
            
            //CASE 18
            //( ( 80.00 - 42.00 ) / 2.00 + ( 60.00 - 23.00 ) * 6.00 ) 
            //80.00 42.00 - 2.00 / 60.00 23.00 - 6.00 * +
            //answer : 241.00
            {
                new String("( ( 80.00 - 42.00 ) / 2.00 + ( 60.00 - 23.00 ) * 6.00 )"),
                new MyQueue<String>(11){{push("80.00");push("42.00");push("-");push("2.00");push("/");push("60.00");push("23.00");push("-");push("6.00");push("*");push("+");}},
                new MyQueue<String>(1){{push("241.00");}}
            },
            
            //CASE 19
            //45.00 * ( 50.00 / 2.00 + ( 32.00 * ( 22.00 + 24.00 - ( -6.00 ) ) ) + ( 72.00 * 2.00 ) ) 
            //45.00 50.00 2.00 / 32.00 22.00 24.00 + -6.00 - * + 72.00 2.00 * + *
            //answer : 82485.00
            {
                new String("45.00 * ( 50.00 / 2.00 + ( 32.00 * ( 22.00 + 24.00 - ( -6.00 ) ) ) + ( 72.00 * 2.00 ) )"),
                new MyQueue<String>(17){{push("45.00");push("50.00");push("2.00");push("/");push("32.00");push("22.00");push("24.00");push("+");push("-6.00");push("-");push("*");push("+");push("72.00");push("2.00");push("*");push("+");push("*");}},
                new MyQueue<String>(1){{push("82485.00");}}
            },
            
            //CASE 20
            //220.00 - 2.00 + ( 250.00 * 5.00 + ( 420.00 / 20.00 + ( 42.00 / 6.00 - ( 23.00 + 2.00 ) ) ) ) + 20.00
            //220.00 2.00 - 250.00 5.00 * 420.00 20.00 / 42.00 6.00 / 23.00 2.00 + - + + + 20.00 +
            //answer : 1491.00
            {
                new String("220.00 - 2.00 + ( 250.00 * 5.00 + ( 420.00 / 20.00 + ( 42.00 / 6.00 - ( 23.00 + 2.00 ) ) ) ) + 20.00"),
                new MyQueue<String>(17){{push("220.00");push("2.00");push("-");push("250.00");push("5.00");push("*");push("420.00");push("20.00");push("/");push("42.00");push("6.00");push("/");push("23.00");push("2.00");push("+");push("-");push("+");push("+");push("+");push("20.00");push("+");}},
                new MyQueue<String>(1){{push("1491.00");}}
            },
            
            //CASE 21
            //2.20 + 3.80 * ( 2 - 15 ) + ( 450 / 2 ) + ( 1.00 + 2.00 )
            //2.20 3.80 2 15 - * + 450 2 / + 1 2 + +
            //answer : 180.80
            {
                new String("2.20 + 3.80 * ( 2 - 15 ) + ( 450 / 2 ) + ( 1.00 + 2.00 )"),
                new MyQueue<String>(15){{push("2.20");push("3.80");push("2");push("15");push("-");push("*");push("+");push("450");push("2");push("/");push("+");push("1.00");push("2.00");push("+");push("+");}},
                new MyQueue<String>(1){{push("180.80");}}
            },
            
            //CASE 22
            //450 + 200 / ( 2.00 ) + 320 - ( ( 150.00 + ( 250.00 - 100.00 ) ) * 2 )
            //450 200 2.00 / + 320 + 150.00 250.00 100.00 - + 2 * -
            //answer : 270.00
            {
                new String("450 + 200 / ( 2.00 ) + 320 - ( ( 150.00 + ( 250.00 - 100.00 ) ) * 2 )"),
                new MyQueue<String>(15){{push("450");push("200");push("2.00");push("/");push("+");push("320");push("+");push("150.00");push("250.00");push("100.00");push("-");push("+");push("2");push("*");push("-");}},
                new MyQueue<String>(1){{push("270.00");}}
            },
            
            //CASE 23
            //( 200 + 200 - ( 450.25 - 250.25 + ( 27.50 / 2 + ( 45.00 - 22.50 * ( 17.50 * 2.00 ) ) ) ) )
            //200 200 + 450.25 250.25 - 27.50 2 / 45.00 22.50 17.50 2.00 * * - + + -
            //answer : 928.75
            {
                new String("( 200 + 200 - ( 450.25 - 250.25 + ( 27.50 / 2 + ( 45.00 - 22.50 * ( 17.50 * 2.00 ) ) ) ) )"),
                new MyQueue<String>(19){{push("200");push("200");push("+");push("450.25");push("250.25");push("-");push("27.50");push("2");push("/");push("45.00");push("22.50");push("17.50");push("2.00");push("*");push("*");push("-");push("+");push("+");push("-");}},
                new MyQueue<String>(1){{push("928.75");}}
            },
            
            //CASE 24
            //( 24 + 2 ) * ( 23 - 5.25 ) / ( 24.20 / 1.20 ) * ( 1.9 + 2.1 ) * ( 3.25 * 4 )  
            //24 2 + 23 5.25 - * 24.20 1.20 / / 1.9 2.1 + * 3.25 4 * *
            //answer : 1189.98
            {
                new String("( 24 + 2 ) * ( 23 - 5.25 ) / ( 24.20 / 1.20 ) * ( 1.9 + 2.1 ) * ( 3.25 * 4 )"),
                new MyQueue<String>(19){{push("24");push("2");push("+");push("23");push("5.25");push("-");push("*");push("24.20");push("1.20");push("/");push("/");push("1.9");push("2.1");push("+");push("*");push("3.25");push("4");push("*");push("*");}},
                new MyQueue<String>(1){{push("1189.98");}}
            },
            
            //CASE 25
            //5 * ( ( 25 + 35 ) * ( 75.25 * 2 ) / ( 25 - ( 100.25 - 32.50 ) ) )
            //5 25 35 + 75.25 2 * * 25 100.25 32.50 - - / *
            //answer : -1056.14
            {
                new String("5 * ( ( 25 + 35 ) * ( 75.25 * 2 ) / ( 25 - ( 100.25 - 32.50 ) ) )"),
                new MyQueue<String>(15){{push("5");push("25");push("35");push("+");push("75.25");push("2");push("*");push("*");push("25");push("100.25");push("32.50");push("-");push("-");push("/");push("*");}},
                new MyQueue<String>(1){{push("-1056.14");}}
            }
            
             
            };
        
        
        return Arrays.asList(datas);
    }
    
    
    @Test
    public void testConversion()
    {
         
        String[] values = strInfix.split(" ");
        MyQueue<String> queueInfix = new MyQueue<String>(10);
        for(String value : values)
            queueInfix.push(value);
        
        Calculator calc = new Calculator(queueInfix);
        
        MyQueue<String> obtainedPostfix = calc.convertInfixToPostfix();
        boolean isEqual = true;
        
        while(!obtainedPostfix.isEmpty())
        {
            if(!obtainedPostfix.pop().equals(postFix.pop()))
                isEqual=false;
        }
        
        assertTrue(isEqual);
        
    }
    
    
    @Test
    public void testResult()
    {
        String[] values = strInfix.split(" ");
        MyQueue<String> queueInfix = new MyQueue<String>(10);
        for(String value : values)
            queueInfix.push(value);
        Calculator calc = new Calculator(queueInfix);
        MyQueue<String> obtainedResult = calc.getResult();
        
        assertEquals(obtainedResult.pop(),result.pop());
    }
    
    
    
    

}
