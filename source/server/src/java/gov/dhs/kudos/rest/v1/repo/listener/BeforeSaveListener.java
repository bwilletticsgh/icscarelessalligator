/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.dhs.kudos.rest.v1.repo.listener;

import com.mongodb.DBObject;
import gov.dhs.kudos.rest.v1.model.BaseEntity;
import java.sql.Timestamp;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

/**
 *
 * @author tdickerson
 * 
 * Keep track of when records are created and modified
 */
public class BeforeSaveListener extends AbstractMongoEventListener<BaseEntity>
{
    @Override
    public void onBeforeSave(BaseEntity source, DBObject dbo)
    {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        if(source.getDateCreated() == null)
            source.setDateCreated(now);
        
        source.setDateModified(now);
        
        super.onBeforeSave(source, dbo);
    }
    
}
