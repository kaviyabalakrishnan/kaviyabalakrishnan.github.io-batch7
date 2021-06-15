/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class MultiSender extends Thread
 {
   public String str,node,sys,port,xVal,yVal,signal;
   UserController ucc;
   RsuController rcc;
   AttackerController atc;
    AuthorityController acc;
   
   public MultiSender(String nodename,String portnumber,String sysnumber,String xVal,String yVal,String signa,UserController uc)
    {
         this.node=nodename;
         this.sys=sysnumber;
         this.port=portnumber;
         this.xVal=xVal;
         this.yVal=yVal;
         this.signal=signa;
         this.ucc=uc;
         str="CARINFO"+"$"+nodename+"$"+sysnumber+"$"+portnumber+"$"+xVal+"$"+yVal+"$"+signa;
         start();
    }
   
   public MultiSender(String nodename,String portnumber,String sysnumber,String xVal,String yVal,RsuController rsu)
    {
       this.node=nodename;
       this.port=portnumber;
       this.sys=sysnumber;
       this.xVal=xVal;
       this.yVal=yVal;
       this.rcc=rsu;
       str="RSUINFO"+"$"+nodename+"$"+portnumber+"$"+sysnumber+"$"+xVal+"$"+yVal;
       start();
    }
   public MultiSender(String nodename,String portnumber,String sysnumber,String xVal,String yVal ,AttackerController at)
     {
         
         this.node=nodename;
         this.sys=sysnumber;
         this.port=portnumber;
         this.xVal=xVal;
         this.yVal=yVal;
         this.atc=at;
         str="ATTACKINFO"+"$"+nodename+"$"+sysnumber+"$"+portnumber+"$"+xVal+"$"+yVal;
         start();
     }
    public MultiSender(String nodename,String portnumber,String sysnumber,String xVal,String yVal,AuthorityController acu)
    {
       this.node=nodename;
       this.port=portnumber;
       this.sys=sysnumber;
       this.xVal=xVal;
       this.yVal=yVal;
       this.acc=acu;
       str="AUTHORITYINFO"+"$"+nodename+"$"+portnumber+"$"+sysnumber+"$"+xVal+"$"+yVal;
       start();
    }
    
    @Override
    public void run()
     {
       try
        {
          InetAddress in = InetAddress.getByName("225.89.67.48");
          MulticastSocket ms = new MulticastSocket(4567);
          ms.joinGroup(in);
          DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), in, 4567);
          ms.send(dp);
          Thread.sleep(2000);
        }
       catch(Exception e)
        {
           e.printStackTrace();
        }
     }
 }
