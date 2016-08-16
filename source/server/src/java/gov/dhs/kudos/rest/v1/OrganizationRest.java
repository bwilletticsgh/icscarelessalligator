package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
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
 *
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/org")
public class OrganizationRest 
{
    private static final Logger LOG = Logger.getLogger(OrganizationRest.class);
    
    @Autowired 
    private KudosService kudosService;
    
    public OrganizationRest()
    {
        
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrgs()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/all]");
        
        return new ResponseEntity(kudosService.findAllOrgs(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byName/{orgName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrgByName(@PathVariable String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/byName/{orgName}] orgName: " + (orgName == null ? "NO ORGNAME SUPPLIED" : orgName));
        
        return new ResponseEntity(kudosService.getOrgByName(orgName), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@PathVariable String orgName)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/org/create/{orgName}] orgName: " + (orgName == null ? "NO ORGNAME SUPPLIED" : orgName));
        
        try
        {
            kudosService.validateOrgSave(orgName);
            return new ResponseEntity(kudosService.saveOrg(new Organization(orgName)), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@RequestBody(required = false) Organization org)
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
    
    @RequestMapping(value = "/addUser/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@PathVariable String orgName, @RequestBody(required = false) User user)
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
}
