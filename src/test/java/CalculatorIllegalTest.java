



import com.naasirjusab.infixpostfixcalculator.Calculator;
import com.naasirjusab.infixpostfixcalculator.MyQueue;
import java.util.Arrays;
import java.util.Collection;
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
public class CalculatorIllegalTest {
    private MyQueue<String> queueInfix;
    
  
    
    
    
    public CalculatorIllegalTest(MyQueue<String> queueInfix) {
        
        this.queueInfix=queueInfix;
        
    }
    
    
    @Parameterized.Parameters 
    public static Collection<Object[]> data()
    {       
        
        Object[][] datas = new Object[][]{  
           
              //CASE 1
              // ""
              {
              new MyQueue<String>(1){{push("");}}
              },
              
              //CASE 2
              // 4+2+
              {
              new MyQueue<String>(4){{push("4");push("+");push("2");push("+");}}
              },
              
              //CASE 3
              // )1+3(
              {
              new MyQueue<String>(5){{push(")");push("1");push("+");push("3");push("(");}}
              },
              
              //CASE 4
              // ( 1 + 3 ) 9 + 8
              {
              new MyQueue<String>(8){{push("(");push("1");push("+");push("3");push(")");push("9");push("+");push("8");}}
              },
              
              
              //CASE 5
              // ( 4 / 1 ) ) 
              {
                   new MyQueue<String>(5){{push("(");push("4");push("/");push("0");push(")");push(")");}}
              },
             
              
              //CASE 6
              // (3+5-9())
              {
                new MyQueue<String>(5){{push("(");push("3");push("+");push("5");push("-");push("9");push("(");push(")");push(")");}}
              },
              
              //CASE 7
              // (3)
              {
                  new MyQueue<String>(5){{push("(");push("3");push(")");}}
              },
              
              //CASE 8
              // 3hdow - 33
              {
                  new MyQueue<String>(5){{push("3hdow");push("-");push("33");}}
              },
              
              //CASE 9
              // 34 = 56
              {
                  new MyQueue<String>(5){{push("34");push("=");push("56");}}
              },
              
              //CASE 10
              // (34+)
              {
                  new MyQueue<String>(5){{push("(");push("34");push("+");push(")");}}
              },
              
              
        };
            
            
        return Arrays.asList(datas);
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testConversionIllegalEquation()
    {
       
        
        Calculator calc = new Calculator(queueInfix);
        
        MyQueue<String> obtainedPostfix = calc.convertInfixToPostfix();
        
        
    }
    
   
}
