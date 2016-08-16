package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
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
 *
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/user")
public class UserRest 
{
    private static final Logger LOG = Logger.getLogger(UserRest.class);
    
    @Autowired 
    private KudosService kudosService;

    public UserRest() 
    {
        
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity register(@RequestBody(required = false) User user, HttpServletResponse response)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/register] user: " + (user == null ? "NO USER OBJECT" : LogUtils.objectToJson(user)));
        
        try
        {
            kudosService.validateUserRegister(user);
            
            user = kudosService.saveUser(user);
            JwtTokenUtil.generateToken(user, response);
            
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }      
    }
  
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity login(@RequestBody(required = false) User user, HttpServletResponse  response)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/login] user: " + (user == null ? "NO USER OBJECT" : LogUtils.objectToJson(user)));
        
        try
        {
            kudosService.validateUserLogin(user);
            
            user = kudosService.loginUser(user);
            JwtTokenUtil.generateToken(user, response);
            
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateUser(@RequestBody(required = false) User user)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/update] user: " + (user == null ? "NO USER OBJECT" : LogUtils.objectToJson(user)));
        
        try
        {
            kudosService.validateUserUpdate(user);
            return new ResponseEntity(kudosService.saveUser(user), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllUsers()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/all]");
        
        return new ResponseEntity(kudosService.findAllUsers(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byEmail/{email}/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserByEmail(@PathVariable String email)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byEmail/{email}] email: " + (email == null ? "NO EMAIL SUPPLIED" : email));
        
        return new ResponseEntity(kudosService.findUserByEmail(email), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byId/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserById(@PathVariable String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byId/{id}] id: " + (id == null ? "NO ID SUPPLIED" : id));
        
        return new ResponseEntity(kudosService.findUserById(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byFirstName/{firstName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsersByFirstName(@PathVariable String firstName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byFirstName/{firstName}] firstName: " + (firstName == null ? "NO FIRSTNAME SUPPLIED" : firstName));
        
        return new ResponseEntity(kudosService.findUsersByFirstName(firstName), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byLastName/{lastName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsersByLastName(@PathVariable String lastName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/user/byLastName/{lastName}] lastName: " + (lastName == null ? "NO LASTNAME SUPPLIED" : lastName));
        
        return new ResponseEntity(kudosService.findUsersByLastName(lastName), HttpStatus.OK);
    }
}
