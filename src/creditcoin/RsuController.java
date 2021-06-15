/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.net.URL;
import java.util.HashMap;
import static creditcoin.UserController.neighbor;
import static creditcoin.MultiReceiver.allcarportnum;
import static creditcoin.LoginController.nodecar;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class RsuController implements Initializable {

    @FXML
    public Label RsuNum;
    @FXML
    public Label portno;
    @FXML
    public TextArea neighbour;
    @FXML
    public TextField latitude;
    @FXML
    public TextField longitude;
    @FXML
    public TextArea receiver;
    
    @FXML
    public TableView<Table> Table;
    @FXML
    public TableColumn<Table,String>Nodename;
    @FXML
    public TableColumn<Table,String>PortNo;
    @FXML
    public TableColumn<Table,String>Location;
    @FXML
    public TableColumn<Table,String>Message;
    @FXML
    public TableColumn<Table,String>Signal;
    @FXML
    ObservableList<Table>pathinfodata;
    
    public MultiSender ms;
    SingleReceiver sr;
    MultiReceiver mr = null;
    Observer obs=new Observer();
    Vector v = new Vector();
    public ArrayList rsn=new ArrayList();
    public Vector Front_neighbour = new Vector();
    public Vector Back_neighbour = new Vector();
    public Vector Left_neighbour = new Vector();
    public Vector Right_neighbour = new Vector();
    public HashMap areaRsu=new HashMap();
    Nodename nn=new Nodename();
    public String xVal, yVal,currentCarLocation;
    public int xPosition, yPosition;
     String neigh="";
    String portnumber = nn.portn();
    String sysnumber = nn.sysname();
    String rsuName;
    HashMap<String, Integer> hashMap;
    int xmin, xmax, ymin, ymax, xymin1, xymin2, xymax1, xymax2;
    int d_xmin, d_xmax, d_ymin, d_ymax, d_xymin1, d_xymin2, d_xymax1, d_xymax2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
     {
        rsuName=FXMLDocumentController.rsuName;
        RsuNum.setText(FXMLDocumentController.rsuName);
        portno.setText(portnumber);
        
        longitude.setText(FXMLDocumentController.yValue);
        latitude.setText(FXMLDocumentController.xValue);
        
        pathinfodata=FXCollections.observableArrayList(new Table());
        
        Nodename.setCellValueFactory(new PropertyValueFactory<Table,String>("Nodename"));
        PortNo.setCellValueFactory(new PropertyValueFactory<Table,String>("Portno"));
        Location.setCellValueFactory(new PropertyValueFactory<Table,String>("Location"));
        Message.setCellValueFactory(new PropertyValueFactory<Table,String>("Message"));
        Signal.setCellValueFactory(new PropertyValueFactory<Table,String>("Signal"));
        
        Table.setItems(pathinfodata); 
        Table.getColumns().setAll(Nodename, PortNo, Location,Message,Signal);
        
        mr = new MultiReceiver(rsuName, portnumber, sysnumber, RsuController.this);
        sr = new SingleReceiver(rsuName, portnumber, sysnumber, this);
        
        neighbour.textProperty().bindBidirectional(obs.neighbourProperty());
        receiver.textProperty().bindBidirectional(obs.dataProperty());
        
        final Timeline multisender1 = new Timeline();
        multisender1.setCycleCount(Timeline.INDEFINITE);
        multisender1.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!latitude.getText().toString().isEmpty()) {
                    xVal = latitude.getText().toString();
                    yVal = longitude.getText().toString();

                    ms = new MultiSender(rsuName, portnumber, sysnumber, xVal, yVal, RsuController.this);
                    double xpos = Double.valueOf(xVal);
                    double ypos = Integer.parseInt(yVal);

                    xPosition = (int) xpos;
                    yPosition = (int) ypos;

                    if (NodeStatus.map.containsKey(rsuName)) {
                        hashMap = NodeStatus.map.get(rsuName);
                   
                        //--------------------Normal Neighbour Calculation--------------
                        hashMap.put("XPOS", xPosition);
                        hashMap.put("YPOS", yPosition);
                        hashMap.put("XMIN", xPosition - 35);
                        hashMap.put("XMAX", xPosition + 145);
                        hashMap.put("YMIN", yPosition - 500);
                        hashMap.put("YMAX", yPosition + 500);

                        xmin = hashMap.get("XMIN");
                        xmax = hashMap.get("XMAX");
                        ymin = hashMap.get("YMIN");
                        ymax = hashMap.get("YMAX");

                    } else {
                        HashMap<String, Integer> hashMap = new HashMap<>();

                        hashMap.put("XPOS", xPosition);
                        hashMap.put("YPOS", yPosition);
                        hashMap.put("XMIN", xPosition - 35);
                        hashMap.put("XMAX", xPosition + 145);
                        hashMap.put("YMIN", yPosition - 500);
                        hashMap.put("YMAX", yPosition + 500);
                        //-----------------Critical Neighbour------------------------------
//                        hashMap.put("XMINCRI", xPosition - 5);
//                        hashMap.put("XMAXCRI", xPosition + 5);
//                        hashMap.put("YMINCRI", yPosition - 35);
//                        hashMap.put("YMAXCRI", yPosition + 35);

                        NodeStatus.map.put(rsuName, hashMap);
                        hashMap = NodeStatus.map.get(rsuName);
                        
                        xmin = hashMap.get("XMIN");
                        xmax = hashMap.get("XMAX");
                        ymin = hashMap.get("YMIN");
                        ymax = hashMap.get("YMAX");
                    }
                }

                for (String name : NodeStatus.map.keySet()) {
                    hashMap = NodeStatus.map.get(name);
                    d_xmin = hashMap.get("XMIN");
                    d_xmax = hashMap.get("XMAX");
                    d_ymin = hashMap.get("YMIN");
                    d_ymax = hashMap.get("YMAX");
                    
                    if ((IntStream.rangeClosed(xmin, xmax).boxed().collect(Collectors.toList()).contains(d_xmin))
                            || (IntStream.rangeClosed(xmin, xmax).boxed().collect(Collectors.toList()).contains(d_xmax))) {
                        if ((IntStream.rangeClosed(ymin, ymax).boxed().collect(Collectors.toList()).contains(d_ymin))
                                || (IntStream.rangeClosed(ymin, ymax).boxed().collect(Collectors.toList()).contains(d_ymax))) {

                            if (!v.contains(name)) {
                                if (name.equals(rsuName)) {
                           
                                } else {
                                    v.add(name);
                                    if((IntStream.rangeClosed(ymin, (int) yPosition).boxed().collect(Collectors.toList()).contains(d_ymax))) {
                                        Front_neighbour.add(name);
                                        //System.out.println("-----------------------");
                                    } else {
                                        Back_neighbour.add(name);
                                    }
                                    if ((IntStream.rangeClosed((int) xPosition, xmax).boxed().collect(Collectors.toList()).contains(d_xmin))) {
                                        Right_neighbour.add(name);
                                    } else {
                                        Left_neighbour.add(name);
                                    }
                                    
                                     neigh=neigh+name+ "\n";
                                     
                                     Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() 
                                         {
                                            obs.setNeighbour(neigh);
                                         }
                                      });
                                    
                                    // testing to append in table
                                     
                                     Vector allNei=new Vector();
                                     String temp[] = name.split("\n");
                               
                                     for(String t : temp)
                                      {
                                          allNei.add(t);
                                      }                
                  
                                     for(int i=0;i<allNei.size();i++)
                                      {
                                         String ele=(String)allNei.get(i);
                                         if(ele.length()<2)
                                          {
                                            String vehicle=ele;
                                            ImageView carImageObject=(ImageView) nodecar.get(vehicle);
                                            
                                            int yPosition=(int)carImageObject.getTranslateY();
                                            
                                            if(yPosition>=1000)
                                             {
                                                currentCarLocation="Guindy";
                                             }
                                            else if(yPosition<1000)
                                             {
                                                currentCarLocation="Koyembedu";
                                             }
                                            String portno=(String)allcarportnum.get(vehicle);
                                          
//                                           try
//                                            {      
//                                                if(pathinfodata.get(0).getNodename().equalsIgnoreCase(""))
//                                                 {
//                                                     pathinfodata.clear();
//                                                 }
//                                                pathinfodata.add(new Table(vehicle,portno,currentCarLocation,""));
//                                            }
//                                           catch(Exception e)
//                                            {
//                                               e.printStackTrace();
//                                            }
                                            
                                          }
                                      }  
                                }

                               // neighbour.setText(v.toString());
                                neighbor.put(rsuName, neigh);
                            }
                        } else if (v.contains(name)) {
                            v.remove(name);
                            neighbor.remove(rsuName, neigh);
                            
                            //neighbour.setText(v.toString());
                            
                              neigh=neigh.replace(name+"\n", "");
                                     
                              Platform.runLater(new Runnable() {
                              @Override
                              public void run() 
                               {
                                  obs.setNeighbour(neigh);
                               }
                             });
                            
                            if (Front_neighbour.contains(name)) {
                                Front_neighbour.remove(name);
                            }
                            if (Back_neighbour.contains(name)) {
                                Back_neighbour.remove(name);
                            }
                            if (Left_neighbour.contains(name)) {
                                Left_neighbour.remove(name);
                            }
                            if (Right_neighbour.contains(name)) {
                                Right_neighbour.remove(name);
                            }
                        }

                    } else if (v.contains(name)) {
                        v.remove(name);
                        //neighbour.setText(v.toString());
                        
                         neigh=neigh.replace(name+"\n", "");
                                     
                              Platform.runLater(new Runnable() {
                              @Override
                              public void run() 
                               {
                                  obs.setNeighbour(neigh);
                               }
                             });
                        
                        
                        neighbor.remove(rsuName, neigh);
                        
                        if (Front_neighbour.contains(name)) {
                            Front_neighbour.remove(name);
                        }
                        if (Back_neighbour.contains(name)) {
                            Back_neighbour.remove(name);
                        }
                        if (Left_neighbour.contains(name)) {
                            Left_neighbour.remove(name);
                        }
                        if (Right_neighbour.contains(name)) {
                            Right_neighbour.remove(name);
                        }
//                    System.out.println("-XMINCRI-" + xmincri + "-XMAXCRI-" + xmaxcri + "-YMINCRI-" + ymincri + "-YMAXCRI-" + ymaxcri + "---FOR--" + nodename);
                    }
                }

            }

        }));
        multisender1.play(); 
         
     }

    public Observer getObserver() {
        return obs;
    }

    
    
}
