package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.model.Organization;
import java.util.Arrays;
import org.springframework.stereotype.Service;

/**
 *
 * @author bsuneson
 */
@Service
public class KudosService extends UserService
{
    
    public KudosService() 
    {
        
    }
    
    public void initDumbydata() 
    {
        userRepo.deleteAll();
        organizationRepo.deleteAll();
        
        Organization org1 = new Organization();
        org1.setOrgName("StarFleet");
        //org1.setUsers(Arrays.<User>asList(new User[] {new User("John Luke","Pichard"), new User("Geordi","LaForge")}));
        
        Organization org2 = new Organization();
        org2.setOrgName("BobsBurgers");
        //org2.setUsers(Arrays.<User>asList(new User[] {new User("Brian","Suneson"), new User("Ben","Willett")}));
        
        organizationRepo.save(org1);
        organizationRepo.save(org2);
    }
}
