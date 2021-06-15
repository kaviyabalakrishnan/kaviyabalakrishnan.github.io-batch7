/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.util.LinkedHashMap;


public class Encryption 
{
//        public String g1="";
        public int len;
        public String pub,pri;
        public LinkedHashMap pubkey;
        public LinkedHashMap prikey;
    	public String encrypt(String str)
	    {
                String g1="";
		int i;
		int b[]=new int[20000];
		int a[]=new int[20000];
		String g=" ";
                //String enkey=key;
		char x[]=new char[50];
                char d,s;

		String h=str;
		//System.out.println("Message Entered:"+h);
                len=h.length();
		//System.out.println("StringLength:"+len);
		//System.out.print("HASH-CODE:");

		for(i=0;i<len;i++)
		{
                    int aa= h.charAt(i);
                    a[i]=aa;
		}

//		System.out.print("\n");
                //System.err.print("HEX-CODE:");
		for(i=0;i<len;i++)
                {
                    g=" ";
                    b[i]=a[i]%16;
                    a[i]=a[i]/16;
                    if(a[i]==10)
                    {
			s='A';
			g=g+s;
                    }
                    else if(a[i]==11)
                    {
			s='B';
			g=g+s;
                    }
                    else if(a[i]==12)
                    {
			s='C';
			g=g+s;
                    }
                    else if(a[i]==13)
                    {
			s='D';
			g=g+s;
                    }
                    else if(a[i]==14)
                    {
                        s='E';
			g=g+s;
                    }
                    else if(a[i]==15)
                    {
			s='F';
			g=g+s;
                    }
                    else
                    {
			s=(char) a[i];
			g=g+a[i];
                    }
                    if(b[i]==10)
                    {
			s='A';
			g=g+s;
			//System.out.print(g.trim());
                    }
                    else if(b[i]==11)
                    {
			s='B';
			g=g+s;
                        //System.out.print(g.trim());
                    }
                    else if(b[i]==12)
                    {
			s='C';
			g=g+s;
			//System.out.print(g.trim());
                    }
                    else if(b[i]==13)
                    {
			s='D';
			g=g+s;
			//System.out.print(g.trim());
                    }
                    else if(b[i]==14)
                    {
			s='E';
			g=g+s;
			//System.out.print(g.trim());
                    }
                    else if(b[i]==15)
                    {
			s='F';
			g=g+s;
			//System.out.print(g.trim());
                    }
                    else
                    {
			s=(char) b[i];
			g=g+b[i];
			//System.out.print(g.trim());
                    }
                    g1=g1+g.trim();
                    //enkey="encrypt";
		}
                //System.out.println(g1);
	        return g1;
              }

    public String encrypt(String data, String data1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
