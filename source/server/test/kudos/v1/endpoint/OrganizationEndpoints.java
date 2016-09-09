package kudos.v1.endpoint;

import java.util.Date;
import java.util.List;
import kudos.v1.client.KudosTestClient;
import kudos.v1.to.KudosCategory;
import kudos.v1.to.Organization;
import kudos.v1.to.User;

/**
 *
 * @author bsuneson
 */
public class OrganizationEndpoints extends Endpoints
{
    private Organization testOrg;
    private KudosCategory testOrgKudosCat;
    
    public OrganizationEndpoints(KudosTestClient client) 
    {
        super(client);
    }
    
    public boolean orgCreateNameExists() 
    {     
        System.out.printf("%-60s", "Testing create organization name already exists...");
        
        String result = client.sendPost("/v1/org/create/DHS", null);
                
        boolean success = (errorEquals(result, "error: Organization name already used"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgCreateValid() 
    {
        System.out.printf("%-60s", "Testing create organization valid...");
        
        testOrg = new Organization("TestOrg" + new Date().hashCode());
        
        String result = client.sendPost("/v1/org/create/" + testOrg.getOrgName(), null);
        
        testOrg = jsonToObject(result, Organization.class);
        
        boolean success = (testOrg != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgUpdateNoOrg()
    {
        System.out.printf("%-60s", "Testing update organization no org param...");
        
        String result = client.sendPost("/v1/org/update", null);
        
        boolean success = (errorEquals(result, "error: Organization object is null"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgUpdateNoId()
    {
        System.out.printf("%-60s", "Testing update organization no id...");
        
        String oldId = testOrg.getId();
        testOrg.setId("");
        
        String result = client.sendPost("/v1/org/update", testOrg);
        
        testOrg.setId(oldId);
        
        boolean success = (errorEquals(result, "error: A required field for Organization update was null - need id"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgUpdateNoName()
    {
        System.out.printf("%-60s", "Testing update organization no name...");
        
        String oldName = testOrg.getOrgName();
        testOrg.setOrgName("");
        
        String result = client.sendPost("/v1/org/update", testOrg);
        
        testOrg.setOrgName(oldName);
        
        boolean success = (errorEquals(result, "error: A required field for Organization update was null - need orgName"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgUpdateValid() 
    {
        System.out.printf("%-60s", "Testing update organization valid...");
        
        testOrg.setOrgName("TestOrg" + new Date().hashCode());
        
        String result = client.sendPost("/v1/org/update", testOrg);
        
        testOrg = jsonToObject(result, Organization.class);
        
        boolean success = (testOrg != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgAddUserNoUser()
    {
        System.out.printf("%-60s", "Testing organization add user no user param...");
        
        String result = client.sendPost("/v1/org/addUser/" + testOrg.getOrgName(), null);
        
        boolean success = (errorEquals(result, "error: User object is null"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgAddUserNoUserId(User testUser) 
    {
        System.out.printf("%-60s", "Testing organization add user no user id...");
        
        String oldId = testUser.getId();
        testUser.setId("");
        
        String result = client.sendPost("/v1/org/addUser/" + testOrg.getOrgName(), testUser);
        
        testUser.setId(oldId);
        
        boolean success = (errorEquals(result, "error: A required field for User add was null - need user id"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgAddUserUserNotExists() 
    {     
        System.out.printf("%-60s", "Testing organization add user not a valid user...");
        
        User fakeUser = new User("im", "fake", "imfake@fake.com", "fake");
        fakeUser.setId("4321");
        fakeUser.setDateCreated(new Date());
        fakeUser.setDateModified(new Date());
        
        String result = client.sendPost("/v1/org/addUser/" + testOrg.getOrgName(), fakeUser);
        
        boolean success = (errorEquals(result, "error: User doesn't exists"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgAddUserOrgNotExists(User testUser)
    {
        System.out.printf("%-60s", "Testing organization add user not a valid org...");
        
        String result = client.sendPost("/v1/org/addUser/" + "fakeOrg", testUser);
        
        boolean success = (errorEquals(result, "error: Organization doesn't exists"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgAddUserValid(User testUser) 
    {
        System.out.printf("%-60s", "Testing organization add user valid...");
        
        String result = client.sendPost("/v1/org/addUser/" + testOrg.getOrgName(), testUser);
        
        testOrg = jsonToObject(result, Organization.class);
        
        boolean success = (testOrg != null && testOrg.getUsers() != null && testOrg.getUsers().contains(testUser));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean orgAddUserAlreadyExists(User testUser)
    {
        System.out.printf("%-60s", "Testing organization add user already in org...");
        
        String result = client.sendPost("/v1/org/addUser/" + testOrg.getOrgName(), testUser);
        
        boolean success = (errorEquals(result, "error: Organization already contains User"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
//    public boolean orgCloneCatCatNotExists()
//    {
//        System.out.print("Testing organization clone cat with cat not exists...");
//        
//        String result = client.sendPost("/v1/org/cloneCat/" + "123" + "/" + testOrg.getOrgName(), null);
//        
//        boolean success = (errorEquals(result, "error: Kudos Category doesn't exists"));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
//    
//    public boolean orgCloneCatOrgNotExists()
//    {
//        System.out.print("Testing organization clone cat with org not exists...");
//        
//        KudosCategory pooledCat = jsonToObject(client.sendGet("/v1/cat/byName/GLOBAL-1"), KudosCategory.class);
//        
//        String result = client.sendPost("/v1/org/cloneCat/" + pooledCat.getId() + "/" + "blah", null);
//        
//        boolean success = (errorEquals(result, "error: Organization doesn't exists"));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
//    
//    public boolean orgCloneCatValid() 
//    {
//        System.out.print("Testing organization clone cat valid...");
//        
//        KudosCategory pooledCat = jsonToObject(client.sendGet("/v1/cat/byName/GLOBAL-1"), KudosCategory.class);
//        
//        String result = client.sendPost("/v1/org/cloneCat/" + pooledCat.getId() + "/" + testOrg.getOrgName(), pooledCat);
//        
//        testOrg = jsonToObject(result, Organization.class);
//        
//        KudosCategory clonedCat = jsonToObject(client.sendGet("/v1/cat/byName/" + pooledCat.getName() + "-" + testOrg.getOrgName()), KudosCategory.class);
//        
//        boolean success = (testOrg != null && testOrg.getKudosCategories() != null && testOrg.getKudosCategories().contains(clonedCat));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
//    
//    public boolean orgCloneCatAlreadyExists() 
//    {
//        System.out.print("Testing organization clone cat with cat already exists...");
//        
//        KudosCategory pooledCat = jsonToObject(client.sendGet("/v1/cat/byName/GLOBAL-1"), KudosCategory.class);
//        
//        String result = client.sendPost("/v1/org/cloneCat/" + pooledCat.getId() + "/" + testOrg.getOrgName(), pooledCat);
//        
//        boolean success = (errorEquals(result, "error: Organization already contains cloned Kudos Category"));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
//    
//    public boolean orgCreateCatNoCatName()
//    {
//        System.out.print("Testing organization create cat with no cat name...");
//        
//        KudosCategory testCat = new KudosCategory("", "no name");
//        
//        String result = client.sendPost("/v1/org/createCat/" + testOrg.getOrgName(), testCat);
//        
//        boolean success = (errorEquals(result, "error: A required field for KudosCategory was null - need name"));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
//    
//    public boolean orgCreateCatOrgNotExists()
//    {
//        System.out.print("Testing organization create cat with org not exists...");
//        
//        KudosCategory testCat = new KudosCategory("test", "test");
//        
//        String result = client.sendPost("/v1/org/createCat/" + "blah", testCat);
//        
//        boolean success = (errorEquals(result, "error: Organization doesn't exists"));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
//    
//    public boolean orgCreateCatValid() 
//    {
//        System.out.print("Testing organization create cat valid...");
//        
//        testOrgKudosCat = new KudosCategory("TEST_CAT" + new Date().hashCode(), "For Org: " + testOrg.getOrgName());
//        
//        String result = client.sendPost("/v1/org/createCat/" + testOrg.getOrgName(), testOrgKudosCat);
//        
//        testOrg = jsonToObject(result, Organization.class);
//        
//        testOrgKudosCat = jsonToObject(client.sendGet("/v1/cat/byName/" + testOrgKudosCat.getName()), KudosCategory.class);
//        
//        boolean success = (testOrg != null && testOrg.getKudosCategories() != null && testOrg.getKudosCategories().contains(testOrgKudosCat));
//        
//        System.out.print(success ? "PASS\n" : "FAILED\n");
//        
//        return success;
//    }
    
    public boolean orgAll() 
    {
        System.out.printf("%-60s", "Testing organization get all...");
        
        String result = client.sendGet("/v1/org/all");
        
        List<Organization> orgs = jsonToList(result, Organization.class);
        
        boolean success = (orgs != null && !orgs.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean orgByName() 
    {
        System.out.printf("%-60s", "Testing organization get by name...");
        
        String result = client.sendGet("/v1/org/byName/" + testOrg.getOrgName());
        
        testOrg = jsonToObject(result, Organization.class);
        
        boolean success = (testOrg != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public KudosCategory getTestOrgKudosCat() 
    {
        return testOrgKudosCat;
    }
}
