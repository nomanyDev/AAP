public class InvalidInputException extends Exception {
    //task 6 Exceptions
    String msg = "Invalid input!";
    public InvalidInputException() {
        //empty constructor
    }
    public InvalidInputException(String msg) {
        super(msg);
        this.msg = msg;
    }
}