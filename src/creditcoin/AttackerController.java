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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gts
 */
public class AttackerController implements Initializable {

    @FXML
    public Label carid;
    @FXML
    public Label CarName;
    @FXML
    public Label PortNo;
    @FXML
    public TextField longitude;
    @FXML
    public TextField latitude;
    @FXML
    public TextArea bottomNeighbor;
    @FXML
    public TextArea topNeighbour;
    @FXML
    public TextArea leftNeighbour;
    @FXML
    public TextArea rightNeighbour;
    @FXML
    public Label speedd;
    @FXML
    public Label receivetext1;
    @FXML
    public Label receivetext;

    
    public MultiSender ms;
    SingleReceiver sr;
    MultiReceiver mr = null;
    Observer obs = new Observer();
    Nodename nn = new Nodename();
    Encryption ecc = new Encryption();
   
    String portnumber = nn.portn();
    String sysnumber = nn.sysname();
    HashMap<String, Integer> hashMap;
    public static HashMap nodespeed = new HashMap();
    public static HashMap neighbor = new HashMap();
    public static String kitNode;
    public Vector Front_neighbour = new Vector();
    public Vector Back_neighbour = new Vector();
    public Vector Left_neighbour = new Vector();
    public Vector Right_neighbour = new Vector();
    public Vector criticalNode = new Vector();
    public static String encvalue = "";
    public String auhash = "";
   // MD5 enc = new MD5();
    public String nodename;
    Timeline criticalnode = new Timeline();
    public String neighbournode = "";
    public int speed;
    public int xPosition, yPosition;
    public String xVal, yVal;
    int xmin, xmax, ymin, ymax, xmincri, xmaxcri, ymincri, ymaxcri;
    int d_xmin, d_xmax, d_ymin, d_ymax, d_ymincri, d_xmincri, d_ymaxcri, d_xmaxcri, d_XPOS, d_YPOS;
    public Vector v = new Vector();
    public boolean checkCondition = false;
    String authority = AuthorityController.auid;
   // AuthorityController acc;
    String id = nn.Id();
    String hashvalue = "";

    @FXML
    public void start(MouseEvent event) {

        speed = (Integer.parseInt(speedd.getText().toString())) + 2;
        sr.startCar(nodename, speed);
        criticalnode.play();

        if (checkCondition) {
            hashvalue = (String) allHashValue.get(id);
            String data = "Accelerating car!!" + authority + hashvalue;

            mr.applyBreak(nodename, xPosition, yPosition, speed, Back_neighbour, data, encvalue);
        }

    }

    @FXML
    public void send(ActionEvent event) throws IOException {
//    tring signalStrength = signal.getText().toString();
        sr.updatetable(nodename, authority, portnumber, id);

    }

    @FXML
    public void Attack(ActionEvent event) throws IOException {
//    tring signalStrength = signal.getText().toString();
        JOptionPane.showMessageDialog(null, "Attack Neighbour Car .!!");
        String data = "Emergency Brake Applied !!!!!! Take Precaution";
        mr.applyBreak(nodename, xPosition, yPosition, speed, Back_neighbour, data, encvalue);

    }

    @FXML
    public void partialBreak(MouseEvent event) {
        String currentSpeed = speedd.getText().toString();
        speed = Integer.valueOf(currentSpeed) - 2;
        hashvalue = (String) allHashValue.get(id);
        System.out.println("----------------------------->" + hashvalue);
        String data = "Partial Brake Applying !!!!!! Reduce Speed" + authority + hashvalue;

        mr.applyBreak(nodename, xPosition, yPosition, speed, Back_neighbour, data, encvalue);
        sr.updateSpeed(speed);
    }

    @FXML
    public void emergencyBrake(MouseEvent event) {

        hashvalue = (String) allHashValue.get(id);
        System.out.println("----------------------------->" + hashvalue);
        String data = "Emergency Brake Applied !!!!!! Take Precaution" + authority + hashvalue;

       
        String currentSpeed = speedd.getText().toString();
        speed = Integer.parseInt(currentSpeed) - Integer.parseInt(currentSpeed);
        mr.applyBreak(nodename, xPosition, yPosition, speed, Back_neighbour, data, encvalue);
        sr.updateSpeed(speed);
        //  JOptionPane.showMessageDialog(null,"Emergency Break Event Triggered");
        checkCondition = true;
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
    public void laneChange(ActionEvent event) {
//        sendtext.setText("Maintain Your Speed,Overtaking You !!");
        JOptionPane.showMessageDialog(null, "Lane Change Event Triggered");
        String data = "Maintain Your Speed,Overtaking You!!";

        String encdata = ecc.encrypt(data);

        sr.checkLane(encdata, nodename);
    }

    @FXML
    public void assignKit(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        nodename = AttackerLoginController.nodename;
        carid.setText(id);
        CarName.setText(nodename);
        PortNo.setText(portnumber);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                longitude.setText(AttackerLoginController.yValue);
                latitude.setText(AttackerLoginController.xValue);
                speedd.setText("0");
            }
        });

        mr = new MultiReceiver(nodename, portnumber, sysnumber, AttackerController.this);
        sr = new SingleReceiver(nodename, portnumber, sysnumber, this, mr);
        topNeighbour.textProperty().bindBidirectional(obs.neighbourProperty());
        longitude.textProperty().bindBidirectional(obs.yPositionProperty());
        latitude.textProperty().bindBidirectional(obs.xPositionProperty());

        speedd.textProperty().bindBidirectional(obs.speedProperty());

        final Timeline multisender1 = new Timeline();
        multisender1.setCycleCount(Timeline.INDEFINITE);
        multisender1.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!latitude.getText().toString().isEmpty()) {
                    xVal = latitude.getText().toString();
                    yVal = longitude.getText().toString();

                    ms = new MultiSender(nodename, portnumber, sysnumber, xVal, yVal, AttackerController.this);
                    double xpos = Double.valueOf(xVal);
                    double ypos = Integer.parseInt(yVal);

                    xPosition = (int) xpos;
                    yPosition = (int) ypos;

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
                        //-----------------Critical Neighbour------------------------------

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

                    //    System.out.println("src min Value-"+xmin+"---src max--"+xmax+"--des xmin--"+d_xmin);
                    if ((IntStream.rangeClosed(xmin, xmax).boxed().collect(Collectors.toList()).contains(d_xmin))
                            || (IntStream.rangeClosed(xmin, xmax).boxed().collect(Collectors.toList()).contains(d_xmax))) {
                        if ((IntStream.rangeClosed(ymin, ymax).boxed().collect(Collectors.toList()).contains(d_ymin))
                                || (IntStream.rangeClosed(ymin, ymax).boxed().collect(Collectors.toList()).contains(d_ymax))) {

                            if (!v.contains(name)) {
                                if (name.equals(nodename)) {

                                } else {
                                    v.add(name);

                                    if ((IntStream.rangeClosed(ymin, (int) yPosition).boxed().collect(Collectors.toList()).contains(d_ymax))) {
                                        Front_neighbour.add(name);
                                        //System.out.println("-----------------------");
                                    } else {
                                        Back_neighbour.add(name);
                                    }
                                    System.out.println("-XMIN" + xmin + "------" + xmax + "-xposito-" + xPosition + "dmin--" + d_xmin + "name" + nodename);
                                    if ((IntStream.rangeClosed((int) xPosition, xmax).boxed().collect(Collectors.toList()).contains(d_xmin))) {
                                        Right_neighbour.add(name);
                                    } else {
                                        Left_neighbour.add(name);
                                    }

                                }

                                topNeighbour.setText(v.toString());
                                neighbor.put(nodename, v);
                            }
                        } else if (v.contains(name)) {
                            v.remove(name);
                            neighbor.remove(nodename, v);
                            topNeighbour.setText(v.toString());
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
                        topNeighbour.setText(v.toString());
                        neighbor.remove(nodename, v);
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
//                    System.out.println("-XMINCRI-" + xmincri + "-XMAXCRI-" + xmaxcri + "-YMINCRI-" + ymincri + "-YMAXCRI-" + ymaxcri + "---FOR--" + nodename);

                }

            }

        }));
        multisender1.play();

        criticalnode.setCycleCount(Timeline.INDEFINITE);
        criticalnode.getKeyFrames().add(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                xVal = latitude.getText().toString();
                yVal = longitude.getText().toString();

                double xpos = Double.valueOf(xVal);
                double ypos = Integer.parseInt(yVal);

                xPosition = (int) xpos;
                yPosition = (int) ypos;

                //  System.out.println("----------------------");
                for (String name : NodeStatus.map.keySet()) {
                    hashMap = NodeStatus.map.get(name);

                    d_xmin = hashMap.get("XMIN");
                    d_xmax = hashMap.get("XMAX");
                    d_ymin = hashMap.get("YMIN");
                    d_ymax = hashMap.get("YMAX");
                    d_XPOS = hashMap.get("XPOS");
                    d_YPOS = hashMap.get("YPOS");

                    int diffeX = Math.abs(xPosition - d_XPOS);
                    int diffeY = Math.abs(yPosition - d_YPOS);
                    if (diffeX < 20) {
                        if (!name.equals(nodename)) {
                            if ((yPosition < d_YPOS) && (diffeY < 60)) {
                                if (!criticalNode.contains(name)) {
                                    if (name.equals("Ambulance")) {

                                    } else {
                                        String currentSpeed = speedd.getText().toString();
                                        speed = Integer.valueOf(currentSpeed);
                                        mr.criticalNeigh(name, nodename, speed);
                                        criticalNode.add(name);
                                    }
                                }
                            } else if (criticalNode.contains(name)) {
                                criticalNode.remove(name);
                            }
                        }
                    } else if (criticalNode.contains(name)) {
                        criticalNode.remove(name);
                    }

//                    if ((IntStream.rangeClosed(xmincri, xmaxcri).boxed().collect(Collectors.toList()).contains(d_xmincri))
//                            || (IntStream.rangeClosed(xmincri, xmaxcri).boxed().collect(Collectors.toList()).contains(d_xmaxcri))) {
//                        if (name.equals(nodename)) {
//
//                        } //                        else if ((IntStream.rangeClosed(ymincri, yPosition).boxed().collect(Collectors.toList()).contains(d_ymincri))) {
//                        //                            if (!criticalNode.contains(name)) {
//                        //                                String currentSpeed = speedd.getText().toString();
//                        //                                speed = Integer.valueOf(currentSpeed);
//                        //                                mr.criticalNeigh(name, nodename, speed);
//                        //                                criticalNode.add(name);
//                        //                            }
//                        //
//                        //                        }
//                        else if ((IntStream.rangeClosed(yPosition, ymaxcri).boxed().collect(Collectors.toList()).contains(d_ymincri))) {
//                            if (!criticalNode.contains(name)) {
//                                String currentSpeed = speedd.getText().toString();
//                                speed = Integer.valueOf(currentSpeed);
//                                mr.criticalNeigh(name, nodename, speed);
//                                criticalNode.add(name);
//                            }
//
//                        } else {
//                            if (criticalNode.contains(name)) {
//                                criticalNode.remove(name);
//                            }
//                        }
//                    }
                }
            }
        }));

    }

    public Observer getObserver() {
        return obs;
    }

    public void recived_data(String data) {
        if (!data.isEmpty()) {
            System.out.println(" data in controller------------->" + data);
            sr.updateSpeed(0);

            String message = "Emergency Event Occured !! Take Precaution";
            String encdata = ecc.encrypt(data);

            mr.applyBreak(nodename, xPosition, yPosition, 0, Back_neighbour, encdata, encvalue);
        }
    }

}
