package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.Kudos;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.KudosService;
import gov.dhs.kudos.rest.v1.to.KudosOneToManyTO;
import gov.dhs.kudos.rest.v1.to.SubKudoCommentTO;
import gov.dhs.kudos.rest.v1.util.LogUtils;
import javax.annotation.PostConstruct;
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
 * Restful v1 endpoint for handling all kudos transactions
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/kudos")
public class KudosRest 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(KudosRest.class);
    
    /** The service layer for logic **/
    @Autowired 
    private KudosService kudosService;
    
    public KudosRest()
    {
        
    }    
    
    @PostConstruct
    public void initData()
    {
        kudosService.initAdminData();
    }
    
    /**
     * Endpoint for retrieving all kudos from a user
     * @param fromUserId The PathVariable of the users id
     * @return All kudos from a user
     */
    @RequestMapping(value = "/fromUser/all/{fromUserId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosFromUser(@PathVariable String fromUserId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/kudos/fromUser/all/{fromUserId}] fromUserId: " + (fromUserId == null ? "NO fromUserId SUPPLIED" : fromUserId));
        
        return new ResponseEntity(kudosService.findAllKudosFromUser(fromUserId), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving all kudos to a user
     * @param toUserId The PathVariable of the users id
     * @return All kudos to a user
     */
    @RequestMapping(value = "/toUser/all/{toUserId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosToUser(@PathVariable String toUserId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/kudos/toUser/all/{toUserId}] toUserId: " + (toUserId == null ? "NO toUserId SUPPLIED" : toUserId));
        
        return new ResponseEntity(kudosService.findAllKudosToUser(toUserId), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving all kudos for a category
     * @param catId The PathVariable of the category id
     * @return All kudos for a category
     */
    @RequestMapping(value = "/cat/all/{catId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getKudosForCat(@PathVariable String catId)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/kudos/cat/all/{catId}] catId: " + (catId == null ? "NO catId SUPPLIED" : catId));
        
        return new ResponseEntity(kudosService.findAllKudosForCat(catId), HttpStatus.OK);
    }
    
    /**
     * Endpoint for giving a kudo to a user
     * @param fromUserId The PathVariable of a user id
     * @param toUserId The PathVariable of a user id
     * @param kudosCatId The PathVariable of a category id
     * @param kudos The RequestBody object for a kudos - must contain comments
     * @return The created Kudo object
     */
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
    
    /**
     * Endpoint for giving a kudo to users
     * @param fromUserId The PathVariable of a user id
     * @param kudosCatId The PathVariable of a category id
     * @param kudosOneToMany The RequestBody object for a kudosOneToMany - must contain a kudos comment and users
     * @return The created Kudo object
     */
    @RequestMapping(value = "/oneToMany/create/{fromUserId}/{kudosCatId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createKudosOneToMany(@PathVariable String fromUserId, @PathVariable String kudosCatId, @RequestBody(required = false) KudosOneToManyTO kudosOneToMany)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("[/v1/kudos/oneToMany/create/{fromUserId}/{kudosCatId}] fromUserId: " + (fromUserId == null ? "NO fromUserId SUPPLIED" : fromUserId));
            LOG.debug("[/v1/kudos/oneToMany/create/{fromUserId}/{kudosCatId}] kudosCatId: " + (kudosCatId == null ? "NO kudosCatId SUPPLIED" : kudosCatId));
            LOG.debug("[/v1/kudos/oneToMany/create/{fromUserId}/{kudosCatId}] kudos: " + (kudosOneToMany == null ? "NO kudosOneToMany OBJECT" : LogUtils.objectToJson(kudosOneToMany)));
        }
        
        try
        {
            kudosService.validateCreateKudosOneToMany(fromUserId, kudosCatId, kudosOneToMany);
            return new ResponseEntity(kudosService.saveKudosOneToMany(fromUserId, kudosCatId, kudosOneToMany), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
    
    /**
     * Endpoint for giving a kudo to users
     * @param kudosId The id of the Kudos object
     * @param subComment The sub-comment to append to the kudos object
     * @param request
     * @return The created Kudo object
     */
    @RequestMapping(value = "/subComment/{kudosId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity subComment(@PathVariable String kudosId, @RequestBody(required = false) SubKudoCommentTO subComment, HttpServletRequest request)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("[/v1/kudos/subComment/{kudosId}] kudosId: " + (kudosId == null ? "NO kudosId SUPPLIED" : kudosId));
            LOG.debug("[/v1/kudos/subComment/{kudosId}] subComment: " + (subComment == null ? "NO subComment OBJECT" : LogUtils.objectToJson(subComment)));
        }
        
        try
        {
            kudosService.validateUpdateKudosAppendSubComment(kudosId, subComment, request);
            return new ResponseEntity(kudosService.saveKudosAppendComment(kudosId, subComment, (User) request.getAttribute("kudosUser")), HttpStatus.OK);
        }
        catch(KudosException e)
        {
            LOG.error(e);
            return new ResponseEntity("error: " + e.getMessage(), e.getHttpStatus());
        }
    }
}
