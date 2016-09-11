package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.to.UserLoginTO;
import gov.dhs.kudos.rest.v1.to.UserRegisterTO;
import gov.dhs.kudos.rest.v1.to.UserUpdateAccountTO;
import gov.dhs.kudos.rest.v1.to.UserUpdateProfileTO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 * Service layer for handling logic for the User v1 endpoints
 * @author bsuneson
 */
public class UserService extends OrganizationService
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(UserService.class);
    
    /**
     * Saves the user object
     * @param userRegTO The user object to save
     * @return The saved user object
     * @throws KudosException 
     */
    public User saveUser(UserRegisterTO userRegTO) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving user");
        
        User alreadyExistUser = userRepo.findByEmailIgnoreCase(userRegTO.getEmail().trim());
        
        if(alreadyExistUser != null)
        {
            if(!alreadyExistUser.isIsDeleted())
                throw new KudosException("Email address already in use by someone", HttpStatus.BAD_REQUEST);
            if(alreadyExistUser.isIsDeleted())
                throw new KudosException("You have been previously deleted by a Site Admin.  Please contact Kudos for reactivation.", HttpStatus.UNAUTHORIZED);
        }
        
        Organization org = organizationRepo.findOne(userRegTO.getOrgId());
        User user = new User(userRegTO.getEmail().trim(), userRegTO.getFirstName().trim(), userRegTO.getLastName().trim(), userRegTO.getPassword().trim(), false);
        user.setOrgName(org.getOrgName());
        user.setStartDate(userRegTO.getStartDate());
        
        return userRepo.save(user);
    }
    
    /**
     * Login a user
     * @param user The user object to login
     * @return The complete user object
     * @throws KudosException 
     */
    public User loginUser(UserLoginTO user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("User login");
        
        User foundUser = userRepo.findByEmailIgnoreCase(user.getEmail());
        
        if(foundUser != null && foundUser.getPassword().equals(user.getPassword()) && !foundUser.isIsDeleted())
            return foundUser;
        else
            throw new KudosException("Bad Credentials", HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Updates a user object
     * @param userAcctTO The user object to update - must already exists and email must be unique
     * @return The updated user object
     * @throws KudosException 
     */
    public User updateUserAccount(UserUpdateAccountTO userAcctTO) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Updating user account");
        
        User currentUser = userRepo.findOne(userAcctTO.getUserId());
        
        if(userAcctTO.getPassword() != null && userAcctTO.getPassword().trim().length() >= 6)
            currentUser.setPassword(userAcctTO.getPassword().trim());
        if(userAcctTO.getEmail() != null && userAcctTO.getEmail().trim().length() > 0)
            currentUser.setEmail(userAcctTO.getEmail().trim());
        
        return userRepo.save(currentUser);
    }
    
    /**
     * Updates a user object
     * @param userProfTO The user object to update - must already exists and email must be unique
     * @return The updated user object
     * @throws KudosException 
     */
    public User updateUserProf(UserUpdateProfileTO userProfTO) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Updating user profile");
        
        User currentUser = userRepo.findOne(userProfTO.getUserId());
        
        if(userProfTO.getFirstName() != null && userProfTO.getFirstName().trim().length() > 0)
            currentUser.setFirstName(userProfTO.getFirstName().trim());
        if(userProfTO.getLastName() != null && userProfTO.getLastName().trim().length() > 0)
            currentUser.setLastName(userProfTO.getLastName().trim());
        if(userProfTO.getAvatarUrl() != null && userProfTO.getAvatarUrl().trim().length() > 0)
            currentUser.setAvatarUrl(userProfTO.getAvatarUrl());
        
        return userRepo.save(currentUser);
    }
    
    /**
     * Finds all users
     * @return A List of user objects
     */
    public List<User> findAllUsers() 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all users");
        
        return userRepo.findAll();
    }
    
    /**
     * Finds all users
     * @return A List of user objects
     */
    public List<User> findAllActiveUsers() 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all active users");
        
        return userRepo.findByIsDeleted(false);
    }
    
    /**
     * Finds a user based on their email
     * @param email The email address to search for
     * @return A User object matching the email
     */
    public User findUserByEmail(String email)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding user by email");
        
        return userRepo.findByEmailIgnoreCase(email);
    }
    
    /**
     * Finds a user based on their id
     * @param id The id to search for
     * @return A User object matching the id
     */
    public User findUserById(String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding user by id");
        
        return userRepo.findOne(id);
    }
    
    /**
     * Finds all users based on first name
     * @param firstName The first name to search for
     * @return A List of users matching the first name
     */
    public List<User> findUsersByFirstName(String firstName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding users by first name");
        
        return userRepo.findByFirstNameIgnoreCase(firstName);
    }
    
    /**
     * Finds all users based on last name
     * @param lastName The last name to search for
     * @return A List of users matching the last name
     */
    public List<User> findUsersByLastName(String lastName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding users by last name");
        
        return userRepo.findByLastNameIgnoreCase(lastName);
    }
    
    /**
     * Deletes a User
     * @param id The id of the user
     * @return The updated User object saved with isDeleted true
     */
    public User deleteUser(String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Toggling deletion status for user by id");
        
        User user = userRepo.findOne(id);
        user.setIsDeleted((!user.isIsDeleted()));
        
        return userRepo.save(user);
    }
    
    /**
     * Un-deletes a User
     * @param id The id of the user
     * @return The updated User object saved with isDeleted false
     */
    public User toggleAdminUser(String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Toggling admin-rights for user by id");
        
        User user = userRepo.findOne(id);
        user.setIsAdmin((!user.isIsAdmin()));
        
        return userRepo.save(user);
    }
    
    public User toggleHrUser(String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Toggling user as HR Director by id");
        
        User user = userRepo.findOne(id);
        user.setIsHr((!user.isIsHr()));
        
        return userRepo.save(user);
    }
}
