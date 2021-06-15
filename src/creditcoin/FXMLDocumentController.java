/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;


public class FXMLDocumentController implements Initializable {
    
    double dragX = 0;
    double dragY = 0;
    public static String xValue,yValue,rsuName;
    Nodename nn=new Nodename();
    public static HashMap rsuLoc=new HashMap();
    public static stackpane sca=new stackpane();
    public Circle circle = new Circle();
    int count=0;
    
    @FXML
    public void Authority(ActionEvent event) throws IOException 
     {
         
         
          xValue = "35";       
           yValue = "2000";
         
          Stage stage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("Authority.fxml"));
          Scene scene = new Scene(root);
          String bg = this.getClass().getResource("rsDesign.css").toExternalForm();
          scene.getStylesheets().add(bg);
          stage.setScene(scene);
          stage.initStyle(StageStyle.TRANSPARENT);
          stage.show(); 
          
         root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) 
             {
                dragX = event.getScreenX() - stage.getX();
                dragY = event.getScreenY() - stage.getY();
             }
         });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - dragX);
                stage.setY(event.getScreenY() - dragY);
            }
        });  
    }
  
    
    @FXML
     public void Attacker(ActionEvent event) throws IOException 
     {
         
           
         
         Parent root = FXMLLoader.load(getClass().getResource("AttackerLogin.fxml"));
         Stage stage = new Stage();
         Scene sc = new Scene(root);
         String ss = getClass().getResource("registra.css").toExternalForm();
         sc.getStylesheets().add(ss);
         stage.setScene(sc);
         stage.initStyle(StageStyle.TRANSPARENT);
         stage.show();
         
         root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) 
                 {
                    stage.setX(me.getScreenX() - dragX);
                    stage.setY(me.getScreenY() - dragY);
                 }
              });
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    dragX = me.getScreenX() - stage.getX();
                    dragY = me.getScreenY() - stage.getY();
                }
            });
         
          
           
    }
    @FXML
    public void registration(ActionEvent event) throws IOException 
     {
          Stage stage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
          Scene scene = new Scene(root);
          String bg = this.getClass().getResource("registra.css").toExternalForm();
          scene.getStylesheets().add(bg);
          stage.setScene(scene);
          stage.initStyle(StageStyle.TRANSPARENT);
          stage.show(); 
          
         root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) 
             {
                dragX = event.getScreenX() - stage.getX();
                dragY = event.getScreenY() - stage.getY();
             }
         });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - dragX);
                stage.setY(event.getScreenY() - dragY);
            }
        });  
    }
    
    @FXML
    public void rsu(ActionEvent event)throws IOException
     {
         if(count<2)
          {
           rsuName=nn.rsun();
           xValue = "35";       
           yValue = JOptionPane.showInputDialog(null, "Enter the Y Co-Ordinate");
           
           if(yValue.equals("500"))
            {
                 String loc="Koyembedu";
                 rsuLoc.put(loc,rsuName);
            }
           else if(yValue.equals("1500"))
            {
                 String loc="Guindy";
                 rsuLoc.put(loc,rsuName);
            }
          
           ImageView rsu = new ImageView("/Image/tower.png");
           
           Parent root = FXMLLoader.load(getClass().getResource("Rsu.fxml"));
           Stage stage1 = new Stage();
           Scene sc1 = new Scene(root);
           String ss = getClass().getResource("rsuDesign.css").toExternalForm();
           sc1.getStylesheets().add(ss);
           stage1.setScene(sc1);
           stage1.initStyle(StageStyle.TRANSPARENT);
           stage1.show();
          
           root.setOnMouseDragged(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent me) 
             {
               stage1.setX(me.getScreenX() - dragX);
               stage1.setY(me.getScreenY() - dragY);
             }
            });
                            
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                dragX = me.getScreenX() - stage1.getX();
                dragY = me.getScreenY() - stage1.getY();
             }
            });
            
            double xVal=Double.valueOf(xValue);
            double yVal=Double.valueOf(yValue);
            
            rsu.setTranslateX(xVal);
            rsu.setTranslateY(yVal); 
            sca.add(rsu);
            count++;
         }
     }
    
    @FXML
    public void login(ActionEvent event)throws IOException 
     {
         Stage stage = new Stage();
         Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
         Scene scene = new Scene(root);
         String bg = this.getClass().getResource("registra.css").toExternalForm();
         scene.getStylesheets().add(bg);
         stage.setScene(scene);
         stage.initStyle(StageStyle.TRANSPARENT);
         stage.show();
         
         root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragX = event.getScreenX() - stage.getX();
                dragY = event.getScreenY() - stage.getY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - dragX);
                stage.setY(event.getScreenY() - dragY);
            }
        });
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        
        circle.setCenterX(70);
        circle.setCenterY(110);
        circle.setRadius(8);
        circle.setFill(javafx.scene.paint.Color.RED);
        sca.circle(circle);
        
        
//        final Timeline circleColorChange = new Timeline();
//        circleColorChange.setCycleCount(Timeline.INDEFINITE);
//        circleColorChange.getKeyFrames().add(new KeyFrame(Duration.seconds(90), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) 
//             {
//               circle.setFill(javafx.scene.paint.Color.RED);
//             }
//           }));
//        circleColorChange.play();
        
        sca.initialize();
    }    
    
}
