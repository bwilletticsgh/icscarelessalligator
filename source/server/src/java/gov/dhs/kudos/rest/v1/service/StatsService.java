package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.model.UsageStatistic;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author bsuneson
 */
public class StatsService extends WiredService
{
    private static final Logger LOG = Logger.getLogger(StatsService.class);
    
    public StatsService()
    {
        
    }
    
    public List<UsageStatistic> findAllUsageStats(Date fromDate, Date toDate)
    {
        if(fromDate != null)
            return usageStatisticRepo.findByDateCreatedBetween(fromDate, (toDate == null ? new Date() : toDate));
        else
            return usageStatisticRepo.findAll();
    }
    
    public List<UsageStatistic> findAllUsageStatsByEmail(String email, Date fromDate, Date toDate)
    {
//        if(fromDate != null)
//            return usageStatisticRepo.findByDateCreatedBetween(fromDate, (toDate == null ? new Date() : toDate), email);
//        else
            return usageStatisticRepo.findByUser(email);
    }
    
    public List<UsageStatistic> findAllUsageStatsByUri(String uri, Date fromDate, Date toDate)
    {
//        if(fromDate != null)
//            return usageStatisticRepo.findByDateCreatedBetween(fromDate, (toDate == null ? new Date() : toDate), email);
//        else
            return usageStatisticRepo.findByUri(uri);
    }
}
