/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Admin
 */
public class ComicManagement{
    private String comicList_filePath;
    private int numberofComics;
    private ArrayList<ComicBook> comicList;

    public ComicManagement(String comicList_FILE) throws ComicException {
        if (comicList_FILE.equals("")) {
            throw new ComicException("File path of comicList.txt cannot be empty!");
        }else{
            this.comicList_filePath = comicList_FILE;
            this.comicList = new ArrayList<>();
            this.numberofComics = 0;
        }
    }
    
    public ArrayList<ComicBook> loadComicList() throws IOException, ComicException {
        File comicList_file = new File(comicList_filePath);
        ArrayList<ComicBook> tempList = new ArrayList<>();
        
        if (!comicList_file.exists()) {
            comicList_file.createNewFile();
            System.out.println("Data file does not exist. "+
                               "Creating data file comicList.txt... "+
                               "Done!");
            this.numberofComics = 0;
        }else{
            System.out.println("Data file comicList.txt has been found. "+
                               "Loading data from the file...");
            try(FileReader reader = new FileReader(comicList_file);
                BufferedReader buffer = new BufferedReader(reader)){
                String id, title, rentalPrice, author, volume; 
                
                this.numberofComics = Integer.parseInt(buffer.readLine());
                
                for (int i = 0; i < this.numberofComics; i++) {
                    id = buffer.readLine();
                    title = buffer.readLine();
                    rentalPrice = buffer.readLine();
                    author = buffer.readLine();
                    volume = buffer.readLine();
                    
                    tempList.add(new ComicBook(Integer.parseInt(id),
                                                    title,
                                                    Double.parseDouble(rentalPrice),
                                                    author,
                                                    Integer.parseInt(volume)));
                }
                
                boolean isDuplicated = false;
                int currentId, nextId, currentVolume, nextVolume;
                String currentTitle, nextTitle;
                
                for (int i=0; i<tempList.size()-1; i++) {
                    for (int j = i+1; j < tempList.size(); j++) {
                    currentId = tempList.get(i).getId();
                    nextId = tempList.get(j).getId();
                    currentVolume = tempList.get(i).getVolume();
                    nextVolume = tempList.get(j).getVolume();
                    currentTitle = tempList.get(i).getTitle();
                    nextTitle = tempList.get(j).getTitle();
                    
                    if (currentId == nextId || (currentVolume == nextVolume && currentTitle.equals(nextTitle))){
                        isDuplicated = true;
                        throw new ComicException("The file contains duplicated data (ID or same Title with same Volume).");
                    }
                        
                    }
                }
                
                Collections.sort(tempList);
                
                if (!isDuplicated) {
                    for (ComicBook tempComic : tempList) {
                        this.comicList.add(tempComic);
                    }
                }
                
            }
                
        }
        System.out.println("Done! [" + this.numberofComics + " comic books]");
        return this.comicList;
    }
    
    public int idGenerator (int id){
        boolean isDuplicated = false;
        int loopCounter = 0;
        do {
            for (int j = 0; j < this.comicList.size(); j++) {
                loopCounter = j;
                if (id == this.comicList.get(j).getId()) {
                    isDuplicated = true;
                    id++;
                    break;
                    }
                else{
                    isDuplicated = false;
                }
            }
        }while(isDuplicated && loopCounter < this.comicList.size() - 1);
        
        boolean usableIDFound = false;
        if (id >= 1000) {       
            do {
                for (int j = 1; j < 1000; j++) {                        
                    if (j != this.comicList.get(j-1).getId()) {
                        id = j;
                        usableIDFound = true;
                        break;
                    }
                            
                }
            } while(!usableIDFound);
        }
        return id;
    }
    
    public int addComic(String title, double rentalPrice, String author, int volume) throws ComicException{
        boolean isExisting = false;
        int id = idGenerator(this.numberofComics + 1);
            
        for (ComicBook comic : this.comicList) {
            if (  (comic.getTitle().equals(title) && comic.getVolume() == volume)) {
                isExisting = true;
                
                
                throw new ComicException("New comic entry has already existed in the database.");
            }
        }
        if (!isExisting) {
            this.comicList.add(new ComicBook(id, title, rentalPrice, author, volume));
            this.numberofComics++;
        }
        Collections.sort(this.comicList);
        
        return this.numberofComics;
    }
            
    
    public void showComicList(){
        ArrayList<String> showInfo = new ArrayList<>();
        String wall = "|";
        int id, volume;
        String title, author;
        double rentalPrice;
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add("| ID    | Title                               | Rental Price  | Author                              | Volume |");               
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        for (ComicBook comicBook : this.comicList ) {
            id = comicBook.getId();
            title = comicBook.getTitle();
            rentalPrice = comicBook.getRentalPrice();
            author = comicBook.getAuthor();
            volume = comicBook.getVolume();
            
            showInfo.add(String.format(wall + "  %04d " + wall + " %-33s   " + wall + "      %6.2f $ " + wall + " %-33s   " + wall + "   %4d " + wall, id, title, rentalPrice, author, volume));
        }                                                                
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add(String.format("| TOTAL | %-4d entry(s)                                                                                      |", this.comicList.size()));
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
    
        for (String info : showInfo) {
            System.out.println(info);
        }
    }
    
    public void updateRentalPrice(int id, double newPrice) throws ComicException{
        
        for(ComicBook comic : this.comicList){
            if (id == comic.getId()) {
                comic.setRentalPrice(newPrice);
            }
           
       }
   
   }
    
        
    public int deleteComic(int id) {
        String formattedID = String.format("%04d", id);
        boolean checkID = false;
        
        Iterator<ComicBook> comicIterator = this.comicList.iterator();
        while(comicIterator.hasNext()){
            if (comicIterator.next().getId() == id) {
                checkID = true;
                comicIterator.remove();
                this.numberofComics--;
            }
            
        }
        Collections.sort(this.comicList);
        
        if (!checkID) {
            System.out.println("ComicException: " + "Comic with ID " + formattedID + " cannnot be found in the comic list.");
        }
    
    return this.numberofComics;
    }
    
    public void saveResult(){
        int id, volume;
        String title, author;
        double rentalPrice;
        String filePath = "comicList_filePath";
        String result="";
        try {
            // Xóa toàn bộ dữ liệu trong file .txt
            BufferedWriter clearWriter = new BufferedWriter(new FileWriter(this.comicList_filePath, false));
            clearWriter.write("");
            clearWriter.close();

            // Nhập các dữ liệu từ mảng vào từng dòng trong file .txt
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.comicList_filePath, true));
            result += String.format("%d\n",this.numberofComics);
            for (ComicBook comicBook : this.comicList) {
                id = comicBook.getId();
                title = comicBook.getTitle();
                rentalPrice = comicBook.getRentalPrice();
                author = comicBook.getAuthor();
                volume = comicBook.getVolume();
            
                result += String.format("%d\n",id) ;
                result += String.format("%s\n",title);
                result += String.format("%f\n",rentalPrice);
                result += String.format("%s\n",author);
                result += String.format("%s\n",volume);
               
            }
             writer.write(result);
                writer.newLine();
                writer.close();

            System.out.println("Data has been updated in file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
