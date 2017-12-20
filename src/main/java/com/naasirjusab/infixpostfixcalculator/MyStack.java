
package com.naasirjusab.infixpostfixcalculator;

import java.util.ArrayList;

/**
 *
 * This class is a Stack data structure that stores strings. These strings can 
 * only be operators. This Stack data structure has the functionalities
 * of a Collections Stack. These functionalities are push(),pop() and isEmpty().
 * @author Naasir Jusab
 */
public class MyStack<T> {
    
    private ArrayList<T> list;
    private int stackPtr;
    
    /**
     * This constructor instantiates a stack with a fixed size. It sets the value
     * of stackPtr to -1 because it allows us to check if there is a value in
     * the stack.
     * @param size of the ArrayList
     */
    public MyStack(int size)
    {
        list = new ArrayList<T>(size);
        stackPtr=-1;
    }
    /**
     * This method checks if the stack is empty. The stack is empty only 
     * if the stackPtr == -1 because if something is added then the stackPtr
     * would have a value -1>.
     * @return 
     */
    public boolean isEmpty()
    {
        return stackPtr ==-1;
    }

    
    /**
     * This method will pop the last element in the stack. It will check if the 
     * stack is not empty which would mean that there is content that can be pop.
     * It will return the content at the index of the stackPtr if it is not 
     * empty otherwise, it will throw an Exception.
     * @return 
     */
    public T pop()
    {
        if(!isEmpty())
            return list.remove(stackPtr--);

        
        else
            throw new IndexOutOfBoundsException("List is empty");

    }
    
    /**
     * This method will add content to the Stack data structure. 
     * It will also increment the stackPtr to know where the current position is.
     * @param t data that will be added
     */
    public void push(T t)
    {
        list.add(t);
        stackPtr++;
           
    }
    
    /**
     * This method will peek in the list to see if there is content inside. It
     * will get the last element in the list if the list is not empty. If it
     * is empty then, it will throw an exception.
     * @return 
     */
    public T peek()
    {
        if(!isEmpty())
            return list.get(stackPtr);
        
        
        else
            throw new IndexOutOfBoundsException("List is empty");
        
    
    }
    
    
}
