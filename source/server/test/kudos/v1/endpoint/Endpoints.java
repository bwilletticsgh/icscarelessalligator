package kudos.v1.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kudos.v1.client.KudosTestClient;
import kudos.v1.to.ErrorTO;

/**
 *
 * @author bsuneson
 */
public class Endpoints
{
    protected final ObjectMapper MAPPER = new ObjectMapper();
    protected KudosTestClient client;

    public Endpoints(KudosTestClient client)
    {
        this.client = client;
    }
    
    public <T> T jsonToObject(String json, Class<T> T)
    {
        try
        {
            return MAPPER.readValue(json, T);
        }
        catch(Exception e){}
        return null;
    }
    
    public <T> List<T> jsonToList(String json, Class<T> T)
    {
        try
        {
            return MAPPER.readValue(json, new TypeReference<List<T>>(){});
        }
        catch(Exception e){}
        return null;
    }
    
    public boolean errorEquals(String json, String expected)
    {        
        return jsonToObject(json, ErrorTO.class).getError().equals(expected);
    }
}
