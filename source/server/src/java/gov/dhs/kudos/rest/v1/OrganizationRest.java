package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Organization;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
import javax.annotation.PostConstruct;
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
    @Autowired 
    private KudosService kudosService;
    
    public OrganizationRest()
    {
        
    }
    
    @PostConstruct
    public void initData()
    {
        kudosService.initDumbydata();
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrgs()
    {
        return new ResponseEntity(kudosService.findAllOrgs(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byName/{orgName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrgByName(@PathVariable String orgName)
    {
        return new ResponseEntity(kudosService.getOrgByName(orgName), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create/{orgName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@PathVariable String orgName)
    {
        try
        {
            kudosService.validateOrgSave(orgName);
            return new ResponseEntity(kudosService.saveOrg(new Organization(orgName)), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@RequestBody(required = false) Organization org)
    {
        try
        {
            kudosService.validateOrgUpdate(org);
            return new ResponseEntity(kudosService.saveOrg(org), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    @RequestMapping(value = "/addUser/{orgName}/{userId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createOrg(@PathVariable String orgName, @PathVariable String userId)
    {
        try
        {
            kudosService.validateOrgAddUser(userId, orgName);
            return new ResponseEntity(kudosService.addOrgUser(userId, orgName), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
}
