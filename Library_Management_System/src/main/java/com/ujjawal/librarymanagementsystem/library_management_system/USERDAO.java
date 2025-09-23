/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujjawal.librarymanagementsystem.library_management_system;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author uskun
 */
public class USERDAO {
    public void addUser(User user) {
        String query="INSERT INTO Users(name,email,phone,address,created_at) VALUES(?,?,?,?,?)";
        try(Connection con=DBConnection.getConnection()){
            String test_query = "SELECT email, phone FROM Users WHERE phone = ? OR email = ?";
            PreparedStatement test_stmt = con.prepareStatement(test_query);
            test_stmt.setString(1, user.getPhone());
            test_stmt.setString(2, user.getEmail());
            ResultSet resultSet = test_stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("phone").equals(user.getPhone())) {
                    System.out.println("This phone number is already used");
                    return;
                }
                if (resultSet.getString("email").equals(user.getEmail())) {
                    System.out.println("This email is already used");
                    return;
                }
            }
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1,user.getName());
            stmt.setString(2,user.getEmail());
            stmt.setString(3,user.getPhone());
            stmt.setString(4,user.getAddress());
            stmt.setTimestamp(5,user.getCreatedAt());
            
            int result=stmt.executeUpdate();
            if(result>0){
                System.out.println("New User Registered Successfully");
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Retrieve the auto-generated ID (assumes it's an integer type)
                        long generatedId = generatedKeys.getInt(1);
                        System.out.println("Inserted row's ID: " + generatedId);
                    } else {
                        System.out.println("No ID was generated.");
                    }
                }
            }   
            else{
                System.out.println("New User Registered Failed");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void userDetailByEmail(String email){
        String query="SELECT * from Users where email=?";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1,email);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                System.out.println("\nUser Details are:-\n");
                System.out.println("User id: "+result.getInt("user_id"));
                System.out.println("User name: "+result.getString("name"));
                System.out.println("User phone: "+result.getString("phone"));
                System.out.println("User email: "+result.getString("email"));
                System.out.println("Created at: "+result.getTimestamp("created_at"));
                
            }
            else{
                System.out.println("No user found");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void allUsersDetail(){
        String query="SELECT * from Users";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            ResultSet result=stmt.executeQuery();
            boolean f=true;
            while(result.next()){
                f=false;
                System.out.println("\nUser Details are:-\n");
                System.out.println("User id: "+result.getInt("user_id"));
                System.out.println("User name: "+result.getString("name"));
                System.out.println("User phone: "+result.getString("phone"));
                System.out.println("User email: "+result.getString("email"));
                System.out.println("Created at: "+result.getTimestamp("created_at"));
                
            }
            if(f){
                System.out.println("No user found");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void userDeleteByEmail(String email){
        String query="DELETE from Users where email=?";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1,email);
            int result=stmt.executeUpdate();
            if(result>0){
                System.out.println("User Successfully Deleted");
            }
            else{
                System.out.println("No user found to delete");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public int getUserID(String email){
        String query="SELECT User_id from users where email=? ";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1,email);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                return result.getInt("user_id");
            }
            
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        return -1;
    }
    public boolean checkUserID(int uid){
        String query="SELECT User_id from users where user_id=? ";
        boolean flag=false;
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1,uid);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                return true;
            }
            
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        return flag;
    }
}
