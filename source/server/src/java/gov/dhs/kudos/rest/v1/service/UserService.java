package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.to.UserLoginTO;
import gov.dhs.kudos.rest.v1.to.UserRegisterTO;
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
        
        User alreadyExistUser = userRepo.findByEmail(userRegTO.getEmail());
        
        if(alreadyExistUser != null)
            throw new KudosException("Email address already in use by someone", HttpStatus.INTERNAL_SERVER_ERROR);
        
        User user = new User(userRegTO.getEmail(), userRegTO.getFirstName(), userRegTO.getLastName(), userRegTO.getPassword(), false);
        
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
        
        User foundUser = userRepo.findByEmail(user.getEmail());
        
        if(foundUser != null && foundUser.getPassword().equals(user.getPassword()))
            return foundUser;
        else
            throw new KudosException("Bad Credentials", HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Updates a user object
     * @param user The user object to update - must already exists and email must be unique
     * @return The updated user object
     * @throws KudosException 
     */
    public User updateUser(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Updating user");
        
        User alreadyExistUser = userRepo.findByEmail(user.getEmail());
        
        if(alreadyExistUser != null && !alreadyExistUser.getId().equals(user.getId()))
            throw new KudosException("Email address already in use by someone", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return userRepo.save(user);
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
     * Finds a user based on their email
     * @param email The email address to search for
     * @return A User object matching the email
     */
    public User findUserByEmail(String email)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding user by email");
        
        return userRepo.findByEmail(email);
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
        
        return userRepo.findByFirstName(firstName);
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
        
        return userRepo.findByLastName(lastName);
    }
}
