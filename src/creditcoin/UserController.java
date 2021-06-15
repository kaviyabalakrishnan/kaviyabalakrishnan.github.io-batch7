/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import static creditcoin.MultiReceiver.allHashValue;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class UserController implements Initializable {

    @FXML
    public Label CarName;
    @FXML
    public Label PortNo;
    @FXML
    public TextField longitude;
    @FXML
    public TextField latitude;
    @FXML
    public Label speedd;
    @FXML
    public Label receivetext;
    @FXML
    public TextArea topNeighbour;
    @FXML
    public TextArea sender;
    @FXML
    public Label signal;
    @FXML
    public ChoiceBox area;
    @FXML
    public Label currentLocation;
    @FXML
    public Label responder;
    @FXML
    public Label creditCoins;
     @FXML
    public Label trustmanagement;

    public String nodename;
    Nodename nn = new Nodename();
    String portnumber = nn.portn();
    String sysnumber = nn.sysname();
    String signall = nn.signal();
    Observer obs = new Observer();
    Observer1 obs1 = new Observer1();
    public int speed;
    HashMap<String, Integer> hashMap;
    public static HashMap neighbor = new HashMap();
    MultiSender ms;
    SingleReceiver sr;
    MultiReceiver mr = null;
    Timeline criticalnode = new Timeline();
    String neighbour = "";
    MD5 enc = new MD5();
    String id = nn.Id();
    public String xVal, yVal;
    public static String trafficRequestVehicle;
    public int xPosition, yPosition;
    int xmin, xmax, ymin, ymax, xmincri, xmaxcri, ymincri, ymaxcri;
    int d_xmin, d_xmax, d_ymin, d_ymax, d_ymincri, d_xmincri, d_ymaxcri, d_xmaxcri, d_XPOS, d_YPOS;
    public Vector Front_neighbour = new Vector();
    public Vector Back_neighbour = new Vector();
    public Vector Left_neighbour = new Vector();
    public Vector Right_neighbour = new Vector();
    public Vector v = new Vector();
    public Vector criticalNode = new Vector();
    public int count;
    public static String encvalue = "";
    public static HashMap respondersNodeName = new HashMap();
    String data1 = "12345";
    public int yPositionn;
    int trustlevel = 0;
    String hashvalue = "";

    @FXML
    public void start(MouseEvent event) {
        speed = (Integer.parseInt(speedd.getText().toString())) + 2;
        sr.startCar(nodename, speed);
        sr.signalBreak();
    }

    @FXML
    public void send(ActionEvent event) throws IOException {
//    tring signalStrength = signal.getText().toString();
        sr.updatetable(nodename, data1, portnumber, id);

    }

    @FXML
    public void request(ActionEvent event) throws IOException {
        String signalStrength = signal.getText().toString();
        String coins = creditCoins.getText().toString();
        sr.request(nodename, coins, signalStrength);
    }

    @FXML
    public void Attack(ActionEvent event) throws IOException {
//    tring signalStrength = signal.getText().toString();
        hashvalue = (String) allHashValue.get(id);
        System.out.println("----------------------------->" + hashvalue);
        String data1 = "partial brake";
        
        mr.applyBreak1(nodename, xPosition, yPosition, speed, Back_neighbour, data1, encvalue);

    }

    @FXML
    public void updateTrafficStatus(ActionEvent event) {
        respondersNodeName.put("Yes", nodename);

        String signalStrength = signal.getText().toString();
        String responseMessage = "Traffic";
        sender.setText(responseMessage);

        sr.updateTrafficStatus(responseMessage, topNeighbour.getText(), nodename, signalStrength);
    }

    @FXML
    public void requestTrafficStatus(ActionEvent event) throws IOException {
        String signalStrength = signal.getText().toString();
        trafficRequestVehicle = nodename;

        String requestMessage = "Is There Traffic?";
        sender.setText(requestMessage);

        String coins = creditCoins.getText().toString();
        String destination = area.getValue().toString();
        System.out.println("-----------"+destination);
        sr.requestTrafficStatus(requestMessage, topNeighbour.getText(), destination, nodename, coins, signalStrength);
    }

    @FXML
    public void left(MouseEvent event) {
        xVal = latitude.getText().toString();
        double xpos = Double.valueOf(xVal);
        xPosition = (int) xpos;

        if (xPosition < 60) {
            JOptionPane.showMessageDialog(null, "Cannot Move Outside the Road Range");
        } else {
            sr.checkLeftNeigh(xPosition);
        }
    }

    @FXML
    public void right(MouseEvent event) {
        xVal = latitude.getText().toString();
        double xpos = Double.valueOf(xVal);
        xPosition = (int) xpos;

        if (xPosition == 200) {
            JOptionPane.showMessageDialog(null, "Cannot Move Outside the Road Range");
        } else {
            sr.checkRightNeigh(xPosition);
        }
    }

    @FXML
    public void partialBreak(MouseEvent event) {
        String currentSpeed = speedd.getText().toString();
        speed = Integer.valueOf(currentSpeed) - 2;

        String data = "partial brake";
        //   JOptionPane.showMessageDialog(null, "slow button");
        //  String encdata=ecc.encrypt(data);
        encvalue = enc.send(data, data1);
        mr.applyBreak(nodename, xPosition, yPosition, speed, data);
        sr.updateSpeed(speed);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        nodename = LoginController.user;
        CarName.setText(LoginController.user);
        PortNo.setText(portnumber);
        signal.setText(signall);

        area.setItems(FXCollections.observableArrayList("Guindy", "Koyembedu"));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                longitude.setText(LoginController.yValue);
                latitude.setText(LoginController.xValue);
                speedd.setText("0");
                creditCoins.setText("15");
            }
        });

        mr = new MultiReceiver(nodename, portnumber, sysnumber, signall, UserController.this);
        sr = new SingleReceiver(nodename, portnumber, sysnumber, this, mr);

        topNeighbour.textProperty().bindBidirectional(obs.neighbourProperty());
        speedd.textProperty().bindBidirectional(obs.speedProperty());
        longitude.textProperty().bindBidirectional(obs.yPositionProperty());
        latitude.textProperty().bindBidirectional(obs.xPositionProperty());
        receivetext.textProperty().bindBidirectional(obs.dataProperty());
        responder.textProperty().bindBidirectional(obs.countProperty());
        creditCoins.textProperty().bindBidirectional(obs.creditCoinProperty());
       // sr.updatetable(nodename, data1, portnumber, id);
        final Timeline multisender1 = new Timeline();
        multisender1.setCycleCount(Timeline.INDEFINITE);
        multisender1.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!latitude.getText().toString().isEmpty()) {
                    xVal = latitude.getText().toString();
                    yVal = longitude.getText().toString();

                    ms = new MultiSender(nodename, portnumber, sysnumber, xVal, yVal, signall, UserController.this);

                    double xpos = Double.valueOf(xVal);
                    double ypos = Integer.parseInt(yVal);

                    xPosition = (int) xpos;
                    yPosition = (int) ypos;
                    yPositionn = Integer.valueOf(yVal);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (yPosition >= 200) {
                                currentLocation.setText("CMBT");
                            }
                            if (yPosition >= 400) {
                                currentLocation.setText("Arumbakkam");
                            }
                            if (yPosition >= 600) {
                                currentLocation.setText("Vadapalani");
                            }
                            if (yPosition >= 800) {
                                currentLocation.setText("Udhayam");
                            }
                            if (yPosition >= 1000) {
                                currentLocation.setText("Ashok Pillar");
                            }
                            if (yPosition >= 1200) {
                                currentLocation.setText("Kaasi theater");
                            }
                            if (yPosition >= 1400) {
                                currentLocation.setText("Ambal nagar");
                            }
                            if (yPosition >= 1600) {
                                currentLocation.setText("Kalaimagal Nagar");
                            }
                            if (yPosition >= 1800) {
                                currentLocation.setText("Ekkattuthamgal");
                            }
                            if (yPosition >= 2000) {
                                currentLocation.setText("Guindy");
                            }
                        }
                    });

                    // Platform.runLater(new Runnable() {
                    //   @Override
                    //   public void run() 
                    //  {
                    //    if(yPosition>=1000)
                    //     {
                    //        currentLocation.setText("Guindy");
                    //    }
                    //    else if(yPosition<1000)
                    //     {
                    //        currentLocation.setText("Koyembedu");
                    //      }
                    //  }
                    // });
                    if (NodeStatus.map.containsKey(nodename)) {
                        hashMap = NodeStatus.map.get(nodename);
                        //--------------------Normal Neighbour Calculation--------------
                        hashMap.put("XPOS", xPosition);
                        hashMap.put("YPOS", yPosition);
                        hashMap.put("XMIN", xPosition - 70);
                        hashMap.put("XMAX", xPosition + 70);
                        hashMap.put("YMIN", yPosition - 40);
                        hashMap.put("YMAX", yPosition + 40);

                        xmin = hashMap.get("XMIN");
                        xmax = hashMap.get("XMAX");
                        ymin = hashMap.get("YMIN");
                        ymax = hashMap.get("YMAX");
                    } else {
                        HashMap<String, Integer> hashMap = new HashMap<>();

                        hashMap.put("XPOS", xPosition);
                        hashMap.put("YPOS", yPosition);
                        hashMap.put("XMIN", xPosition - 70);
                        hashMap.put("XMAX", xPosition + 70);
                        hashMap.put("YMIN", yPosition - 40);
                        hashMap.put("YMAX", yPosition + 40);

                        NodeStatus.map.put(nodename, hashMap);
                        hashMap = NodeStatus.map.get(nodename);

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

                    if ((IntStream.rangeClosed(d_ymin, d_ymax).boxed().collect(Collectors.toList()).contains(ymin))
                            || (IntStream.rangeClosed(d_ymin, d_ymax).boxed().collect(Collectors.toList()).contains(ymax))) {
                        if (!v.contains(name)) {
                            if (name.equals(nodename)) {

                            } else {
                                v.add(name);
                                if ((IntStream.rangeClosed(ymin, (int) yPosition).boxed().collect(Collectors.toList()).contains(d_ymax))) {
                                    Front_neighbour.add(name);
                                } else {
                                    Back_neighbour.add(name);
                                }
                                //  System.out.println("-XMIN" + xmin + "------" + xmax + "-xposito-" + xPosition + "dmin--" + d_xmin + "name" + nodename);
                                if ((IntStream.rangeClosed((int) xPosition, xmax).boxed().collect(Collectors.toList()).contains(d_xmin))) {
                                    Right_neighbour.add(name);
                                } else {
                                    Left_neighbour.add(name);
                                }

                                neighbour = neighbour + name + "\n";

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        obs.setNeighbour(neighbour);
                                    }
                                });
                            }
                            neighbor.put(nodename, neighbour);
                        }
                    } else if (v.contains(name)) {
                        v.remove(name);
                        neighbor.remove(nodename, neighbour);

                        //topNeighbour.setText(v.toString());
                        neighbour = neighbour.replace(name + "\n", "");

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                obs.setNeighbour(neighbour);
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
                    } else if (v.contains(name)) {
                        v.remove(name);
                        // topNeighbour.setText(v.toString());

                        neighbour = neighbour.replace(name + "\n", "");

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                obs.setNeighbour(neighbour);
                            }
                        });

                        neighbor.remove(nodename, neighbour);
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
                }
            }
        }));
        multisender1.play();

//        criticalnode.setCycleCount(Timeline.INDEFINITE);
//        criticalnode.getKeyFrames().add(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                xVal = latitude.getText().toString();
//                yVal = longitude.getText().toString();
//
//                double xpos = Double.valueOf(xVal);
//                double ypos = Integer.parseInt(yVal);
//
//                xPosition = (int) xpos;
//                yPosition = (int) ypos;
//
//                //  System.out.println("----------------------");
//                for (String name : NodeStatus.map.keySet()) {
//                    hashMap = NodeStatus.map.get(name);
//
//                    d_xmin = hashMap.get("XMIN");
//                    d_xmax = hashMap.get("XMAX");
//                    d_ymin = hashMap.get("YMIN");
//                    d_ymax = hashMap.get("YMAX");
//                    d_XPOS = hashMap.get("XPOS");
//                    d_YPOS = hashMap.get("YPOS");
//
//                    int diffeX = Math.abs(xPosition - d_XPOS);
//                    int diffeY = Math.abs(yPosition - d_YPOS);
//                    if (diffeX < 20) {
//                        if (!name.equals(nodename)) {
//                            if ((yPosition < d_YPOS) && (diffeY < 60)) {
//                                if (!criticalNode.contains(name)) {
//                                    if (name.equals("Ambulance")) {
//
//                                    } else {
//                                        String currentSpeed = speedd.getText().toString();
//                                        speed = Integer.valueOf(currentSpeed);
//                                      //  mr.criticalNeigh(name, nodename, speed);
//                                        criticalNode.add(name);
//                                    }
//                                }
//                            } else {
//                                if (criticalNode.contains(name)) {
//                                    criticalNode.remove(name);
//                                }
//                            }
//                        }
//                    } else {
//                        if (criticalNode.contains(name)) {
//                            criticalNode.remove(name);
//                        }
//                    }
//                }
//            }
//        }));
    }

    public Observer getObserver() {
        return obs;
    }

    public Observer1 getObserver1() {
        return obs1;
    }

}
