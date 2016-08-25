package gov.dhs.kudos.rest.v1.repo;

import gov.dhs.kudos.rest.v1.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The mongo repository interface for Organization queries
 * @author bsuneson
 */
public interface OrganizationRepo extends MongoRepository<Organization, String>
{
    Organization findByOrgName(String orgName);
    Organization findByOrgNameIgnoreCase(String orgName);
}
