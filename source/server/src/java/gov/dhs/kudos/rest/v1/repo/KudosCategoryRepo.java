package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.KudosCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The mongo repository interface for KudosCategory queries
 * @author bsuneson
 */
public interface KudosCategoryRepo extends MongoRepository<KudosCategory, String>
{
    KudosCategory findByName(String name);
}
