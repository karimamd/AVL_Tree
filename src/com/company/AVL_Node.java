package com.company;



/**
 * Created by Kareem on 23-Apr-17.
 */
public class AVL_Node {
    private static class AvlNode<AnyType>
    {
         // Constructors
         AvlNode( AnyType theElement )
         { this( theElement, null, null ); }

         AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
         { element = theElement; left = lt; right = rt; height = 0; }

         AnyType element; // The data in the node
         AvlNode<AnyType> left; // Left child
         AvlNode<AnyType> right; // Right child
         int height; // Height
    }


    /**
     * Return the height of node t, or -1, if null.
      */
 private int height( AvlNode<AnyType> t )
 {
         return t == null ? -1 : t.height;

         }

    /**
      * Internal method to insert into a subtree.
      * @param x the item to insert.
      * @param t the node that roots the subtree.
      * @return the new root of the subtree.
      */
 private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t )
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
         return balance( t );
         }

         private static final int ALLOWED_IMBALANCE = 1;

         // Assume t is either balanced or within one of being balanced
         private AvlNode<AnyType> balance( AvlNode<AnyType> t )
 {
         if( t == null )
             return t;

         if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
             if( height( t.left.left ) >= height( t.left.right ) )
             t = rotateWithLeftChild( t );
         else
         t = doubleWithLeftChild( t );
         else
         if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
             if( height( t.right.right ) >= height( t.right.left ) )
             t = rotateWithRightChild( t );
         else
         t = doubleWithRightChild( t );

         t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
         return t;
         }



          /**
           * Rotate binary tree node with left child.
           * For AVL trees, this is a single rotation for case 1.
           * Update heights, then return new root.
           */
                  private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 )
        {
         AvlNode<AnyType> k1 = k2.left;
         k2.left = k1.right;
         k1.right = k2;
         k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
         k1.height = Math.max( height( k1.left ), k2.height ) + 1;
         return k1;
         }


          /**
           * Double rotate binary tree node: first left child
           * with its right child; then node k3 with new left child.
           * For AVL trees, this is a double rotation for case 2.
           * Update heights, then return new root.
           */
                  private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 )
         {
         k3.left = rotateWithRightChild( k3.left );
         return rotateWithLeftChild( k3 );
         }
    /**
  * Internal method to remove from a subtree.
  * @param x the item to remove.
  * @param t the node that roots the subtree.
  * @return the new root of the subtree.
  */
         private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t )
        {
         if( t == null )
             return t; // Item not found; do nothing

         int compareResult = x.compareTo( t.element );

         if( compareResult < 0 )
             t.left = remove( x, t.left );
         else if( compareResult > 0 )
             t.right = remove( x, t.right );
         else if( t.left != null && t.right != null ) // Two children
             {
             t.element = findMin( t.right ).element;
             t.right = remove( t.element, t.right );
             }
         else
         t = ( t.left != null ) ? t.left : t.right;
         return balance( t );
         }


}
