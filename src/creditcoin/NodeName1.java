/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.net.InetAddress;
import java.util.Random;


public class NodeName1 
{
    String sysname,portno,rsuNo,signal,Id;
    Random r=new Random();
    
    public String sysname()
     {
        try
         {
            sysname=InetAddress.getLocalHost().getHostName().toString();
         }
        catch(Exception e)
         {
            e.printStackTrace();
         }
        return sysname;
     }
    
    public String portn()
     {
        int p = r.nextInt(9999);
        portno = String.valueOf(p);
        return portno;
     }
//    public String noden()
//      {
//        int nodename = (r.nextInt(9999));
//        nodeno = "N"+nodename;
//        return nodeno;
//      }
    
      public String rsun()
       {
          int rsuName=(r.nextInt(9999));
          rsuNo="RSU"+rsuName;
          return rsuNo;
       }
      
      public String signal()
       {
            int sig=r.nextInt(10);
            signal= String.valueOf(sig);
            return signal;
       }
      public  String Id()
       {
            int rsuName=(r.nextInt(9999));
          rsuNo="ID"+rsuName;
          return rsuNo;
       }
}
