package gov.dhs.kudos.rest.v1.model;

/**
 *
 * @author tdickerson
 */
@SuppressWarnings("serial")
public class UsageStatistic extends BaseEntity
{
    public static final String FIND_BY_EMAIL_AND_DATE = 
              "{"
            + "  $and : "
            + "  [ "
            + "    {'user' : ?0}, "
            + "    {$or : [{ ?1 : null}, { 'dateCreated' : {$gte : ?1}}]}, "
            + "    {$or : [{ ?2 : null}, { 'dateCreated' : {$lte : ?2}}]} "
            + "  ] "
            + "}";
    
    public static final String FIND_BY_URI_AND_DATE = 
              "{"
            + "  $and : "
            + "  [ "
            + "    {'uri' : ?0}, "
            + "    {$or : [{ ?1 : null}, { 'dateCreated' : {$gte : ?1}}]}, "
            + "    {$or : [{ ?2 : null}, { 'dateCreated' : {$lte : ?2}}]} "
            + "  ] "
            + "}";
    
            
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
