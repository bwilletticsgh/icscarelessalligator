package kudos.v1.endpoint;

import java.util.List;
import kudos.v1.client.KudosTestClient;
import kudos.v1.to.Kudos;
import kudos.v1.to.KudosCategory;
import kudos.v1.to.User;
import kudos.v1.to.UserLoginTO;

/**
 *
 * @author bsuneson
 */
public class KudosEndpoints extends Endpoints
{
    private Kudos testKudos = new Kudos("WOW Great job!");
    private final User adminUser;    
    
    public KudosEndpoints(KudosTestClient client) 
    {
        super(client);
        
        this.adminUser = jsonToObject(client.sendPost("/v1/user/login", new UserLoginTO("admin@kudos.com", "11!!qqQQaaAAzzZZ")), User.class);
    }
    
    public boolean kudosCreateKudosFromUserNotExists(User testUser, KudosCategory testKudosCat)
    {
        System.out.printf("%-60s", "Testing create kudos with from user not exists...");
        
        String result = client.sendPost("/v1/kudos/create/" + "123" + "/" + testUser.getId() + "/" + testKudosCat.getId(), testKudos);
        
        boolean success = (errorEquals(result, "error: No User exists for fromUserId"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean kudosCreateKudosToUserNotExists(User testUser, KudosCategory testKudosCat)
    {
        System.out.printf("%-60s", "Testing create kudos with to user not exists...");
        
        String result = client.sendPost("/v1/kudos/create/" + adminUser.getId() + "/" + "123" + "/" + testKudosCat.getId(), testKudos);
        
        boolean success = (errorEquals(result, "error: No User exists for toUserId"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean kudosCreateKudosCatNotExists(User testUser)
    {
        System.out.printf("%-60s", "Testing create kudos with cat not exists...");
        
        String result = client.sendPost("/v1/kudos/create/" + adminUser.getId() + "/" + testUser.getId() + "/" + "123", testKudos);
        
        boolean success = (errorEquals(result, "error: No KudosCategory exists for kudosCatId"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean kudosCreateKudosNoComments(User testUser, KudosCategory testKudosCat)
    {
        System.out.printf("%-60s", "Testing create kudos with no comments...");
        
        testKudos.setComments("");
        
        String result = client.sendPost("/v1/kudos/create/" + adminUser.getId() + "/" + testUser.getId() + "/" + "123", testKudos);
        
        testKudos.setComments("WOW Great job!");
        
        boolean success = (errorEquals(result, "error: No comments in the Kudos object"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean kudosCreateKudosSameUser(User testUser, KudosCategory testKudosCat)
    {      
        System.out.printf("%-60s", "Testing create kudos to same user...");
        
        String result = client.sendPost("/v1/kudos/create/" + testUser.getId() + "/" + testUser.getId() + "/" + testKudosCat.getId(), testKudos);
        
        boolean success = (errorEquals(result, "error: Silly Kudoer - kudos are for others"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean kudosCreateValid(User testUser, KudosCategory testKudosCat) 
    {
        System.out.printf("%-60s", "Testing create kudos valid...");
        
        String result = client.sendPost("/v1/kudos/create/" + adminUser.getId() + "/" + testUser.getId() + "/" + testKudosCat.getId(), testKudos);
        
        testKudos = jsonToObject(result, Kudos.class);
        
        boolean success = (testKudos != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    } 
    
    public boolean kudosFromUserAll() 
    {
        System.out.printf("%-60s", "Testing get kudos from user...");
        
        String result = client.sendGet("/v1/kudos/fromUser/all/" + adminUser.getId());
        
        List<Kudos> kudosList = jsonToList(result, Kudos.class);
        
        boolean success = (kudosList != null && !kudosList.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean kudosToUserAll(User testUser)
    {
        System.out.printf("%-60s", "Testing get kudos to user...");
        
        String result = client.sendGet("/v1/kudos/toUser/all/" + testUser.getId());
        
        List<Kudos> kudosList = jsonToList(result, Kudos.class);
        
        boolean success = (kudosList != null && !kudosList.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean kudosCatAll(KudosCategory testKudosCat) 
    {
        System.out.printf("%-60s", "Testing get kudos for a cat...");
        
        String result = client.sendGet("/v1/kudos/cat/all/" + testKudosCat.getId());
        
        List<Kudos> kudosList = jsonToList(result, Kudos.class);
        
        boolean success = (kudosList != null && !kudosList.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
}
