package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.KudosCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author bsuneson
 */
public interface KudosCategoryRepo extends MongoRepository<KudosCategory, String>
{
    KudosCategory findByName(String name);
}
