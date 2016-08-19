package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author bsuneson
 */
public class OrganizationService extends StatsService
{
    private static final Logger LOG = Logger.getLogger(OrganizationService.class);
    
    public Organization saveOrg(Organization org) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving organization");
        
        return organizationRepo.save(org);
    }
    
    public Organization addOrgUser(User user, String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Adding user to organization");
        
        Organization org = organizationRepo.findByOrgName(orgName);
        org.addUser(user);
        
        return organizationRepo.save(org);
    }

    public List<Organization> findAllOrgs() 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all organizations");
        
        return organizationRepo.findAll();
    }
    
    public Organization getOrgByName(String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding organization by name");
        
        return organizationRepo.findByOrgName(orgName);
    }
    
    public Organization cloneCat(String catId, String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Cloning global cat into org");
        
        KudosCategory kudosCat = kudosCatRepo.findOne(catId);
        KudosCategory cloned = new KudosCategory(kudosCat.getName(), kudosCat.getDesc(), kudosCat.getIcon(), kudosCat.getColor());
        cloned = kudosCatRepo.save(cloned);        
        
        Organization org = organizationRepo.findByOrgName(orgName);        
        org.addKudosCat(cloned);
        
        return organizationRepo.save(org);
    }
    
    public Organization createCat(KudosCategory kudosCat, String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Creating Kudos Category for org");
        
        kudosCat = kudosCatRepo.save(kudosCat);
        
        Organization org = organizationRepo.findByOrgName(orgName);
        org.addKudosCat(kudosCat);
        
        return organizationRepo.save(org);
    }
}
