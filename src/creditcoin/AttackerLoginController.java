
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import java.util.HashMap;


public class AttackerLoginController implements Initializable {

    @FXML
    TextField username, pass;
    
    public static String xValue,yValue;
    double dragX = 0;
    double dragY = 0;
    public static String nodename;
    Nodename nn=new Nodename();
    public static String user="",userPassword="",phoneNumber="",email="";
     public static HashMap nodecar=new HashMap();
    
    public void login(MouseEvent event)
     {
        nodename=nn.noden();
        String pass1 = "";
        String name = username.getText().toString();
        String password = pass.getText().toString();
        
         System.out.println("----"+name);
        
        try
        {
            if (name.equals("") || password.equals(""))
             {
                JOptionPane.showMessageDialog(null, "Fields should not be Empty");   
             }
            
                 
            else if (name.equals("admin") || password.equals("admin"))
                   {
                       JOptionPane.showMessageDialog(null, "User Login Success"); 
                       
                      
                       
                       
                       
                       Stage stage = (Stage) pass.getScene().getWindow();
                       stage.close();
                       
                       xValue = JOptionPane.showInputDialog(null, "Enter the X Co-Ordinate");
                       
                       if(Integer.parseInt(xValue) >= 100 && (Integer.parseInt(xValue) <=180))
                         {
                             yValue = JOptionPane.showInputDialog(null, "Enter the Y Co-Ordinate");
                           
                             
                             Parent root = FXMLLoader.load(getClass().getResource("Attacker.fxml"));
                             Stage stage1 = new Stage();
                             Scene sc1 = new Scene(root);
                             String ss = getClass().getResource("design_1.css").toExternalForm();
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
            
                             
                             
                            
                             
                        }
                       else{
                             JOptionPane.showMessageDialog(null, "X Value Should Grater Than 100 and Less Than 180");
                         }
                   }
                  else 
                   {
                         JOptionPane.showMessageDialog(null, "Check Your Login name & Password");
                   }
             }
        
        catch(Exception e)
         {
            e.printStackTrace();
         }
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }    
    
}
