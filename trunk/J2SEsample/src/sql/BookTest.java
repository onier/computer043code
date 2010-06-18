/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class BookTest {

    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = null;
            String insert = "insert into books values(18,?,?)";
            String query = "select * from books";
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            PreparedStatement queStatement = connection.prepareStatement(query);
            ResultSet resultSet = queStatement.executeQuery();
            int size = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                System.out.println("  ");
                for (int i = 1; i < size + 1; i++) {
                    System.out.print(resultSet.getObject(i) + "  ");
                }
            }
            PreparedStatement insertStatement = connection.prepareStatement(insert);
            insertStatement.setString(1, "javaEE");
            insertStatement.setDate(2, Date.valueOf("1999-09-09"));
            insertStatement.executeUpdate();
            resultSet = queStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("  ");
                for (int i = 1; i < size + 1; i++) {
                    System.out.print(resultSet.getObject(i) + "  ");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BookTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
