package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Date {
    private LocalDate date;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public Date(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }
    
    public Date(LocalDate date) {
        this.date = date;
    }
    
    public Date(String dateString) {
        this.date = LocalDate.parse(dateString, FORMATTER);
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }
    
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }
    
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }


}
