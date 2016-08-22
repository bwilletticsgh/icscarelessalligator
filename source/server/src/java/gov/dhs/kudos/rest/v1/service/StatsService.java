package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.model.UsageStatistic;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Service layer for handling logic for the Usage Statistics v1 endpoints
 * @author bsuneson
 */
public class StatsService extends EndpointValidation
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(StatsService.class);
    
    public StatsService()
    {
        
    }
    
    /**
     * Finds all usage statistic objects
     * @param fromDate Narrow the query - may be null
     * @param toDate Narrow the query - may be null
     * @return A List of usage statistic objects
     */
    public List<UsageStatistic> findAllUsageStats(Date fromDate, Date toDate)
    {
        if(fromDate != null)
            return usageStatisticRepo.findByDateCreatedBetween(fromDate, (toDate == null ? new Date() : toDate));
        else
            return usageStatisticRepo.findAll();
    }
    
    /**
     * Finds all usage statistics by email
     * @param email The email to search for
     * @param fromDate Narrow the query - may be null
     * @param toDate Narrow the query - may be null
     * @return A List of usage statistic objects
     */
    public List<UsageStatistic> findAllUsageStatsByEmail(String email, Date fromDate, Date toDate)
    {
        if(fromDate != null)
            return usageStatisticRepo.findByUserAndDate(email, fromDate, (toDate == null ? new Date() : toDate));
        else
            return usageStatisticRepo.findByUser(email);
    }
    
    /**
     * Finds all usage statistics by uri
     * @param uri The uri to search for
     * @param fromDate Narrow the query - may be null
     * @param toDate Narrow the query - may be null
     * @return A List of usage statistic objects
     */
    public List<UsageStatistic> findAllUsageStatsByUri(String uri, Date fromDate, Date toDate)
    {
        if(fromDate != null)
            return usageStatisticRepo.findByUriAndDate(uri, fromDate, (toDate == null ? new Date() : toDate));
        else
            return usageStatisticRepo.findByUri(uri);
    }
}
