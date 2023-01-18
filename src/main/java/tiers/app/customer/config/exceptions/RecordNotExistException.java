package tiers.app.customer.config.exceptions;

public class RecordNotExistException extends RuntimeException{
    private String message;
    private RecordNotExistException(){}
    private RecordNotExistException(String msg){
        super(msg);
        this.message=msg;
    }
}
