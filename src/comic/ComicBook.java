/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comic;

/**
 *
 * @author Admin
 */
public class ComicBook implements Comparable<ComicBook>{
    private int id;
    private String title;
    private double rentalPrice;
    private String author;
    private int volume;

    public ComicBook(int id, String title, double rentalPrice, String author, int volume) throws ComicException{
        this.setId(id);
        this.setTitle(title);
        this.setRentalPrice(rentalPrice);
        this.setAuthor(author);
        this.setVolume(volume);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws ComicException {
        if (id <= 0 || id >= 1000) {
            throw new ComicException("Comic ID must be a positive integer less than 1000.");
        } else {
            this.id = id;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws ComicException{
        if (title.length() > 33) {
            throw new ComicException("Comic Title cannot contain more than 33 characters.");
        } else {
            this.title = title;
        }
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) throws ComicException {
        if (rentalPrice <= 0 || rentalPrice >= 1000) {
            throw new ComicException("Rental Price must be a positive number less than 1000.");
        } else {
            this.rentalPrice = rentalPrice;
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) throws ComicException{
        if (author.length() > 33) {
            throw new ComicException("Author's Name cannot contain more than 33 characters.");
        } else {
            this.author = author;
        }
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) throws ComicException {
        if (volume <= 0 || volume >= 1000 ) {
            throw new ComicException("Comic Volume must be a positive integer less than 1000.");
        } else {
            this.volume = volume;
        }
    }

    @Override
    public int compareTo(ComicBook otherComic) {
        return this.id - otherComic.id;
    }
    
    
}
