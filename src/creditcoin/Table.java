/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Table 
{
    private final StringProperty pathtable1 = new SimpleStringProperty("");
    private final StringProperty pathtable2 = new SimpleStringProperty("");
    private final StringProperty pathtable3 = new SimpleStringProperty("");
    private final StringProperty pathtable4 = new SimpleStringProperty("");
    private final StringProperty pathtable5 = new SimpleStringProperty("");
    
   public Table()
    {
        this("","","","","");
    }
   
   public Table(String nodename,String portno,String location,String message,String signal)
   {
       setNodename(nodename);
       setPortno(portno);
       setLocation(location);
       setMessage(message);
       setSignal(signal);
   }
   
   public String getSignal(){
       return pathtable5.get();
   }
   
   public void setSignal(String value)
   {
       pathtable5.set(value);
   }
   
   
    public String getNodename() {
        return pathtable1.get();
    }

    public void setNodename(String value) {
        pathtable1.set(value);
    }
   
     public String getPortno() {
        return pathtable2.get();
    }

    public void setPortno(String value) {
        pathtable2.set(value);
    }
    
     public String getLocation() {
        return pathtable3.get();
    }

    public void setLocation(String value) {
        pathtable3.set(value);
    }

   public String getMessage(){
       return pathtable4.get();
   }
   
   public void setMessage(String value)
   {
       pathtable4.set(value);
   }
    
}
