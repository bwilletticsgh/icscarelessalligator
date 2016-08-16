package gov.dhs.kudos.rest.v1.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class KudosException extends Exception
{
    private final HttpStatus httpStatus;

    public KudosException(HttpStatus httpStatus) 
    {
        this.httpStatus = httpStatus;
    }

    public KudosException(String message, HttpStatus httpStatus)
    {
        super(message);
        this.httpStatus = httpStatus;
    }

    public KudosException(String message, Throwable cause, HttpStatus httpStatus) 
    {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public KudosException(Throwable cause, HttpStatus httpStatus) 
    {
        super(cause);
        this.httpStatus = httpStatus;
    }    

    public HttpStatus getHttpStatus() 
    {
        return httpStatus;
    }
}
