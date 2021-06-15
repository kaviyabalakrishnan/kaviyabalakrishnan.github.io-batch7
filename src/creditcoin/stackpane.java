/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class stackpane extends Application
{
      AnchorPane root=new AnchorPane();
      Scene scene;
      Stage primeStage=new Stage();
      double dragX=0;
      double dragY=0;
      
      public void initialize()
       {
          ImageView imageView = new ImageView("/Image/road preview.jpg");
          imageView.setFitHeight(2000); 
          imageView.setFitWidth(280); 
          root.getChildren().add(imageView);
          
//          CheckBox checkBox1 = new CheckBox("Traffic");
//          checkBox1.setTranslateX(40);
//          checkBox1.setTranslateY(100);
//          root.getChildren().add(checkBox1);
         String location[] = {"CMBT", "Arumbakkam", "Vadapalani", "Udhayam", "Ashok Pillar", "Kaasi theater", "Ambal nagar", "Kalaimagal Nagar", "Ekkattuthamgal", "Guindy"};

          String image=stackpane.class.getResource("/Image/road preview.jpg").toExternalForm();
          ScrollPane sc = new ScrollPane();
          sc.setContent(root);
          
          root.setStyle("-fx-background-image: url('" + image + "'); " +"-fx-background-size: stretch;"+"-fx-background-repeat: stretch;");
          sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
          scene=  new Scene(sc, 280, 620);
            double a = 0;
        for (int i = 0; i < 10; i++) {
            a = a + 200;
            ImageView toll1 = new ImageView("/Image/gate.png");
            toll1.setFitHeight(30);
            toll1.setFitWidth(30);
            toll1.setTranslateX(20.00);
            toll1.setTranslateY(a);

            Tooltip t = new Tooltip(location[i]);
            Tooltip.install(toll1, t);
            root.getChildren().add(toll1);
        }
             root.setOnMouseDragged(new EventHandler<MouseEvent>()
                {
                   @Override
                   public void handle (MouseEvent me) 
                    {                       
                       primeStage.setX(me.getScreenX() - dragX);
                       primeStage.setY(me.getScreenY() - dragY);                    
                    }
                 });
        
               root.setOnMousePressed(new EventHandler<MouseEvent>()
                {
                   @Override
                   public void handle (MouseEvent me)
                    {
                      dragX = me.getScreenX() - primeStage.getX();
                      dragY = me.getScreenY() - primeStage.getY();                      
                    }
                });
       }
      
      public void add(ImageView image)
       {
           Platform.runLater(new Runnable() {
               @Override
               public void run() 
                {         
                    root.getChildren().add(image);
                    primeStage.setTitle("VANET ENVIRONMENT");
                    primeStage.setScene(scene);
                    primeStage.show();
                }
           });
       }
      
     public void circle(Circle circle)
      {
          Platform.runLater(new Runnable() {
              @Override
              public void run() {
                    root.getChildren().add(circle);
                    primeStage.setTitle("VANET ENVIRONMENT");
                    primeStage.setScene(scene);
                    primeStage.show();
              }
          });
          
      }
      

    @Override
    public void start(Stage primaryStage) throws Exception 
     {
     }
}
