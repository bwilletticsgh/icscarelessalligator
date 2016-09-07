package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.KudosSubComment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author bsuneson
 */
public interface KudosSubCommentRepo extends MongoRepository<KudosSubComment, String>
{
    
}
