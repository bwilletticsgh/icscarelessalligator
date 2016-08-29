package gov.dhs.kudos.rest.v1.notification;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import org.apache.log4j.Logger;

/**
 * Custom transport listener for email notifications.  Logs successful and problematic emails
 * @author bsuneson
 */
public class EmailTransportListener implements TransportListener
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(TransportListener.class);    

    @Override
    public void messageDelivered(TransportEvent te) 
    {
        try
        {
            String info = "Message SUCCESSFULLY delivered to " + getAddress(te.getMessage().getAllRecipients()) + " from " + getAddress(te.getMessage().getFrom());
            LOG.info(info);
        }
        catch(MessagingException e)
        {
            LOG.error(e);
        }
    }

    @Override
    public void messageNotDelivered(TransportEvent te) 
    {
        try
        {
            String error = "Message NOT delivered to " + getAddress(te.getMessage().getAllRecipients()) + " from " + getAddress(te.getMessage().getFrom());
            error += (". Invalid to addresses " + getAddress(te.getInvalidAddresses()));
            LOG.error(error);
        }
        catch(MessagingException e)
        {
            LOG.error(e);
        }
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent te) 
    {
        try
        {
            String error = "Message PARTIALLY delivered to " + getAddress(te.getValidSentAddresses()) + " from " + getAddress(te.getMessage().getFrom());
            error += (". Unset to " + getAddress(te.getValidUnsentAddresses()));
            error += (". Invalid to addresses " + getAddress(te.getInvalidAddresses()));
            
            LOG.error(error);
        }
        catch(MessagingException e)
        {
            LOG.error(e);
        }
    }
    
    private String getAddress(Address[] addresses)
    {
        String result = "[ ";
        
        if(addresses != null && addresses.length > 0)
        {
            for(Address address : addresses)
            {
                result += (address.toString() + " ");
            }
        }
        
        return result += ("]");
    }
}
