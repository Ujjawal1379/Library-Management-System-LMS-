/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujjawal.librarymanagementsystem.library_management_system;

/**
 *
 * @author uskun
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;
public class BORROWDAO {
    public void borrowBook(Borrow borrow){
        if(!checkAvailabilityStatus(borrow.getBookID())){
            System.out.println("\nThis Book is not available \n");
            return;
        }
        try(Connection con=DBConnection.getConnection()){
            
            if(checkBookBorrowStatus(borrow.getBookID(),borrow.getUserID())){
                System.out.println("This Book is already borrowed by same person");
                return;
            }
            
            String query="INSERT INTO borrowed_books (book_id,user_id,borrow_date,due_date) VALUES(?,?,?,?)";
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1,borrow.getBookID());
            stmt.setInt(2,borrow.getUserID());
            stmt.setObject(3,borrow.getBorrowDate());
            stmt.setObject(4,borrow.getDueDate());
            int result=stmt.executeUpdate();
            if(result>0){
                System.out.println("\nBook is borrowed to the user \n");
                return;
            }
            System.out.println("\nCan't let user to borrow the book\n");
        
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public boolean checkAvailabilityStatus(int bid){
        boolean status=true;
        String query_1="SELECT COUNT(borrow_id) from borrowed_books where book_id=? and return_date IS NULL ";
        String query_2="SELECT quantity from books where book_id=?";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt_1=con.prepareStatement(query_1);
            stmt_1.setInt(1,bid);
            ResultSet result_1=stmt_1.executeQuery();
            int c1=0;
            if(result_1.next())
                c1=result_1.getInt(1);
            
            PreparedStatement stmt_2=con.prepareStatement(query_2);
            stmt_2.setInt(1,bid);
            ResultSet result_2=stmt_2.executeQuery();
            int c2=0;
            if(result_2.next())
                c2=result_2.getInt(1);
            
            if(c2<=c1){
                status=false;
            }
        }
        catch(SQLException e){
            System.out.print("Error : "+e.getMessage());
        }
        return status;
    }
    
    public void viewBorrowedBook(){
       String query="SELECT * FROM borrowed_books where return_date IS NULL";
       try(Connection con=DBConnection.getConnection()){
           PreparedStatement stmt=con.prepareStatement(query);
           ResultSet result=stmt.executeQuery();
           boolean flag=false;
           int i=1;
           System.out.println("\nDetails are : ");
           while(result.next()){
               flag=true;
               System.out.println((i++)+".----------------------------->");
               System.out.println("borrow id : "+result.getInt("borrow_id"));
               System.out.println("book id : "+result.getInt("book_id"));
               System.out.println("user id : "+result.getInt("user_id"));
               System.out.println("borrow date : "+result.getObject("borrow_date"));
               System.out.println("due date : "+result.getObject("due_date"));
               System.out.println("return date : "+result.getObject("return_date"));
               if(!result.getString("status").equalsIgnoreCase("overdue")&&checkOverDue(result.getInt("book_id"),result.getInt("user_id"))){
                   changeStatus(result.getInt("book_id"),result.getInt("user_id"),"OverDue");
               }
               System.out.println("Status : "+result.getString("status"));
           }
           if(!flag){
               System.out.println("No Borrowed Book data Found");
           }
       }
       catch(SQLException e){
           System.out.println("Error : "+e.getMessage());
       }
    }
    public void allBooksTransactionsHistory(){
       String query="SELECT * FROM borrowed_books";
       try(Connection con=DBConnection.getConnection()){
           PreparedStatement stmt=con.prepareStatement(query);
           ResultSet result=stmt.executeQuery();
           boolean flag=true;
           int i=1;
           System.out.println("\nDetails of all Books Transaction History are : ");
           while(result.next()){
               flag=false;
               System.out.println((i++)+".----------------------------->");
               System.out.println("borrow id : "+result.getInt("borrow_id"));
               System.out.println("book id : "+result.getInt("book_id"));
               System.out.println("user id : "+result.getInt("user_id"));
               System.out.println("borrow date : "+result.getObject("borrow_date"));
               System.out.println("due date : "+result.getObject("due_date"));
               System.out.println("return date : "+result.getObject("return_date"));
               if(!result.getString("status").equalsIgnoreCase("overdue")&&checkOverDue(result.getInt("book_id"),result.getInt("user_id"))){
                   changeStatus(result.getInt("book_id"),result.getInt("user_id"),"OverDue");
               }
               System.out.println("Status : "+result.getString("status"));
           }
           if(flag){
               System.out.println("No Record Found");
           }
       }
       catch(SQLException e){
           System.out.println("Error : "+e.getMessage());
       }
    }
    public void viewOverDueBook(){
       String query="SELECT * FROM borrowed_books where return_date IS NULL and status='OverDue'";
       try(Connection con=DBConnection.getConnection()){
           PreparedStatement stmt=con.prepareStatement(query);
           ResultSet result=stmt.executeQuery();
           boolean flag=false;
           int i=1;
           System.out.println("\nDetails are : ");
           while(result.next()){
               flag=true;
               System.out.println((i++)+".----------------------------->");
               System.out.println("borrow id : "+result.getInt("borrow_id"));
               System.out.println("book id : "+result.getInt("book_id"));
               System.out.println("user id : "+result.getInt("user_id"));
               System.out.println("borrow date : "+result.getObject("borrow_date"));
               System.out.println("due date : "+result.getObject("due_date"));
               System.out.println("return date : "+result.getObject("return_date"));
               System.out.println("Status : "+result.getString("status"));
           }
           if(!flag){
               System.out.println("No   borrowed data Found");
           }
       }
       catch(SQLException e){
           System.out.println("Error : "+e.getMessage());
       }
    }
    
    public boolean checkBookBorrowStatus(int bid,int uid){
        String query="SELECT COUNT(*) FROM borrowed_books where book_id=? and user_id=? and return_date IS NULL";
        
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1, bid);
            stmt.setInt(2, uid);
            ResultSet result=stmt.executeQuery();
            if(result.next())
            {
                if(result.getInt(1)>0){
                    return true;
                }
            }
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        
        return false;
    }
    
    public boolean changeStatus(int bid,int uid,String status){
        String query="UPDATE borrowed_books SET status=? where book_id=? and user_id=? and return_date IS NULL";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setInt(2, bid);
            stmt.setInt(3, uid);
            int result=stmt.executeUpdate();
            if(result>0){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        return false;
    }
    
    public boolean checkOverDue(int bid,int uid){
        LocalDate current_date=LocalDate.now();
        LocalDate due_date = null;
        String query="Select due_date from borrowed_books where book_id=? and user_id=? and return_date IS NULL";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1, bid);
            stmt.setInt(2, uid);
            ResultSet temp_result=stmt.executeQuery();
            if(temp_result.next()){
                due_date=temp_result.getObject("due_date",LocalDate.class);
            }
            if(due_date!=null&&current_date.isAfter(due_date)){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        return false;
    }
    
    public void returnBook(int bid, int uid,Scanner sc){
        if(!checkBookBorrowStatus(bid,uid)){
            System.out.println("this book is currently not borrowed by this user");
            return;
        }
        LocalDate return_date=LocalDate.now();
        try(Connection con=DBConnection.getConnection()){
            
            if(checkOverDue(bid,uid)){
                if(!changeStatus(bid,uid,"OverDue")){
                    System.out.println("Error occured while changing the status from borrowed to overdue ");
                    return;
                }
                System.out.println("This book is overdue ");
                System.out.println("Press Y to return borrowed book after paying fine ");
                String ch=sc.nextLine();
                if(!ch.equalsIgnoreCase("y")){
                    System.out.println("Status Changed");
                    return;
                }else{
                    System.out.println("fine is collected for overdue borrowed book ");
                }
            }
            if(!changeStatus(bid,uid,"Returned")){
                System.out.println("Error occured while changing the status from borrowed to return ");
                return;
            }
            String query="UPDATE borrowed_books SET return_date=? where book_id=? and user_id=? and return_date IS NULL";
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setObject(1,return_date);
            stmt.setInt(2,bid);
            stmt.setInt(3,uid);
            int result=stmt.executeUpdate();
            if(result>0){
                System.out.println("Book is returned ");
            }
            else{
                System.out.println("Error ocurred in system while returning borrowed book ");
            }
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public boolean checkIfUserHaveBook(int uid){
        String query="SELECT COUNT(*) From borrowed_books where user_id=? and return_date IS NULL";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1,uid);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                return result.getInt(1) > 0;
            }
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        return false;
    }
    public boolean checkIfBookIsBorrowed(int uid){
        String query="SELECT COUNT(*) From borrowed_books where book_id=? and return_date IS NULL";
        try(Connection con=DBConnection.getConnection()){
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1,uid);
            ResultSet result=stmt.executeQuery();
            if(result.next()){
                return result.getInt(1) > 0;
            }
        }
        catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
        return false;
    }
    
}
