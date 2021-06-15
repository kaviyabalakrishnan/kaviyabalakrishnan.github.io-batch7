
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
import static creditcoin.FXMLDocumentController.sca;
import java.util.HashMap;


public class LoginController implements Initializable {

    @FXML
    TextField username, pass;
    
    public static String xValue,yValue;
    double dragX = 0;
    double dragY = 0;
   
    
    Nodename nn=new Nodename();
    public static String user="",userPassword="",phoneNumber="",email="";
     public static HashMap nodecar=new HashMap();
    
    public void login(MouseEvent event)
     {
        String pass1 = "";
        String name = username.getText().toString();
        String password = pass.getText().toString();
        
        try
        {
            if (name.equals("") || password.equals(""))
             {
                JOptionPane.showMessageDialog(null, "Fields should not be Empty");   
             }
            else
             {
                 DbConnection conn = new DbConnection();
                 Connection c = conn.JDBCconnection();
                 String sql = "select Password from registration where UserName=?"; 
                 PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
                 
                 ps.setString(1, name);
                 ResultSet rs = ps.executeQuery();
                  if (rs.next()) {
                    pass1 = rs.getString("Password");
                  }
                  if (pass1.equalsIgnoreCase(password))
                   {
                       JOptionPane.showMessageDialog(null, "User Login Success"); 
                       
                       String sql1 = "select * from registration where UserName=?";
                       PreparedStatement ps1 = (PreparedStatement) c.prepareStatement(sql1);
                       ps1.setString(1, name);
                       ResultSet rs1 = ps1.executeQuery();
                       
                        if (rs1.next()) {
                          user = rs1.getString("UserName");
                          userPassword = rs1.getString("Password");
                          phoneNumber = rs1.getString("Number");
                          email = rs1.getString("Email");
                        }
                       
                       Stage stage = (Stage) pass.getScene().getWindow();
                       stage.close();
                       
                       xValue = JOptionPane.showInputDialog(null, "Enter the X Co-Ordinate");
                       
                       if(Integer.parseInt(xValue) >= 100 && (Integer.parseInt(xValue) <=180))
                         {
                             yValue = JOptionPane.showInputDialog(null, "Enter the Y Co-Ordinate");
                             ImageView car1 = new ImageView("/Image/carpng.png");
                             
                             Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
                             Stage stage1 = new Stage();
                             Scene sc1 = new Scene(root);
                             String ss = getClass().getResource("design.css").toExternalForm();
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
            
                             car1.setTranslateX(xVal);
                             car1.setTranslateY(yVal);
                             
                             sca.add(car1);
                             nodecar.put(user, car1);
                             
                             Tooltip t=new Tooltip(user);
                             Tooltip.install(car1, t);
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
