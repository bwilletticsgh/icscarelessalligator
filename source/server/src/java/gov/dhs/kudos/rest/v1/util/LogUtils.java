package gov.dhs.kudos.rest.v1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Utility for logging
 * @author bsuneson
 */
public class LogUtils 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(LogUtils.class);
    /** Holder of all endpoint URIs that contain parameters **/
    private static final List<String> USAGE_PATH_FILTER;
    
    /**
     * Convert the object to a JSON string
     * @param obj The object to convert
     * @return A JSON string representation of the object
     */
    public static String objectToJson(Object obj)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Converting Object to JSON: " + obj.getClass().getSimpleName());
        
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch(JsonProcessingException e)
        {
            LOG.error(e);
            return "error converting obj to JSON";
        }
    }

    /**
     * Filters the uri to not include and URI parameters - for logging UsageStats
     * @param uri The uri to parse
     * @return A URI without any parameters
     */
    public static String filterUsageURI(String uri) 
    {
        for(String pathFilter : USAGE_PATH_FILTER)
        {
            if(uri.startsWith(pathFilter))
                return pathFilter;
        }
        return uri;
    }
    
    static
    {
        USAGE_PATH_FILTER = Arrays.<String>asList(new String[] 
        {
            "/v1/user/byEmail","/v1/user/byId","/v1/user/byFirstName","/v1/user/byLastName",
            "/v1/org/byName","/v1/org/create","/v1/org/addUser","/v1/org/cloneCat","/v1/org/createCat",
            "/v1/cat/byName",
            "/v1/kudos/fromUser/all","/v1/kudos/toUser/all","/v1/kudos/cat/all","/v1/kudos/create","/v1/kudos/oneToMany/create",
            "/v1/usage/all","/v1/usage/byEmail","/v1/usage/byUri",
            "/v1/user/toggleDelete","/v1/user/toggleAdmin"
        });
    }
}
