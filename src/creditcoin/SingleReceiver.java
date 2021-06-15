/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import static creditcoin.LoginController.nodecar;
import static creditcoin.FXMLDocumentController.rsuLoc;
import static creditcoin.MultiReceiver.allHashValue;

import static creditcoin.MultiReceiver.allcarportnum;
import static creditcoin.MultiReceiver.allcarsysnum;
import static creditcoin.UserController.neighbor;
import static creditcoin.MultiReceiver.allrsusysnum;
import static creditcoin.MultiReceiver.allrsuportnum;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;

public class SingleReceiver extends Thread{

     public String node, sys,dest,rsu,curLoc,responders,currentCar;
     public static String initializer ;
      AuthorityController acc;
     int portno;
     MultiReceiver mr;
     UserController ucc;
     Encryption ecc = new Encryption();
     RsuController rcc;
     public int loca;
     Observer o = new Observer();
     TreeMap vehiStrength=new TreeMap();
     public static Vector allCars=new Vector();
     ImageView image;
     int count=0;
     int countt=0;
      AttackerController atc;
      String data1=AuthorityController.auid;
      MD5 enc = new MD5();
     public final Timeline move = new Timeline();
     public static String encvalue1 = "";
     
     public SingleReceiver(String nodename, String portnumber, String sysnumber, UserController uc, MultiReceiver mrec) 
     {
          this.node = nodename;
          this.sys = sysnumber;
          this.portno = Integer.parseInt(portnumber);  
          mr = mrec; 
          
          o = uc.getObserver();
          ucc = uc;
          start();
     }
    public SingleReceiver(String nodename,String portnumber,String sysnumber,RsuController rc)
     {
          this.node = nodename;
          this.sys = sysnumber;
          this.portno = Integer.parseInt(portnumber);
          this.rcc = rc;
          
          o=rc.getObserver();
          start();
     }
    
     public SingleReceiver(String nodename, String portnumber, String sysnumber, AttackerController at, MultiReceiver mrec) {
        this.node = nodename;
        this.sys = sysnumber;
        this.portno = Integer.parseInt(portnumber);

        o = at.getObserver();
        mr = mrec;
        atc = at;
        start();
    }
    public SingleReceiver(String Name, String portnumber, String sysnumber, AuthorityController ac) {

        this.node = Name;
        this.portno = Integer.parseInt(portnumber);
        this.sys = sysnumber;
        this.acc = ac;

        o = ac.getObserver();
        start();
    }
    @Override
    public void run() {
        try
         {
              ServerSocket ss = new ServerSocket(portno);
              
              while (true)
               {
                  Socket s = ss.accept();
                  ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                  String status = (String) ois.readObject();
                  
                  if(status.equalsIgnoreCase("dataToCar"))
                    {
                      String data=(String)ois.readObject();
                      String carDestination=(String)ois.readObject();
                      int giveCoins=(int)ois.readObject();
                      
                      JOptionPane.showMessageDialog(null, "Request Received In vehicle:"+carDestination);
                    
                      Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            o.setData(data);
                        }
                       });
                    }
                  else if (status.equals("Critical")) {
                    String neighName = (String) ois.readObject();
                    String nodeName = (String) ois.readObject();
                    int speed = (int) ois.readObject();
                    updateSpeed(speed);

                    // call change lane method
                    String data = "Lane Change !!";
                    String encdata = ecc.encrypt(data);
                    checkLane(encdata, nodeName);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            o.setData("Neighbour Node Is Too Close");
                        }
                    });
                } 
                  else if (status.equals("Break1")) {
                    String neighNode = (String) ois.readObject();
                    int neighXpos = (int) ois.readObject();
                    int neighYpos = (int) ois.readObject();
                    int speed = (int) ois.readObject();
                    String recInfo = (String) ois.readObject();
                    String encvalue = (String) ois.readObject();
                   // String data = "1234";
                   String auid=AuthorityController.auid;
                    System.out.println("***********************************" + encvalue);
                   
                    System.out.println("***************%%%%%%%%%%%%%%%%%********************" + encvalue1);
                    
                        checkPositionBreak1(neighXpos, neighYpos, neighNode, speed, recInfo, encvalue);
                    
                } 
                  else if(status.equalsIgnoreCase("responseToCar"))
                   {
                      String data=(String)ois.readObject();
                      String carDestination=(String)ois.readObject();
                      String responders=(String)ois.readObject();
                      
                      JOptionPane.showMessageDialog(null, "Response Received In vehicle:"+carDestination);
                      
                      Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            o.setData(data);
                            o.setCount(responders);
                        }
                       });
                   }
                  else if(status.equalsIgnoreCase("RSU"))
                   {
                      String data=(String)ois.readObject();
                      String rsu=(String)ois.readObject();
                      String sourceNode=(String)ois.readObject();
                      String portno=(String)ois.readObject();
                      String curLoc=(String)ois.readObject();
                      String count=(String)ois.readObject();
                      String signalStrength=(String)ois.readObject();
                      
                       if(rcc.pathinfodata.get(0).getNodename().equalsIgnoreCase(""))
                        {
                          rcc.pathinfodata.clear();
                        }
                      rcc.pathinfodata.add(new Table(sourceNode,portno,curLoc,data,signalStrength));
                      
                         responders=count;
                         System.out.println("responders--------------->"+responders);
                         
                         try{    
                       JSONObject jsonObject = new JSONObject();
		        jsonObject.put("source",sourceNode);
		        jsonObject.put("port",portno);
		        jsonObject.put("location", curLoc);
                          jsonObject.put("rsu", rsu);
                            jsonObject.put("data", data);
		        String jstr = jsonObject.toJSONString();
		
//                          PythonCall call = new PythonCall();
//                          call.addTransaction(jsonObject.toJSONString(), PythonCall.SENT);
//                          call.mineChain(PythonCall.MINE);
//                          call.mineChain(PythonCall.RECEIVE);
                          String url="http://localhost:"+5000+"/";
                         
		
		BlockChain.addTransaction(jsonObject.toString(),url
				+ BlockChain.Store);
		BlockChain.mineChain(url+ BlockChain.Mine);
                                  
                      }
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                         
                         
                         
                         
                         
                   }else if (status.equals("UpdateDetails")) {
                    String nodename = (String) ois.readObject();
                    String portnum = (String) ois.readObject();
                    String id = (String) ois.readObject();
                  

                    System.out.println("id------------>" + id);
                    if (acc.pathinfodata.get(0).getNodename().equalsIgnoreCase("")) {
                        acc.pathinfodata.clear();
                    }
                    acc.pathinfodata.add(new Table1(nodename, portnum, id));

                
                   

                }
                  
                  else if(status.equalsIgnoreCase("data"))
                    {
                      String data=(String)ois.readObject();
                      String rsuDestination=(String)ois.readObject();
                      String signal=(String)ois.readObject();

                      if(this.node.equalsIgnoreCase(rsuDestination))
                       {
                           JOptionPane.showMessageDialog(null, "Request Reached RSU:"+rsuDestination);
                           
                            Platform.runLater(new Runnable() {
                               @Override
                               public void run() {
                                       o.setData(data);
                               }
                           });
                            
                           String carDesti=UserController.trafficRequestVehicle; 
                           
                           String message="There Is Traffic!!";
                           String carsysno=(String)allcarsysnum.get(carDesti).toString().trim(); 
                           String carportno=(String)allcarportnum.get(carDesti).toString().trim();
                           
                           try
                            {
                               Socket s1=new Socket(carsysno, Integer.parseInt(carportno));
                               ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream());
                              
                               oos.writeObject("responseToCar");
                               oos.writeObject(message);
                               oos.writeObject(carDesti);
                               oos.writeObject(responders);
                               oos.close();
                               s1.close();                     
                            }
                           catch(Exception e)
                            {
                               e.printStackTrace();
                            }
                       }
                   }
                  else if(status.equals("vehicleResponse"))
                   {
                      count++;
                      
                      String data=(String)ois.readObject();
                      String rsuDestination=(String)ois.readObject();  
                      String sourceNode=(String)ois.readObject();
                      String signalStrength=(String)ois.readObject();
                      
                       if(countt==0)
                        {
                          initializer=sourceNode; 
                        }
                          countt++;
                          
                      JOptionPane.showMessageDialog(null, "Vehicle Response Received In RSU:"+rsuDestination);
                    
                      String portno1=allcarportnum.get(sourceNode).toString();
                      ImageView sourceCar=(ImageView)nodecar.get(sourceNode);
                      
                      int y=(int)sourceCar.getTranslateY();
                      
                      if(y>1000)
                       {
                         curLoc="Guindy";   
                       }
                      else if(y<1000)
                       {
                         curLoc="Koyembedu";
                       }
                      
                      if(rcc.pathinfodata.get(0).getNodename().equalsIgnoreCase(""))
                        {
                          rcc.pathinfodata.clear();
                        }
                      rcc.pathinfodata.add(new Table(sourceNode,portno1,curLoc,data,signalStrength));
                      
                   // send data to 1500 rsu from 500 rsu
                      String neighbours=(String)neighbor.get(rsuDestination);
                      Vector allNei=new Vector();
                      String temp[] = neighbours.split("\n");
                      
                      for(String t : temp)
                       {
                         allNei.add(t);
                       } 
                                 
                      for(int i=0;i<allNei.size();i++)
                       {
                         String ele=(String)allNei.get(i);
                         if(ele.length()>2)
                           {
                             rsu=ele;
                           }
                       } 
                               
                     JOptionPane.showMessageDialog(null, "Forwarding Response To RSU:"+rsu);
                               
                     String sysno=(String)allrsusysnum.get(rsu).toString().trim();
                     String portno=(String)allrsuportnum.get(rsu).toString().trim();
                      
                     try
                      {
                        Socket s1=new Socket(sysno, Integer.parseInt(portno));
                        ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream());   
                               
                        oos.writeObject("RSU");
                        oos.writeObject(data);
                        oos.writeObject(rsu);
                        oos.writeObject(sourceNode);
                        oos.writeObject(portno1);
                        oos.writeObject(curLoc);
                        oos.writeObject(String.valueOf(count));
                        oos.writeObject(signalStrength);
                        oos.close();
                        s1.close();
                      }
                     catch(Exception e)
                      {
                         e.printStackTrace();
                      }
                   }
                  if (status.equals("Break")) {

                    String neighNode = (String) ois.readObject();
                    int neighXpos = (int) ois.readObject();
                    int neighYpos = (int) ois.readObject();
                    int speed = (int) ois.readObject();
                    String recInfo = (String) ois.readObject();
                    String encvalue = (String) ois.readObject();
                    String data="Corrupted Message";
                    System.out.println("***********************************" + encvalue);
                    String encvalue1 = enc.send(recInfo,data1);
                    System.out.println("***************%%%%%%%%%%%%%%%%%********************" + encvalue1);
                    if (encvalue1.equals(encvalue)) {
                        checkPositionBreak(neighXpos, neighYpos, neighNode, speed, recInfo, encvalue);
                    } else {
                        checkPositionBreak1(neighXpos, neighYpos, neighNode, speed, recInfo, encvalue);
                    }
                    // checkPositionBreak(neighXpos, neighYpos, neighNode, speed, recInfo, encvalue);

                }    
                 else if(status.equals("coinUpdate"))
                  {
                      int coin=(int)ois.readObject();

                      String existingCoins=ucc.creditCoins.getText().toString();
                      int total=Integer.valueOf(existingCoins)+coin;
                      Platform.runLater(new Runnable() {
                          @Override
                          public void run() {
                              o.setCreditCoin(String.valueOf(total));
                          }
                      });
                  }
                 else if(status.equals("creditCoinUpdate"))
                  {
                     int coin=(int)ois.readObject();
                     
                     String existing=ucc.creditCoins.getText().toString();
                     
                     int total=Integer.parseInt(existing)-coin;
                     
                     Platform.runLater(new Runnable() {
                         @Override
                         public void run() {
                              o.setCreditCoin(String.valueOf(total));
                         }
                     });
                  }
                 
                  else if (status.equals("Critical")) {
                    String neighName = (String) ois.readObject();
                    String nodeName = (String) ois.readObject();
                    int speed = (int) ois.readObject();
                    updateSpeed(speed);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
               }
         }
        catch(Exception e)
         {
            e.printStackTrace();
         }
    }  
    
    
    public void startCar(String nodenmae, int speed) {
        ImageView nodeImage = (ImageView) nodecar.get(nodenmae);
        int xPosition = (int) nodeImage.getTranslateX();
        int yPosition = (int) nodeImage.getTranslateY();
        if (move.getStatus() == Animation.Status.RUNNING) {
            updateSpeed(speed);
        } else {
            dynamicNodes(nodenmae, xPosition, yPosition, speed);
        }
    }
    public void updateSpeed(int speed) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                o.setSpeed(String.valueOf(speed));
            }
        });
    }
    
    public void dynamicNodes(String node, double xPos, double yPos, int speed) {
        loca = (int) yPos;
        image = (ImageView) nodecar.get(node);
      //  nodespeed.put(node, speed);
        move.setCycleCount(Timeline.INDEFINITE);
        move.getKeyFrames().add(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int speed1 = (Integer.parseInt(ucc.speedd.getText().toString())) / 2;

                loca = loca - speed1;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        image.setTranslateY(loca);
                        o.setyPosition(String.valueOf(loca));
                    }
                });
            }
        }));
        move.play();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                o.setSpeed(String.valueOf(speed));

            }
        });
    }
    
    
    public void checkPositionBreak(int neighXpos, int neighYos, String nodeName, int speed, String receiveInfo) {

        String currentxVal = ucc.latitude.getText().toString();
        String currentyVal = ucc.longitude.getText().toString();
        double cxvalue = Double.valueOf(currentxVal);
        double cyvalue = Double.valueOf(currentyVal);
        int checkXVal = (int) cxvalue;
        int checkYVal = (int) cyvalue;
        int difference = Math.abs(neighXpos - checkXVal);

        if (difference <= 20 && (neighYos < checkYVal)) {
            mr.applyBreak(node, checkXVal, checkYVal, speed, receiveInfo);
            Platform.runLater(new Runnable() {
                @Override
                public void run() 
                 {
                    o.setSpeed(String.valueOf(speed));
                 }
               });    
          } 
    }
    
    
   public void signalBreak()
    {
        final Timeline carSignal = new Timeline();
        carSignal.setCycleCount(Timeline.INDEFINITE);
        carSignal.getKeyFrames().add(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
             {
                   String currentyVal = ucc.longitude.getText().toString();
                   if(Integer.valueOf(currentyVal)<=120)
                    {
                       Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                         o.setSpeed("0");
                       }
                      });
                    }
             }
           }));
        carSignal.play();
    }
    
  public void checkLeftNeigh(int position) {
        Vector<String> leftNeigh = ucc.Left_neighbour;
        boolean b = checkPosition(leftNeigh, position);
        if (b) {
            upadteXpos(String.valueOf(position - 20));
        } else{
            JOptionPane.showMessageDialog(null, "Neighbour To Close");
         }
     }
  
  public void checkRightNeigh(int position) {
        Vector<String> rightNeigh = ucc.Right_neighbour;
        boolean b = checkPosition(rightNeigh, position);
        if (b) {
            upadteXpos(String.valueOf(position + 20));
        } else {
            JOptionPane.showMessageDialog(null, "Neighbour To Close");
        }
     }


 public boolean checkPosition(Vector<String> neighNode, int xposition) {
        boolean b = true;
        for (String name : neighNode) {
            HashMap<String, Integer> hashMap = NodeStatus.map.get(name);
            int xposi = hashMap.get("XPOS");
            int yposi = hashMap.get("YPOS");
            int diff = Math.abs(xposi - xposition);
            int diffeY = Math.abs(yposi - ucc.yPosition);
            if ((diff <= 30) && (diffeY <= 40)) {
                return false;
            } else {
                b = true;
            }
        }
        return b;
    }  
 
  public void upadteXpos(String position) {
        ImageView nodeImage = (ImageView) nodecar.get(node);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                o.setxPosition(position);
                nodeImage.setTranslateX(Integer.parseInt(position));
            }
        });
    }
     public void checkLane(String data, String node) {
        String speed = ucc.speedd.getText().toString();
        if (checkPosition(ucc.Left_neighbour, ucc.xPosition)) {
            upadteXpos(String.valueOf(ucc.xPosition - 20));
            updateSpeed(Integer.parseInt(speed) + 2);
        } else if (checkPosition(ucc.Right_neighbour, ucc.xPosition)) {
            upadteXpos(String.valueOf(ucc.xPosition + 20));
            updateSpeed(Integer.parseInt(speed) + 2);
        }

        for (int i = 0; i < ucc.Front_neighbour.size(); i++) {
            String frontNeighbour = (String) ucc.Front_neighbour.get(i);

            String sysno = allcarsysnum.get(frontNeighbour).toString().trim();
            String portno = allcarportnum.get(frontNeighbour).toString().trim();

            try {
                Socket s = new Socket(sysno, Integer.parseInt(portno));
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject("LaneChangeMessage");
                oos.writeObject(data);
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//     public void dynamicAmbulanceNodes(String node, int xPos, int yPos, int speed) {
//
//        loca = (int) yPos;
//        image = (ImageView) nodeAmbulance.get(node);
//
//        ambulancemove.setCycleCount(Timeline.INDEFINITE);
//        ambulancemove.getKeyFrames().add(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                int speed = (Integer.parseInt(amc.speedd.getText().toString())) / 2;
//                loca = loca - speed;
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        image.setTranslateY(loca);
//                        o.setyPosition(String.valueOf(loca));
//                    }
//                });
//            }
//        }));
//        ambulancemove.play();
//
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                o.setSpeed(String.valueOf(speed));
//            }
//        });
//    }

//--------------------------Used Code end----------------------------------------------
    public void updatetable(String nodename, String authority, String portnumber, String id) {

        String nname = nodename;
        String pname = portnumber;
        String ID = id;
       

        String auvalue = nname + pname + ID;
        
        String auhash = enc.send(auvalue,data1);
        
        
       
        allHashValue.put(ID, auhash);
       
        String port = authority.trim();

        String sysno = (String) allrsusysnum.get(port).toString().trim();
        String portnum = (String) allrsuportnum.get(port).toString().trim();
       
        try {
            Socket s = new Socket(sysno, Integer.parseInt(portnum.trim()));
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

            oos.writeObject("UpdateDetails");
            oos.writeObject(nodename);
            oos.writeObject(portnumber);
            oos.writeObject(id);

            oos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void request(String sourceNode,String coins,String signalStrength) throws IOException
     {
           JOptionPane.showMessageDialog(null, "Requesting Neighbour Vehicles To Respond To Traffic Status Request!!");
           String messageRequest="Requesting To Respond!!";
                
           String neigh=(String)neighbor.get(sourceNode);
                
           Vector allNeiResponse=new Vector();
           String tem[] = neigh.split("\n");
           for(String cars : tem)
            {
              allNeiResponse.add(cars);
            }
                 
           for(int i=0;i<allNeiResponse.size();i++)
            {
              String element=(String)allNeiResponse.get(i);
                     
              if(element.length()<2)
                {
                  String carName=element;
                  allCars.add(carName);
                }
            }

           int sendCoins=1;  
           String totalCoins=ucc.creditCoins.getText().toString();
           int totalCreditCoins=Integer.parseInt(totalCoins);
           for(int i=0;i<allCars.size();i++)
            {
              String carNodeDestination=(String)allCars.get(i);
              String sysno1=(String)allcarsysnum.get(carNodeDestination).toString().trim();
              String portno1=(String)allcarportnum.get(carNodeDestination).toString().trim();
                     
              Socket s1=new Socket(sysno1,Integer.parseInt(portno1));
              ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream());
                       
              oos.writeObject("dataToCar");
              oos.writeObject(messageRequest);
              oos.writeObject(carNodeDestination);
              oos.writeObject(sendCoins);
              oos.close();
              s1.close();
           }
    }
    
   public void requestTrafficStatus(String sendText,String neighbour,String destination,String sourceNode,String coins,String signalStrength) throws IOException
    {
            String rsuDestination=  (String) rsuLoc.get(destination);
            
            Vector data=new Vector();
            data.add(sendText);
            
            int giveCoins=4;
            int balanceCoins=Integer.valueOf(coins)-giveCoins;
                 
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
              o.setCreditCoin(String.valueOf(balanceCoins));
             }
            });
           
                Vector allNei=new Vector();
                String temp[] = neighbour.split("\n");
                for(String t : temp)
                 {
                   allNei.add(t);
                 }                
                  
                 for(int i=0;i<allNei.size();i++)
                  {
                     String ele=(String)allNei.get(i);
                      if(ele.length()>2){
                           dest=ele;   
                       }
                  }
                 
                  String rsudestination=dest;
                 
                  String sysno=(String)allrsusysnum.get(dest).toString().trim();
                  String portno=(String)allrsuportnum.get(dest).toString().trim();
                   
                  try
                   {
                      Socket s1=new Socket(sysno,Integer.parseInt(portno));
                      ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream());
                      
                      oos.writeObject("data");
                      oos.writeObject(sendText);
                      oos.writeObject(rsudestination);
                      oos.writeObject(signalStrength);
                      
                      oos.close();
                      s1.close();
                   }
                  catch(Exception e)
                   {
                     e.printStackTrace();
                   }
               
                  
                  String sysno1=(String)allcarsysnum.get(initializer).toString().trim();
                  String portno1=(String)allcarportnum.get(initializer).toString().trim();
                  
                 try
                   {
                        Socket s1=new Socket(sysno1,Integer.parseInt(portno1));
                        ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream()); 
                        
                        oos.writeObject("coinUpdate");
                        oos.writeObject(giveCoins);
                        
                        System.out.println("Give Coins***********"+giveCoins);
                        oos.close();
                        s1.close();
                   }
                 catch(Exception e)
                   {
                     e.printStackTrace();
                   }
                 
                 // blockchain code
                 
                 
    }
   
    public void updateTrafficStatus(String sendText,String neighbour,String sourceNode,String signalStrength)
    {
                Vector allNei=new Vector();
                String temp[] = neighbour.split("\n");
                for(String t : temp)
                 {
                   allNei.add(t);
                 }                
                  
                 for(int i=0;i<allNei.size();i++)
                  {
                     String ele=(String)allNei.get(i);
                      if(ele.length()>2){
                           dest=ele;   
                       }
                  }
               
                  String sysno=(String)allrsusysnum.get(dest).toString().trim();
                  
                  System.out.println("-------------"+sysno);
                  
                  
                  String portno=(String)allrsuportnum.get(dest).toString().trim();
                 
                  try
                   {
                      Socket s1=new Socket(sysno,Integer.parseInt(portno));
                      ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream());
                      oos.writeObject("vehicleResponse");
                      oos.writeObject(sendText);
                      oos.writeObject(dest);
                      oos.writeObject(sourceNode);
                      oos.writeObject(signalStrength);
                      oos.close();
                      s1.close();
                   }
                  catch(Exception e)
                   {
                      e.printStackTrace();
                   } 
                  
                    if(!allCars.isEmpty())
                      { 
                        for(int i=0;i<allCars.size();i++)
                         {
                            currentCar=allCars.get(i).toString();
                           
                           if(this.node.equalsIgnoreCase(currentCar))
                            {
                                
                                String creditCoin=ucc.creditCoins.getText().toString();
                                int totalCoins=Integer.valueOf(creditCoin)+1;
                                
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() 
                                     {
                                        o.setCreditCoin(String.valueOf(totalCoins));
                                     }
                                });
                            }
                          }
                        
                        int giveCoins=1;
                        String sysno1=allcarsysnum.get(initializer).toString().trim();
                        String portno1=allcarportnum.get(initializer).toString().trim();
                        
                        try
                         {
                           
                            Socket s1=new Socket(sysno1,Integer.parseInt(portno1));
                            ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream()); 
                        
                            oos.writeObject("creditCoinUpdate");
                            oos.writeObject(giveCoins);
                            oos.close();
                            s1.close();
                         }
                        catch(Exception e)
                         {
                            e.printStackTrace();
                         }
                        
                        // blockchain connection
                    
                      }
                    
    }
    public void checkPositionBreak(int neighXpos, int neighYos, String nodeName, int speed, String receiveInfo, String encvalue) {

        String currentxVal = ucc.latitude.getText().toString();
        String currentyVal = ucc.longitude.getText().toString();
        double cxvalue = Double.valueOf(currentxVal);
        double cyvalue = Double.valueOf(currentyVal);
        int checkXVal = (int) cxvalue;
        int checkYVal = (int) cyvalue;
        int difference = Math.abs(neighXpos - checkXVal);

        if (difference <= 20 && (neighYos < checkYVal)) {
            mr.applyBreak(node, checkXVal, checkYVal, speed, ucc.Back_neighbour, receiveInfo, encvalue);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    o.setSpeed(String.valueOf(speed));
                    o.setData(receiveInfo);
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    o.setData(receiveInfo);
                }
            });
        }
    }
     public void checkPositionBreak1(int neighXpos, int neighYos, String nodeName, int speed, String receiveInfo, String encvalue) {

        String currentxVal = ucc.latitude.getText().toString();
        String currentyVal = ucc.longitude.getText().toString();
        double cxvalue = Double.valueOf(currentxVal);
        double cyvalue = Double.valueOf(currentyVal);
        int checkXVal = (int) cxvalue;
        int checkYVal = (int) cyvalue;
        int difference = Math.abs(neighXpos - checkXVal);
        System.out.println("---------------------receiveInfo-------------------->" + receiveInfo);
        // String decr = decry.decrypt(receiveInfo);
        String dataset = "Corrupted Message";

        if (difference <= 20 && (neighYos < checkYVal)) {
            mr.applyBreak(node, checkXVal, checkYVal, speed, ucc.Back_neighbour, receiveInfo, encvalue);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                     JOptionPane.showMessageDialog(null, "Malicious Message .!!");
                    o.setSpeed(String.valueOf(speed));
                    o.setData(dataset);
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                     JOptionPane.showMessageDialog(null, "Malicious Message .!!");
                    o.setData(dataset);
                }
            });
        }

    }


}
