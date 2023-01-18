package tiers.app.customer.config.exceptions;

public class RecordAlreadyExisitsException extends RuntimeException{

    private String message;
    public RecordAlreadyExisitsException(){}
    public RecordAlreadyExisitsException(String msg){
        super(msg);
        this.message=msg;
    }



}
