package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author bsuneson
 */
public class UserService extends OrganizationService
{
    public void validateUserRegister(User user) throws KudosException
    {
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null)
            throw new KudosException("A required field for registration was null", HttpStatus.BAD_REQUEST);
    }
    
    public void validateUserLogin(User user) throws KudosException
    {
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getEmail() == null || user.getPassword() == null)
            throw new KudosException("A required field for login was null", HttpStatus.BAD_REQUEST);
    }
    
    public void validateUserUpdate(User user) throws KudosException
    {
        if(user == null)
            throw new KudosException("User object is null", HttpStatus.BAD_REQUEST);
        if(user.getId() == null)
            throw new KudosException("A required field for user update was null - need id", HttpStatus.BAD_REQUEST);
    }
    
    public User saveUser(User user) throws KudosException
    {
        User alreadyExistUser = userRepo.findByEmail(user.getEmail());
        
        if(alreadyExistUser != null)
            throw new KudosException("Email address already in use by someone", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return userRepo.save(user);
    }
    
    public User loginUser(User user) throws KudosException
    {
        User foundUser = userRepo.findByEmail(user.getEmail());
        
        if(foundUser.getPassword().equals(user.getEmail()))
            return foundUser;
        else
            throw new KudosException("Bad Credentials", HttpStatus.UNAUTHORIZED);
    }
    
    public User updateUser(User user) throws KudosException
    {
        User alreadyExistUser = userRepo.findByEmail(user.getEmail());
        
        if(alreadyExistUser != null && !alreadyExistUser.getId().equals(user.getId()) && alreadyExistUser.getEmail().equals(user.getEmail()))
            throw new KudosException("Email address already in use by someone", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return userRepo.save(user);
    }
    
    public List<User> findAllUsers() 
    {
        return userRepo.findAll();
    }
    
    public User findUserByEmail(String email)
    {
        return userRepo.findByEmail(email);
    }
    
    public User findUserById(String id)
    {
        return userRepo.findOne(id);
    }
    
    public List<User> findUsersByFirstName(String firstName)
    {
        return userRepo.findByFirstName(firstName);
    }
    
    public List<User> findUsersByLastName(String lastName)
    {
        return userRepo.findByLastName(lastName);
    }
}
