package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired 
    private KudosService kudosService;

    public UserRest() 
    {
        
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity register(@RequestBody(required = false) User user, HttpServletResponse response)
    {
        try
        {
            kudosService.validateUserRegister(user);
            
            user = kudosService.saveUser(user);
            JwtTokenUtil.generateToken(user, response);
            
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(KudosException e)
        {
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }      
    }
  
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity login(@RequestBody(required = false) User user, HttpServletResponse  response)
    {
        try
        {
            kudosService.validateUserLogin(user);
            
            user = kudosService.loginUser(user);
            JwtTokenUtil.generateToken(user, response);
            
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(KudosException e)
        {
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateUser(@RequestBody(required = false) User user)
    {
        try
        {
            kudosService.validateUserUpdate(user);
            return new ResponseEntity(kudosService.saveUser(user), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllUsers()
    {
        return new ResponseEntity(kudosService.findAllUsers(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byEmail/{email}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserByEmail(@PathVariable String email)
    {
        return new ResponseEntity(kudosService.findUserByEmail(email), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byId/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserById(@PathVariable String id)
    {
        return new ResponseEntity(kudosService.findUserById(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byFirstName/{firstName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsersByFirstName(@PathVariable String firstName)
    {
        return new ResponseEntity(kudosService.findUsersByFirstName(firstName), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byLastName/{lastName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsersByLastName(@PathVariable String lastName)
    {
        return new ResponseEntity(kudosService.findUsersByLastName(lastName), HttpStatus.OK);
    }
}
