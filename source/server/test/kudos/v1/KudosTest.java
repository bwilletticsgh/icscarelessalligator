package kudos.v1;

import kudos.v1.client.KudosTestClient;
import kudos.v1.endpoint.KudosCategoryEndpoints;
import kudos.v1.endpoint.KudosEndpoints;
import kudos.v1.endpoint.OrganizationEndpoints;
import kudos.v1.endpoint.UsageEndpoints;
import kudos.v1.endpoint.UserEndpoints;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Local JUnit Test for quickly determining any problems prior to build
 * @author bsuneson
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KudosTest 
{
    private static final KudosTestClient CLIENT;
    private static final UserEndpoints USER_ENDPOINTS;
    private static final OrganizationEndpoints ORG_ENDPOINTS;
    private static final KudosCategoryEndpoints KUDOS_CAT_ENDPOINTS;
    private static final KudosEndpoints KUDOS_ENDPOINTS;
    private static final UsageEndpoints USAGE_ENDPOINTS;
    
    static
    {
        CLIENT = KudosTestClient.getInstance();
        USER_ENDPOINTS = new UserEndpoints(CLIENT);
        ORG_ENDPOINTS = new OrganizationEndpoints(CLIENT);
        KUDOS_CAT_ENDPOINTS = new KudosCategoryEndpoints(CLIENT);
        KUDOS_ENDPOINTS = new KudosEndpoints(CLIENT);
        USAGE_ENDPOINTS = new UsageEndpoints(CLIENT);
    }
    
    @Test
    public void test1_userEndpoints()
    {
        waitForIt();
        
        System.out.println("\n\n#Begin TEST 1 - User Endpoints");
        
        assertTrue(USER_ENDPOINTS.registerNoPass());
        assertTrue(USER_ENDPOINTS.registerInvalidPass());
        assertTrue(USER_ENDPOINTS.registerNoEmail());
        assertTrue(USER_ENDPOINTS.registerValid());
        
        assertTrue(USER_ENDPOINTS.loginNoPass());
        assertTrue(USER_ENDPOINTS.loginInvalidPass());
        assertTrue(USER_ENDPOINTS.loginNoEmail());
        assertTrue(USER_ENDPOINTS.loginInvalidEmail());
        assertTrue(USER_ENDPOINTS.loginValid());
                
        assertTrue(USER_ENDPOINTS.userAll());
        assertTrue(USER_ENDPOINTS.userActive());
        assertTrue(USER_ENDPOINTS.userByEmail());
        assertTrue(USER_ENDPOINTS.userByFirstName());
        assertTrue(USER_ENDPOINTS.userById());
        assertTrue(USER_ENDPOINTS.userByLastName());
        
        System.out.println("End TEST 1 - User Endpoints\n\n");
    }
    
    @Test
    public void test2_organizationEndpoints()
    {
        System.out.println("#Begin TEST 2 - Organization Endpoints");
        
        assertTrue(ORG_ENDPOINTS.orgCreateNameExists());
        assertTrue(ORG_ENDPOINTS.orgCreateValid());
        
        assertTrue(ORG_ENDPOINTS.orgUpdateNoOrg());
        assertTrue(ORG_ENDPOINTS.orgUpdateNoId());
        assertTrue(ORG_ENDPOINTS.orgUpdateNoName());
        assertTrue(ORG_ENDPOINTS.orgUpdateValid());
        
        assertTrue(ORG_ENDPOINTS.orgAddUserNoUser());
        assertTrue(ORG_ENDPOINTS.orgAddUserNoUserId(USER_ENDPOINTS.getTestUser()));
        assertTrue(ORG_ENDPOINTS.orgAddUserUserNotExists());
        assertTrue(ORG_ENDPOINTS.orgAddUserOrgNotExists(USER_ENDPOINTS.getTestUser()));
        assertTrue(ORG_ENDPOINTS.orgAddUserValid(USER_ENDPOINTS.getTestUser()));
        assertTrue(ORG_ENDPOINTS.orgAddUserAlreadyExists(USER_ENDPOINTS.getTestUser()));
        
//        assertTrue(ORG_ENDPOINTS.orgCloneCatCatNotExists());
//        assertTrue(ORG_ENDPOINTS.orgCloneCatOrgNotExists());
//        assertTrue(ORG_ENDPOINTS.orgCloneCatValid());
//        assertTrue(ORG_ENDPOINTS.orgCloneCatAlreadyExists());
//        
//        assertTrue(ORG_ENDPOINTS.orgCreateCatNoCatName());
//        assertTrue(ORG_ENDPOINTS.orgCreateCatOrgNotExists());
//        assertTrue(ORG_ENDPOINTS.orgCreateCatValid());
        
        assertTrue(ORG_ENDPOINTS.orgAll());
        assertTrue(ORG_ENDPOINTS.orgByName());
        
        System.out.println("End TEST 2 - Organization Endpoints\n\n");
    }
    
    @Test
    public void test3_kudosCategoryEndpoints()
    {
        System.out.println("#Begin TEST 3 - Kudos Category Endpoints");
        
        assertTrue(KUDOS_CAT_ENDPOINTS.catAll());
        assertTrue(KUDOS_CAT_ENDPOINTS.catByName());
        
        System.out.println("End TEST 3 - Kudos Category Endpoints\n\n");
    }
    
    @Test
    public void test4_kudosEndpoints()
    {
        System.out.println("#Begin TEST 4 - Kudos Endpoints");
        
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosFromUserNotExists(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosToUserNotExists(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosCatNotExists(USER_ENDPOINTS.getTestUser()));
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosNoComments(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosSameUser(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        assertTrue(KUDOS_ENDPOINTS.kudosCreateValid(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        
        assertTrue(KUDOS_ENDPOINTS.kudosFromUserAll());
        assertTrue(KUDOS_ENDPOINTS.kudosToUserAll(USER_ENDPOINTS.getTestUser()));
        assertTrue(KUDOS_ENDPOINTS.kudosCatAll(KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        
        System.out.println("End TEST 4 - Kudos Endpoints\n\n");
    }
    
    @Test
    public void test5_adminRights()
    {
        System.out.println("#Begin TEST 5 - Admin Endpoints");
        
        USER_ENDPOINTS.adminLogin();
        
        assertTrue(USER_ENDPOINTS.makeUserAdmin());
        assertTrue(USER_ENDPOINTS.deleteUserAsAdmin());
        
        assertTrue(KUDOS_CAT_ENDPOINTS.catCreateNoCat());
        assertTrue(KUDOS_CAT_ENDPOINTS.catCreateNoName());
        assertTrue(KUDOS_CAT_ENDPOINTS.catCreateValid());
        
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateNoCat());
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateNoName());
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateNoId());
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateValid());

        System.out.println("End TEST 5 - Admin Endpoints\n\n");
    }
    
    @Test
    public void test6_usageEndpoints()
    {
        System.out.println("#Begin TEST 6 - Usage Endpoints");
        
        assertTrue(USAGE_ENDPOINTS.usageAll());
        assertTrue(USAGE_ENDPOINTS.usageByEmail(USER_ENDPOINTS.getTestUser()));
        assertTrue(USAGE_ENDPOINTS.usageByUri());
        
        System.out.println("End TEST 6 - Usage Endpoints");
    }
    
    private void waitForIt()
    {
        String message = "Waiting for kudos to standup within docker image's tomcat container...";
        System.out.println(message);
        
        for(int i = 30; i >= 0; --i)
        {
            rest();
        }
    }
    
    private void rest()
    {
        try
        {
            Thread.currentThread().sleep(1000);
        }
        catch(Exception e){}
    }
}
