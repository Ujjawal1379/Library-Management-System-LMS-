/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujjawal.librarymanagementsystem.library_management_system;

/**
 *
 * @author uskun
 */
import java.sql.Timestamp;

public class User {
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Timestamp createdAt;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
//    public User(String name, String email, String phone, String address) {
////        this.userId = userId;
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.createdAt = new Timestamp(System.currentTimeMillis());
//    }

    // Getters
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public Timestamp getCreatedAt() { return createdAt; }

    // Setters
    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setCreatedAt() { this.createdAt = new Timestamp(System.currentTimeMillis()); }
}
