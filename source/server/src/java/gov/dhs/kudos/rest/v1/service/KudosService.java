package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.model.Kudos;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
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
    
    public void initAdminData() 
    {
        Organization dhs = organizationRepo.findByOrgName("DHS");
        if(dhs == null)
        {
            usageStatisticRepo.deleteAll();
            userRepo.deleteAll();
            kudosRepo.deleteAll();
            kudosCatRepo.deleteAll();
            organizationRepo.deleteAll();
            
            User admin = userRepo.save(new User("admin@kudos.com", "kudos", "admin", "11!!qqQQaaAAzzZZ", true));
            KudosCategory dhsKudosCat = kudosCatRepo.save(new KudosCategory("SAMPLE 1", "AWW SNAP"));            
            dhs = organizationRepo.save(new Organization("DHS", Arrays.<User>asList(new User[] {admin}), Arrays.<KudosCategory>asList(new KudosCategory[] {dhsKudosCat})));
            
            kudosCatRepo.save(new KudosCategory("GLOBAL 1", "BANG"));
        }
    }
}
