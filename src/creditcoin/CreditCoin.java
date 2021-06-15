/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import com.controller.net.ApplicationBase;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class CreditCoin extends ApplicationBase {
    
    double dragX=0;
    double dragY=0;  
    @Override
    public void start(Stage stage) throws Exception 
     {
       Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
       Scene scene = new Scene(root);
       String a=this.getClass().getResource("design.css").toExternalForm();
       scene.getStylesheets().add(a);
       stage.setScene(scene);
       stage.initStyle(StageStyle.TRANSPARENT);
       stage.show();
       
       
       root.setOnMouseDragged(new EventHandler<MouseEvent>()
         {
                   @Override
                   public void handle (MouseEvent me) 
                    {                       
                       stage.setX(me.getScreenX() - dragX);
                       stage.setY(me.getScreenY() - dragY);                    
                    }
         });
        
        root.setOnMousePressed(new EventHandler<MouseEvent>()
         {
                   @Override
                   public void handle (MouseEvent me)
                    {
                      dragX = me.getScreenX() - stage.getX();
                      dragY = me.getScreenY() - stage.getY();                      
                    }
                
         });
       
       
       
    }

   
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
