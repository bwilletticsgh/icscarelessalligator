package gov.dhs.kudos.rest.v1.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
    private Timestamp dateCreated;
    private Timestamp dateModified;
    
    public String getId() 
    {
        return id;
    }
    
    public void setId(String id) 
    {
        this.id = id;
    }

    public Timestamp getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateModified()
    {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified)
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
