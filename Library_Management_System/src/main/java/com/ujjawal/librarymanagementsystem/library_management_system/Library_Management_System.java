    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ujjawal.librarymanagementsystem.library_management_system;

/**
 *
 * @author uskun
 */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;
import org.json.JSONObject;
import org.json.JSONArray;


public class Library_Management_System {
    static String getName(Scanner sc){
            String name = sc.nextLine();
            while(name.length()<6){
                System.out.println("\nName is too small Enter Full Name");
                System.out.print("\nEnter Name: ");
                name=sc.nextLine();
            }
        return name;
    }
    
    static String getEmail(Scanner sc){
        String email=sc.nextLine().trim();
        boolean f;
        do{
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern p = Pattern.compile(emailRegex);
            f=p.matcher(email).matches();
            if(!f){
                System.out.println("\nEmail is not valid please enter valid email address");
                System.out.print("\nEnter Email: ");
                email=sc.nextLine().trim();
            }
        }while(!f);
        return email;
    }
    static String getPhone(Scanner sc){
        String phone=sc.nextLine().trim();
        boolean f;
        do{
            String PhoneRegex="^[0-9]{10}$";
            Pattern p=Pattern.compile(PhoneRegex);
            f=p.matcher(phone).matches();
            if(!f){
                System.out.println("\nPhone number is not valid please enter valid Phone number");
                System.out.print("\nEnter Phone: ");
                phone=sc.nextLine().trim();
            }
        }while(!f);
        return phone;
    }
    
    static String getISBN(Scanner sc){
        String isbn=sc.nextLine().trim();
        boolean f;
        do{
            String isbnRegex = "^(?:\\d{9}[\\dX]|\\d{13})$";
            Pattern pattern = Pattern.compile(isbnRegex);
            f = pattern.matcher(isbn).matches();
            if(!f){
                System.out.println("\nISBN number is not valid please enter valid ISBN number");
                System.out.print("\nEnter ISBN: ");
                isbn=sc.nextLine().trim();
            }
        }while(!f);
        return isbn;
    }
    private static int readInt(Scanner sc){
        int number=0;
        boolean con=true;
        while(con)
        {  try
           { number=Integer.parseInt(sc.next());
             sc.nextLine();
              con=false;
            }
        catch(Exception e)
            {  System.out.println("Invalid value. Please enter a valid amount number.");        
            }
        }
        return number; 
      }
    static JSONObject getBookDetail(String isbn,Scanner sc){
        JSONObject data=new JSONObject();
        String key="YOUR_GOOGLE_API_KEY";
        String url="https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn+"&key="+key;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();

        HttpClient client=HttpClient.newBuilder().build();
        try{
            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            String value=response.body();
            JSONObject obj = new JSONObject(value);
            if (!obj.has("items")) {
                return data;
            }
            JSONArray items = obj.getJSONArray("items");
            int len = items.length();
            if(len==0)
                return data;
            for(int i=0;i<len;i++){
                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
                String t=volumeInfo.getString("title");
                JSONArray authArray = volumeInfo.optJSONArray("authors");
                String auth = "Unknown";
                if (authArray != null) {
                    auth = authArray.join(", ").replace("\"", "");
                }

                String publYear=volumeInfo.optString("publishedDate","unknown");
                System.out.println("\n"+(i+1)+". Book Details ");
                System.out.println("Title : "+t);
                System.out.println("Author : "+auth);
                System.out.println("Published Year : "+publYear);
            }
            System.out.print("\nwhich is the book you want to add :");
            int j=readInt(sc);
            if(j<1||j>len){
                return data;
            }
            JSONObject volumeInfo = items.getJSONObject(j-1).getJSONObject("volumeInfo");
            String title=volumeInfo.getString("title");
            JSONArray authorsArray = volumeInfo.optJSONArray("authors");
            String authors = "Unknown";
            if (authorsArray != null) {
                authors = authorsArray.join(", ").replace("\"", "");
            }

            String publishedYear=volumeInfo.optString("publishedDate","unkown");
            if ((!publishedYear.equals("unkown"))&&publishedYear.length() >= 4) {
                publishedYear = publishedYear.substring(0, 4); // always get the year
            }
            String language=volumeInfo.optString("language","Unknown");
            JSONArray genreArray = volumeInfo.optJSONArray("categories");
            String genre = "Unknown";
            if (genreArray != null) {
                genre = genreArray.join(", ").replace("\"", "");
            }
            String publisher=volumeInfo.optString("publisher", "unknown");
            data.put("title",title);
            data.put("author",authors);
            data.put("publishedYear",publishedYear);
            data.put("language",language);
            data.put("genre",genre);
            data.put("publisher",publisher);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) {
        Scanner kb=new Scanner(System.in);
        USERDAO userDAO = new USERDAO();
        BOOKDAO bookDAO=new BOOKDAO();
        BORROWDAO borrowDAO=new BORROWDAO();
        while(true){
            System.out.println("\n===== 📚 Library Management System =====");
            System.out.println("1. Add New User");
            System.out.println("2. View User by EMAIL");
            System.out.println("3. Add New Book");
            System.out.println("4. Search Book by ID");
            System.out.println("5. Issue Book to User");
            System.out.println("6. Return Book");
            System.out.println("7. View All Borrowed Books");
            System.out.println("8. View Overdue Books");
            System.out.println("9. Delete User");
            System.out.println("10. Delete Book");
            System.out.println("11. view all Users Details");
            System.out.println("12. view all Books Details");
            System.out.println("12. view all Books Transaction Details");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            int choice = readInt(kb);
            System.out.println();
            String email,phone,name,address;
            int uid,bid;
            boolean ucheck,bcheck;
            switch(choice){
                
                case 1:
                    System.out.print("Enter Name: ");
                    name = getName(kb);
                    System.out.print("Enter Email: ");
                    email= getEmail(kb);
                    System.out.print("Enter Phone: ");
                    phone = getPhone(kb);
                    System.out.print("Enter Address: ");
                    address = kb.nextLine();
                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setPhone(phone);
                    newUser.setEmail(email);
                    newUser.setAddress(address);
                    newUser.setCreatedAt();
                    userDAO.addUser(newUser);
                    break;
                case 2:
                    System.out.print("Enter Regestered Email: ");
                    email = getEmail(kb);
                    userDAO.userDetailByEmail(email);
                    break;
                case 3:
                    System.out.print("Enter ISBN Code(Must be 13/10 digit length) of the book: ");
                    String isbn=getISBN(kb);
                    JSONObject  data=getBookDetail(isbn,kb);
                    if(data.isEmpty()){
                        System.out.println("\nBook not found\n");
                        break;
                    }
                    System.out.print("\nEnter Quantity of : ");
                    int quantity=readInt(kb);
                    while(quantity<1){
                        System.out.print("\nQuantity of book can't be negative, Re-enter Quantity");
                        quantity=readInt(kb);
                    }
                    Book newBook=new Book();
                    newBook.setAuthor(data.getString("author"));
                    newBook.setIsbn(isbn);
                    newBook.setTitle(data.getString("title"));
                    newBook.setPublisher(data.getString("publisher"));
                    newBook.setLanguage(data.getString("language"));
                    newBook.setPublishedYear(data.getString("publishedYear"));
                    newBook.setGenre(data.getString("genre"));
                    newBook.setQuantity(quantity);
                    
                    bookDAO.addBOOK(newBook);
                    break;
                case 4:
                    System.out.print("\nEnter Book ID : ");
                    bid=readInt(kb);
                    bookDAO.printBOOKDetailsByTitle(bid);
                    break;
                case 5:
                    System.out.print("Enter user id : ");
                    uid=readInt(kb);
                    ucheck=userDAO.checkUserID(uid);
                    if(!ucheck){
                        System.out.println("\nUser not found or Wrong user id\n");
                        break;
                    }
                    System.out.print("Enter Book id : ");
                    bid=readInt(kb);
                    bcheck=bookDAO.checkBookID(bid);
                    if(!bcheck){
                        System.out.println("\nBook not found\n");
                        break;
                    }
                    System.out.print("Enter number of Weeks you want to borrow the book : ");
                    int w=readInt(kb);
                    if(w<1){
                        System.out.println("\nWorng input week can't be taken input in negative or zero\n");
                        break;
                    }
                    Borrow borrow=new Borrow();
                    borrow.setUserID(uid);
                    borrow.setBookID(bid);
                    borrow.setBorrowDate();
                    borrow.setDueDate(w);
                    borrowDAO.borrowBook(borrow);
                    break;
                case 6:
                    System.out.print("Enter user id : ");
                    uid=readInt(kb);
                    ucheck=userDAO.checkUserID(uid);
                    if(!ucheck){
                        System.out.println("\nUser not found or Wrong user id\n");
                        break;
                    }
                    System.out.print("Enter Book id : ");
                    bid=readInt(kb);
                    bcheck=bookDAO.checkBookID(bid);
                    if(!bcheck){
                        System.out.println("\nBook not found\n");
                        break;
                    }
                    if (!borrowDAO.checkBookBorrowStatus(bid,uid)) {
                        System.out.println("No data of borrowing this book by this user found");
                        break;
                    }
                    borrowDAO.returnBook(bid, uid,kb);
                    break;
                case 7:
                    System.out.println("List of all current  borrowed books");
                    borrowDAO.viewBorrowedBook();
                    break;
                case 8:
                    System.out.println("List of all current overdue books ");
                    borrowDAO.viewOverDueBook();
                    break;
                case 9:
                    System.out.print("Enter Regestered Email: ");
                    email = getEmail(kb);
                    int id=userDAO.getUserID(email);
                    if(id==-1){
                       System.out.println("\nUser not found or Wrong user id\n");
                        break; 
                    }
                    if(borrowDAO.checkIfUserHaveBook(id)){
                        System.out.println("You can't delete this user cause this user still haven't returned the borrowed book");
                        break;
                    }
                    System.out.println("User details goinng to be delete also records of the borrowed book");
                    userDAO.userDeleteByEmail(email);
                    break;
                case 10:
                    System.out.print("Enter ID of the book you want to delete : ");
                    bid=readInt(kb);
                    bcheck=bookDAO.checkBookID(bid);
                    if(!bcheck){
                        System.out.println("\nBook not found in the library system\n");
                        break;
                    }
                    if(borrowDAO.checkIfBookIsBorrowed(bid)){
                        System.out.println("\nBook is borrowed by someone can't delete it now\n");
                        break;
                    }
                    System.out.println("\nBook data as well as it's borrowed data is also going to delete\n");
                    bookDAO.deleteBOOKDetailsByID(bid);
                    break;
                case 11:
                    System.out.println("All User Details : ");
                    userDAO.allUsersDetail();
                    break;
                case 12:
                    System.out.println("All Books Details : ");
                    bookDAO.allBooksDetails();
                    break;
                case 13:
                    System.out.println("All Book Transaction History : ");
                    borrowDAO.allBooksTransactionsHistory();
                    break;
                case 14:
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                    break;

                default:
                    
            }
        }
    }
}
