package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
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
@RequestMapping(value = "/v1/cat")
public class KudosCategoryRest 
{
    private static final Logger LOG = Logger.getLogger(KudosCategoryRest.class);
    
    @Autowired 
    private KudosService kudosService;
    
    public KudosCategoryRest()
    {
        
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosCats()
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/all]");
        
        return new ResponseEntity(kudosService.findAllKudosCats(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosCatByName(@PathVariable String name)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/{name}] name: " + (name == null ? "NO name SUPPLIED" : name));
        
        return new ResponseEntity(kudosService.findKudosCatByName(name), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createKudosCat(@RequestBody(required = false) KudosCategory kudosCat)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/cat/create] kudosCat: " + (kudosCat == null ? "NO kudosCat OBJECT" : LogUtils.objectToJson(kudosCat)));
        
        try
        {
            kudosService.validateCreateKudosCat(kudosCat);
            return new ResponseEntity(kudosService.saveKudosCat(kudosCat), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
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
