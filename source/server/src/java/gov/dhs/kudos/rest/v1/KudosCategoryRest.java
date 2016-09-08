package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.service.KudosService;
import gov.dhs.kudos.rest.v1.util.LogUtils;
import javax.servlet.http.HttpServletRequest;
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
 * Restful v1 endpoint for handling all kudos category transactions
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/cat")
public class KudosCategoryRest 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(KudosCategoryRest.class);
    
    /** The service layer for logic **/
    @Autowired 
    private KudosService kudosService;
    
    public KudosCategoryRest()
    {
        
    }
    
    /**
     * Endpoint for retrieving all kudos categories
     * @return All kudos categories
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosCats()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/all]");
        
        return new ResponseEntity(kudosService.findAllKudosCats(), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a kudos category by name
     * @param name The PathVariable of the kudos category name
     * @return A Kudos Category object
     */
    @RequestMapping(value = "/byName/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosCatByName(@PathVariable String name)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/{name}] name: " + (name == null ? "NO name SUPPLIED" : name));
        
        return new ResponseEntity(kudosService.findKudosCatByName(name), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a kudos category by id
     * @param id The PathVariable of the kudos category id
     * @return A Kudos Category object
     */
    @RequestMapping(value = "/byId/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosCatById(@PathVariable String id)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/{id}] id: " + (id == null ? "NO id SUPPLIED" : id));
        
        return new ResponseEntity(kudosService.findKudosCatById(id), HttpStatus.OK);
    }
    
    /**
     * Endpoint for creating a new kudos category
     * @param kudosCat The RequestBody object of a new kudos category
     * @param request The request containing the User
     * @return The saved kudos category object
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createKudosCat(@RequestBody(required = false) KudosCategory kudosCat, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/create] kudosCat: " + (kudosCat == null ? "NO kudosCat OBJECT" : LogUtils.objectToJson(kudosCat)));
        
        try
        {
            kudosService.validateCreateKudosCat(kudosCat, request);
            return new ResponseEntity(kudosService.saveKudosCat(kudosCat), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for updating a kudos category
     * @param kudosCat The RequestBody object of a new kudos category
     * @return The updated kudos category object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity updateKudosCat(@RequestBody(required = false) KudosCategory kudosCat)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/update] kudosCat: " + (kudosCat == null ? "NO kudosCat OBJECT" : LogUtils.objectToJson(kudosCat)));
        
        try
        {
            kudosService.validateUpdateKudosCat(kudosCat);
            return new ResponseEntity(kudosService.updateKudosCat(kudosCat), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
}
