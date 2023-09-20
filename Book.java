// Book.java
import java.util.ArrayList;
import java.io.*;

public class Book implements Serializable{
    // private variables
    private String title;
    private String author;

    // first constructor
    public Book(){
        title = "";
        author = "";
    } // end constructor

    // second constructor
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    } // end constructor

    // title getter
    public String getTitle(){
        return title;
    } // end getTitle

    // title setter
    public void setTitle(String title){
        this.title = title;
    } // end setTitle

    // author getter
    public String getAuthor(){
        return author;
    } // end getAuthor

    // author setter
    public void setAuthor(String author){
        this.author = author;
    } // end setAuthor
} // end Book
