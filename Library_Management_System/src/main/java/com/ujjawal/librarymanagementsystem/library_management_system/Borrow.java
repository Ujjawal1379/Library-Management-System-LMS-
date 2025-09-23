/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujjawal.librarymanagementsystem.library_management_system;
import java.time.LocalDate;
/**
 *
 * @author uskun
 */
public class Borrow {
    private int book_id;
    private int user_id;
    private LocalDate borrow_date;
    private LocalDate due_date;
    private LocalDate return_date;
    
    public int getBookID(){return book_id;}
    public int getUserID(){return user_id;}
    public LocalDate getBorrowDate(){return borrow_date;}
    public LocalDate getDueDate(){return due_date;}
    public LocalDate getReturnDate(){return return_date;}
    
    public void setBookID(int book_id){this.book_id=book_id;}
    public void setUserID(int user_id){this.user_id=user_id;}
    public void setBorrowDate(){this.borrow_date=LocalDate.now();}
    public void setDueDate(int w){this.due_date=borrow_date.plusWeeks(w);}
    public void setReturnDate(){this.return_date=LocalDate.now();}
    
    
}
//    CREATE TABLE Borrowed_Books (
//    borrow_id INT AUTO_INCREMENT PRIMARY KEY,
//    user_id INT NOT NULL,
//    book_id INT NOT NULL,
//    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    due_date DATE NOT NULL,
//    return_date DATE,
//    status ENUM('Borrowed', 'Returned', 'Overdue') DEFAULT 'Borrowed',
//
//    -- Foreign keys
//    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
//    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Books(book_id) ON DELETE CASCADE
//);
