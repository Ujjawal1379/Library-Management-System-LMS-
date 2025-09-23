/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujjawal.librarymanagementsystem.library_management_system;

/**
 *
 * @author uskun
 */
public class Book {

    // Fields
    private int bookID;
    private int quantity;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String language;
    private String publishedYear;
    private String publisher;

    // Getters
    public int getBookID() {return bookID;}
    public int getQuantity() {return quantity;}
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public String getIsbn() {return isbn;}
    public String getGenre() {return genre;}
    public String getLanguage() {return language;}
    public String getPublishedYear() {return publishedYear;}
    public String getPublisher() {return publisher;}

    // Setters
    public void setBookID(int bookID) {this.bookID = bookID;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setTitle(String title) {this.title = title;}
    public void setAuthor(String author) {this.author = author;}
    public void setIsbn(String isbn) {this.isbn = isbn;}
    public void setGenre(String genre) {this.genre = genre;}
    public void setLanguage(String language) {this.language = language;}
    public void setPublishedYear(String publishedYear) {this.publishedYear = publishedYear;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
}

//CREATE TABLE Books (
//    book_id INT AUTO_INCREMENT PRIMARY KEY,
//    title VARCHAR(255) NOT NULL,
//    author VARCHAR(150) NOT NULL,
//    publisher VARCHAR(150),
//    isbn VARCHAR(20) UNIQUE,
//    genre VARCHAR(100),
//    language VARCHAR(50) DEFAULT 'English',   -- Added language
//    published_year YEAR,                      -- Added published year
//    quantity INT DEFAULT 1,
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//);
