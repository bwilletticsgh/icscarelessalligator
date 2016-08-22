package gov.dhs.kudos.rest.v1.repo.query;

/**
 * Commons holder for Usage Statistic queries
 * @author bsuneson
 */
public class UsageStatisticQuery 
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
}
