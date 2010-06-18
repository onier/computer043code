/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ConnectionTest {

    public static void main(String[] args) {
        String sql = " select * from employee where id = 1";
        String add = "insert into employee values ( 1 , 'Jake','stone','1986-06-05','1988-06-05',1234.11,'LOS','Tester')";
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            statement = connection.createStatement();
            statement.execute(add);
            result = statement.executeQuery(sql);
            ResultSetMetaData metaData = result.getMetaData();
            int size = metaData.getColumnCount();
            for (int i = 1; i < size + 1; i++) {
                System.out.println(metaData.getColumnName(i));
            }
            while (result.next()) {
                System.out.println("  ");
                for (int i = 1; i < size + 1; i++) {
                    System.out.print(result.getObject(i) + "    ");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
