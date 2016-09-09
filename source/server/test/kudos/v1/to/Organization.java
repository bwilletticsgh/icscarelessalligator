package kudos.v1.to;

import java.util.ArrayList;
import java.util.List;

/**
 * The entity representing a Organization object
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class Organization extends BaseEntity
{
    private String orgName;
    private List<User> users;
    private List<KudosCategory> kudosCategories;

    public Organization(String orgName, List<User> users, List<KudosCategory> kudosCategories) {
        this.orgName = orgName;
        this.users = users;
        this.kudosCategories = kudosCategories;
    }

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

    public List<KudosCategory> getKudosCategories()
    {
        return kudosCategories;
    }

    public void setKudosCategories(List<KudosCategory> kudosCategories)
    {
        this.kudosCategories = kudosCategories;
    }
    
    public void addKudosCat(KudosCategory kudosCat)
    {
        if(kudosCategories == null)
            kudosCategories = new ArrayList<>();
        
        kudosCategories.add(kudosCat);
    }
}
