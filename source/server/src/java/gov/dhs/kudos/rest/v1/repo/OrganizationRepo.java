package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author bsuneson
 */
public interface OrganizationRepo extends MongoRepository<Organization, String>
{
    Organization findByOrgName(String orgName);
}
