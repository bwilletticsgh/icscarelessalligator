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
    public void test01_USER_registerNoPass()
    {
        waitForIt();
        System.out.println("\n\n*******************#Begin Test on User Endpoints");
        assertTrue(USER_ENDPOINTS.registerNoPass());
    }
    
    @Test
    public void test02_USER_registerInvalidPass()
    {
        assertTrue(USER_ENDPOINTS.registerInvalidPass());
    }
    
    @Test
    public void test03_USER_registerNoEmail()
    {
        assertTrue(USER_ENDPOINTS.registerNoEmail());
    }
    
    @Test
    public void test04_USER_registerValid()
    {
        assertTrue(USER_ENDPOINTS.registerValid());
    }
    
    @Test
    public void test05_USER_loginNoPass()
    {
        assertTrue(USER_ENDPOINTS.loginNoPass());
    }
    
    @Test
    public void test06_USER_loginInvalidPass()
    {
        assertTrue(USER_ENDPOINTS.loginInvalidPass());
    }
    
    @Test
    public void test07_USER_loginNoEmail()
    {
        assertTrue(USER_ENDPOINTS.loginNoEmail());
    }
    
    @Test
    public void test08_USER_loginInvalidEmail()
    {
        assertTrue(USER_ENDPOINTS.loginInvalidEmail());
    }
    
    @Test
    public void test09_USER_loginValid()
    {
        assertTrue(USER_ENDPOINTS.loginValid());
    }
    
/*     @Test
    public void test10_USER_userAll()
    {
        assertTrue(USER_ENDPOINTS.userAll());
    }
    
    @Test
    public void test11_USER_userActive()
    {
        assertTrue(USER_ENDPOINTS.userActive());
    }
    
    @Test
    public void test12_USER_userByEmail()
    {
        assertTrue(USER_ENDPOINTS.userByEmail());
    }
    
    @Test
    public void test13_USER_userByFirstName()
    {
        assertTrue(USER_ENDPOINTS.userByFirstName());
    }
    
    @Test
    public void test14_USER_userById()
    {
        assertTrue(USER_ENDPOINTS.userById());
    }
    
    @Test
    public void test15_USER_userByLastName()
    {
        assertTrue(USER_ENDPOINTS.userByLastName());
        System.out.println("*******************End TEST User Endpoints\n\n");
    }
    
    @Test
    public void test16_ORG_orgCreateNameExists()
    {
        System.out.println("\n\n*******************#Begin Test on Organization Endpoints");
        assertTrue(ORG_ENDPOINTS.orgCreateNameExists());
    }
    
    @Test
    public void test17_ORG_orgCreateValid()
    {
        assertTrue(ORG_ENDPOINTS.orgCreateValid());
    }
    
    @Test
    public void test18_ORG_orgUpdateNoOrg()
    {
        assertTrue(ORG_ENDPOINTS.orgUpdateNoOrg());
    }
    
    @Test
    public void test19_ORG_orgUpdateNoId()
    {
        assertTrue(ORG_ENDPOINTS.orgUpdateNoId());
    }
    
    @Test
    public void test20_ORG_orgUpdateNoName()
    {
        assertTrue(ORG_ENDPOINTS.orgUpdateNoName());
    }
    
    @Test
    public void test21_ORG_orgUpdateValid()
    {
        assertTrue(ORG_ENDPOINTS.orgUpdateValid());
    }
    
    @Test
    public void test22_ORG_orgAddUserNoUser()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserNoUser());
    }
    
    @Test
    public void test23_ORG_orgAddUserNoUserId()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserNoUserId(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test24_ORG_orgAddUserNoUserId()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserNoUserId(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test25_ORG_orgAddUserUserNotExists()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserUserNotExists());
    }
    
    @Test
    public void test26_ORG_orgAddUserOrgNotExists()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserOrgNotExists(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test27_ORG_orgAddUserValid()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserValid(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test28_ORG_orgAddUserAlreadyExists()
    {
        assertTrue(ORG_ENDPOINTS.orgAddUserAlreadyExists(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test30_ORG_orgAll()
    {
        assertTrue(ORG_ENDPOINTS.orgAll());
    }
    
    @Test
    public void test31_ORG_orgByName()
    {
        assertTrue(ORG_ENDPOINTS.orgByName());
        System.out.println("*******************End TEST Organization Endpoints\n\n");
    }
    
    @Test
    public void test32_CAT_catAll()
    {
        System.out.println("*******************#Begin TEST Kudos Category Endpoints");
        assertTrue(KUDOS_CAT_ENDPOINTS.catAll());
    }
    
    @Test
    public void test33_CAT_catAll()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catByName());
        System.out.println("*******************End TEST Kudos Category Endpoints\n\n");
    }
    
    @Test
    public void test34_KUDOS_kudosCreateKudosFromUserNotExists()
    {
        System.out.println("*******************#Begin TEST Kudos Endpoints");
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosFromUserNotExists(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
    }
    
    @Test
    public void test35_KUDOS_kudosCreateKudosToUserNotExists()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosToUserNotExists(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
    }
    
    @Test
    public void test36_KUDOS_kudosCreateKudosCatNotExists()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosCatNotExists(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test37_KUDOS_kudosCreateKudosNoComments()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosNoComments(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
    }
    
    @Test
    public void test38_KUDOS_kudosCreateKudosSameUser()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosCreateKudosSameUser(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
    }
    
    @Test
    public void test39_KUDOS_kudosCreateValid()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosCreateValid(USER_ENDPOINTS.getTestUser(), KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
    }
    
    @Test
    public void test40_KUDOS_kudosFromUserAll()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosFromUserAll());
    }
    
    @Test
    public void test41_KUDOS_kudosToUserAll()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosToUserAll(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test42_KUDOS_kudosToUserAll()
    {
        assertTrue(KUDOS_ENDPOINTS.kudosCatAll(KUDOS_CAT_ENDPOINTS.getTestPooledCat()));
        System.out.println("*******************End TEST Kudos Endpoints\n\n");
    }
    
    @Test
    public void test43_ADMIN_makeUserAdmin()
    {
        System.out.println("*******************#Begin TEST Admin Endpoints");
        USER_ENDPOINTS.adminLogin();
        assertTrue(USER_ENDPOINTS.makeUserAdmin());
    }
    
    @Test
    public void test44_ADMIN_deleteUserAsAdmin()
    {
        assertTrue(USER_ENDPOINTS.deleteUserAsAdmin());
    }
    
    @Test
    public void test45_ADMIN_catCreateNoCat()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catCreateNoCat());
    }
    
    @Test
    public void test46_ADMIN_catCreateNoName()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catCreateNoName());
    }
    
    @Test
    public void test47_ADMIN_catCreateValid()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catCreateValid());
    }
    
    @Test
    public void test48_ADMIN_catUpdateNoCat()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateNoCat());
    }
    
    @Test
    public void test49_ADMIN_catUpdateNoName()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateNoName());
    }
    
    @Test
    public void test50_ADMIN_catUpdateNoId()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateNoId());
    }
    
    @Test
    public void test51_ADMIN_catUpdateValid()
    {
        assertTrue(KUDOS_CAT_ENDPOINTS.catUpdateValid());
        System.out.println("*******************End TEST Admin Endpoints\n\n");
    }
    
    @Test
    public void test52_USAGE_usageAll()
    {
        System.out.println("#Begin TEST Usage Endpoints");
        assertTrue(USAGE_ENDPOINTS.usageAll());
    }
    
    @Test
    public void test53_USAGE_usageByEmail()
    {
        assertTrue(USAGE_ENDPOINTS.usageByEmail(USER_ENDPOINTS.getTestUser()));
    }
    
    @Test
    public void test54_USAGE_usageByUri()
    {
        assertTrue(USAGE_ENDPOINTS.usageByUri());
        System.out.println("*******************End TEST Usage Endpoints");
    } */
    
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
