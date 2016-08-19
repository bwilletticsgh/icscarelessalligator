package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.UsageStatistic;
import gov.dhs.kudos.rest.v1.repo.query.UsageStatisticQuery;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author tdickerson
 */
public interface UsageStatisticRepo extends MongoRepository<UsageStatistic, String>
{
    List<UsageStatistic> findByDateCreatedBetween(Date from, Date to);
    List<UsageStatistic> findByUser(String user);
    List<UsageStatistic> findByUri(String uri);
    
    @Query(value = UsageStatisticQuery.FIND_BY_EMAIL_AND_DATE)
    List<UsageStatistic> findByUserAndDate(String user, Date from, Date to);
    
    @Query(value = UsageStatisticQuery.FIND_BY_URI_AND_DATE)
    List<UsageStatistic> findByUriAndDate(String uri, Date from, Date to);
}
