package kudos.v1.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author bsuneson
 */
public class KudosTestClient 
{
    private final String BASE_URL = "http://52.40.27.250/KudosREST";//"http://localhost:8084/KudosREST";
    private static KudosTestClient kudos;
    private HttpClient client;
    
    private String currentAuthToken;

    private KudosTestClient() 
    {
        if(client == null)
            client = HttpClientBuilder.create().build();
    }
    
    public static KudosTestClient getInstance()
    {
        if(kudos == null)
            kudos = new KudosTestClient();
        
        return kudos;
    }
    
    public String sendGet(String uri)
    {
        String result = null;
        HttpResponse response = null;
        try
        {
            HttpGet get = new HttpGet(BASE_URL + uri);
            get.addHeader("Authorization", "Bearer " + currentAuthToken);
            
            response = client.execute(get);
            
            HttpEntity respopnseEntity = response.getEntity();
            
            result = getResponseContent(respopnseEntity);
        }
        catch(Exception e){}
        finally
        {
            updateCurrentAuthToken(response);
            safeConsumeEntity(response);
        }
        
        return result;
    }
    
    public String sendPost(String uri, Object entity)
    {
        String result = null;
        HttpResponse response = null;
        try
        {
            HttpPost post = new HttpPost(BASE_URL + uri);
            post.addHeader("Authorization", "Bearer " + currentAuthToken);
            
            if(entity != null)
            {
                post.addHeader("Content-type", "application/json");
                post.setEntity(new StringEntity(objectToJson(entity)));
            }
            
            response = client.execute(post);
            
            HttpEntity respopnseEntity = response.getEntity();
            result = getResponseContent(respopnseEntity);
        }
        catch(Exception e){}
        finally
        {
            updateCurrentAuthToken(response);
            safeConsumeEntity(response);
        }
        
        return result;
    }
    
    private void updateCurrentAuthToken(HttpResponse response)
    {
        if(response != null && response.containsHeader("Authorization"))
            currentAuthToken = response.getHeaders("Authorization")[0].getValue().substring(7);
    }

    public String getCurrentAuthToken() 
    {
        return currentAuthToken;
    }
    
    private String objectToJson(Object obj)
    {
        
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch(Exception e) {}
        
        return null;
    }
    
    private String getResponseContent(HttpEntity respopnseEntity)
    {
        try
        {
            try(BufferedReader rd = new BufferedReader(new InputStreamReader(respopnseEntity.getContent())))
            {
                StringBuilder content = new StringBuilder();
                String line;
                while((line = rd.readLine()) != null)
                {
                    content.append(line);
                    content.append('\r');
                }
                
                return content.toString();
            }
        }
        catch(Exception e) {}
        return null;
    }
    
    private void safeConsumeEntity(HttpResponse response)
    {
        try
        {
            if(response != null)
                EntityUtils.consume(response.getEntity());
        }
        catch(Exception e){}
    }
}
