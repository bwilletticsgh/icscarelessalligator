package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author bsuneson
 */
public class OrganizationService extends WiredService
{
    public Organization saveOrg(Organization org) throws KudosException
    {
        return organizationRepo.save(org);
    }
    
    public void validateOrgSave(String orgName) throws KudosException
    {
        if(orgExists(orgName))
            throw new KudosException("Organization name already used", HttpStatus.BAD_REQUEST);
    }
    
    public void validateOrgUpdate(Organization org) throws KudosException
    {
        if(org == null)
            throw new KudosException("Organization object is null", HttpStatus.BAD_REQUEST);
        if(org.getId() == null)
            throw new KudosException("A required field for Organization update was null - need id", HttpStatus.BAD_REQUEST);
    }
    
    public void validateOrgAddUser(String userId, String orgName) throws KudosException
    {
        if(userId == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(userId == null)
            throw new KudosException("A required field for User add was null - need user id", HttpStatus.BAD_REQUEST);
        if(orgName == null)
            throw new KudosException("A required field for User add was null - need orgName", HttpStatus.BAD_REQUEST);                
        if(!userExists(userId))
            throw new KudosException("User doesn't exists", HttpStatus.BAD_REQUEST);
        if(!orgExists(orgName))
            throw new KudosException("Organization doesn't exists", HttpStatus.BAD_REQUEST);
        if(orgHasUser(userId, orgName))
            throw new KudosException("Organization already contains User", HttpStatus.BAD_REQUEST);
    }
    
    public Organization addOrgUser(String userId, String orgName)
    {
        Organization org = organizationRepo.findByOrgName(orgName);
        org.addUser(userId);
        
        return organizationRepo.save(org);
    }

    public List<Organization> findAllOrgs() 
    {
        return organizationRepo.findAll();
    }
    
    public Organization getOrgByName(String orgName)
    {
        return organizationRepo.findByOrgName(orgName);
    }
    
    private boolean orgExists(String orgName)
    {
        return (organizationRepo.findByOrgName(orgName) != null);
    }
    
    private boolean userExists(String userId)
    {
        return (userRepo.findOne(userId) != null);
    }
    
    private boolean orgHasUser(String userId, String orgName)
    {
        Organization org = organizationRepo.findByOrgName(orgName);
        
        if(org == null || org.getUserIds() == null)
            return false;
        
        return org.getUserIds().contains(userId);
    }
}
