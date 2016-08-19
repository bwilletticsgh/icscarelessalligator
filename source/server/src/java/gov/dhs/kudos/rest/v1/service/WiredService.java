package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.repo.KudosCategoryRepo;
import gov.dhs.kudos.rest.v1.repo.KudosRepo;
import gov.dhs.kudos.rest.v1.repo.OrganizationRepo;
import gov.dhs.kudos.rest.v1.repo.UsageStatisticRepo;
import gov.dhs.kudos.rest.v1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bsuneson
 */
public class WiredService
{
    @Autowired 
    protected OrganizationRepo organizationRepo;
    @Autowired 
    protected UserRepo userRepo;
    @Autowired
    protected KudosCategoryRepo kudosCatRepo;
    @Autowired
    protected KudosRepo kudosRepo;
    @Autowired
    protected UsageStatisticRepo usageStatisticRepo;
}
