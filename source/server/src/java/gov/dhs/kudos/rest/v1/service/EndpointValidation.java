package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Kudos;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 * Service layer for handling logic for validating all v1 endpoints
 * @author bsuneson
 */
public class EndpointValidation extends WiredService
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(EndpointValidation.class);
    
    /**
     * Validates the a user prior to registration
     * @param user The user object to validate
     * @throws KudosException 
     */
    public void validateUserRegister(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user registration");
        
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null)
            throw new KudosException("A required field for registration was null", HttpStatus.BAD_REQUEST);
        if(user.getEmail() == null || user.getEmail().length() == 0 || !user.getEmail().contains("@"))
            throw new KudosException("A required field for user update was invalid - need valid email", HttpStatus.BAD_REQUEST);
        if(user.getPassword().length() < 6)
            throw new KudosException("Password must be at least six characters", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates a user prior to login
     * @param user The user object to validate
     * @throws KudosException 
     */
    public void validateUserLogin(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user login");
        
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getEmail() == null || user.getPassword() == null)
            throw new KudosException("A required field for login was null", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates a user prior to an update
     * @param user The user to validate
     * @throws KudosException 
     */
    public void validateUserUpdate(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user update");
        
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getId() == null)
            throw new KudosException("A required field for user update was null - need id", HttpStatus.BAD_REQUEST);
        if(user.getEmail() == null || user.getEmail().length() == 0 || !user.getEmail().contains("@"))
            throw new KudosException("A required field for user update was invalid - need valid email", HttpStatus.BAD_REQUEST);
        if(user.getPassword() == null || user.getPassword().length() < 6)
            throw new KudosException("Password must be at least six characters", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates an organization prior to saving
     * @param orgName The organization name to validate
     * @throws KudosException 
     */
    public void validateOrgSave(String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating if organization already exists");
        
        if(orgExists(orgName))
            throw new KudosException("Organization name already used", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates an organization prior to updating
     * @param org The organization to validate
     * @throws KudosException 
     */
    public void validateOrgUpdate(Organization org) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for organization update");
        
        if(org == null)
            throw new KudosException("Organization object is null", HttpStatus.BAD_REQUEST);
        if(org.getId() == null)
            throw new KudosException("A required field for Organization update was null - need id", HttpStatus.BAD_REQUEST);
        if(org.getOrgName() == null || org.getOrgName().length() == 0)
            throw new KudosException("A required field for Organization update was null - need orgName", HttpStatus.BAD_REQUEST);
        if(orgExists(org.getOrgName()))
            throw new KudosException("Organization name already exists", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates the user and organization prior to saving
     * @param user The user to validate
     * @param orgName The organization name to validate
     * @throws KudosException 
     */
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
    
    /**
     * Validates the kudos category and organization prior to cloning a category into an organization
     * @param catId The kudos category id to validate
     * @param orgName The organization name to validate
     * @throws KudosException 
     */
    public void validateOrgCloneCat(String catId, String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required field for organization clone cat");
        
        if(catId == null)
            throw new KudosException("catId is null", HttpStatus.BAD_REQUEST);
        if(!kudosCatExists(catId))
            throw new KudosException("Kudos Category doesn't exists", HttpStatus.BAD_REQUEST);
        if(orgName == null)
            throw new KudosException("orgName is null", HttpStatus.BAD_REQUEST);
        if(!orgExists(orgName))
            throw new KudosException("Organization doesn't exists", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates a kudos category and organization prior to saving a new category within an organization
     * @param kudosCat The kudos category object to validate
     * @param orgName The organization name to validate
     * @throws KudosException 
     */
    public void validateOrgCreateCat(KudosCategory kudosCat, String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required field for organization create cat");
        
        if(kudosCat == null)
            throw new KudosException("kudosCat Object is null", HttpStatus.BAD_REQUEST);
        if(kudosCat.getName() == null || kudosCat.getName().length() == 0)
            throw new KudosException("A required field for KudosCategory was null - need name", HttpStatus.BAD_REQUEST);
        if(orgName == null)
            throw new KudosException("orgName is null", HttpStatus.BAD_REQUEST);
        if(!orgExists(orgName))
            throw new KudosException("Organization doesn't exists", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates from user, to user, kudos category, and kudos object prior to saving a kudos
     * @param fromUserId The from user id
     * @param toUserId The to user id
     * @param kudosCatId The kudos category id
     * @param kudos The Kudos object
     * @throws KudosException 
     */
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
    
    /**
     * Validates a kudos category prior to creating
     * @param kudosCat The kudos category to save
     * @throws KudosException 
     */
    public void validateCreateKudosCat(KudosCategory kudosCat) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos-category creation");
        
        if(kudosCat == null)
            throw new KudosException("KudosCategory object is null", HttpStatus.BAD_REQUEST);
        if(kudosCat.getName() == null || kudosCat.getName().length() == 0)
            throw new KudosException("A required field for KudosCategory save was null - need name", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates a kudos category prior to updating
     * @param kudosCat The kudos category to update
     * @throws KudosException 
     */
    public void validateUpdateKudosCat(KudosCategory kudosCat) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos-category update");
        
        if(kudosCat == null)
            throw new KudosException("KudosCategory object is null", HttpStatus.BAD_REQUEST);
        if(kudosCat.getId() == null)
            throw new KudosException("A required field for KudosCategory update was null - need id", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Determines if the kudos category already exists by id
     * @param id The id of the kudos category
     * @return Whether or not the kudos category already exists
     */
    private boolean kudosCatExists(String id)
    {
        return (kudosCatRepo.findOne(id) != null);
    }
    
    /**
     * Determines if the organization already exists by name
     * @param orgName The name of the organization
     * @return Whether or not the organization already exists
     */
    private boolean orgExists(String orgName)
    {
        return (organizationRepo.findByOrgName(orgName) != null);
    }
    
    /**
     * Determines if the user already exists based on id
     * @param userId The id of the user
     * @return Whether or not the user already exists
     */
    private boolean userExists(String userId)
    {
        return (userRepo.findOne(userId) != null);
    }
    
    /**
     * Determines if an organization already has a user
     * @param user The User object
     * @param orgName The name of the organization
     * @return Whether or not the organization already has a user
     */
    private boolean orgHasUser(User user, String orgName)
    {
        Organization org = organizationRepo.findByOrgName(orgName);
        
        if(org == null || org.getUsers() == null)
            return false;
        
        return org.getUsers().contains(user);
    }
}
