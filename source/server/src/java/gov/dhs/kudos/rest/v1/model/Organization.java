package gov.dhs.kudos.rest.v1.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * The entity representing a Organization object
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class Organization extends BaseEntity
{
    @Indexed(unique=true)
    private String orgName;
    @DBRef
    private List<KudosCategory> kudosCategories;

    public Organization(String orgName, List<KudosCategory> kudosCategories) {
        this.orgName = orgName;
        this.kudosCategories = kudosCategories;
    }

    public Organization(String orgName) {
        this.orgName = orgName;
    }

    public Organization() {
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
