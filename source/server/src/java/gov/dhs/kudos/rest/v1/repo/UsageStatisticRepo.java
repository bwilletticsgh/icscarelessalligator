package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.UsageStatistic;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author tdickerson
 */
public interface UsageStatisticRepo extends MongoRepository<UsageStatistic, String>
{
    List<UsageStatistic> findByDateCreatedBetween(Date from, Date to);
    List<UsageStatistic> findByUser(String user);
    List<UsageStatistic> findByUri(String uri);
}
