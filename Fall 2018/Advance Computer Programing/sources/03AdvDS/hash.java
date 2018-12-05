   import java.io. FileReader;
   import java.io.BufferedReader;
   import java.io.IOException;

///////////////////////////////////////////////////////
// A class that handles spell-check operations on
// a file.
// This clas implements external chaining
// Programmer : John Coffey
// Date       : 11/22/2004
//////////////////////////////////////////////////////
   class hash
   {
      final int tablesize = 3000;
      final int chars = 26;
		
	   /////////////////////////////////
	   // A node class for our table
		/////////////////////////////////
       class node {
   
	      // Node constructor		
         public node(String st, node n)
         { 
            next = n; 
            word = st;
         }
      
		   // node attributes - the word and a link
         String word;
         node next;
      }
   
	   // instance variables for class hash
      node current;  // an access reference
      node [] table; // the hash table
   
      // a constructor for the hash class    
		public hash()
      {
         table = new node[tablesize];  // allocate the references
         for(int i = 0; i < tablesize;i++)
            table[i] = null;  // initialize them
      }
   
      // Add an entry to the table = string is the word, where is the index    
		public void add(String word, int where)
      {
         table[where] = new node(word, table[where]);
      }
   
       public void buildtable()
      {
      
         FileReader theFile;
         BufferedReader fileIn = null;
         String line;
         int where;
      
         try
         {
            theFile =new FileReader("words.txt");
            fileIn = new BufferedReader(theFile);
            while((line = fileIn.readLine()) !=null) {
            // System.out.println(line);
               where = hash(line);
               add(line,where);
            }
         }
         catch(IOException e)
           {System.out.println (" We got an exception which is " + e); }
         finally
         {
            try
            {
               if(fileIn != null) fileIn.close();
            }
                catch(IOException e)
               {System.out.println (e); }    
         } 
      }
   
      // the hash function - primitive but usable    
		public int hash(String st)
      {
         int tot = 0;
         for(int i = 0; i < st.length(); i++)
            tot += (int)st.charAt(i) + i;
         tot = tot * tot;
         if (tot < 0)
            tot = -1 * tot;
         return tot % tablesize;
      }
   
      // print out the hash table "empty list" means nothing hashed to
		// that location    
		public void list()
      {
         node current;
         for (int i = 0; i < tablesize; i++) {
            System.out.print( i + " ");
            if(table[i] == null)
               System.out.println("empty list");
            else {
               current = table[i];
               while(current != null) {
                  System.out.println(current.word);
                  current = current.next;
               }
            }
         }
      }
   
      // lookup grabs the location in the table and searches the list
      public boolean lookup(String word)
      {
         int where = hash(word);
         boolean found = false;
         node current = table[where];
         while(current !=null && !found)
         {
            if (current.word.equals(word))
               return true;
            else
               current = current.next;
         }
         return found;
      }
   
	   // this is for you!!!!
      public void suggest(String word)
      {
	     oneLetterDeleted(word);	  
        // twoLettersReversed(word);	  
	    //  oneLetterAdded(word);   
	   }
	
	
	public void oneLetterAdded(String word)
	{
	  int i, j,k;
     char ch;
     StringBuffer st = new StringBuffer(word);
     for(i = 0; i < st.length(); i++)   // one letter added
		 {
		   ch = st.charAt(i);
		   st.deleteCharAt(i);
		   System.out.println("one letter added = " + st);
			st.insert(i,ch);
		 }
	}	

	public void twoLettersReversed(String word)
	{
	  int i, j,k;
     char ch1,ch2;
     for(i = 0; i < word.length() - 1; i++)  // two letters reversed
	    {
		   StringBuffer st = new StringBuffer(word);
			ch1 = st.charAt(i);
			ch2 = st.charAt(i+1);
         
			st.deleteCharAt(i);
			st.deleteCharAt(i);
			st.insert(i,ch1);
			st.insert(i,ch2);			
			System.out.println("two letter reversed = " + st);
       }
	}	


	public void oneLetterDeleted(String word)
	{
	  int i, j, k,l; 
	  int x = 0;
	  for(i = 0; i < word.length(); i++)  
		 {
	      StringBuffer st = new StringBuffer(word);
			for(k = 0; k < chars; k++) {
			  st.insert(i, (char)('a' + k));
			  System.out.println(x++ + " one letter deleted = " + st);
			  st.deleteCharAt(i);
			}
		 }  
	}


	
	
       public static void main(String args[])
      { 
         hash h = new hash();
         h.buildtable();
         // h.list();
         System.out.println(" word = technical " +  h.lookup("technical"));
         System.out.println(" word = ability " +  h.lookup("ability"));
         System.out.println(" word = ready " +  h.lookup("ready"));
         System.out.println(" word = section " +  h.lookup("section"));
         System.out.println(" word = redy " +  h.lookup("redy"));
         System.out.println(" word = abiltiy " +  h.lookup("abiltiy"));
			h.suggest("abcde");
      }
   }