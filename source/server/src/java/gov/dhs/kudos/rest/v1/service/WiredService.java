package gov.dhs.kudos.rest.v1.service;

import gov.dhs.kudos.rest.v1.notification.EmailNotification;
import gov.dhs.kudos.rest.v1.repo.KudosCategoryRepo;
import gov.dhs.kudos.rest.v1.repo.KudosRepo;
import gov.dhs.kudos.rest.v1.repo.OrganizationRepo;
import gov.dhs.kudos.rest.v1.repo.UsageStatisticRepo;
import gov.dhs.kudos.rest.v1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service layer for auto-wiring mongodb repositories
 * @author bsuneson
 */
public class WiredService
{
    /** The organization mongo repository **/
    @Autowired 
    protected OrganizationRepo organizationRepo;
    /** The user mongo repository **/
    @Autowired 
    protected UserRepo userRepo;
    /** The kudos category mongo repository **/
    @Autowired
    protected KudosCategoryRepo kudosCatRepo;
    /** The kudos mongo repository **/
    @Autowired    
    protected KudosRepo kudosRepo;
    /** The usage statistics mongo repository **/
    @Autowired
    protected UsageStatisticRepo usageStatisticRepo;
    /** The notification layer **/
    @Autowired
    protected EmailNotification emailNotifier;
}
