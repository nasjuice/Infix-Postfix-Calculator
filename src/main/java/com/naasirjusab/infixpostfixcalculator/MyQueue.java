
package com.naasirjusab.infixpostfixcalculator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is a Queue data structure that stores strings. These strings can 
 * be operators or operands. This Queue data structure has the functionalities
 * of a Collections Queue. These functionalities are push(),pop() and isEmpty().
 * @author Naasir Jusab
 */
public class MyQueue<T> {
    
    private ArrayList<T> list;

    private int start;
    private int end;

    /**
     * This constructor instantiates a queue with a fixed size. It sets the
     * values of start and end to 0 because the arrayList is empty at the
     * beginning.
     */
    public MyQueue(int size)
    {
 
        list = new ArrayList<T>(size);
        this.start =0;
        this.end =0;
        
    }
   
    
    /**
     * The queue is empty when the start and end are the same values.
     * It will return true if they are the same values otherwise it will 
     * return false.
     * @return 
     */
    public boolean isEmpty()
    {
        return start == end;
        
    }
    

    /**
     * This method will check if the list is not empty. If it is not then it 
     * will increment the start position(FIFO) and return it. If
     * it is empty then it will throw an exception
     * @return 
     */
    public T pop()
    {
        if(!isEmpty())
        {
           T data = list.get(start);
           start++;
           return data;
        }
        else
            throw new IndexOutOfBoundsException("List is empty");
        
        
    }
    
    /**
     * This method will add content to the Queue data structure. 
     * It will increment the end because the list has grown and so the end
     * needs to grow as well.
     * @param str 
     */
    public void push(T str)
    {
        list.add(str);
        end++;
        
    }
   
   

    
   
}
