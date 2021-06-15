/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gts
 */
public class Table1 {

    private final StringProperty pathtable1 = new SimpleStringProperty("");
    private final StringProperty pathtable2 = new SimpleStringProperty("");
    private final StringProperty pathtable3 = new SimpleStringProperty("");
    private final StringProperty pathtable4 = new SimpleStringProperty("");

    public Table1() {
        this("", "", "");
    }

    public Table1(String nodename, String portnum, String id) {
        setNodename(nodename);
        setPortno(portnum);
        setId(id);
        
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

    public String getId() {
        return pathtable3.get();
    }

    public void setId(String value) {
        pathtable3.set(value);
    }
 
}
