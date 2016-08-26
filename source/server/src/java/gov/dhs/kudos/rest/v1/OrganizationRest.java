package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
import gov.dhs.kudos.rest.v1.util.LogUtils;
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
 * Restful v1 endpoint for handling all organization transactions
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/org")
public class OrganizationRest 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(OrganizationRest.class);
    
    /** The service layer for logic **/
    @Autowired 
    private KudosService kudosService;
    
    public OrganizationRest()
    {
        
    }
    
    /**
     * Endpoint for retrieving all organizations
     * @return All organizations
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrgs()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/all]");
        
        return new ResponseEntity(kudosService.findAllOrgs(), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving an organization by name
     * @param orgName The PathVariable of the organizations name
     * @return Organization object
     */
    @RequestMapping(value = "/byName/{orgName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrgByName(@PathVariable String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/byName/{orgName}] orgName: " + (orgName == null ? "NO ORGNAME SUPPLIED" : orgName));
        
        return new ResponseEntity(kudosService.getOrgByName(orgName), HttpStatus.OK);
    }
    
    /**
     * Endpoint for creating an organization
     * @param orgName The PathVariable of the new organizations name
     * @return The saved organization object
     */
    @RequestMapping(value = "/create/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@PathVariable String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/create/{orgName}] orgName: " + (orgName == null ? "NO ORGNAME SUPPLIED" : orgName));
        
        try
        {
            kudosService.validateOrgCreate(orgName);
            return new ResponseEntity(kudosService.saveOrg(new Organization(orgName.trim())), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for updating an organization
     * @param org The RequestBody object containing an organization - must contain id
     * @return The updated organization object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateOrg(@RequestBody(required = false) Organization org)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/update] org: " + (org == null ? "NO org OBJECT" : LogUtils.objectToJson(org)));
        
        try
        {
            kudosService.validateOrgUpdate(org);
            return new ResponseEntity(kudosService.saveOrg(org), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for adding a user to an organization
     * @param orgName The PathVariable of the organizations name
     * @param user The RequestBody object containing a user - must be valid user, organization must exists, and user must not already be a member
     * @return The updated organization object
     */
    @RequestMapping(value = "/addUser/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity orgAddUser(@PathVariable String orgName, @RequestBody(required = false) User user)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("[/v1/org/addUser/{orgName}] user: " + (user == null ? "NO user OBJECT" : LogUtils.objectToJson(user)));
            LOG.debug("[/v1/org/addUser/{orgName}] orgName: " + (orgName == null ? "NO ORGNAME SUPPLIED" : orgName));
        }            
        
        try
        {
            kudosService.validateOrgAddUser(user, orgName);
            return new ResponseEntity(kudosService.addOrgUser(user, orgName), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for cloning a kudos category into an organization
     * @param catId The PathVariable id of the kudos category to clone
     * @param orgName The PathVariable organization to clone the kudos category into
     * @return The updated organization object
     */
    @RequestMapping(value = "/cloneCat/{catId}/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity orgCloneCat(@PathVariable String catId, @PathVariable String orgName)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("[/v1/org/cloneCat/{catId}/{orgName}] catId: " + (catId == null ? "NO catId" : catId));
            LOG.debug("[/v1/org/cloneCat/{catId}/{orgName}] orgName: " + (orgName == null ? "NO orgId" : orgName));
        }            
        
        try
        {
            kudosService.validateOrgCloneCat(catId, orgName);
            return new ResponseEntity(kudosService.cloneCat(catId, orgName), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for creating a new kudos category for an organization
     * @param orgName The PathVariable name of the organization that will have the new kudos category
     * @param kudosCat The RequestBody of the new kudos category object
     * @return The updated organization object
     */
    @RequestMapping(value = "/createCat/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity orgCreateCat(@PathVariable String orgName, @RequestBody(required = false) KudosCategory kudosCat)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("[/v1/org/addUser/{orgName}] user: " + (kudosCat == null ? "NO kudosCat OBJECT" : LogUtils.objectToJson(kudosCat)));
            LOG.debug("[/v1/org/addUser/{orgName}] orgName: " + (orgName == null ? "NO ORGNAME SUPPLIED" : orgName));
        }            
        
        try
        {
            kudosService.validateOrgCreateCat(kudosCat, orgName);
            return new ResponseEntity(kudosService.createCat(kudosCat, orgName), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
}
