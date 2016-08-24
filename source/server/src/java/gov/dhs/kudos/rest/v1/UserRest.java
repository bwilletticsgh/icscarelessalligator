package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
import gov.dhs.kudos.rest.v1.to.UserLoginTO;
import gov.dhs.kudos.rest.v1.to.UserRegisterTO;
import gov.dhs.kudos.rest.v1.util.LogUtils;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Restful v1 endpoint for handling all user transactions
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/user")
public class UserRest 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(UserRest.class);
    
    /** The service layer for logic **/
    @Autowired 
    private KudosService kudosService;

    public UserRest() 
    {
        
    }
    
    /**
     * Endpoint for registering users
     * @param userRegTO The RequestBody User object - expects email, firstName, lastName and password
     * @param response The HttpServletResponse for replying back with a valid JSON Web Token
     * @return Updated User object and JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity register(@RequestBody(required = false) UserRegisterTO userRegTO, HttpServletResponse response)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/register] user: " + (userRegTO == null ? "NO USER OBJECT" : LogUtils.objectToJson(userRegTO)));
        
        try
        {
            kudosService.validateUserRegister(userRegTO);
            
            User user = kudosService.saveUser(userRegTO);
            JwtTokenUtil.generateToken(user, response);
            
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }      
    }
  
    /**
     * Endpoint for logging in
     * @param userTO The RequestBody User object - expects email and password
     * @param response The HttpServletResponse for replying back with a valid JSON Web Token
     * @return Updated User object and JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity login(@RequestBody(required = false) UserLoginTO userTO, HttpServletResponse  response)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/login] user: " + (userTO == null ? "NO USER OBJECT" : LogUtils.objectToJson(userTO)));
        
        try
        {
            kudosService.validateUserLogin(userTO);
            
            User user = kudosService.loginUser(userTO);
            JwtTokenUtil.generateToken(user, response);
            
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for updating a User
     * @param user The RequestBody User object - expects id
     * @return Updated User object and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateUser(@RequestBody(required = false) User user)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/update] user: " + (user == null ? "NO USER OBJECT" : LogUtils.objectToJson(user)));
        
        try
        {
            kudosService.validateUserUpdate(user);
            return new ResponseEntity(kudosService.updateUser(user), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for retrieving all Users
     * @return All Users and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllUsers()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/all]");
        
        return new ResponseEntity(kudosService.findAllUsers(), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a User by their email address
     * @param email The PathVariable for a users email
     * @return A User object and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/byEmail/{email}/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserByEmail(@PathVariable String email)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byEmail/{email}] email: " + (email == null ? "NO EMAIL SUPPLIED" : email));
        
        return new ResponseEntity(kudosService.findUserByEmail(email), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a User by their id
     * @param id The PathVariable for a users id
     * @return A User object and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/byId/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserById(@PathVariable String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byId/{id}] id: " + (id == null ? "NO ID SUPPLIED" : id));
        
        return new ResponseEntity(kudosService.findUserById(id), HttpStatus.OK);
    }
    
    /**
     * Endpoint for searching users based on their first name
     * @param firstName The PathVariable for a users firstName
     * @return A list of Users that matched the PathVariable and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/byFirstName/{firstName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsersByFirstName(@PathVariable String firstName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byFirstName/{firstName}] firstName: " + (firstName == null ? "NO FIRSTNAME SUPPLIED" : firstName));
        
        return new ResponseEntity(kudosService.findUsersByFirstName(firstName), HttpStatus.OK);
    }
    
    /**
     * Endpoint for searching users based on their last name
     * @param lastName The PathVariable for a users lastName
     * @return A list of Users that matched the PathVariable and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/byLastName/{lastName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsersByLastName(@PathVariable String lastName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byLastName/{lastName}] lastName: " + (lastName == null ? "NO LASTNAME SUPPLIED" : lastName));
        
        return new ResponseEntity(kudosService.findUsersByLastName(lastName), HttpStatus.OK);
    }
}
