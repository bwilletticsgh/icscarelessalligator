package kudos.v1.endpoint;

import java.util.Date;
import java.util.List;
import java.util.Map;
import kudos.v1.client.KudosTestClient;
import kudos.v1.to.Organization;
import kudos.v1.to.User;
import kudos.v1.to.UserLoginTO;
import kudos.v1.to.UserRegisterTO;

/**
 *
 * @author bsuneson
 */
public class UserEndpoints extends Endpoints
{    
    private User adminUser; 
    private User testUser;
    
    public UserEndpoints(KudosTestClient client) 
    {
        super(client);
    }
    
    public boolean registerNoPass()
    {
        System.out.printf("%-60s", "Testing register with no password...");
        
        String result = client.sendPost("/v1/user/register", new UserRegisterTO("BobBarker@nowhere.com", "Bob", "Barker", "", ""));
        
        boolean success = (errorEquals(result, "error: A required field for registration was empty"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean registerInvalidPass()
    {
        System.out.printf("%-60s", "Testing register with invalid password...");
        
        String result = client.sendPost("/v1/user/register", new UserRegisterTO("BobBarker@nowhere.com", "Bob", "Barker", "12345", ""));
        
        boolean success = (errorEquals(result, "error: Password must be at least six characters"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean registerNoEmail()
    {
        System.out.printf("%-60s", "Testing register with no email...");
        
        String result = client.sendPost("/v1/user/register", new UserRegisterTO("", "Bob", "Barker", "1234567", ""));
        
        boolean success = (errorEquals(result, "error: A required field for registration was invalid - need valid email"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean registerValid()
    {
        System.out.printf("%-60s", "Testing register with valid credentails...");
        
        String result = client.sendGet("/v1/org/all");
        
        List<Organization> orgs = jsonToList(result, Organization.class);
        
        result = client.sendPost("/v1/user/register", new UserRegisterTO("TestUser" + new Date().hashCode() + "@kudos.com", "Bob", "Barker", "11!!qqQQaaAAzzZZ", (String)((Map)orgs.get(0)).get("id")));
        
        testUser = jsonToObject(result, User.class);
        testUser.setPassword("11!!qqQQaaAAzzZZ");
        
        boolean success = (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean loginNoPass()
    {
        System.out.printf("%-60s", "Testing login with no password...");
        
        String result = client.sendPost("/v1/user/login", new UserLoginTO(testUser.getEmail(), ""));
        
        boolean success = (errorEquals(result, "error: A required field for login was empty or null"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean loginInvalidPass()
    {
        System.out.printf("%-60s", "Testing login with invalid password...");
        
        String result = client.sendPost("/v1/user/login", new UserLoginTO(testUser.getEmail(), "22@@wwWWssSSxxXX"));
        
        boolean success = (errorEquals(result, "error: Bad Credentials"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean loginNoEmail()
    {
        System.out.printf("%-60s", "Testing login with no email...");
        
        String result = client.sendPost("/v1/user/login", new UserLoginTO("", testUser.getPassword()));
        
        boolean success = (errorEquals(result, "error: A required field for login was empty or null"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean loginInvalidEmail()
    {
        System.out.printf("%-60s", "Testing login with invalid email...");
        
        String result = client.sendPost("/v1/user/login", new UserLoginTO("notauser@kudos.com", testUser.getPassword()));
        
        boolean success = (errorEquals(result, "error: Bad Credentials"));
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean loginValid()
    {
        System.out.printf("%-60s", "Testing login with valid credentails...");
        
        String result = client.sendPost("/v1/user/login", new UserLoginTO(testUser.getEmail(), testUser.getPassword()));
        
        testUser = jsonToObject(result, User.class);
        testUser.setPassword("11!!qqQQaaAAzzZZ");
        
        boolean success =  (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
//    public boolean userUpdateEmailExists()
//    {
//        String oldEmail = testUser.getEmail();
//        testUser.setEmail("admin@kudos.com");
//        
//        String result = client.sendPost("/v1/user/update", testUser);
//        
//        testUser.setEmail(oldEmail);
//        
//        return (errorEquals(result, "error: Email address already in use by someone"));
//    }
    
//    public boolean userUpdateNoId()
//    {
//        String oldId = testUser.getId();
//        testUser.setId("");
//        
//        String result = client.sendPost("/v1/user/update", testUser);
//        
//        testUser.setId(oldId);
//        
//        return (errorEquals(result, "error: A required field for user update was null - need id"));
//    }
    
//    public boolean userUpdateValid()
//    {
//        testUser.setFirstName("Brian");
//        testUser.setLastName("Suneson");
//        testUser.setPassword("22@@wwWWssSSxxXX");
//        
//        String result = client.sendPost("/v1/user/update", testUser);
//        
//        testUser = jsonToObject(result, User.class);
//        testUser.setPassword("22@@wwWWssSSxxXX");
//        
//        return (testUser != null);
//    }
    
    public boolean userAll()
    {
        System.out.printf("%-60s", "Testing getting all users...");
        
        String result = client.sendGet("/v1/user/all");
        
        List<User> users = jsonToList(result, User.class);
        
        boolean success = (users != null && !users.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean userActive()
    {
        System.out.printf("%-60s", "Testing getting all active users...");
        
        String result = client.sendGet("/v1/user/active");
        
        List<User> users = jsonToList(result, User.class);
        
        boolean success = (users != null && !users.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean userByEmail()
    {
        System.out.printf("%-60s", "Testing getting user by email...");
        
        String result = client.sendGet("/v1/user/byEmail/" + testUser.getEmail() + "/");
        
        testUser = jsonToObject(result, User.class);
        testUser.setPassword("22@@wwWWssSSxxXX");
        
        boolean success = (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean userByFirstName()
    {
        System.out.printf("%-60s", "Testing getting user(s) by first name...");
        
        String result = client.sendGet("/v1/user/byFirstName/" + testUser.getFirstName());
        
        List<User> users = jsonToList(result, User.class);
        
        boolean success = (users != null && !users.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean userById()
    {
        System.out.printf("%-60s", "Testing getting user by id...");
        
        String result = client.sendGet("/v1/user/byId/" + testUser.getId());
        
        testUser = jsonToObject(result, User.class);
        testUser.setPassword("22@@wwWWssSSxxXX");
        
        boolean success = (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean userByLastName()
    {
        System.out.printf("%-60s", "Testing getting user(s) by last name...");
        
        String result = client.sendGet("/v1/user/byLastName/" + testUser.getLastName());
        
        List<User> users = jsonToList(result, User.class);
        
        boolean success = (users != null && !users.isEmpty());
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public User getTestUser() 
    {
        return testUser;
    }

    public void adminLogin() 
    {
        this.adminUser = jsonToObject(client.sendPost("/v1/user/login", new UserLoginTO("admin@kudos.com", "11!!qqQQaaAAzzZZ")), User.class);
    }
    
    public boolean makeUserAdmin() 
    {
        System.out.printf("%-60s", "Testing make user an admin...");
        
        String result = client.sendPost("/v1/user/toggleAdmin/" + testUser.getId(), null);
        
        testUser = jsonToObject(result, User.class);
        
        boolean success = (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }

    public boolean deleteUserAsAdmin() 
    {
        System.out.printf("%-60s", "Testing delete a user...");
        
        String result = client.sendPost("/v1/user/toggleDelete/" + testUser.getId(), null);
        
        testUser = jsonToObject(result, User.class);
        
        boolean success = (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
    
    public boolean makeUserHr() 
    {
        System.out.printf("%-60s", "Testing make user an admin...");
        
        String result = client.sendPost("/v1/user/toggleHr/" + testUser.getId(), null);
        
        testUser = jsonToObject(result, User.class);
        
        boolean success = (testUser != null);
        
        System.out.printf("%-60s\n", (success ? "PASS" : "FAILED"));
        
        return success;
    }
}
