
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Name: Ezinne Megwa
 * Class: CS 3345
 * Section: 003
 * Semester: Fall 2019
 * Project number 
 * Description: The task of this project is to implement in Java a red-black tree data structure. 
                However, the tree will be simplified – you only need to support insertion, not deletion. * Description:
 * 
 * 
 * 
 * 
 */

/**
 *
 * @author enmeg
 */
public class Main 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
        Scanner in;
        if (args.length!=2) 
        {
            System.out.print("Error Incorrect Arguments:" + Arrays.toString(args));
            System.exit(0);    
        } 
        try 
        {
           File input_file = new File(args[0]);
           in = new Scanner(input_file);
           File output_file = new File(args[1]); 
           PrintWriter  out;
           out = new PrintWriter(output_file);

           //IDedLinkedList<MyItem> LL = new IDedLinkedList(); 
           //Reads in the type of tree
           String treeType = in.next();
           
           RedBlackTree RBT = new RedBlackTree();

           String operation = "";
           String line = "";
           
           //String numb = "";
           
           String element = "";
           String strVal = "";
           int numVal = 0;
           int colon = 0;
           int lineno = 0;
           int foundNum = 0;


           
           boolean result = false;
           //List<Integer> name = new LinkedList<>();

           whileloop:
           while (in.hasNext()) 
           {
               if(treeType.compareTo("Integer") != 0 && treeType.compareTo("String") != 0)
               {
                   //Print an error
                   out.println("Only works for objects Integers and Strings");
                   
                   //Break out of the loop and end the program
                   break;
               }
               //else continue on as normal
               lineno++;
               
               //Reads the line
               line = in.next();
               //finds the colon in the line
               colon = line.indexOf(':');
               
               //colon was found
               if(colon != -1)
               {
                   //read the operation in
                   operation = line.substring(0, colon);
                   
                   //read the element to perform the operation on
                   element = line.substring(colon+1);
                   
                   //if the treeType is an Integer
                   if(treeType.compareTo("Integer") == 0)
                   {
                        numVal = Integer.parseInt(element);
                   }
                   //if the treeType is a String
                   if(treeType.compareTo("String") == 0)
                   {
                       strVal = element;
                   }
                  
               }
               else//colon was not found
               {
                   operation = line;
               }       

               switch (operation) 
               {
               case "End":
                   break whileloop;
               
               case "Insert":
                   try 
                   {
                       
                     if(treeType.compareTo("Integer") == 0)
                        result = RBT.insert(numVal);
                     
                     if(treeType.compareTo("String") == 0)
                        result = RBT.insert(strVal);
                     
                      //result = BST.insert(value);
                      //result = Insert the item into the linkedlist and get true or false 
                      out.println(result ? "True" :"False");
                      out.flush();
                   }
                     catch (Exception e)
                     {
                        out.println("Error in insert: IllegalArgumentException raised");
                        out.flush();
                    }

                   break;
                   
               case "Contains":
                   try 
                   {
                   //id = in.nextInt();
                   //MyItem item1 = LL.findID(id);
                      //foundNum = BST.findMin();
                      if(treeType.compareTo("Integer") == 0)
                        result = RBT.contains(numVal);
                     
                     if(treeType.compareTo("String") == 0)
                        result = RBT.contains(strVal);
                       
                   //Call the FindID method and printID method to print to the output file the entire item in a line. If the item is not found or the list is empty print Null
                   out.println(result ? "True" :"False");
                   out.flush();
                   }
                     catch (Exception e){
                        out.println("Error in conatins: IllegalArgumentException raised");
                        out.flush();
                    }
                   break;
               

               case "PrintTree":
                   //Call the printtotal method of the linkedlist and print the given int into the output file.
                   //int total = LL.printTotal();
                   //out.println(total);
                   String printTree = RBT.toString();
                   out.println(printTree);
                   out.flush();
                   break;

                default:
                   out.println("Error in line :" + operation);
                   out.flush();
                   in.nextLine();

               }

            }
           if(treeType.compareTo("Integer") != 0 && treeType.compareTo("String") != 0)
               {
                   //Print an error
                   out.println("Only works for objects Integers and Strings");
               }
            in.close();
            out.close();       
        }
        catch(Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        } 
        
    }
    
    
}
