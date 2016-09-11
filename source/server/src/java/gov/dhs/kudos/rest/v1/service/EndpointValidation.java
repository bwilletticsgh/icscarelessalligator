package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Kudos;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.to.KudosOneToManyTO;
import gov.dhs.kudos.rest.v1.to.SubKudoCommentTO;
import gov.dhs.kudos.rest.v1.to.UserLoginTO;
import gov.dhs.kudos.rest.v1.to.UserRegisterTO;
import gov.dhs.kudos.rest.v1.to.UserUpdateAccountTO;
import gov.dhs.kudos.rest.v1.to.UserUpdateProfileTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
     * @param userRegTO The user object to validate
     * @throws KudosException 
     */
    public void validateUserRegister(UserRegisterTO userRegTO) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user registration");
        
        if(userRegTO == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(userRegTO.getFirstName() == null || userRegTO.getLastName() == null || userRegTO.getPassword() == null)
            throw new KudosException("A required field for registration was null", HttpStatus.BAD_REQUEST);
        if(userRegTO.getFirstName().trim().length() == 0 || userRegTO.getLastName().trim().length() == 0 || userRegTO.getPassword().trim().length() == 0)
            throw new KudosException("A required field for registration was empty", HttpStatus.BAD_REQUEST);
        if(userRegTO.getEmail() == null || userRegTO.getEmail().trim().length() == 0 || !userRegTO.getEmail().contains("@"))
            throw new KudosException("A required field for registration was invalid - need valid email", HttpStatus.BAD_REQUEST);
        if(userRegTO.getPassword().trim().length() < 6)
            throw new KudosException("Password must be at least six characters", HttpStatus.BAD_REQUEST);
        if(userRegTO.getOrgId() == null || userRegTO.getOrgId().trim().length() == 0)
            throw new KudosException("Must supply orgId", HttpStatus.BAD_REQUEST);
        if(!orgExistsById(userRegTO.getOrgId()))
            throw new KudosException("Organization doesn't exists", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates a user prior to login
     * @param user The user object to validate
     * @throws KudosException 
     */
    public void validateUserLogin(UserLoginTO user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user login");
        
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getEmail() == null || user.getPassword() == null || user.getEmail().trim().length() == 0 || user.getPassword().trim().length() == 0)
            throw new KudosException("A required field for login was empty or null", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates a user prior to an update
     * @param userAcctTO The user to validate
     * @param request The request containing the actual user
     * @throws KudosException 
     */
    public void validateUserUpdateAccount(UserUpdateAccountTO userAcctTO, HttpServletRequest request) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user account update");
        
        if(userAcctTO == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(userAcctTO.getUserId() == null || userAcctTO.getUserId().trim().length() == 0)
            throw new KudosException("A required field for user update was null - need id", HttpStatus.BAD_REQUEST);
        if(!userExists(userAcctTO.getUserId()))
            throw new KudosException("User doesn't exists", HttpStatus.BAD_REQUEST);
        if(userAcctTO.getEmail() != null && (userAcctTO.getEmail().trim().length() == 0 || !userAcctTO.getEmail().contains("@")))
            throw new KudosException("A required field for user update was invalid - need valid email", HttpStatus.BAD_REQUEST);
        if(userAcctTO.getPassword() != null && userAcctTO.getPassword().trim().length() < 6)
            throw new KudosException("Password must be at least six characters", HttpStatus.BAD_REQUEST);
        
        if(request == null)
            throw new KudosException("No HttpRequest associated", HttpStatus.BAD_REQUEST);
        if(request.getAttribute("kudosUser") == null)
            throw new KudosException("No User object associated in the HttpRequest", HttpStatus.BAD_REQUEST);
        
        User reqUser = (User)request.getAttribute("kudosUser");
        if(!reqUser.isIsAdmin() && !reqUser.isIsHr() && !reqUser.getId().equals(userAcctTO.getUserId()))
            throw new KudosException("Only site Admins or HR Directors can mod other users", HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Validates a user prior to an update
     * @param userProfTO The user to validate
     * @param request The request containing the actual user
     * @throws KudosException 
     */
    public void validateUserUpdateProf(UserUpdateProfileTO userProfTO, HttpServletRequest request) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for user profile update");
        
        if(userProfTO == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(userProfTO.getUserId() == null || userProfTO.getUserId().trim().length() == 0)
            throw new KudosException("A required field for user update was null - need id", HttpStatus.BAD_REQUEST);
        if(!userExists(userProfTO.getUserId()))
            throw new KudosException("User doesn't exists", HttpStatus.BAD_REQUEST);
        if(userProfTO.getLastName() != null && userProfTO.getLastName().trim().length() == 0)
            throw new KudosException("Can't have a blank name", HttpStatus.BAD_REQUEST);
        if(userProfTO.getFirstName() != null && userProfTO.getFirstName().trim().length() == 0)
            throw new KudosException("Can't have a blank name", HttpStatus.BAD_REQUEST);
        if(userProfTO.getAvatarUrl() != null && userProfTO.getAvatarUrl().trim().length() == 0)
            throw new KudosException("Can't have a blank avatar url", HttpStatus.BAD_REQUEST);
        
        if(request == null)
            throw new KudosException("No HttpRequest associated", HttpStatus.BAD_REQUEST);
        if(request.getAttribute("kudosUser") == null)
            throw new KudosException("No User object associated in the HttpRequest", HttpStatus.BAD_REQUEST);
        
        User reqUser = (User)request.getAttribute("kudosUser");
        if(!reqUser.isIsAdmin() && !reqUser.getId().equals(userProfTO.getUserId()))
            throw new KudosException("Only site Admins can mod other users", HttpStatus.UNAUTHORIZED);      
    }
    
    /**
     * Validates an organization prior to saving
     * @param orgName The organization name to validate
     * @throws KudosException 
     */
    public void validateOrgCreate(String orgName) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating if organization already exists");
        
        if(orgName == null || orgName.trim().length() == 0)
            throw new KudosException("A required field for Organization was null - need orgName", HttpStatus.BAD_REQUEST);
        if(orgExists(orgName.trim()))
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
        if(org.getId() == null || org.getId().trim().length() == 0)
            throw new KudosException("A required field for Organization update was null - need id", HttpStatus.BAD_REQUEST);
        if(org.getOrgName() == null || org.getOrgName().trim().length() == 0)
            throw new KudosException("A required field for Organization update was null - need orgName", HttpStatus.BAD_REQUEST);
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
        if(user.getId() == null || user.getId().trim().length() == 0)
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
        if(orgHasClonableCatName(catId, orgName))
            throw new KudosException("Organization already contains cloned Kudos Category", HttpStatus.BAD_REQUEST);
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
        if(kudosCat.getName() == null || kudosCat.getName().trim().length() == 0)
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
        if(kudos.getComments() == null || kudos.getComments().trim().length() == 0)
            throw new KudosException("No comments in the Kudos object", HttpStatus.BAD_REQUEST);
        if(!userExists(fromUserId))
            throw new KudosException("No User exists for fromUserId", HttpStatus.BAD_REQUEST);
        if(!userExists(toUserId))
            throw new KudosException("No User exists for toUserId", HttpStatus.BAD_REQUEST);
        if(!kudosCatExists(kudosCatId))
            throw new KudosException("No KudosCategory exists for kudosCatId", HttpStatus.BAD_REQUEST);
        if(fromUserId.equals(toUserId))
            throw new KudosException("Silly Kudoer - kudos are for others", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates the criteria for appending a comment to a kudos
     * @param kudosId The id of the kudos
     * @param subComment The sub comment
     * @param request The request containing the User
     * @throws KudosException 
     */
    public void validateUpdateKudosAppendSubComment(String kudosId, SubKudoCommentTO subComment, HttpServletRequest request) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for appending a comment to a kudo");
        
        if(request == null)
            throw new KudosException("No HttpRequest associated", HttpStatus.BAD_REQUEST);
        if(request.getAttribute("kudosUser") == null)
            throw new KudosException("No User object associated in the HttpRequest", HttpStatus.BAD_REQUEST);
        if(kudosId == null || subComment == null)
            throw new KudosException("Reuired obejct(s) was null - required kudosId and KudosSubComment", HttpStatus.BAD_REQUEST);
        if(subComment.getComment() == null || subComment.getComment().trim().length() == 0)
            throw new KudosException("No comments in the SubKudoCommentTO object", HttpStatus.BAD_REQUEST);
        if(!kudosExists(kudosId))
            throw new KudosException("No Kudos exists for kudosId", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates from user, to users, kudos category, and kudosOneToMany object prior to saving a kudos
     * @param fromUserId The from user id
     * @param kudosCatId The kudos category id
     * @param kudosOneToMany The transfer object containing the Kudos object and a list of user ids
     * @throws KudosException 
     */
    public void validateCreateKudosOneToMany(String fromUserId, String kudosCatId, KudosOneToManyTO kudosOneToMany) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos one-to-many creation");
        
        if(fromUserId == null || kudosCatId == null || kudosOneToMany == null)
            throw new KudosException("Reuired obejct(s) was null - required fromUserId, kudosCatId, and kudosOneToMany", HttpStatus.BAD_REQUEST);
        if(!userExists(fromUserId))
            throw new KudosException("No User exists for fromUserId", HttpStatus.BAD_REQUEST);
        if(!kudosCatExists(kudosCatId))
            throw new KudosException("No KudosCategory exists for kudosCatId", HttpStatus.BAD_REQUEST);
        if(kudosOneToMany.getUserIds() == null || kudosOneToMany.getUserIds().isEmpty())
            throw new KudosException("No Users defined to send the kudo too", HttpStatus.BAD_REQUEST);
        if(kudosOneToMany.getComments() == null || kudosOneToMany.getComments().trim().length() == 0)
            throw new KudosException("No comments in the Kudos object", HttpStatus.BAD_REQUEST);
        
        for(String toUserId : kudosOneToMany.getUserIds())
        {                
            if(toUserId == null || toUserId.trim().length() == 0 || !userExists(toUserId))
                throw new KudosException("One of the Users listed doesn't exists", HttpStatus.BAD_REQUEST);
            if(toUserId.equals(fromUserId))
                throw new KudosException("Silly Kudoer - kudos are for others", HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Validates a kudos category prior to creating
     * @param kudosCat The kudos category to save
     * @param request The request containing the User
     * @throws KudosException 
     */
    public void validateCreateKudosCat(KudosCategory kudosCat, HttpServletRequest request) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for kudos-category creation");
        
        if(request == null)
            throw new KudosException("No HttpRequest associated", HttpStatus.BAD_REQUEST);
        if(request.getAttribute("kudosUser") == null)
            throw new KudosException("No User object associated in the HttpRequest", HttpStatus.BAD_REQUEST);
        if(!((User)request.getAttribute("kudosUser")).isIsAdmin())
            throw new KudosException("Admin rights are required for this operation", HttpStatus.UNAUTHORIZED);
        if(kudosCat == null)
            throw new KudosException("KudosCategory object is null", HttpStatus.BAD_REQUEST);
        if(kudosCat.getName() == null || kudosCat.getName().trim().length() == 0)
            throw new KudosException("A required field for KudosCategory save was empty - need name", HttpStatus.BAD_REQUEST);
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
        if(kudosCat.getId() == null || kudosCat.getId().trim().length() == 0)
            throw new KudosException("A required field for KudosCategory update was empty - need id", HttpStatus.BAD_REQUEST);
        if(kudosCat.getName() == null || kudosCat.getName().trim().length() == 0)
            throw new KudosException("A required field for KudosCategory update was empty - need name", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Validates the user permissions for modding a User and confirms the requested user id exists
     * @param request The requestor
     * @param id The user id validate for modding
     * @throws KudosException 
     */
    public void validateAdminUserMod(HttpServletRequest request, String id) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for delete User");
        
        if(request == null)
            throw new KudosException("No HttpRequest associated", HttpStatus.BAD_REQUEST);
        if(request.getAttribute("kudosUser") == null)
            throw new KudosException("No User object associated in the HttpRequest", HttpStatus.BAD_REQUEST);
        if(id == null || id.trim().length() == 0 || !userExists(id))
            throw new KudosException("Kudos user doesn't exists", HttpStatus.BAD_REQUEST);
        if(!((User)request.getAttribute("kudosUser")).isIsAdmin())
            throw new KudosException("Admin rights are required for this operation", HttpStatus.UNAUTHORIZED);
        if(((User)request.getAttribute("kudosUser")).getId().equals(id))
            throw new KudosException("Don't mod yourself!", HttpStatus.BAD_REQUEST);
    }
    
    
    public void validateHrUserMod(HttpServletRequest request, String id) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating required fields for toggling HR User");
        
        if(request == null)
            throw new KudosException("No HttpRequest associated", HttpStatus.BAD_REQUEST);
        if(request.getAttribute("kudosUser") == null)
            throw new KudosException("No User object associated in the HttpRequest", HttpStatus.BAD_REQUEST);
        if(id == null || id.trim().length() == 0 || !userExists(id))
            throw new KudosException("Kudos user doesn't exists", HttpStatus.BAD_REQUEST);
        if(!((User)request.getAttribute("kudosUser")).isIsAdmin() && !((User)request.getAttribute("kudosUser")).isIsHr())
            throw new KudosException("Admin or HR rights are required for this operation", HttpStatus.UNAUTHORIZED);
        if(((User)request.getAttribute("kudosUser")).getId().equals(id))
            throw new KudosException("Don't mod yourself!", HttpStatus.BAD_REQUEST);
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
     * Determines if a kudos already exists by id
     * @param id The id of the kudos
     * @return Whether or not the kudos already exists
     */
    private boolean kudosExists(String id)
    {
        return (kudosRepo.findOne(id) != null);
    }
    
    /**
     * Determines if an Organization already has a KudosCategory with that name
     * @param catId The id of the Kudos Category to clone from
     * @param orgName the name of the Organization
     * @return Whether or not an Organization already contains the clonable KudosCat name
     */
    private boolean orgHasClonableCatName(String catId, String orgName)
    {        
        String nameSearch = kudosCatRepo.findOne(catId).getName() + "-" + orgName;
        List<KudosCategory> kcList = organizationRepo.findByOrgNameIgnoreCase(orgName).getKudosCategories();
        
        if(kcList != null && !kcList.isEmpty())
        {
            for(KudosCategory kc : kcList)
            {
                if(kc.getName().equals(nameSearch))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Determines if the organization already exists by name
     * @param orgName The name of the organization
     * @return Whether or not the organization already exists
     */
    private boolean orgExists(String orgName)
    {
        return (organizationRepo.findByOrgNameIgnoreCase(orgName) != null);
    }
    
    private boolean orgExistsById(String orgId)
    {
        return (organizationRepo.findOne(orgId) != null);
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
        if(user.getOrgName() == null)
            return false;
        
        return (user.getOrgName().equals(orgName));
    }
}
