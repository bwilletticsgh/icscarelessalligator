package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author bsuneson
 */
public class UserService extends OrganizationService
{
    private static final Logger LOG = Logger.getLogger(UserService.class);
    
    public User saveUser(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Saving user");
        
        User alreadyExistUser = userRepo.findByEmail(user.getEmail());
        
        if(alreadyExistUser != null)
            throw new KudosException("Email address already in use by someone", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return userRepo.save(user);
    }
    
    public User loginUser(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("User login");
        
        User foundUser = userRepo.findByEmail(user.getEmail());
        
        if(foundUser != null && foundUser.getPassword().equals(user.getPassword()))
            return foundUser;
        else
            throw new KudosException("Bad Credentials", HttpStatus.UNAUTHORIZED);
    }
    
    public User updateUser(User user) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Updating user");
        
        User alreadyExistUser = userRepo.findByEmail(user.getEmail());
        
        if(alreadyExistUser != null && !alreadyExistUser.getId().equals(user.getId()) && alreadyExistUser.getEmail().equals(user.getEmail()))
            throw new KudosException("Email address already in use by someone", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return userRepo.save(user);
    }
    
    public List<User> findAllUsers() 
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding all users");
        
        return userRepo.findAll();
    }
    
    public User findUserByEmail(String email)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding user by email");
        
        return userRepo.findByEmail(email);
    }
    
    public User findUserById(String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding user by id");
        
        return userRepo.findOne(id);
    }
    
    public List<User> findUsersByFirstName(String firstName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding users by first name");
        
        return userRepo.findByFirstName(firstName);
    }
    
    public List<User> findUsersByLastName(String lastName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Finding users by last name");
        
        return userRepo.findByLastName(lastName);
    }
}
