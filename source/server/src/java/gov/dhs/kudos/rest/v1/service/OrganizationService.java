package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Service layer for handling logic for the Organization v1 endpoints
 * @author bsuneson
 */
public class OrganizationService extends StatsService
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(OrganizationService.class);
    
    /**
     * Saves an organization
     * @param org The organization object to save
     * @return The saved organization object
     * @throws KudosException 
     */
    public Organization saveOrg(Organization org) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving organization");
        
        org.setOrgName(org.getOrgName().trim());
        
        return organizationRepo.save(org);
    }

    /**
     * Finds all organizations
     * @return A List of all organizations
     */
    public List<Organization> findAllOrgs() 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all organizations");
        
        return organizationRepo.findAll();
    }
    
    /**
     * Finds an organization by name
     * @param orgName The organizations name to search for
     * @return A matching organization object
     */
    public Organization getOrgByName(String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding organization by name");
        
        return organizationRepo.findByOrgNameIgnoreCase(orgName);
    }
    
    /**
     * Clones a kudos category into an organization
     * @param catId The id of the kudos category to clone
     * @param orgName The name of the organization
     * @return An updated organization object
     */
    public Organization cloneCat(String catId, String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Cloning global cat into org");
        
        KudosCategory kudosCat = kudosCatRepo.findOne(catId);
        KudosCategory cloned = new KudosCategory(kudosCat.getName() + "-" + orgName, kudosCat.getDesc(), kudosCat.getIcon(), kudosCat.getColor());
        cloned = kudosCatRepo.save(cloned);        
        
        Organization org = organizationRepo.findByOrgNameIgnoreCase(orgName);        
        org.addKudosCat(cloned);
        
        return organizationRepo.save(org);
    }
    
    /**
     * Creates a new kudos category within an organization
     * @param kudosCat The new kudos category to create
     * @param orgName The organization name to associate the kudos cat with
     * @return The updated Organization object
     */
    public Organization createCat(KudosCategory kudosCat, String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Creating Kudos Category for org");
        
        kudosCat.setName(kudosCat.getName().trim());
        
        kudosCat = kudosCatRepo.save(kudosCat);
        
        Organization org = organizationRepo.findByOrgNameIgnoreCase(orgName);
        org.addKudosCat(kudosCat);
        
        return organizationRepo.save(org);
    }
}
