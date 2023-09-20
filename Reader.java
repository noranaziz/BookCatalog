// Reader.java
import java.util.ArrayList;
import java.io.*;

public class Reader implements Serializable{
    // private variables
    private String displayName;
    private String username;
    private String password;
    private ArrayList<Book> booksRead;
    private ArrayList<Book> currentRead;
    private ArrayList<Book> tbr;

    // 1st constructor
    public Reader(){
        displayName = "";
        username = "";
        password = "";
        booksRead = new ArrayList<Book>();
        currentRead = new ArrayList<Book>();
        tbr = new ArrayList<Book>();
    } // end constructor

    // 2nd constuctor
    public Reader(String username, String password){
        displayName = "";
        this.username = username;
        this.password = password;
        booksRead = new ArrayList<Book>();
        currentRead = new ArrayList<Book>();
        tbr = new ArrayList<Book>();
    } // end constructor

    // displayName getter
    public String getDisplayName(){
        return displayName;
    } // end getDisplayName 

    // displayName setter
    public void setDisplayName(String displayName){
        this.displayName = displayName;
    } // end setDisplayName

    // username getter
    public String getUsername(){
        return username;
    } // end getUsername

    // username setter
    public void setUsername(String username){
        this.username = username;
    } // end setUsername

    // password getter
    public String getPassword(){
        return password;
    } // end getPassword

    // password setter
    public void setPassword(String password){
        this.password = password;
    } // end setPassword

    // booksRead getter
    public ArrayList<Book> getBooksRead(){
        return booksRead;
    } // end getBooksRead

    // currentRead getter
    public ArrayList<Book> getCurrentRead(){
        return currentRead;
    } // end getCurrentRead

    // tbr getter
    public ArrayList<Book> getTBR(){
        return tbr;
    } // end getTBR

    // sets booksRead to an empty arraylist
    public void clearBooksRead(){
        booksRead = new ArrayList<Book>();
    } // end clearBooksRead

    // sets currentRead to an empty arraylist
    public void clearCurrentRead(){
        currentRead = new ArrayList<Book>();
    } // end clearCurrentRead

    // sets tbr to an empty arraylist
    public void clearTBR(){
        tbr = new ArrayList<Book>();
    } // end clearTBR

    // adds a Book object to booksRead
    public void addBookRead(Book book){
        booksRead.add(book);
    } // end addBookRead

    // adds a Book object to currentRead
    public void addCurrentRead(Book book){
        currentRead.add(book);
    } // end addCurrentRead

    // adds a Book object to tbr
    public void addBookTBR(Book book){
        tbr.add(book);
    } // end addBookTBR

    // deletes a Book object from booksRead with index
    public void deleteBookRead(int index){
        booksRead.remove(index);
    } // end deleteBookRead

    // deletes a Book object from currentRead with index
    public void deleteCurrentRead(int index){
        currentRead.remove(index);
    } // end deleteCurrentRead

    // deletes a Book object from tbr with index
    public void deleteBookTBR(int index){
        tbr.remove(index);
    } // end deleteBookTBR
} // end Reader
