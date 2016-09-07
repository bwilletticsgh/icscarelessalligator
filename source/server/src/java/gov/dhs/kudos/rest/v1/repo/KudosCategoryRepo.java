package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.repo.query.SearchQuery;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * The mongo repository interface for KudosCategory queries
 * @author bsuneson
 */
public interface KudosCategoryRepo extends MongoRepository<KudosCategory, String>
{
    KudosCategory findByName(String name);
    KudosCategory findByNameIgnoreCase(String name);
    
    @Query(value = SearchQuery.KUDOS_CATEGORY_SEARCH_QUERY)
    List<KudosCategory> findBySearch(String search);
}
