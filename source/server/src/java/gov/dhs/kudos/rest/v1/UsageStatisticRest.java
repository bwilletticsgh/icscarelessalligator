package gov.dhs.kudos.rest.v1;

import gov.dhs.kudos.rest.v1.service.KudosService;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Restful v1 endpoint for handling all usage statistic transactions
 * @author bsuneson
 */
@RestController
@RequestMapping(value = "/v1/usage")
public class UsageStatisticRest 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(UsageStatisticRest.class);
    
    /** The service layer for logic **/
    @Autowired 
    private KudosService kudosService;
    
    public UsageStatisticRest()
    {
        
    }
    
    /**
     * Endpoint for retrieving all usage statistics
     * @param fromDate Filter for query (RequestParam) - not required
     * @param toDate Filter for query (RequestParam) - not required
     * @return All usage statistics
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllUsageStats(@RequestParam(required = false, value = "fromDate") @DateTimeFormat(iso = ISO.DATE) Date fromDate, 
                                           @RequestParam(required = false, value = "toDate") @DateTimeFormat(iso = ISO.DATE) Date toDate)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/usage/all]");
        
        return new ResponseEntity(kudosService.findAllUsageStats(fromDate, toDate), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving usage statistics for a user
     * @param fromDate Filter for query (RequestParam) - not required
     * @param toDate Filter for query (RequestParam) - not required
     * @param email PathVariable for a users email
     * @return All usage statistics for a user
     */
    @RequestMapping(value = "/byEmail/{email}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsageStatByUserId(@RequestParam(required = false, value = "fromDate") @DateTimeFormat(iso = ISO.DATE) Date fromDate, 
                                               @RequestParam(required = false, value = "toDate") @DateTimeFormat(iso = ISO.DATE) Date toDate,
                                               @PathVariable("email") String email)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/usage/byEmail/{email}]");
        
        return new ResponseEntity(kudosService.findAllUsageStatsByEmail(email, fromDate, toDate), HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving usage statistics for a uri
     * @param fromDate Filter for query (RequestParam) - not required
     * @param toDate Filter for query (RequestParam) - not required
     * @param uri PathVariable for a uri - must be URLEncoded
     * @return All usage statistics for a uri
     */
    @RequestMapping(value = "/byUri/{uri}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsageStatByURI(@RequestParam(required = false, value = "fromDate") @DateTimeFormat(iso = ISO.DATE) Date fromDate, 
                                            @RequestParam(required = false, value = "toDate") @DateTimeFormat(iso = ISO.DATE) Date toDate,
                                            @PathVariable("uri") String uri)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("[/v1/usage/byUri/{uri}]");
        
        return new ResponseEntity(kudosService.findAllUsageStatsByUri(uri, fromDate, toDate), HttpStatus.OK);
    }
}
