/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbConnection {

    public synchronized static Connection JDBCconnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return (Connection) DriverManager.getConnection("jdbc:mysql://localhost/creditcoin", "root", "root");
    }
}
