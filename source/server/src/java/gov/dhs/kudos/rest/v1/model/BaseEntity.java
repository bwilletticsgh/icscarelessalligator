package gov.dhs.kudos.rest.v1.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 *
 * @author bsuneson
 */
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable
{
    @Id
    private String id;
    private Date dateCreated;
    private Date dateModified;
    
    public String getId() 
    {
        return id;
    }
    
    public void setId(String id) 
    {
        this.id = id;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified()
    {
        return dateModified;
    }

    public void setDateModified(Date dateModified)
    {
        this.dateModified = dateModified;
    }
    
    @Override
    public int hashCode() 
    {
        return (id == null) ? 0 : id.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) 
    {
        if(this == obj) 
            return true;
        if(obj == null) 
            return false;
        if(getClass() != obj.getClass()) 
            return false;
        
        BaseEntity other = (BaseEntity) obj;
        if(id == null) 
            return other.id == null;
        
        return id.equals(other.id);
    }
}
