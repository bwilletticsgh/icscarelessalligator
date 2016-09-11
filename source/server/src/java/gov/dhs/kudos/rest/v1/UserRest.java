package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
import gov.dhs.kudos.rest.v1.to.UserLoginTO;
import gov.dhs.kudos.rest.v1.to.UserRegisterTO;
import gov.dhs.kudos.rest.v1.to.UserUpdateAccountTO;
import gov.dhs.kudos.rest.v1.to.UserUpdateProfileTO;
import gov.dhs.kudos.rest.v1.util.LogUtils;
import javax.servlet.http.HttpServletRequest;
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
     * Endpoint for updating a User account
     * @param userAcctTO The RequestBody UserUpdateAccountTO object - expects id
     * @param request The request containing the actual user
     * @return Updated User object and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateUserAccount(@RequestBody(required = false) UserUpdateAccountTO userAcctTO, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/updateAccount] user: " + (userAcctTO == null ? "NO userAcctTO OBJECT" : LogUtils.objectToJson(userAcctTO)));
        
        try
        {
            kudosService.validateUserUpdateAccount(userAcctTO, request);
            return new ResponseEntity(kudosService.updateUserAccount(userAcctTO), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for updating a User profile
     * @param userProfTO The RequestBody UserUpdateProfileTO object - expects id
     * @param request The request containing the actual user
     * @return Updated User object and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateUserProfile(@RequestBody(required = false) UserUpdateProfileTO userProfTO, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/updateProfile] user: " + (userProfTO == null ? "NO userProfTO OBJECT" : LogUtils.objectToJson(userProfTO)));
        
        try
        {
            kudosService.validateUserUpdateProf(userProfTO, request);
            return new ResponseEntity(kudosService.updateUserProf(userProfTO), HttpStatus.OK);
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
     * Endpoint for retrieving all active Users
     * @return All active Users and updated expiry time JSON Web Token in the Authorization Header
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllActiveUsers()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/active]");
        
        return new ResponseEntity(kudosService.findAllActiveUsers(), HttpStatus.OK);
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
    
    /**
     * Endpoint for deleting / un-deleting users based on their id
     * @param id The PathVariable for a users id
     * @param request The request containing the User object
     * @return The updated User object
     */
    @RequestMapping(value = "/toggleDelete/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity toggleDelete(@PathVariable String id, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/toggleDelete/{id}] id: " + (id == null ? "NO id SUPPLIED" : id));
        
        try
        {
            kudosService.validateAdminUserMod(request, id);
            return new ResponseEntity(kudosService.deleteUser(id), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for toggling admin-rights for users based on their id
     * @param id The PathVariable for a users id
     * @param request The request containing the User object
     * @return The updated User object
     */
    @RequestMapping(value = "/toggleAdmin/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity toggleAdmin(@PathVariable String id, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/toggleAdmin/{id}] id: " + (id == null ? "NO id SUPPLIED" : id));
        
        try
        {
            kudosService.validateAdminUserMod(request, id);
            return new ResponseEntity(kudosService.toggleAdminUser(id), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/toggleHr/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity toggleHr(@PathVariable String id, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/toggleHr/{id}] id: " + (id == null ? "NO id SUPPLIED" : id));
        
        try
        {
            kudosService.validateHrUserMod(request, id);
            return new ResponseEntity(kudosService.toggleHrUser(id), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }    
}
