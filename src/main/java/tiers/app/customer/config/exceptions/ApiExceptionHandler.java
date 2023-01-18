package tiers.app.customer.config.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, ApiException> handleInvalidArguments(MethodArgumentNotValidException exception){
        Map<String, ApiException> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->{
            ApiException apiException =  new ApiException(
                    error.getDefaultMessage(),
                    HttpStatus.BAD_REQUEST,
                    LocalDateTime.now()
            );
            errorMap.put(error.getField(), apiException);
        });
        return errorMap;
    }

    @ExceptionHandler(value = {RecordAlreadyExisitsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> HandleRecordAlreadyExistsException(RecordAlreadyExisitsException e){
        ApiException apiException =  new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {RecordNotExistException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> HandleRecordNotExistException(RecordNotExistException e){
        ApiException apiException =  new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }


}
