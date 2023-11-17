package Xanadu.Exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException(String title, Integer quantity, Integer inventory) {
        super(title + " quantity(" + quantity + ") in stock is less than the quantity(" + inventory + ") you purchased");
    }
}
