package kudos.v1.endpoint;

import java.util.List;
import kudos.v1.client.KudosTestClient;
import kudos.v1.to.UsageStatistic;
import kudos.v1.to.User;

/**
 *
 * @author bsuneson
 */
public class UsageEndpoints extends Endpoints
{
    
    public UsageEndpoints(KudosTestClient client) 
    {
        super(client);
    }

    public boolean usageAll() 
    {
        System.out.printf("%-60s", "Testing getting all usage...");
        
        String result = client.sendGet("/v1/usage/all");
        
        List<UsageStatistic> usages = jsonToList(result, UsageStatistic.class);
        
        boolean success = (usages != null && !usages.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean usageByEmail(User testUser) 
    {
        System.out.printf("%-60s", "Testing getting all usage by email...");
        
        String result = client.sendGet("/v1/usage/byEmail/" + testUser.getEmail() + "/");
        
        List<UsageStatistic> usages = jsonToList(result, UsageStatistic.class);
        
        boolean success = (usages != null && !usages.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean usageByUri() 
    {
        System.out.printf("%-60s", "Testing getting all usage by uri...");
        
        String result = client.sendGet("/v1/usage/byUri?uri=/v1/usage/byEmail");
        
        List<UsageStatistic> usages = jsonToList(result, UsageStatistic.class);
        
        boolean success = (usages != null && !usages.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }    
}
