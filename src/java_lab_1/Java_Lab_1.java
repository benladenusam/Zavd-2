/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_lab_1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.io.*;

/**
 *
 * @author m-sev
 */
public class Java_Lab_1 {
    static ResultSet rs = null;
    static int e = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Java_Lab_1.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Java_Lab_1;integratedSecurity=true");
            Statement st = con.createStatement();
            Scanner in = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(e != 0){
                System.out.println("Виберіть дію, яку хочете провести над таблицею:");
                System.out.println("0 - Вихід.");
                System.out.println("1 - Додати рядок.");
                System.out.println("2 - Видалити рядок.");
                System.out.println("3 - Редагувати рядок.");
                System.out.println("4 - Вивести таблицю.");
                e = in.nextInt();
                
                if (e == 1){
                    System.out.println("Введіть id:");
                    int id = in.nextInt();
                    
                    String name;
                    String surname;
                    try {
                        System.out.println("Введіть name:");
                        name = br.readLine();
                        System.out.println("Введіть surname:");
                        surname = br.readLine();
                        PreparedStatement ps = con.prepareStatement("INSERT INTO dbo.Students (id, name, surname) values(?, ?, ?)");
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setString(3, surname);
                        ps.executeUpdate();
                    } catch (IOException ex) {
                        Logger.getLogger(Java_Lab_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                if (e == 2){
                    System.out.println("Введіть id:");
                    int id = in.nextInt();
                    
                    PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.Students WHERE id = ?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
                
                if (e == 3){
                    System.out.println("Введіть id для пошуку:");
                    int id1 = in.nextInt();
                    
                    System.out.println("Введіть id:");
                    int id = in.nextInt();
                    String name;
                    String surname;
                    try {
                        System.out.println("Введіть name:");
                        name = br.readLine();
                        System.out.println("Введіть surname:");
                        surname = br.readLine();
                        PreparedStatement ps = con.prepareStatement("UPDATE dbo.Students SET id = ?, name = ?, surname = ? WHERE id = ?");
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setString(3, surname);
                        ps.setInt(4, id1);
                        ps.executeUpdate();
                    } catch (IOException ex) {
                    Logger.getLogger(Java_Lab_1.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                if (e == 4){
                    rs = st.executeQuery("SELECT * FROM dbo.Students");
                    for(int i = 1; i<=rs.getMetaData().getColumnCount(); i++){
                        System.out.print(rs.getMetaData().getColumnName(i)+" ");
                    }
                    System.out.println();
                    while(rs.next()){
                        for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                            System.out.print(rs.getString(i)+" ");
                        }
                        System.out.println();
                    }
                }
            }
            st.close();
           // rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Java_Lab_1.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                
    }
    
}
