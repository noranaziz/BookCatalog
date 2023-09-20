// Catalog.java
import java.util.*;
import java.io.*;

public class Catalog{
    // global variables
    ArrayList<Reader> readers;
    Reader currentReader;
    int currentIndex = 1;

    // mainMenu() - the beginning of the program where the user can either create a new account or log in to an existing account.
    public void mainMenu(){
        // loads in Reader objects that have existing accounts
        loadReaders();
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;

        // print out menu choices & ask for input
        System.out.println("-----BOOK CATALOG-----");
        System.out.println();
        System.out.println("Welcome! This is a place for readers-avid or casual-to view and manage lists of books they've read, books they're currently reading, and books on their wanted list.");
        System.out.println("What would you like to do?");
        System.out.println("1) Create a new account");
        System.out.println("2) Log in (into an existing account)");
        System.out.println("3) Exit the program");
        String choice = input.nextLine();

        // take user to different menus depending on their choice
        if(choice.equals("1")){
            System.out.println("Taking you to the sign up menu...");
            spacer();
            createAccountMenu();
        } else if(choice.equals("2")){
            System.out.println("Taking you to the log in menu...");
            spacer();
            loginMenu();
        } else if(choice.equals("3")){
            System.out.println("Exiting...");
            saveReaders();
            System.exit(0);
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            mainMenu();
        } // end if-elseif-else

        // save new changes in the Reader objects
        saveReaders();
    } // end mainMenu

    // createAccountMenu() - allows the user to create a new account. checks for matching usernames: if the inputted username is the same as an existing username, it asks the user to input another.
    public void createAccountMenu(){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        int counter = 0;

        // print out menu & asks for user input for username and password
        System.out.println("---SIGN UP---\n");
        System.out.println("Please enter your new login credentials.");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        // increases counter by one if the inputted username already is taken by another account
        for(int i = 0; i < readers.size(); i++){
            if(readers.get(i).getUsername().equals(username)){
                counter++;
            } // end if
        } // end for

        // if the counter hasn't been increased, the account is created and added to the arraylist of readers
        // otherwise, they are asked to try again
        if(counter == 0){
            addReader(new Reader(username, password));
            System.out.println("Account successfully created! Returning to main menu...");
            spacer();
            saveReaders();
            mainMenu();
        } else {
            System.out.println("Username has already been taken. Please try again.");
            spacer();
            createAccountMenu();
        } // end if-else
    } // end createAccountMenu

    // loginMenu() - allows the user to sign in to an existing account by inputting a username and password.
    public void loginMenu(){
        // initialize necessary variables
        int counter = 0;
        Scanner input = new Scanner(System.in);

        // print out menu & ask for username and password input
        System.out.println("---LOG IN---\n");
        System.out.println("Please enter your login credentials.");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        // checks to see if the inputted username and password matches any of the existing accounts by increasing the counter
        for(int i = 0; i < readers.size(); i++){
            if(readers.get(i).getUsername().equals(username) && readers.get(i).getPassword().equals(password)){
                currentReader = readers.get(i);
                counter++;
            } // end if
        } // end for

        // if the counter has been increased, the user is logged in to their account and taken to their home menu
        // otherwise, they are asked to try again
        if(counter == 1){
            System.out.println("Successfully logged in! Taking you to your page...");
            spacer();
            readerMenu();
        } else {
            System.out.println("Invalid login credentials.\nWould you like to try again? (Answering Yes/Y will allow you to try again; all other answers will take you to the main menu.)");
            String answer = input.nextLine().toUpperCase();
            // if the user wants to try again, they are asked for username and password input once again
            // otherwise, they are taken back to the main menu
            if(answer.equals("Y") || answer.equals("YES")){
                spacer();
                loginMenu();
            } else {
                System.out.println("Returning to the main menu...");
                spacer();
                mainMenu();
            } // end if-else
        } // end if-else
    } // end loginMenu

    // readerMenu() - allows a logged in user to either go to their library or view their account settings.
    public void readerMenu(){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        System.out.println("---READER MENU---");
        System.out.print("Hello, ");
        // prints out the user's name if they have it set
        // otherwise prints out their username
        if(!(currentReader.getDisplayName().equals(""))){
            System.out.println(currentReader.getDisplayName() + "!\n");
        } else {
            System.out.println(currentReader.getUsername() + "!\n");
        } // end if-else

        // prints out menu choices & asks for user input
        System.out.println("What would you like to do?");
        System.out.println("1) View your library");
        System.out.println("2) Account settings");
        System.out.println("3) Log out");
        String choice = input.nextLine();

        // take user to different menus depending on their choice
        if(choice.equals("1")){
            System.out.println("Taking you to your library...");
            spacer();
            libraryMenu();
        } else if(choice.equals("2")){
            System.out.println("Taking you to your account settings...");
            spacer();
            accountSettings();
        } else if(choice.equals("3")){
            System.out.println("Are you sure you'd like to log out?\nYou will have to log back in. (Answering Yes/Y will log you out; all other answers will take you to your home menu.)");
            String answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                System.out.println("Successfully logged out! Returning to the main menu...");
                spacer();
                currentReader = null;
                mainMenu();
            } else {
                System.out.println("Invalid input. Returning to your home menu...");
                spacer();
                readerMenu();
            } // end if-else
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            readerMenu();
        } // end if-elseif-else
    } // end readerMenu

    // libraryMenu() - allows the user to move to different menus for each of their lists
    public void libraryMenu(){
        // initialize Scanner variable
        Scanner input = new Scanner(System.in);

        // prints out menu choices & asks for user input
        System.out.println("---LIBRARY MENU---");
        System.out.println("What would you like to do?");
        System.out.println("1) View books you have read");
        System.out.println("2) View books you are currently reading");
        System.out.println("3) View books you want to read");
        System.out.println("4) Return to your home menu");
        String choice = input.nextLine();

        // takes user to different menus depending on their choice
        if(choice.equals("1")){
            System.out.println("Taking you to your 'Books Read' menu...");
            spacer();
            booksReadMiniMenu();
        } else if(choice.equals("2")){
            System.out.println("Taking you to your 'Currently Reading' menu...");
            spacer();
            currentReadsMiniMenu();
        } else if(choice.equals("3")){
            System.out.println("Taking you to your 'Want to Read' menu...");
            spacer();
            TBRMiniMenu();
        } else if(choice.equals("4")){
            System.out.println("Returning to your home menu...");
            spacer();
            readerMenu();
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            libraryMenu();
        } // end if-elseif-else
    } // end libraryMenu

    // booksReadMenu() - bigger form of booksReadMiniMenu(). prints out the user's booksRead arraylist along with the menu.
    public void booksReadMenu(){
        // initialize necessary variables
        boolean keepGoing = true;
        int booksRead = currentReader.getBooksRead().size();
        Scanner input = new Scanner(System.in);
        System.out.println("---BOOKS READ MENU---");
        // prints out how many books the user has read
        System.out.print("You have read " + booksRead);
        if(booksRead == 1){
            System.out.println(" book.\n");
        } else {
            System.out.println(" books.\n");
        } // end if-else

        // if the user hasn't read any books yet, it prints that out
        // otherwise, booksRead is printed out 10 entries at a time
        if(booksRead == 0){
            System.out.println("There are no books to list.\n");
        } else {
            // first page of entries are printed out
            viewBooks(currentReader.getBooksRead());
            System.out.println();
            // if the user has more than 10 Book objects in their booksRead arraylist, they are given the option to print the rest out or continue on to the menu
            // if they have 10 Book objects or less, the rest of the menu is printed out
            while(keepGoing){
                if((currentIndex - 1) < booksRead){
                    System.out.println("\nWould you like to view the next page of entries? (Answering Yes/Y will list them out; all other answers will open the rest of the menu.)");
                    String answer = input.nextLine().toUpperCase();
                    System.out.println();
                    // if the user would like to see the next page of entries, they are printed out
                    // otherwise, the rest of the menu is printed out
                    if(answer.equals("YES") || answer.equals("Y")){
                        viewBooks(currentReader.getBooksRead());
                        System.out.println();
                        if((currentIndex - 1) >= booksRead){
                            currentIndex = 1;
                            keepGoing = false;
                        } // end if
                    } else {
                        System.out.println();
                        System.out.println();
                        keepGoing = false;
                        currentIndex = 1;
                    } // end if-else
                } else {
                    System.out.println();
                    System.out.println();
                    keepGoing = false;
                    currentIndex = 1;
                } // end if-else
            } // end while
        } // end if-else

        // print out booksRead menu
        booksReadMiniMenu();
    } // end booksReadMenu

    // currentReadsMenu() - similar to booksReadMenu(); bigger form of currentReadsMiniMenu(). prints out user's currentRead arraylist along with the menu.
    public void currentReadsMenu(){
        // intialize necessary variables
        boolean keepGoing = true;
        int currentReads = currentReader.getCurrentRead().size();
        Scanner input = new Scanner(System.in);
        System.out.println("---CURRENTLY READING MENU---");
        // prints out how many books the user is currently reading
        System.out.print("You are reading " + currentReads);
        if(currentReads == 1){
            System.out.println(" book.\n");
        } else {
            System.out.println(" books.\n");
        } // end if-else

        // if the user isn't reading any books, it prints that out
        // otherwise, currentRead is printed out 10 entries at a time
        if(currentReads == 0){
            System.out.println("There are no books to list.\n");
        } else {
            // first page of entries printed out
            viewBooks(currentReader.getCurrentRead());
            System.out.println();
            // if the user has more than 10 Book objects in their currentRead arraylist, they are given the option to print the rest out or continue on to the menu
            // if they have 10 Book objects or less, the rest of the menu is printed out
            while(keepGoing){
                if((currentIndex - 1) < currentReads){
                    System.out.println("\nWould you like to view the next page of entries? (Answering Yes/Y will list them out; all other answers will open the rest of the menu.)");
                    String answer = input.nextLine().toUpperCase();
                    System.out.println();
                    // if the user would like to see the next page of entries, they are printed out
                    // otherwise, the rest of the menu is printed out
                    if(answer.equals("YES") || answer.equals("Y")){
                        viewBooks(currentReader.getCurrentRead());
                        System.out.println();
                        if((currentIndex - 1) >= currentReads){
                            currentIndex = 1;
                            keepGoing = false;
                        } // end if
                    } else {
                        System.out.println();
                        System.out.println();
                        keepGoing = false;
                        currentIndex = 1;
                    } // end if-else
                } else {
                    System.out.println();
                    System.out.println();
                    keepGoing = false;
                    currentIndex = 1;
                } // end if-else
            } // end while
        } // end if-else

        // print out currentReads menu
        currentReadsMiniMenu();
    } // end currentReadsMenu

    // TBRMenu() - similar to booksReadMenu(); bigger form of TBRMiniMenu(). prints out the user's tbr arraylist along with the menu.
    public void TBRMenu(){
        // initialize necessary variables
        boolean keepGoing = true;
        int booksWanted = currentReader.getTBR().size();
        Scanner input = new Scanner(System.in);
        System.out.println("---BOOKS WANTED MENU---");
        System.out.print("You want to read " + booksWanted);
        // prints out how many books the user wants to read
        if(booksWanted == 1){
            System.out.println(" book.\n");
        } else {
            System.out.println(" books.\n");
        } // end if-else

        // if the user doesn't have any books in their 'want to read' list, prints that out
        // otherwise, tbr is listed 10 entries at a time
        if(booksWanted == 0){
            System.out.println("There are no books to list.\n");
        } else {
            // first page of entries are printed out
            viewBooks(currentReader.getTBR());
            System.out.println();
            // if the user has more than 10 Book objects in their tbr arraylist, they are given the option to print the rest out or continue on to the menu
            // if they have 10 Book objects or less, the rest of the menu is printed out
            while(keepGoing){
                if((currentIndex - 1) < booksWanted){
                    System.out.println("\nWould you like to view the next page of entries? (Answering Yes/Y will list them out; all other answers will open the rest of the menu.)");
                    String answer = input.nextLine().toUpperCase();
                    // if the user would like to view the next page of entries, they are printed out
                    // otherwise, the rest of the menu is printed out
                    if(answer.equals("YES") || answer.equals("Y")){
                        viewBooks(currentReader.getTBR());
                        System.out.println();
                        if((currentIndex - 1) >= booksWanted){
                            currentIndex = 1;
                            keepGoing = false;
                        } // end if
                    } else {
                        System.out.println();
                        System.out.println();
                        keepGoing = false;
                        currentIndex = 1;
                    } // end if-else
                } else {
                    System.out.println();
                    System.out.println();
                    keepGoing = false;
                    currentIndex = 1;
                } // end if-else
            } // end while
        } // end if-else

        // print out TBR menu
        TBRMiniMenu();
    } // end TBRMenu

    // viewBooks(ArrayList<Book> bookList) - prints out the entries of an arraylist of books (parameter is usually filled with the user's booksRead arraylist, currentRead arraylist, or tbr arraylist).
    public void viewBooks(ArrayList<Book> bookList){
        // initialize size of list
        int listSize = bookList.size();

        // if there are still more books to print out after 10 entries, then 10 entries will be printed out
        // otherwise, if there are less than 10 entries to print out, then all entries until the end of the arraylist will be printed out
        if(listSize >= (currentIndex + 9)){
            System.out.println("Listing books " + currentIndex + "-" + (currentIndex + 9) + ":");
            for(int i = (currentIndex - 1); i < (currentIndex + 9); i++){
                System.out.println((i + 1) + ") " + bookList.get(i).getTitle() + " by " + bookList.get(i).getAuthor());
            } // end for
            currentIndex = currentIndex + 10;
            if((currentIndex - 1) == listSize){
                System.out.println("\nYou have reached the end of the list.");
            } // end if
        } else {
            System.out.println("Listing books " + currentIndex + "-" + listSize + ":");
            for(int i = (currentIndex - 1); i < listSize; i++){
                System.out.println((i + 1) + ") " + bookList.get(i).getTitle() + " by " + bookList.get(i).getAuthor());
            } // end for
            currentIndex = listSize + 1;
            System.out.println("\nYou have reached the end of the list.");
        } // end if-else
    } // end viewBooks

    // booksReadMiniMenu() - allows the user to go view/sort their arraylist, add Book objects to their arraylist, delete Book objects from their arraylist, and return to the previous menu.
    public void booksReadMiniMenu(){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        String answer = "";
        
        // print out menu choices
        System.out.println("---BOOKS READ MENU---");
        System.out.println("What would you like to do?");
        System.out.println("1) View list");
        System.out.println("--> 1.5) Sort list");
        System.out.println("2) Add to list");
        System.out.println("3) Delete from list");
        System.out.println("--> 3.5) Clear entire list");
        System.out.println("4) Return to the library menu");
        String choice = input.nextLine();

        // take the user to different menus depending on their choice
        if(choice.equals("1")){
            spacer();
            booksReadMenu();
        } else if(choice.equals("1.5")){
            if(currentReader.getBooksRead().size() == 0){
                System.out.println("There are no books to sort.");
                spacer();
                booksReadMiniMenu();
            } else {
                spacer();
                sortBooksMenu(currentReader.getBooksRead());
            } // end if-else
        } else if(choice.equals("2")){
            spacer();
            addBookReadMenu();
        } else if(choice.equals("3")){
            spacer();
            deleteBookMenu(currentReader.getBooksRead());
        } else if(choice.equals("3.5")){
            spacer();
            System.out.println("Are you sure you'd like to delete your entire list? This cannot be recovered. (Answering Yes/Y confirms your choice; all other answers will return you to the menu.)");
            answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
               currentReader.clearBooksRead();
               System.out.println("All books in your 'Books Read' list have been cleared.\nReturning to previous menu...");
               spacer();
               saveReaders();
               booksReadMiniMenu();
            } else {
                System.out.println("Invalid input. Please try again.");
                spacer();
                booksReadMiniMenu();
            } // end if-else
        } else if(choice.equals("4")){
            System.out.println("Returning to the library menu...");
            spacer();
            libraryMenu();
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            booksReadMiniMenu();
        } // end if-elseif-else
    } // end booksReadMiniMenu

    // currentReadsMiniMenu() - allows the user to finish a book from their currently reading, view/sort arraylist, add/delete from the arraylist, and return to the previous menu.
    public void currentReadsMiniMenu(){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        String answer = "";

        // print out menu choices
        System.out.println("---CURRENTLY READING MENU---");
        System.out.println("What would you like to do?");
        System.out.println("1) I've finished a book on my list!");
        System.out.println("2) View list");
        System.out.println("--> 2.5) Sort list");
        System.out.println("3) Add to list");
        System.out.println("4) Delete from list");
        System.out.println("--> 4.5) Clear entire list");
        System.out.println("5) Return to the library menu");
        String choice = input.nextLine();

        // take user to different menus depending on their choice
        if(choice.equals("1")){
            spacer();
            finishBook();
        } else if(choice.equals("2")){
            spacer();
            currentReadsMenu();
        } else if(choice.equals("2.5")){
            if(currentReader.getCurrentRead().size() == 0){
                System.out.println("There are no books to sort.");
                spacer();
                currentReadsMiniMenu();
            } else {
                spacer();
                sortBooksMenu(currentReader.getCurrentRead());
            } // end if-else
        } else if(choice.equals("3")){
            spacer();
            addCurrentReadMenu();
        } else if(choice.equals("4")){
            spacer();
            deleteBookMenu(currentReader.getCurrentRead());
        } else if(choice.equals("4.5")){
            spacer();
            System.out.println("Are you sure you'd like to delete your entire list? This cannot be recovered. (Answering Yes/Y confirms your choice; all other answers will return you to the menu.)");
            answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                currentReader.clearCurrentRead();
                System.out.println("All books in your 'Current Reads' list have been cleared.\nReturning to previous menu...");
                spacer();
                saveReaders();
                currentReadsMiniMenu();
            } else {
                System.out.println("Invalid input. Please try again.");
                spacer();
                currentReadsMiniMenu();
            } // end if-else
        } else if(choice.equals("5")){
            System.out.println("Returning to the library menu...");
            spacer();
            libraryMenu();
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            currentReadsMiniMenu();
        } // end if-elseif-else
    } // end currentReadsMiniMenu

    // TBRMiniMenu() - allows user to generate a book to read from their TBR, view/sort arraylist, add/delete from arraylist, and return to previous menu.
    public void TBRMiniMenu(){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        String answer = "";

        // print out menu choices
        System.out.println("---BOOKS WANTED MENU---");
        System.out.println("What would you like to do?");
        System.out.println("1) Generate next book to read!");
        System.out.println("2) View list");
        System.out.println("--> 2.5) Sort list");
        System.out.println("3) Add to list");
        System.out.println("4) Delete from list");
        System.out.println("--> 4.5) Clear entire list");
        System.out.println("5) Return to the library menu");
        String choice = input.nextLine();

        // take user to different menus depending on choice
        if(choice.equals("1")){
            spacer();
            generateBook();
        } else if(choice.equals("2")){
            spacer();
            TBRMenu();
        } else if(choice.equals("2.5")){
            if(currentReader.getTBR().size() == 0){
                System.out.println("There are no books to sort.");
                spacer();
                TBRMiniMenu();
            } else {
                spacer();
                sortBooksMenu(currentReader.getTBR());
            } // end if-else
        } else if(choice.equals("3")){
            spacer();
            addTBRMenu();
        } else if(choice.equals("4")){
            spacer();
            deleteBookMenu(currentReader.getTBR());
        } else if(choice.equals("4.5")){
            spacer();
            System.out.println("Are you sure you'd like to delete your entire list? This cannot be recovered. (Answering Yes/Y confirms your choice; all other answers will return you to the menu.)");
            answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                currentReader.clearTBR();
                System.out.println("All books in your 'Want to Read' list have been cleared.\nReturning to previous menu...");
                spacer();
                saveReaders();
                TBRMiniMenu();
            } else {
                System.out.println("Invalid input. Please try again.");
                spacer();
                TBRMiniMenu();
            } // end if-else
        } else if(choice.equals("5")){
            System.out.println("Returning to the library menu...");
            spacer();
            libraryMenu();
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            TBRMiniMenu();
        } // end if-elseif-else
    } // end TBRMiniMenu

    // finishBook() - adds a book that the user is currently reading to their 'Books Read' arraylist and deleting it from their 'Currently Reading'
    public void finishBook(){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;
        int currentReads = currentReader.getCurrentRead().size();
        String position = "";
        int positionNum = 0;
        String title = "";
        String author = "";

        // if the user is not reading anything, print that out
        if(currentReads == 0){
            System.out.println("You are currently not reading any books.");
            spacer();
            currentReadsMiniMenu();
        // otherwise, their current reads are printed out
        } else {
            System.out.println("Printing list...");
            viewBooks(currentReader.getCurrentRead());
            System.out.println();
            while(keepGoing){
                if((currentIndex - 1) < currentReads){
                    viewBooks(currentReader.getCurrentRead());
                    System.out.println();
                } else {
                    keepGoing = false;
                } // end if-else
            } // end while
            currentIndex = 1;

            // ask for user input on what book they just finished
            System.out.println("Please enter the position of the book you just finished (# next to the title).");
            position = input.nextLine();
            positionNum = Integer.parseInt(position) - 1;
            Scanner checker = new Scanner(position);
            while(!checker.hasNextInt()){
                System.out.println("Input must be a number. Please try again.");
                position = input.nextLine();
                checker = new Scanner(position);
            } // end while
            while(positionNum < 0 || (positionNum + 1) > currentReads){
                System.out.println("Input out of bounds. Please try again.");
                position = input.nextLine();
                positionNum = Integer.parseInt(position) - 1;
            } // end while
            title = currentReader.getCurrentRead().get(positionNum).getTitle();
            author = currentReader.getCurrentRead().get(positionNum).getAuthor();
            System.out.println();
            System.out.println("You finished reading:");
            System.out.println(position + ") " + title + " by " + author);
            System.out.println();
            System.out.println("Is this correct? (Answering Yes/Y will confirm your choice; all other answers will return you to the previous menu.)");
            String answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                currentReader.addBookRead(new Book(title, author));
                currentReader.deleteCurrentRead(positionNum);
                System.out.println("Complete. Returning to previous menu...");
                spacer();
                saveReaders();
                currentReadsMiniMenu();
            } else {
                spacer();
                currentReadsMiniMenu();
            } // end if-else
        } // end if-else
    } // end finishBook

    // generateBook() - randomly generates a book using Math.random from the user's tbr.
    public void generateBook(){
        // initializes necessary variables
        Scanner input = new Scanner(System.in);
        int max = currentReader.getTBR().size();
        int randomNum = (int)(Math.random() * max);
        if(max == 0){
            System.out.println("There are no books in your TBR.");
            spacer();
            TBRMiniMenu();
        } else {
            String title = currentReader.getTBR().get(randomNum).getTitle();
            String author = currentReader.getTBR().get(randomNum).getAuthor();
            System.out.println("You should read " + title + " by " + author + " next.");
            spacer();
            TBRMiniMenu();
        } // end if-else
    } // end generateBook

    // sortBooksMenu(ArrayList<Book> bookList) - sorts an arraylist of books by author's first name or title (either booksRead, currentRead, or tbr)
    public void sortBooksMenu(ArrayList<Book> bookList){
        // initializes necessary variables
        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;
        int listSize = bookList.size();

        // prints out menu choices
        System.out.println("---SORT LIST---");
        System.out.println("What would you like to do?");
        System.out.println("1) Sort by author");
        System.out.println("2) Sort by title");
        String choice = input.nextLine();

        // sorts list by author's first name (see compareAuthor class below)
        if(choice.equals("1")){
            ArrayList<Book> sortAuthor = bookList;
            Collections.sort(sortAuthor, new CompareAuthor());
            // print out list 
            System.out.println("Sorted list:\n");
            viewBooks(sortAuthor);
            System.out.println();
            while(keepGoing){
                if((currentIndex - 1) < listSize){
                    viewBooks(sortAuthor);
                    System.out.println();
                } else {
                    keepGoing = false;
                } // end if-else
            } // end while
            currentIndex = 1;
            System.out.println("Sorted successfully.\nReturning to previous menu...");
            spacer();
            if(bookList.equals(currentReader.getBooksRead())){
                booksReadMiniMenu();
            } else if(bookList.equals(currentReader.getCurrentRead())){
                currentReadsMiniMenu();
            } else {
                TBRMiniMenu();
            } // end if-elseif-else
        // sorts list by title (see compareTitle class below)
        } else if(choice.equals("2")){
            ArrayList<Book> sortTitle = bookList;
            Collections.sort(sortTitle, new CompareTitle());
            // print out list
            System.out.println("Sorted list:\n");
            viewBooks(sortTitle);
            System.out.println();
            while(keepGoing){
                if((currentIndex - 1) < listSize){
                    viewBooks(sortTitle);
                    System.out.println();
                } else {
                    keepGoing = false;
                } // end if-else
            } // end while
            currentIndex = 1;
            System.out.println("Sorted successfully.\nReturning to previous menu...");
            spacer();
            if(bookList.equals(currentReader.getBooksRead())){
                booksReadMiniMenu();
            } else if(bookList.equals(currentReader.getCurrentRead())){
                currentReadsMiniMenu();
            } else {
                TBRMiniMenu();
            } // end if-elseif-else
        } else {
            System.out.println("Invalid input. Returning to previous menu...");
            spacer();
            if(bookList.equals(currentReader.getBooksRead())){
                booksReadMiniMenu();
            } else if(bookList.equals(currentReader.getCurrentRead())){
                currentReadsMiniMenu();
            } else {
                TBRMiniMenu();
            } // end if-elseif-else
        } // end if-elseif-else
    } // end sortBooksMenu

    // addBookReadMenu() - adds a Book object to the user's arraylist of books read
    public void addBookReadMenu(){
        // initializes Scanner to take input
        Scanner input = new Scanner(System.in);

        // asks for user input for title and author of book
        System.out.println("---ADD A BOOK---");
        System.out.println("Please enter information about the book:");
        System.out.print("Book title: ");
        String title = input.nextLine();
        System.out.print("Book author: ");
        String author = input.nextLine();
        System.out.println();
        System.out.println("Please review your inputs:");
        System.out.println("Book title: " + title);
        System.out.println("Book author: " + author);
        System.out.println();
        System.out.println("Are you satisfied with your answers? (Answering Yes/Y will confirm your inputs; all other answers will return you to the previous menu.)");
        String answer = input.nextLine().toUpperCase();
        // if the user is satisfied with their answers, the Book is added to their booksRead
        // otherwise, they are taken back to the previous menu
        if(answer.equals("YES") || answer.equals("Y")){
                currentReader.addBookRead(new Book(title, author));
                System.out.println(title + " by " + author + " has been added to your 'Books Read' list.\nReturning to previous menu...");
                spacer();
                saveReaders();
                booksReadMiniMenu();
        } else {
            spacer();
            booksReadMiniMenu();
        } // end if-else
    } // end addBookReadMenu

    // addCurrentReadMenu() - adds a Book object to the user's arraylist of current reads
    public void addCurrentReadMenu(){
        // initializes necessary variables
        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;
        int currentReads = currentReader.getCurrentRead().size();
        int wantedBooks = currentReader.getTBR().size();
        String position = "";
        int positionNum = 0;
        String title = "";
        String author = "";
        System.out.println("---ADD A BOOK---");
        System.out.println("Is this book currently in your 'Want to Read' list? (Answering Yes/Y will allow you to pick from your TBR; all other answers will have you manually enter information.)");
        String answer = input.nextLine().toUpperCase();
        // if the book the user wants to add to their currentReads is in their tbr, then their tbr will be printed out to pick a book from
        // otherwise, the user will have to manually input a title and author to the book they want to add
        if(answer.equals("YES") || answer.equals("Y")){
            System.out.println("Printing list...");
            viewBooks(currentReader.getTBR());
            System.out.println();
            while(keepGoing){
                if((currentIndex - 1) < wantedBooks){
                    viewBooks(currentReader.getTBR());
                    System.out.println();
                } else {
                    keepGoing = false;
                } // end if-else
            } // end while
            currentIndex = 1;
            System.out.println("Please enter the position of the book to add to your 'Currently Reading' list. (# next to the title)");
            position = input.nextLine();
            positionNum = Integer.parseInt(position) - 1;
            Scanner checker = new Scanner(position);
            while(!checker.hasNextInt()){
                System.out.println("Input must be a number. Please try again.");
                position = input.nextLine();
                checker = new Scanner(position);
            } // end while
            while(positionNum < 0 || (positionNum + 1) > wantedBooks){
                System.out.println("Input out of bounds. Please try again.");
                position = input.nextLine();
                positionNum = Integer.parseInt(position) - 1;
            } // end while
            title = currentReader.getTBR().get(positionNum).getTitle();
            author = currentReader.getTBR().get(positionNum).getAuthor();
            System.out.println();
            System.out.println("You want to add:");
            System.out.println(position + ") " + title + " by " + author);
            System.out.println();
            System.out.println("Is this correct? (Answering Yes/Y will add this entry to your 'Currently Reading' list and delete it from your 'Want to Read' list; all other answers will return you to the previous menu.)");
            answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                currentReader.addCurrentRead(new Book(title, author));
                currentReader.deleteBookTBR(positionNum);
                System.out.println("Complete. Returning to previous menu...");
                spacer();
                saveReaders();
                currentReadsMiniMenu();
            } else {
                spacer();
                currentReadsMiniMenu();
            } // end if-else
        } else {
            // this part is very similar to addBookReadMenu()
            System.out.println("Please enter information about the book:");
            System.out.print("Book title: ");
            title = input.nextLine();
            System.out.print("Book author: ");
            author = input.nextLine();
            System.out.println();
            System.out.println("Please review your inputs:");
            System.out.println("Book title: " + title);
            System.out.println("Book author: " + author);
            System.out.println();
            System.out.println("Are you satisfied with your answers? (Answering Yes/Y will confirm your inputs; all other answers will return you to the previous menu.)");
            answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                currentReader.addCurrentRead(new Book(title, author));
                System.out.println(title + " by " + author + " has been added to your 'Currently Reading' list.\nReturning to previous menu...");
                spacer();
                saveReaders();
                currentReadsMiniMenu();
            } else {
                spacer();
                currentReadsMiniMenu();
            } // end if-else
        } // end if-else
    } // end addCurrentReadMenu

    // addTbrMenu() - adds a Book object to an arraylist of books the user wants to read
    public void addTBRMenu(){
        // initialize Scanner for user input
        Scanner input = new Scanner(System.in);

        // following code is very similar to addBookReadMenu()
        System.out.println("---ADD A BOOK---");
        System.out.println("Please enter information about the book:");
        System.out.print("Book title: ");
        String title = input.nextLine();
        System.out.print("Book author: ");
        String author = input.nextLine();
        System.out.println();
        System.out.println("Please review your inputs:");
        System.out.println("Book title: " + title);
        System.out.println("Book author: " + author);
        System.out.println();
        System.out.println("Are you satisfied with your answers? (Answering Yes/Y will confirm your inputs; all other answers will return you to the previous menu.)");
        String answer = input.nextLine().toUpperCase();
        if(answer.equals("YES") || answer.equals("Y")){
            currentReader.addBookTBR(new Book(title, author));
            System.out.println(title + " by " + author + " has been added to your 'Want to Read' list.\nReturning to previous menu...");
            spacer();
            saveReaders();
            TBRMiniMenu();
        } else {
            spacer();
            TBRMiniMenu();
        } // end if-else
    } // end addTBRMenu

    // deleteBookMenu(ArrayList<Book> bookList) - delete a book from an arraylist (either booksRead, currentReads, or tbr)
    public void deleteBookMenu(ArrayList<Book> bookList){
        // initialize necessary variables
        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;
        int listSize = bookList.size();
        String position = "";
        int positionNum = 0;
        String title = "";
        String author = "";

        // prints out list for user to pick a book to delete (if there are no books in the list then they are redirected to the previous menu)
        System.out.println("---REMOVE A BOOK---");
        if(listSize == 0){
            System.out.println("There are no books to list.\nReturning to the previous menu...");
            spacer();
            if(bookList.equals(currentReader.getBooksRead())){
                booksReadMiniMenu();
            } else if(bookList.equals(currentReader.getCurrentRead())){
                currentReadsMiniMenu();
            } else {
                TBRMiniMenu();
            } // end if-elseif-else
        } else {
            // bookList is printed out
            System.out.println("Printing list...");
            viewBooks(bookList);
            System.out.println();
            while(keepGoing){
                if((currentIndex - 1) < listSize){
                    viewBooks(bookList);
                    System.out.println();
                } else {
                    keepGoing = false;
                } // end if-else
            } // end while
            currentIndex = 1;

            // get user input for the position of the book in the arraylist
            System.out.println("Please enter the position of the book (# next to the title).");
            position = input.nextLine();
            positionNum = Integer.parseInt(position) - 1;
            Scanner checker = new Scanner(position);
            while(!checker.hasNextInt()){
                System.out.println("Input must be a number. Please try again.");
                position = input.nextLine();
                checker = new Scanner(position);
            } // end while
            while(positionNum < 0 || (positionNum + 1) > listSize){
                System.out.println("Input out of bounds. Please try again.");
                position = input.nextLine();
                positionNum = Integer.parseInt(position) - 1;
            } // end while
            title = bookList.get(positionNum).getTitle();
            author = bookList.get(positionNum).getAuthor();
            System.out.println();
            System.out.println("You entered:");
            System.out.println(position + ") " + title + " by " + author);
            System.out.println();
            System.out.println("Are you sure you want to delete this entry? (Answering Yes/Y will confirm your choice; all other answers will return you to the previous menu.)");
            String answer = input.nextLine().toUpperCase();

            // deletes book from arraylist
            if(answer.equals("YES") || answer.equals("Y")){
                bookList.remove(positionNum);
                System.out.println(title + " by " + author + " has been deleted from your list.\nReturning to your library...");
                spacer();
                saveReaders();
                libraryMenu();
            } else {
                spacer();
                libraryMenu();
            } // end if-else
        } // end if-else
    } // end deleteBookMenu

    // accountSettings() - allows the user to change their display name, username, password, and delete their account.
    public void accountSettings(){
        // initialize Scanner for user input
        Scanner input = new Scanner(System.in);

        // print out username and display name if it exists
        System.out.println("---ACCOUNT SETTINGS---");
        System.out.println("Username: " + currentReader.getUsername());
        if(currentReader.getDisplayName().equals("")){
            System.out.println("You currently have not set a display name.");
        } else {
            System.out.println("Name: " + currentReader.getDisplayName());
        } // end if-else

        // print out menu choices
        System.out.println("What would you like to do?");
        System.out.println("1) Set display name");
        System.out.println("2) Change username");
        System.out.println("3) Change password");
        System.out.println("4) Delete account");
        System.out.println("5) Return to main menu");
        String choice = input.nextLine();

        // allow the user to change their display name
        if(choice.equals("1")){
            if(currentReader.getDisplayName().equals("")){
                System.out.println("What would you like to set your name to?");
                String name = input.nextLine();
                currentReader.setDisplayName(name);
                System.out.println("Display name successfully set.\nReturning to previous menu...");
                spacer();
                saveReaders();
                accountSettings();
            } else {
                System.out.println("What would you like to change your name to?");
                String name = input.nextLine();
                System.out.println(currentReader.getDisplayName() + "  -->  " + name);
                System.out.println("Do you want to go ahead with this change? (Answering Yes/Y will go ahead with it; all other answers will cancel it.)");
                String answer = input.nextLine().toUpperCase();
                if(answer.equals("YES") || answer.equals("Y")){
                    currentReader.setDisplayName(name);
                    System.out.println("Display name successfully updated.\nReturning to previous menu...");
                    spacer();
                    saveReaders();
                    accountSettings();
                } else {
                    spacer();
                    accountSettings();
                } // end if-else
            } // end if-else
        // allow the user to change their username
        } else if(choice.equals("2")){
            int counter = 0;
            System.out.println("What would you like to change your username to?");
            String username = input.nextLine();
            for(int i = 0; i < readers.size(); i++){
                if(username.equals(readers.get(i).getUsername())){
                    counter++;
                } // end if
            } // end for
            if(counter != 0){
                System.out.println("This username has already been taken.\nReturning to previous menu...");
                spacer();
                accountSettings();
            } else {
                System.out.println(currentReader.getUsername() + "  -->  " + username);
                System.out.println("Do you want to go ahead with this change? (Answering Yes/Y will go ahead with it; all other answers will cancel it.)");
                String answer = input.nextLine().toUpperCase();
                if(answer.equals("YES") || answer.equals("Y")){
                    currentReader.setUsername(username);
                    System.out.println("Username successfully updated.\nReturning to previous menu...");
                    spacer();
                    saveReaders();
                    accountSettings();
                } else {
                    spacer();
                    accountSettings();
                } // end if-else
            } // end if-else
        // allows the user to change their password
        } else if(choice.equals("3")){
            System.out.print("Current password: ");
            String oldPassword = input.nextLine();
            if(oldPassword.equals(currentReader.getPassword())){
                System.out.print("New password: ");
                String newPassword = input.nextLine();
                System.out.println("Do you want to go ahead with this change? (Answering Yes/Y will go ahead with it; all other answers will cancel it.)");
                String answer = input.nextLine().toUpperCase();
                if(answer.equals("YES") || answer.equals("Y")){
                    currentReader.setPassword(newPassword);
                    System.out.println("Password successfully updated.\nReturning to previous menu...");
                    spacer();
                    saveReaders();
                    accountSettings();
                } else {
                    spacer();
                    accountSettings();
                } // end if-else
            } else {
                System.out.println("Incorrect password input.\nReturning to previous menu...");
                spacer();
                accountSettings();
            } // end if-else
        // allows the user to delete their account
        } else if(choice.equals("4")){
            int index = 0;
            System.out.println("Are you sure you want to delete your account? All of your data will be lost. This is not reversible.\n(Answering Yes/Y will go ahead with this process; all other answers will cancel it.)");
            String answer = input.nextLine().toUpperCase();
            if(answer.equals("YES") || answer.equals("Y")){
                System.out.print("Enter username: ");
                String username = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                if(username.equals(currentReader.getUsername()) && password.equals(currentReader.getPassword())){
                    for(int i = 0; i < readers.size(); i++){
                        if(readers.get(i).getUsername().equals(username)){
                            index = i;
                        } // end if
                    } // end for
                    deleteReader(index);
                    System.out.println("Your account has been deleted.\nReturning to login page...");
                    spacer();
                    saveReaders();
                    mainMenu();
                } else {
                    System.out.println("Incorrect username or password.\nReturning to previous menu...");
                    spacer();
                    accountSettings();
                } // end if-else
            } else {
                System.out.println("Cancelled. Returning to previous menu...");
                spacer();
                accountSettings();
            } // end if-else
        } else if(choice.equals("5")){
            spacer();
            readerMenu();
        } else {
            System.out.println("Invalid input. Please try again.");
            spacer();
            accountSettings();
        } // end if-elseif-else
    } // end accountSettings

    // add Reader object to readers
    public void addReader(Reader reader){
        readers.add(reader);
    } // end addReader

    // delete Reader object from readers
    public void deleteReader(int index){
        readers.remove(index);
    } // end deleteReader

    // save readers arraylist to a file called readers.dat
    public void saveReaders(){
        try{
            FileOutputStream fo = new FileOutputStream("readers.dat");
            ObjectOutputStream obOut = new ObjectOutputStream(fo);
            obOut.writeObject(readers);
        } catch(Exception e){
            // System.out.println(e.getMessage());
            return;
        } // end try
    } // end saveReaders

    // load readers arraylist from a file called readers.dat
    public void loadReaders(){
        try{
            FileInputStream fIn = new FileInputStream("readers.dat");
            ObjectInputStream obIn = new ObjectInputStream(fIn);
            readers = (ArrayList<Reader>)obIn.readObject();
            // System.out.println(readers.get(0).getUsername());
        } catch(Exception e){
            readers = new ArrayList<Reader>();
            // System.out.println(e.getMessage());
        } // end try
    } // end loadReaders

    public void spacer(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    } // end spacer

    // compares two Book objects by author name
    public static class CompareAuthor implements Comparator<Book>{
        public int compare(Book book1, Book book2){
            return book1.getAuthor().compareTo(book2.getAuthor());
        } // end compare
    } // end CompareAuthor

    // compares two Book objects by title name
    public static class CompareTitle implements Comparator<Book>{
        public int compare(Book book1, Book book2){
            return book1.getTitle().compareTo(book2.getTitle());
        } // end compare
    } // end CompareTitle
} // end Catalog
