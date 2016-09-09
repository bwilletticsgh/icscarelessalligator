package kudos.v1.to;

/**
 * The entity representing a UsageStatistic object
 * @author tdickerson
 */
@SuppressWarnings("serial")
public class UsageStatistic extends BaseEntity
{
    private String uri;
    private String user;
    
    public UsageStatistic(String uri, String user)
    {
        this.uri = uri;
        this.user = user;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }
}
