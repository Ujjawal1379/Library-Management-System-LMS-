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
public class BOOKDAO {
    public void addBOOK(Book newBook) {
        String Test_Query="SELECT COUNT(*) from Books where title=? and author=? and publisher=? and isbn=? and genre=? and language=? and published_year=?";
        String query="INSERT INTO Books(title,author,publisher,isbn,genre,language,published_year,quantity) VaLUES(?,?,?,?,?,?,?,?)";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement test_stmt=con.prepareStatement(Test_Query);
            test_stmt.setString(1, newBook.getTitle());
            test_stmt.setString(2, newBook.getAuthor());
            test_stmt.setString(3, newBook.getPublisher());
            test_stmt.setString(4, newBook.getIsbn());
            test_stmt.setString(5, newBook.getGenre());
            test_stmt.setString(6, newBook.getLanguage());
            test_stmt.setString(7, newBook.getPublishedYear());
            ResultSet testResultSet = test_stmt.executeQuery();
            if(testResultSet.next() && testResultSet.getInt(1) > 0){
                System.out.println("\nBook is already added");
                return;
            }
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1, newBook.getTitle());
            stmt.setString(2, newBook.getAuthor());
            stmt.setString(3, newBook.getPublisher());
            stmt.setString(4, newBook.getIsbn());
            stmt.setString(5, newBook.getGenre());
            stmt.setString(6, newBook.getLanguage());
            stmt.setString(7, newBook.getPublishedYear());
            stmt.setInt(8, newBook.getQuantity());
            int result=stmt.executeUpdate();
            if(result>0){
                System.out.println("\nBook Addded Successfully\n");
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Retrieve the auto-generated ID (assumes it's an integer type)
                        long generatedId = generatedKeys.getInt(1);
                        System.out.println("Books ID: " + generatedId);
                        System.out.println("Make sure to remember it");
                    }
                }
            }
            else{
                System.out.println("Process failed , Unexpected error Occured");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void printBOOKDetailsByTitle(int bid) {
        String query="Select * from Books where book_id=?";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1, bid);
            ResultSet result=stmt.executeQuery();
            boolean found=false;
            while(result.next()){
                found = true;
                System.out.println("\nBook Details are ");
                System.out.println("\nBook ID is                : "+result.getInt("book_id"));
                System.out.println("\nBook Title                : "+result.getString("title"));
                System.out.println("\nBook Author               : "+result.getString("author"));
                System.out.println("\nBook publisher            : "+result.getString("publisher"));
                System.out.println("\nBook Publihed Year        : "+result.getString("published_year"));
                System.out.println("\nBook ISBN Number          : "+result.getString("isbn"));
                System.out.println("\nBook Quantity             : "+result.getInt("quantity"));
                System.out.println("\nBook Language             : "+result.getString("language"));
                System.out.println("\nBook Genre is             : "+result.getString("genre"));
                System.out.println("\nBook Added to Library at  : "+result.getString("created_at"));
                
            }
            if (!found) {
                System.out.println("\nBook not found\n");
            }
        }
        catch(SQLException e){
            System.out.println("Error "+e.getMessage());
        }
    }
    public void allBooksDetails() {
        String query="Select * from Books";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            ResultSet result=stmt.executeQuery();
            boolean found=true;
            while(result.next()){
                found = false;
                System.out.println("\nBook Details are ");
                System.out.println("\nBook ID is                : "+result.getInt("book_id"));
                System.out.println("\nBook Title                : "+result.getString("title"));
                System.out.println("\nBook Author               : "+result.getString("author"));
                System.out.println("\nBook publisher            : "+result.getString("publisher"));
                System.out.println("\nBook Publihed Year        : "+result.getString("published_year"));
                System.out.println("\nBook ISBN Number          : "+result.getString("isbn"));
                System.out.println("\nBook Quantity             : "+result.getInt("quantity"));
                System.out.println("\nBook Language             : "+result.getString("language"));
                System.out.println("\nBook Genre is             : "+result.getString("genre"));
                System.out.println("\nBook Added to Library at  : "+result.getString("created_at"));
                
            }
            if (found) {
                System.out.println("\nBook not found\n");
            }
        }
        catch(SQLException e){
            System.out.println("Error "+e.getMessage());
        }
    }
    public void deleteBOOKDetailsByID(int bid) {
        String query="DELETE from Books where book_id=?";
        try(Connection con=DBConnection.getConnection()){
            
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1,bid);
            int result=stmt.executeUpdate();
            if(result>0){
                System.out.println("\nBook Details are permanently deleted from the Library database\n");
                return;
            }
            System.out.println("\nError occured while deleting book \n");
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    public boolean checkBookID(int bid){
        String query="SELECT book_id from books where book_id=? ";
        boolean flag=false;
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1,bid);
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
    public int getBookID(){
        
        return -1;
    }
}
