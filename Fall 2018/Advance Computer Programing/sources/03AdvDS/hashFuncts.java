
/**
 Programmer : John Coffey
  */
   class hashFuncts
   {
      final int tablesize = 3000;	
      
		public int hash1(String st)  // multiplication
      {
         int tot = 0;
         for(int i = 0; i < st.length(); i++)
            tot += (int)st.charAt(i);
         tot = tot * tot;
         tot >>>= 1; // puts zero (0) in high-order bit
         return tot % tablesize;
      }
		
	   public int hash2(String st)
      {
         int tot = 1;
         for(int i = 0; i < st.length(); i++)
            tot += ((int)st.charAt(i) * (i+1));
         tot *= tot;      
			tot >>>= 1;  // tot >>= 1 extends the sign bit
			System.out.println("tot = " + tot); 
         return tot % tablesize;
      }

		
   
      public void demo1()
		{
         System.out.println(" word = technical = " +  hash1("technical"));
         System.out.println(" word = ability = " +  hash1("ability"));
         System.out.println(" word = ready = " +  hash1("ready"));
         System.out.println(" word = section = " +  hash1("section"));
         System.out.println(" word = red; pos = " +  hash1("red"));
			System.out.println(" word = dre; pos = " +  hash1("dre"));			
		}

      public void demo2()
		{
         System.out.println(" word = technical = " +  hash2("technical"));
         System.out.println(" word = ability = " +  hash2("ability"));
         System.out.println(" word = ready = " +  hash2("ready"));
         System.out.println(" word = section = " +  hash2("section"));
         System.out.println(" word = red; pos = " +  hash2("red"));
		   System.out.println(" word = dre; pos = " +  hash2("dre"));		
		}
	
       public static void main(String args[])
      { 
         hashFuncts h = new hashFuncts();
         h.demo1();
			h.demo2();

      }
   }