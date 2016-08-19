package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.model.KudosCategory;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author bsuneson
 */
public class KudosCategoryService extends UserService
{
    private static final Logger LOG = Logger.getLogger(KudosCategoryService.class);
        
    public KudosCategory findKudosCatByName(String name) 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding kudos-category by name");
        
        return kudosCatRepo.findByName(name);
    }
    
    public List<KudosCategory> findAllKudosCats() 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all kudos-categories within an org");
        
        return kudosCatRepo.findAll();
    }
    
    public KudosCategory saveKudosCat(KudosCategory kudosCat)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving kudos-categories");
        
        return kudosCatRepo.save(kudosCat);
    }
    
    public KudosCategory updateKudosCat(KudosCategory kudosCat)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Updating kudos-categories");
        
        return kudosCatRepo.save(kudosCat);
    }
}
