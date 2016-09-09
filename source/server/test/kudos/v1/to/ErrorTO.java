package kudos.v1.to;

/**
 *
 * @author bsuneson
 */
public class ErrorTO 
{
    private String error;

    public ErrorTO(String error) {
        this.error = error;
    }

    public ErrorTO() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    @Override
    public String toString()
    {
        return error;
    }
}
