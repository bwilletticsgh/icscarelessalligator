package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.Kudos;
import gov.dhs.kudos.rest.v1.model.KudosCategory;
import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author bsuneson
 */
public interface KudosRepo extends MongoRepository<Kudos, String>
{
    List<Kudos> findByFromUser(User fromUser);
    List<Kudos> findByToUser(User toUser);
    List<Kudos> findByKudosCat(KudosCategory kudosCat);
}
