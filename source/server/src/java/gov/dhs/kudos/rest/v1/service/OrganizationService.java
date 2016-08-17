package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

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
    
    public void validateOrgSave(String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating if organization already exists");
        
        if(orgExists(orgName))
            throw new KudosException("Organization name already used", HttpStatus.BAD_REQUEST);
    }
    
    public void validateOrgUpdate(Organization org) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for organization update");
        
        if(org == null)
            throw new KudosException("Organization object is null", HttpStatus.BAD_REQUEST);
        if(org.getId() == null)
            throw new KudosException("A required field for Organization update was null - need id", HttpStatus.BAD_REQUEST);
    }
    
    public void validateOrgAddUser(User user, String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for organization add user");
        
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getId() == null)
            throw new KudosException("A required field for User add was null - need user id", HttpStatus.BAD_REQUEST);
        if(orgName == null)
            throw new KudosException("A required field for User add was null - need orgName", HttpStatus.BAD_REQUEST);                
        if(!userExists(user.getId()))
            throw new KudosException("User doesn't exists", HttpStatus.BAD_REQUEST);
        if(!orgExists(orgName))
            throw new KudosException("Organization doesn't exists", HttpStatus.BAD_REQUEST);
        if(orgHasUser(user, orgName))
            throw new KudosException("Organization already contains User", HttpStatus.BAD_REQUEST);
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
    
    protected boolean orgExists(String orgName)
    {
        return (organizationRepo.findByOrgName(orgName) != null);
    }
    
    protected boolean userExists(String userId)
    {
        return (userRepo.findOne(userId) != null);
    }
    
    protected boolean orgHasUser(User user, String orgName)
    {
        Organization org = organizationRepo.findByOrgName(orgName);
        
        if(org == null || org.getUsers() == null)
            return false;
        
        return org.getUsers().contains(user);
    }
}
