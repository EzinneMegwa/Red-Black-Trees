/*
 * Name: Ezinne Megwa
 * Class: CS 3345
 * Section: 003
 * Semester: Fall 2019
 * Project number: #4
 * Description: The task of this project is to implement in Java a red-black tree data structure. 
                However, the tree will be simplified – you only need to support insertion, not deletion.
 * 
 * 
 * 
 * 
 */

/**
 *
 * @author enmeg
 */
public class RedBlackTree <E extends Comparable <E>>
{
    private static boolean RED = false;
    private static boolean BLACK = true;
    
    private static class Node <E extends Comparable <E>>
    {
        E element;
        Node leftChild;
        Node rightChild;
        Node parent;
        boolean color = RED;
        
        public Node(E val, boolean col, Node p)
        {
            element = val;
            leftChild = null;
            rightChild = null;
            parent = p;
            color = col;
        }
    }
    
    private Node root = null;
    
    
    public boolean insert(E element) throws NullPointerException
    {
        if(element == null)
        {
            System.out.println("Error in insert: NullPointerException raised");
            throw new NullPointerException();            
        }

        //check if element is already in the tree
        //if true
        if(contains(element))
        {
            return false;
        }
        //else element is not in the tree
        //insert by calling helper function
        root = insert(element, root, null);
        
        //balance the tree
        balance(element, root);
        
        return true;
    }
    public Node insert(E element, Node current, Node parentNode)
    {
        //if the tree is empty
        if(root == null)
        {
            current = new Node(element, BLACK, parentNode);
        }
        //tree is not empty but we are at the end of the tree
        if(current == null)
        {
            //create the root and make the color black
            current = new Node(element, RED, parentNode);
        }
        //if less than 0 then it is smaller and goes to the left
        if(element.compareTo((E)current.element) < 0)
        {
            current.leftChild = insert(element,current.leftChild, current);
        }
        //if greater than 0 then it is bigger and goes to the right
        else if(element.compareTo((E)current.element) > 0)
        {
            current.rightChild = insert(element, current.rightChild, current);
        }
        else    //else a duplicate - do nothing
            ;
        return current;
    }
    
    private Node balance(E element, Node current)
    {
        //if the node is at the end of the tree
        if(current == null)
            return current;
        //if the node is less than the current node
        if(element.compareTo((E)current.element) < 0)
            balance(element, current.leftChild);
        //if the node is more than the current node
        if(element.compareTo((E)current.element) > 0)
            balance(element, current.rightChild);
        //if the node is equal tothe curent node
        if(element.compareTo((E)current.element) == 0)
            //if the current node and the parent node are booth red
            if(current.color == RED && current.parent.color == RED)
                //rotate the node because double red situation
                return rotate(current);
        
        return current;
    }
    
    private Node rotate(Node current)
    {
        Node p = current.parent;
        Node g = null;
        Node u = getUncle(current);
        
        if(p.parent != null)
            g = p.parent;
        
        //Case 1: Uncle is black or null
        //Reconstruct
        if(u == null || u.color == BLACK)
        {
            return reconstruct(current);
        }
        
        //Case 2: Uncle is red
        //Recolor
        else
        {
            //recolor uncle and parent to black
            u.color = BLACK;
            p.color = BLACK;
            
            //if the grandparent is not the root
            //color it RED (the root must be black, so it cannot be RED)
            if(g != root)
            {
                g.color = RED;
                rotate(g);
            }
            
        }
        
        return current;        
    }
    
    //gets the uncle of the current node
    private Node getUncle(Node current)
    {
        Node par = current.parent;
        
        if(par != null)
        {
            Node grandP = par.parent;
            
            if(grandP != null)
            {
                if(grandP.leftChild == par)
                    return grandP.rightChild;
                if(grandP.rightChild == par)
                    return grandP.leftChild;
            }
        }
        //there is no uncle
        return null;
    }
    
    private Node reconstruct(Node current)
    {
        Node par = current.parent;
        Node gParent = null;
        
        //if the parent is not null
        if(par.parent != null)
        {
            gParent = par.parent;
            
            //if the GP's leftchild is parent
            if(gParent.leftChild == par)
            {
                //if the Parents left child is current
                //we have a left left situation
                if(par.leftChild == current)
                {
                    //leftleft situation
                    LeftLeft(current, par, gParent);
                }
                else
                {
                    //left right situation
                    LeftRight(current,par, gParent);
                }
            }
            //if GP's rightchild is parent
            if(gParent.rightChild == par)
            {
                //if parent right child is current
                //we have a right right situation
                if(par.rightChild == current)
                {
                    //rightright situation
                    RightRight(current, par, gParent);
                }
                else
                {
                    //rightleft situation
                    RightLeft(current, par, gParent);
                }
            }
        }
        
        return current;        
    }
    
    //
    private Node leftRotate(Node current)
    {
        Node cur_right = current.rightChild;
        current.rightChild = cur_right.leftChild;
        
        if(current.rightChild != null)
            current.rightChild.parent = current;
        cur_right.parent = current.parent;
        
        if(current.parent == null)
            root = cur_right;
        else if(current == current.parent.leftChild)
            current.parent.leftChild = cur_right;
        else
            current.parent.rightChild = cur_right;
        
        cur_right.leftChild = current;
        current.parent = cur_right;
        return current;
    }
    private Node rightRotate(Node current)
    {
        Node cur_left = current.leftChild;
        current.leftChild = cur_left.rightChild;
        
        if(current.leftChild != null)
            current.leftChild.parent = current;
        cur_left.parent = current.parent;
        
        if(current.parent == null)
            root = cur_left;
        else if(current == current.parent.leftChild)
            current.parent.leftChild = cur_left;
        else
            current.parent.rightChild = cur_left;
        
        cur_left.rightChild = current;
        current.parent = cur_left;
        return current;
        
    }
    
    private Node LeftLeft(Node current, Node par, Node gP)
    {
        //right rotate the grandparent of the current node
        rightRotate(gP);
        //swap the colors of grand parent and parent
        par.color = !par.color;
        
        gP.color = !gP.color;
        
        return current;
        
    }
    private Node LeftRight(Node current, Node par, Node gP)
    {
        //left rotate the parent
        leftRotate(par);
        //call leftlfet method
        LeftLeft(par, current, gP);
        
        return current;
    }
    
    private Node RightRight(Node current, Node par, Node gP)
    {
        leftRotate(gP);
        par.color = !par.color;
        
        gP.color = !gP.color;
        
        return current;
    }
    private Node RightLeft(Node current, Node par, Node gP)
    {
        rightRotate(par);
        
        RightRight(par, current, gP);
        
        return current;
        
    }
    
    //Checks the entire tree to find if the tree contains
    //the element it is looking for
    public boolean contains(E element)
    {
        return contains(element, root );
    }
    private boolean contains(E element, Node current)
    {
        //If the tree is empty
        if( current == null )
            return false;
        
        //The value being search for is less than the current node
        if( element.compareTo((E)current.element) < 0 )
            return contains( element, current.leftChild );
        
        //If the value being searched for is greater than the current node
        else if(element.compareTo((E)current.element) > 0 )
            return contains( element, current.rightChild );
        
        //else the values is equal
        else if(element.compareTo((E)current.element) == 0)
        {
            //the element is in the tree already
            //return true for found
            return true;
        }
        //else it is not in the tree
        return false;
    }
    
    public String toString()
    {
        StringBuffer rbTree = new StringBuffer();
        
        toString(rbTree,root);
        String p = rbTree.toString();
        return p;
       
    }
    private void toString(StringBuffer tree, Node current)
    {
        if (current == null) 
            return; 
  
        /* first print data of node */
        if(current.color == RED)
        {
            tree.append("*");
        }
        tree.append(current.element + " ");
        
        //System.out.print(current.element + " "); 
  
        /* then recur on left sutree */
        toString(tree, current.leftChild); 
  
        /* now recur on right subtree */
        toString(tree, current.rightChild);
        
        return;
    }
    
}
