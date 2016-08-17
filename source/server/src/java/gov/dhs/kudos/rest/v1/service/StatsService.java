package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author bsuneson
 */
@Service
public class StatsService 
{
    private static final Logger LOG = Logger.getLogger(StatsService.class);
    
    public StatsService()
    {
        
    }

    public void record(String uri, User user) 
    {
        try
        {
            
        }
        catch(Exception e)
        {
            LOG.error(e);
        }
    }
}
