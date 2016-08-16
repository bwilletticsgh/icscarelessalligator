package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author bsuneson
 */
public class KudosCategoryService extends UserService
{
    private static final Logger LOG = Logger.getLogger(KudosCategoryService.class);
    
    public void validateCreateKudosCat(KudosCategory kudosCat, String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos-category creation");
        
        if(kudosCat == null)
            throw new KudosException("KudosCategory object is null", HttpStatus.BAD_REQUEST);
        if(orgName == null)
            throw new KudosException("orgName is null", HttpStatus.BAD_REQUEST);
        if(kudosCat.getName() == null)
            throw new KudosException("A required field for KudosCategory save was null - need name", HttpStatus.BAD_REQUEST);
        if(!orgExists(orgName))
            throw new KudosException("No such Organization", HttpStatus.BAD_REQUEST);
        if(kudosCatExistsInOrg(kudosCat.getName(), orgName))
            throw new KudosException("KudosCategory name already exists", HttpStatus.BAD_REQUEST);
    }
    
    public void validateUpdateKudosCat(KudosCategory kudosCat) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos-category update");
        
        if(kudosCat == null)
            throw new KudosException("KudosCategory object is null", HttpStatus.BAD_REQUEST);
        if(kudosCat.getId() == null)
            throw new KudosException("A required field for KudosCategory update was null - need id", HttpStatus.BAD_REQUEST);
    }
    
    protected boolean kudosCatExistsInOrg(String name, String orgName)
    {
        return (kudosCatRepo.findByName(name, organizationRepo.findByOrgName(orgName)) != null);
    }
    
    protected boolean kudosCatExists(String id)
    {
        return (kudosCatRepo.findOne(id) != null);
    }
    
    public KudosCategory findKudosCatByName(String name, String orgName) 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding kudos-category by name and org");
        
        return kudosCatRepo.findByName(name, organizationRepo.findByOrgName(orgName));
    }
    
    public List<KudosCategory> findAllKudosCats(String orgName) 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all kudos-categories within an org");
        
        return kudosCatRepo.findAllByOrg(organizationRepo.findByOrgName(orgName));
    }
    
    public KudosCategory saveKudosCat(KudosCategory kudosCat, String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving kudos-categories");
        
        kudosCat.setOrg(organizationRepo.findByOrgName(orgName));
        return kudosCatRepo.save(kudosCat);
    }
    
    public KudosCategory updateKudosCat(KudosCategory kudosCat)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Updating kudos-categories");
        
        return kudosCatRepo.save(kudosCat);
    }
}
