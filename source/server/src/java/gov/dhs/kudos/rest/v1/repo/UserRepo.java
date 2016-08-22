package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The mongo repository interface for User queries
 * @author bsuneson
 */
public interface UserRepo extends MongoRepository<User, String>
{
    User findByEmail(String email);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
}
