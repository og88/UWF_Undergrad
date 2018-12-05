/**
   This class implements a binary search tree whose
   nodes hold objects that implement the Comparable
   interface.
*/
public class BinarySearchTree
{

   private Node root, current, parent;
   /**
      A node of a tree stores a data item and references
      of the child nodes to the left and to the right.
   */
   private class Node
   {  
      public Comparable data;
      public Node left;
      public Node right;
   }

   /**
      Constructs an empty tree.
   */
   public BinarySearchTree()
   {  
      root = null;
   }
   
   /**
      Inserts a new node into the tree.
      @param obj the object to insert
   */
   public void add(Comparable obj) 
   {  
      Node newNode = new Node();
      newNode.data = obj;
      newNode.left = null;
      newNode.right = null;
      if (root == null) 
		  root = newNode;
      else 
		  addNode(newNode, root);
   }

   /**
      Tries to find an object in the tree.
      @param obj the object to find
      @return true if the object is contained in the tree
   */
   public boolean find(Comparable obj)
   {

      current = root;
      parent = null;    
		while (current != null)
      {
         int d = current.data.compareTo(obj);    
			if (d == 0)
			{ 
			  System.out.println("found ");
			  return true;
         }  
	      parent = current;      
			if (d > 0) 
			  current = current.left;
         else 
			  current = current.right;
      }
		parent = null;
      return false;
   }
   
   /**
      Tries to remove an object from the tree. Does nothing
      if the object is not contained in the tree.
      @param obj the object to remove
   */
   public void remove(Comparable obj)
   {
      boolean found = find(obj);

      if (!found) 
		   return;

      if (current.left == null || current.right == null)
      {
		   removeWithOneChild();    
		}
		else
		{
		   removeWithTwoChildren();
		}   
   }
   
	public void removeWithTwoChildren()
	{
      Node smallestParent = current;
      Node smallest = current.right;
      while (smallest.left != null)
      {
         smallestParent = smallest;
         smallest = smallest.left;
      }
      current.data = smallest.data;
      smallestParent.left = smallest.right;
	}
	
	public void removeWithOneChild()
	{
      Node newChild;
      if (current.left == null) 
         newChild = current.right;
      else 
         newChild = current.left;

      if (parent == null) // Found in root
         root = newChild;
      else if (parent.left == current)
         parent.left = newChild;
      else 
         parent.right = newChild;
	}
	
	
	public void print()
	{
	  System.out.println("Printing nodes:");  
	  printNodes(root);
     System.out.println("");
	}
	
      /**
         Prints the tree
         in sorted order.
      */ 
   public void printNodes(Node p)
   {  
     if(p != null)
	  {
		   printNodes(p.left);
			System.out.print(p.data);
			printNodes(p.right);
      }
   }
  
  
      /**
         Inserts a new node.
         @param newNode the node to insert
      */
      public void addNode(Node newNode, Node current)
      {  
         int comp = newNode.data.compareTo(current.data);
         if (comp < 0)
         {  
            if (current.left == null)
				  current.left = newNode;
            else addNode(newNode, current.left);
         }
         else if (comp > 0)
         {  
            if (current.right == null)
				  current.right = newNode;
            else 
				  addNode(newNode, current.right);
         }
      }
}



