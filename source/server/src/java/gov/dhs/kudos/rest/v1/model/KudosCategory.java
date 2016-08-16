package gov.dhs.kudos.rest.v1.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class KudosCategory extends BaseEntity
{
    @DBRef
    private Organization org;
    private String name;    
    private String desc;

    public KudosCategory(String name, String desc, Organization org) 
    {
        this.name = name;
        this.desc = desc;
        this.org = org;
    }

    public KudosCategory(String name, Organization org) 
    {
        this.name = name;
        this.org = org;
    }

    public KudosCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
}
