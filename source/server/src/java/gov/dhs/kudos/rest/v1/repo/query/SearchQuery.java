package gov.dhs.kudos.rest.v1.repo.query;

/**
 *
 * @author tdickerson
 */
public class SearchQuery
{
    public static final String USER_SEARCH_QUERY = ""
            + "{"
            + "  $or : ["
            + "          { firstName : { $regex : ?0, $options : 'si' } },"
            + "          { lastName  : { $regex : ?0, $options : 'si' } }"
            + "        ]"
            + "}";
    
    public static final String KUDOS_CATEGORY_SEARCH_QUERY = ""
            + "{"
            + "  $or : ["
            + "          { name : { $regex : ?0, $options : 'si' } },"
            + "          { desc : { $regex : ?0, $options : 'si' } }"
            + "        ]"
            + "}";
}
