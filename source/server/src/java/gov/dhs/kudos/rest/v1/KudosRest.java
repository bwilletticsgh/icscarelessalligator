package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Kudos;
import gov.dhs.kudos.rest.v1.service.KudosService;
import gov.dhs.kudos.rest.v1.util.LogUtils;
import javax.annotation.PostConstruct;
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
@RequestMapping(value = "/v1/kudos")
public class KudosRest 
{
    private static final Logger LOG = Logger.getLogger(KudosRest.class);
    
    @Autowired 
    private KudosService kudosService;
    
    
    public KudosRest()
    {
        
    }    
    
    @PostConstruct
    public void initData()
    {
        kudosService.initDumbydata();
    }
    
    @RequestMapping(value = "/fromUser/all/{fromUserId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosFromUser(@PathVariable String fromUserId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/kudos/fromUser/all/{fromUserId}] fromUserId: " + (fromUserId == null ? "NO fromUserId SUPPLIED" : fromUserId));
        
        return new ResponseEntity(kudosService.findAllKudosFromUser(fromUserId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/toUser/all/{toUserId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosToUser(@PathVariable String toUserId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/kudos/toUser/all/{toUserId}] toUserId: " + (toUserId == null ? "NO toUserId SUPPLIED" : toUserId));
        
        return new ResponseEntity(kudosService.findAllKudosToUser(toUserId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/cat/all/{catId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosForCat(@PathVariable String catId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/kudos/cat/all/{catId}] catId: " + (catId == null ? "NO catId SUPPLIED" : catId));
        
        return new ResponseEntity(kudosService.findAllKudosForCat(catId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create/{fromUserId}/{toUserId}/{kudosCatId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createKudos(@PathVariable String fromUserId, @PathVariable String toUserId, @PathVariable String kudosCatId, @RequestBody(required = false) Kudos kudos)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("[/v1/kudos/create/{fromUserId}/{toUserId}/{kudosCatId}] fromUserId: " + (fromUserId == null ? "NO fromUserId SUPPLIED" : fromUserId));
            LOG.debug("[/v1/kudos/create/{fromUserId}/{toUserId}/{kudosCatId}] toUserId: " + (toUserId == null ? "NO toUserId SUPPLIED" : toUserId));
            LOG.debug("[/v1/kudos/create/{fromUserId}/{toUserId}/{kudosCatId}] kudosCatId: " + (kudosCatId == null ? "NO kudosCatId SUPPLIED" : kudosCatId));
            LOG.debug("[/v1/kudos/create/{fromUserId}/{toUserId}/{kudosCatId}] kudos: " + (kudos == null ? "NO kudos OBJECT" : LogUtils.objectToJson(kudos)));
        }
        
        try
        {
            kudosService.validateCreateKudos(fromUserId, toUserId, kudosCatId, kudos);
            return new ResponseEntity(kudosService.saveKudos(fromUserId, toUserId, kudosCatId, kudos), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
}
