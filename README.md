# 📚 Library Management System

A **Java-based Library Management System (LMS)** built with **Maven**, JDBC, and MySQL. This project allows librarians or administrators to manage books, users, and borrowing/returning transactions in a structured way.

---

## 🚀 Features

* **User Management**

  * Add, view, and delete users.
  * Automatic cascade delete (removes borrowed records when a user is deleted).

* **Book Management**

  * Add, view, and delete books.
  * Track multiple copies of books.
  * Check book availability before borrowing.

* **Borrowing & Returning**

  * Borrow books if available.
  * Prevents users from borrowing the same book twice simultaneously.
  * Track `borrow_date`, `due_date`, and `return_date`.
  * Automatic **Overdue check** when the due date is passed.
  * Return books (with overdue fine handling logic in code).

* **Status Tracking**

  * Each borrow record has a `status`:

    * `Borrowed`
    * `Returned`
    * `OverDue`

* **Database Integrity**

  * Foreign key relationships ensure proper linking between users, books, and borrowed records.

---

## 📂 Project Structure

```
Library_Management_System/    # Main project folder
│
├── src/                     # Java source code
│   └── com/ujjawal/...       # Packages and classes
│
├── target/                  # (auto-generated, ignored in Git)
│
├── pom.xml                  # Maven dependencies
│
.gitignore                   # Ignore target/ and unnecessary files
README.md                    # Project Documentation (this file)
library.sql                  # Database schema and sample data
```


---

## 🛠️ Technologies Used

* **Java (JDK 17 or above)**
* **Maven** (build automation)
* **MySQL** (database)
* **JDBC** (database connectivity)
* **Google Book API** (Book Data)

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Ujjawal1379/Library_Management_System.git
cd Library_Management_System
```

### 2. Database Setup

1. Open **MySQL**.
2. Import the `library.sql` file:

   ```sql
   SOURCE path/to/library.sql;
   ```
3. The database will include the following tables:

   * `Users`
   * `Books`
   * `Borrowed_Books`

### 3. Configure Database Connection

Update `DBConnection.java` with your database credentials:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 4. Build & Run

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.ujjawal.librarymanagementsystem.library_management_system.Main"
```

---

## 📖 Database Schema Overview

### Users Table

```sql
CREATE TABLE users (
    user_id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) DEFAULT NULL UNIQUE,
    address VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

```

### Books Table

```sql
CREATE TABLE Books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(150) NOT NULL,
    publisher VARCHAR(150),
    isbn VARCHAR(20) UNIQUE,
    genre VARCHAR(100),
    language VARCHAR(50) DEFAULT 'English',   -- Added language
    published_year YEAR,                      -- Added published year
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Borrowed\_Books Table

```sql
CREATE TABLE Borrowed_Books (
    borrow_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE NOT NULL,
    return_date DATE,
    status ENUM('Borrowed', 'Returned', 'OverDue') DEFAULT 'Borrowed',

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Books(book_id) ON DELETE CASCADE
);
```

---

## 📊 Example Workflows

* **Borrow a Book**

  * Check if quantity > borrowed count.
  * Insert record into `Borrowed_Books`.
  * Mark as `Borrowed`.

* **Return a Book**

  * If `return_date > due_date` → mark as `OverDue`.
  * Else update `return_date` and mark as `Returned`.

* **Delete User/Book**

  * Related borrowed records are deleted automatically (`ON DELETE CASCADE`).

---

## 🔮 Future Improvements

* Implement **Graphical User Interface (GUI)** using JavaFX or Swing.
* Add **Fine Calculation System** for overdue books.
* Add **Admin vs Student Roles** for better access control.
* REST API integration for web-based usage.

---

## 🤝 Contributing

Pull requests are welcome! For significant changes, please open an issue first to discuss what you’d like to change.

---

## 👨‍💻 Author

**Ujjawal Singh Kunwar**
📌 GitHub: [Ujjawal1379](https://github.com/Ujjawal1379)
