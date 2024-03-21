/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Main {
    ArrayList<ComicBook> comicList;
    int numberOfComics;
    String filePath = "comicEntries.txt";
    ComicManagement comicManager;
    Scanner sc = new Scanner(System.in);
    
    public void loadData(){
        comicList = new ArrayList<>();
        try {
            comicManager = new ComicManagement(filePath);
            comicList = comicManager.loadComicList();
            numberOfComics = comicList.size();
            
            
        } catch (ComicException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void showList (){
        comicManager.showComicList();
    }
    

    public int getInteger(String message, String errorMessage){
        boolean isValid = false;
        int input = 0;
        do {            
            try {
                System.out.print(message);
                input = Integer.parseInt(sc.nextLine());
                if (input <= 0 || input >= 1000) {
                    throw new Exception();
                }
                else
                    isValid = true;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            
        } while (!isValid);
        
            return input;
    }
    public double getDouble(String message, String errorMessage){
        boolean isValid = false;
        double input = 0;
        do {            
            try {
                System.out.print(message);
                input = Double.parseDouble(sc.nextLine());
       
                if (input <= 0 || input >= 1000) {
                    throw new Exception();
                }
                else
                    isValid = true;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            
        } while (!isValid);
        
            return input;
    }
    
    public String getString(String message, String errorMessage){
        String input = "";
        boolean isValid = false;
        String pattern = "^\\s+$";
        do {            
            try {
                System.out.print(message);
                input = sc.nextLine();
                if (input.equals("") || input.matches(pattern) || input.length() > 33) {
                    throw new Exception();
                }
                else {
                    isValid = true;
                    input = toUpperFirstLetter(input);
                }
                
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        } while (!isValid);
        
        return input;
    }
    
    
    
    public String toUpperFirstLetter(String unmodifiedString){
        
        char[] chars = unmodifiedString.toLowerCase().toCharArray();
        boolean isFound = false;
        for (int i = 0; i < chars.length; i++) {
            if (!isFound && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                isFound = true;
            } else if (!Character.isLetter(chars[i])) { 
                isFound = false;
          }
        }
        return String.valueOf(chars);
    }
    
    public void add() {
        int volume;
        double rentalPrice;
        String author, title;
        boolean isValid = false;
        
        do {            
            try {
                title = getString("Enter Title of the comic: ", "Accept a string with at least 1 visible character and at most 33 visible characters! Please try again.");
                rentalPrice = getDouble("Enter Rental Price of the comic: ", "Accept positive number less than 1000 only! Please try again.");
                author = getString("Enter Author's Name: ", "Accept a string with at least 1 visible character and at most 33 visible characters! Please try again.");
                volume = getInteger("Enter Volume of the comic: ", "Accept positive integer less than 1000 only! Please try again.");

                numberOfComics = comicManager.addComic(title, rentalPrice, author, volume);
                isValid = true;
            } catch (ComicException ex) {
                   System.out.println(ex.getMessage() + " Please try again.");
            }
            
        } while (!isValid);
        comicManager.showComicList();
    }
    
    public void updateRentalPrice () {
        int id;
        double newPrice;
        boolean isValid = false;
        
        comicManager.showComicList();
        do {
            try {
                id = getInteger("Please enter the ID of the book: ",
                                ">>> ERROR: Accept positive number less than 1000 only! Please try again..");
                newPrice = getDouble("Please enter the Rental Price of the book: ",
                                ">>> ERROR: Accept positive number less than 1000 only! Please try again.");
                comicManager.updateRentalPrice(id, newPrice);
                isValid = true;
                
            } catch (ComicException ex) {
                System.out.println(ex.getMessage() + " Please try again.");
            }
            
        } while (!isValid);
        comicManager.showComicList();
    }
     public void saveResult(){
        comicManager.saveResult();
    }
     
     
    
    public void delete() {
         Scanner scanner = new Scanner(System.in);
        
        int comic = getInteger("Enter Id ", "Accept ID (form 1 to 999 only)");
        numberOfComics = comicManager.deleteComic(comic);
        comicManager.showComicList();
    }
     public void SearchAuthor(){
       String Author;
       Author = getString("Please enter Author's name: ","Accept a string with at least 1 visible character and at most 30 visible characters! Please try again.");
       int b = 0;
       int a = 0;
       ArrayList<String> showInfo = new ArrayList<>();
       ArrayList<ComicBook> CM = new ArrayList<>();
       for( int i = 0 ; i < comicList.size();i++){
           if(comicList.get(i).getAuthor().toLowerCase().equals(Author.toLowerCase())==true){
               b = 1;
               
               CM.add(comicList.get(i));
               
           }
       }
       if(b==0){
           System.out.println("Not Found Author's name. Please enter another Author's name!");
       }else{
        String wall = "|";
        int id, volume;
        String title, author;
        double rentalPrice;
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add("| ID    | Title                               | Rental Price  | Author                              | Volume |");               
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        for (ComicBook comicBook : CM ) {
            id = comicBook.getId();
            title = comicBook.getTitle();
            rentalPrice = comicBook.getRentalPrice();
            author = comicBook.getAuthor();
            volume = comicBook.getVolume();
            
            showInfo.add(String.format(wall + "  %04d " + wall + " %-33s   " + wall + "      %6.2f $ " + wall + " %-33s   " + wall + "   %4d " + wall, id, title, rentalPrice, author, volume));
        }                                                                
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add(String.format("| TOTAL | %-4d entry(s)                                                                                      |", CM.size()));
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
    
        for (String info : showInfo) {
            System.out.println(info);
        }
           
       
       
}
   }  
    
     public  void SearchTitle(){
       String Title;
       Title = getString("Enter Title of the comic: ", "Accept a string with at least 1 visible character and at most 33 visible characters! Please try again.");
       int b = 0;
       int a = 0;
       ArrayList<String> showInfo = new ArrayList<>();
       ArrayList<ComicBook> CM = new ArrayList<>();
       for( int i = 0 ; i < comicList.size();i++){
           if(comicList.get(i).getTitle().toLowerCase().contains(Title.toLowerCase())){
               b = 1;
               
               CM.add(comicList.get(i));
               
           }
       }
       if(b==0){
           System.out.println("Not Found title. Please enter another title!");
       }else{
        String wall = "|";
        int id, volume;
        String title, author;
        double rentalPrice;
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add("| ID    | Title                               | Rental Price  | Author                              | Volume |");               
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        for (ComicBook comicBook : CM ) {
            id = comicBook.getId();
            title = comicBook.getTitle();
            rentalPrice = comicBook.getRentalPrice();
            author = comicBook.getAuthor();
            volume = comicBook.getVolume();
            
            showInfo.add(String.format(wall + "  %04d " + wall + " %-33s   " + wall + "      %6.2f $ " + wall + " %-33s   " + wall + "   %4d " + wall, id, title, rentalPrice, author, volume));
        }                                                                
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add(String.format("| TOTAL | %-4d entry(s)                                                                                      |", CM.size()));
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
    
        for (String info : showInfo) {
            System.out.println(info);
        }
       }
   }
      public static int GetInteger(String message, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        boolean isOk = false;
        int n = 0;
        do {
            try {
                System.out.println(message);
                n = Integer.parseInt(sc.nextLine());
                isOk = true;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        } while (!isOk);
        return n;
    }
     public static void main(String[] args) {
        Main operator = new Main();
        int func;
        String bID;
        String bTitle = "";
        boolean isOk;

        Scanner cin = new Scanner(System.in);
//        ComicManagement comicManager = new ComicManagement();
            operator.loadData();
        do {
            
            System.out.println("          COMIC BOOK RENTAL SHOP");
            System.out.println("1. Add new comic book.");
            System.out.println("2. Search book by title.");
            System.out.println("3. Search book of an author.");
            System.out.println("4. Update book rental price.");
            System.out.println("5. Delete comic book.");
            System.out.println("6. Quit.");


            do {
                isOk = false;
                func = GetInteger("     Please select a function: ", "Function not found! Please select from 1 to 6:");

                if (func < 1 || 6 < func) {
                    System.out.println("     Function not found! Please select from 1 to 6:");
                } else {
                    isOk = true;
                }
            } while (!isOk);

            String strUserEntered = "";

            switch (func) {
                case 1:
                    
                    System.out.println("FUNCTION 1: ADD NEW COMIC BOOK.");
                    System.out.println("-------------------------------");
                    do {
//                      
                        
                        operator.add();

                        do {
                            System.out.print("Do you want to add more book? (Yes/No) ");
                            strUserEntered = cin.nextLine();
                            if (!(strUserEntered.equals("Yes") || strUserEntered.equals("No"))) {
                                System.out.println("Error: You must type 'Yes' or 'No'!");
                            }
                        } while(!(strUserEntered.equals("Yes") || strUserEntered.equals("No")));
                    } while (strUserEntered.equals("Yes"));
                    System.out.print("\n");
                    
                    operator.showList();
                    break;
                case 2:
                    System.out.println("FUNCTION 2: SEARCH BOOK BY TITLE.");
                    System.out.println("---------------------------------");

                    operator.SearchTitle();
                    
                    break;
                case 3:
                    System.out.println("FUNCTION 3: SEARCH BOOK OF AN AUTHOR.");
                    System.out.println("-------------------------------------");

                    operator.SearchAuthor();
                    break;

                case 4:
                    System.out.println("FUNCTION 4: UPDATE BOOK RENTAL PRICE.");
                    System.out.println("-------------------------------------");
                    operator.updateRentalPrice();
                    break;
                case 5:
                    System.out.println("FUNCTION 5: DELETE COMIC BOOK.");
                    System.out.println("------------------------------");
                    
                    operator.delete();
                    break;
                case 6:
                    operator.saveResult();
                    System.out.println("\n------------------------------");
                    System.out.println("Thank you for visiting our shop!\n"
                            + "See you again!");
                    break;
                default:
                    System.out.println("Error: The function must be from 1 to 5!");
            }
        } while (func != 6);

    }

}

    

