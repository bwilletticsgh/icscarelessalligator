package gov.dhs.kudos.rest.v1.repo.listener;

import com.mongodb.DBObject;
import gov.dhs.kudos.rest.v1.model.BaseEntity;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

/**
 * Keep track of when records are created and modified
 * @author tdickerson
 */
public class BeforeSaveListener extends AbstractMongoEventListener<BaseEntity>
{
    /**
     * Auto generate dateCreated and dateModified upon saving
     * @param source The BaseEntity
     * @param dbo The actual object being saved
     */
    @Override
    public void onBeforeSave(BaseEntity source, DBObject dbo)
    {
        Date now = new Date();
        
        if(source.getDateCreated() == null)
            dbo.put("dateCreated", now);
        
        dbo.put("dateModified", now);
    }    
}
