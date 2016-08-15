package gov.dhs.kudos.rest.v1.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class Organization extends BaseEntity
{
    @Indexed(unique=true)
    private String orgName;
    private List<String> userIds;

    public Organization(String orgName, List<String> userIds) {
        this.orgName = orgName;
        this.userIds = userIds;
    }

    public Organization(String orgName) {
        this.orgName = orgName;
    }

    public Organization() {
    }
    
    public void addUser(String userId){
        if(userIds == null)
            userIds = new ArrayList<>();
        
        userIds.add(userId);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
