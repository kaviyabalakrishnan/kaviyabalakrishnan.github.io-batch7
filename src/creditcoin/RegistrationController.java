/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class RegistrationController implements Initializable {

     @FXML
     TextField name,password,number,email;
  
     
     public void submit(ActionEvent event) throws IOException
      {
         String username,pass,contactNum,mailId;
          
          
          if(name.getText().toString().equals(""))
           {
              JOptionPane.showMessageDialog(null, "Name Should not be empty");   
           }
          else if(password.getText().toString().equals(""))
           {
                 JOptionPane.showMessageDialog(null, "Password Should not be empty");   
           }
          else if(number.getText().toString().equals(""))
           {
              JOptionPane.showMessageDialog(null, "Number Should not be empty");   
           }
          else if(email.getText().toString().equals(""))
           {
              JOptionPane.showMessageDialog(null, "Email Should not be empty");   
           }
          else
           {
              username = name.getText().toString();
              pass = password.getText().toString();
              contactNum = number.getText().toString(); 
              mailId=email.getText().toString();
              
              DbConnection conn = new DbConnection();  
              
              try
              {
                  Connection c = conn.JDBCconnection();
                  String sql = "insert into registration(UserName,Password,Number,Email)values(?,?,?,?)";
                  String sql1 = "select UserName from registration where UserName=?";
                  
                  PreparedStatement ps1 = (PreparedStatement) c.prepareStatement(sql1);
                  ps1.setString(1, username);
                  ResultSet rs = ps1.executeQuery(); 
                  
                  if (rs.next()) 
                   {
                     JOptionPane.showMessageDialog(null, "UserName Alredy Exist");
                   }
                  else
                   {
                       PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
                       ps.setString(1, username);
                       ps.setString(2, pass);
                       ps.setString(3, contactNum);
                       ps.setString(4, mailId);
                       ps.executeUpdate();
                       
                        name.setText("");
                        password.setText("");
                        number.setText("");
                        email.setText("");
                        
                        JOptionPane.showMessageDialog(null, "--User Register Successfully--");
                   }
                  
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
              
           }       
      }
     
     public void finish(ActionEvent event) throws IOException {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
