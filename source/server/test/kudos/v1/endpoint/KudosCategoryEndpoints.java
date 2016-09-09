package kudos.v1.endpoint;

import java.util.Date;
import java.util.List;
import kudos.v1.client.KudosTestClient;
import kudos.v1.to.KudosCategory;
import org.apache.catalina.util.URLEncoder;

/**
 *
 * @author bsuneson
 */
public class KudosCategoryEndpoints extends Endpoints
{
    private KudosCategory testPooledCat;
    
    public KudosCategoryEndpoints(KudosTestClient client)
    {
        super(client);
    }
    
    public boolean catCreateNoCat()
    {
        System.out.printf("%-60s", "Testing create cat no cat param...");
        
        String result = client.sendPost("/v1/cat/create", null);
        
        boolean success = (errorEquals(result, "error: KudosCategory object is null"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean catCreateNoName()
    {
        System.out.printf("%-60s", "Testing create cat no cat name...");
        
        KudosCategory testCat = new KudosCategory("", "no name");
        
        String result = client.sendPost("/v1/cat/create", testCat);
        
        boolean success = (errorEquals(result, "error: A required field for KudosCategory save was empty - need name"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean catCreateValid() 
    {
        System.out.printf("%-60s", "Testing create cat valid...");
        
        testPooledCat = new KudosCategory("KUDOS CAT POOL TEST" + new Date().hashCode(), "KUDOS CAT IN POOL");
        
        String result = client.sendPost("/v1/cat/create", testPooledCat);
        
        testPooledCat = jsonToObject(result, KudosCategory.class);
        
        boolean success = (testPooledCat != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean catUpdateNoCat()
    {
        System.out.printf("%-60s", "Testing update cat no cat param...");
        
        String result = client.sendPost("/v1/cat/update", null);
        
        boolean success = (errorEquals(result, "error: KudosCategory object is null"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean catUpdateNoName()
    {
        System.out.printf("%-60s", "Testing update cat no cat name...");
        
        String oldName = testPooledCat.getName();
        
        testPooledCat.setName("");
        
        String result = client.sendPost("/v1/cat/update", testPooledCat);
        
        testPooledCat.setName(oldName);
        
        boolean success = (errorEquals(result, "error: A required field for KudosCategory update was empty - need name"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean catUpdateNoId()
    {
        System.out.printf("%-60s", "Testing update cat no cat id...");
        
        String oldId = testPooledCat.getId();
        
        testPooledCat.setId("");
        
        String result = client.sendPost("/v1/cat/update", testPooledCat);
        
        testPooledCat.setId(oldId);
        
        boolean success = (errorEquals(result, "error: A required field for KudosCategory update was empty - need id"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean catUpdateValid() 
    {
        System.out.printf("%-60s", "Testing update cat valid...");
        
        String control = "modded desc test";
        
        testPooledCat.setDesc(control);
        
        String result = client.sendPost("/v1/cat/update", testPooledCat);
        
        testPooledCat = jsonToObject(result, KudosCategory.class);
        
        boolean success = (testPooledCat != null && testPooledCat.getDesc().equals(control));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean catAll() 
    {
        System.out.printf("%-60s", "Testing get all cats...");
        
        String result = client.sendGet("/v1/cat/all");
        
        List<KudosCategory> kudoCats = jsonToList(result, KudosCategory.class);
        
        boolean success = (kudoCats != null && !kudoCats.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean catByName()
    {
        System.out.printf("%-60s", "Testing get cat by name...");
        
        String result = client.sendGet("/v1/cat/byName/" + URLEncoder.DEFAULT.encode("AWWW SNAP!"));
        
        testPooledCat = jsonToObject(result, KudosCategory.class);
        
        boolean success = (testPooledCat != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public KudosCategory getTestPooledCat() 
    {
        return testPooledCat;
    }
}
