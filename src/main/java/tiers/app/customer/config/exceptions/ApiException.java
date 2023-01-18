package tiers.app.customer.config.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ApiException{
    private final String message;
    private final HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    public ApiException(String message,
                        HttpStatus httpStatus,
                        LocalDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();;
    }

}
