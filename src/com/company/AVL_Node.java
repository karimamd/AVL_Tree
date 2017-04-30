package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kareem on 23-Apr-17.
 */

    public class AVL_Node{
    static AvlNode<String> dictionary;

        public  static  void main(String args[])
        {

            //must put file path and without .txt
            File f=new File("E:\\self learning\\trailsAndTesting\\src\\kareem.c");
            //function must be called between try and catch
            try{
                dictionary=loadDictionary(f);
            }catch(IOException e)
            {
                e.printStackTrace();
            }


        }

    private static class AvlNode<String>
    {

        String element; // The data in the node
        AvlNode<String> left; // Left child
        AvlNode<String> right; // Right child
        int height; // Height

        // Constructors

        //making a node with no children assigned
        AvlNode( String theElement )
        {
            this( theElement, null, null );
        }

        //making a new node with left and right children
        AvlNode(String theElement, AvlNode<String> lt, AvlNode<String> rt )
        {
            element = theElement;
            left = lt;
            right = rt;
            height = 0;
        }


    }
    public static AvlNode<String> loadDictionary(File f)throws IOException
    {
        //file reader object takes a file
        FileReader fr=new FileReader(f);
        //buffer takes a file reader object
        BufferedReader br=new BufferedReader(fr);
        //variable to store each line read
        String line;
        //list of lines of program
        AvlNode<String> dictionary=insert("",null);

        while((line=br.readLine())!=null)
        {

            dictionary=insert(line.trim(),dictionary);


        }
        dictionary=remove("",dictionary);

        //close readers stream
        br.close();
        fr.close();
        return dictionary;
    }


    /**
     * Return the height of node t, or -1, if null.
     */
    private static int height(AvlNode<String> t)
    {
        return t == null ? -1 : t.height;

    }
     void printHeight(AvlNode<String> t)
    {
        System.out.println(t.height);
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */

    //insert is a recursive method , keeps calling itself till finds an empty place to be inserted in
    private static AvlNode<String> insert(String x, AvlNode<String> t)
    {
        if( t == null )
            return new AvlNode<>( x, null, null );

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ; // Duplicate; do nothing
        //at end of method calls balance on the main root
        //height of root is updated in balance method
        return balance( t );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    //method returns the tree/node after fully balancing it
    private static AvlNode<String> balance(AvlNode<String> t)
    {
        if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                //right rotation with left chile
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );

        else if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                //left rotation with right child as new parent
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;

    }



    /**
     * Right rotation of root
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private static AvlNode<String> rotateWithLeftChild(AvlNode<String> k2)
    {
        AvlNode<String> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Left Rotation of root
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private static AvlNode<String> rotateWithRightChild(AvlNode<String> k2)
    {
        AvlNode<String> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max( height( k2.right ), height( k2.left ) ) + 1;
        k1.height = Math.max( height( k1.right ), k2.height ) + 1;
        return k1;
    }


    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private static AvlNode<String> doubleWithLeftChild(AvlNode<String> k3)
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }


    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k3 with new right child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private static AvlNode<String> doubleWithRightChild(AvlNode<String> k3)
    {
        k3.right = rotateWithLeftChild( k3.right );
        return rotateWithRightChild( k3 );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private static AvlNode<String> remove( String x, AvlNode<String> t )
    {
        if( t == null )
            return t; // Item not found; do nothing

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        //if wanted element is the root
        else if( t.left != null && t.right != null ) // Two children
        {
            //exchange least element in right sub tree with root and then remove that last element
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        //if one of children ==null then replace root by the other child
        else
            t = ( t.left != null ) ? t.left : t.right;
        return balance( t );
    }


    /**
      * Internal method to find the smallest item in a subtree.
      * @param t the node that roots the subtree.
      * @return node containing the smallest item.
      */
 private static AvlNode<String> findMin(AvlNode<String> t)
 {
     if( t == null )
         return null;
     else if( t.left == null )
         return t;
     return findMin( t.left );
     }

     public AvlNode<String> search(String element,AvlNode <String> t)
     {
         if( t == null )
             return null;

         int compareResult = element.compareTo( t.element );

         if( compareResult < 0 )
             t= search( element, t.left );
         else if( compareResult > 0 )
             t= search( element, t.right );

         return t;
     }

    void printDictionarySize(AvlNode<String> dictionary)
    {
        //TODO: implement
    }

/*
    public static ArrayList<String> readLines(File f)throws IOException
    {
        //file reader object takes a file
        FileReader fr=new FileReader(f);
        //buffer takes a file reader object
        BufferedReader br=new BufferedReader(fr);
        //variable to store each line read
        String line;
        //list of lines of program
        ArrayList<String> lines=new ArrayList<>();

        while((line=br.readLine())!=null)
        {
            lines.add(line);

        }

        //close readers stream
        br.close();
        fr.close();

        return  lines;
    }
*/



    }
