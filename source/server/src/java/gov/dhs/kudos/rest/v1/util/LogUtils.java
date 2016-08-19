package gov.dhs.kudos.rest.v1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

/**
 * Utility for logging
 * @author bsuneson
 */
public class LogUtils 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(LogUtils.class);
    
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
}
