package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Kudos;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author bsuneson
 */
@Service
public class KudosService extends KudosCategoryService
{
    private static final Logger LOG = Logger.getLogger(KudosService.class);
    
    public KudosService() 
    {
        
    }
    
    public void validateCreateKudos(String fromUserId, String toUserId, String kudosCatId, Kudos kudos) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos creation");
        
        if(fromUserId == null || toUserId == null || kudosCatId == null || kudos == null)
            throw new KudosException("Reuired obejct(s) was null - required fromUserId, toUserId, kudosCatId, and Kudos", HttpStatus.BAD_REQUEST);
        if(kudos.getComments() == null)
            throw new KudosException("No comments in the Kudos object", HttpStatus.BAD_REQUEST);
        if(!userExists(fromUserId))
            throw new KudosException("No User exists for fromUserId", HttpStatus.BAD_REQUEST);
        if(!userExists(toUserId))
            throw new KudosException("No User exists for toUserId", HttpStatus.BAD_REQUEST);
        if(!kudosCatExists(kudosCatId))
            throw new KudosException("No KudosCategory exists for kudosCatId", HttpStatus.BAD_REQUEST);
    }
    
    public List<Kudos> findAllKudosFromUser(String fromUserId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all kudos from a user");
        
        return kudosRepo.findByFromUser(userRepo.findOne(fromUserId));
    }
    
    public List<Kudos> findAllKudosToUser(String toUserId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all kudos to a user");
        
        return kudosRepo.findByToUser(userRepo.findOne(toUserId));
    }
    
    public List<Kudos> findAllKudosForCat(String catId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all kudos for a kudos-category");
        
        return kudosRepo.findByKudosCat(kudosCatRepo.findOne(catId));
    }
    
    public Kudos saveKudos(String fromUserId, String toUserId, String kudosCatId, Kudos kudo)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving kudos");
        
        kudo.setFromUser(userRepo.findOne(fromUserId));
        kudo.setToUser(userRepo.findOne(toUserId));
        kudo.setKudosCat(kudosCatRepo.findOne(kudosCatId));
        return kudosRepo.save(kudo);
    }
    
    public void initDumbydata() 
    {
//        usageStatisticRepo.deleteAll();
//        userRepo.deleteAll();
//        organizationRepo.deleteAll();
//        
//
//        User user1 = new User("John Luke","Pichard","JL@nowhere.com","123");
//        User user2 = new User("Geordi","LaForge","GL@nowhere.com","123");
//        User user3 = new User("Brian","Suneson","BS@nowhere.com","123");
//        User user4 = new User("Ben","Willett","BW@nowhere.com","123");
//        
//        userRepo.save(user1);
//        userRepo.save(user2);
//        userRepo.save(user3);
//        userRepo.save(user4);
//        
//        Organization org1 = new Organization();
//        org1.setOrgName("StarFleet");
//        org1.setUsers(Arrays.<User>asList(new User[] {user1, user2}));
//        
//        Organization org2 = new Organization();
//        org2.setOrgName("BobsBurgers");
//        org2.setUsers(Arrays.<User>asList(new User[] {user3, user4}));
//        
//        organizationRepo.save(org1);
//        organizationRepo.save(org2);
    }
}
