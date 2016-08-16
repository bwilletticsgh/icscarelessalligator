package gov.dhs.kudos.rest.v1.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class Organization extends BaseEntity
{
    @Indexed(unique=true)
    private String orgName;
    @DBRef
    private List<User> users;

    public Organization(String orgName, List<User> users) {
        this.orgName = orgName;
        this.users = users;
    }

    public Organization(String orgName) {
        this.orgName = orgName;
    }

    public Organization() {
    }
    
    public void addUser(User user){
        if(users == null)
            users = new ArrayList<>();
        
        users.add(user);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
