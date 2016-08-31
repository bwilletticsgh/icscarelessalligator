package gov.dhs.kudos.rest.v1.notification;

import gov.dhs.kudos.rest.v1.to.EmailNotificationTO;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 * Notification service responsible for handling / transporting a queued concurrent list of email
 * @author bsuneson
 */
public class EmailNotification
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(EmailNotification.class);
    
    /** The concurrent queued list containing EmailNotificationTO objects **/
    private final ConcurrentLinkedQueue<EmailNotificationTO> emailQueue = new ConcurrentLinkedQueue<>();
    /** The thread scheduler for probing the queue **/
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    private final String kudosFrom;
    private final String smtpHost;
    private final String smtpPort;
    private final String smtpUser;
    private final String smtpAuth;

    public EmailNotification(String kudosFrom, String smtpHost, String smtpPort, String smtpUser, String smtpAuth) {
        this.kudosFrom = kudosFrom;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpUser = smtpUser;
        this.smtpAuth = smtpAuth;
    }
    
    @PostConstruct
    public void initSmtp()
    {
        scheduler.scheduleWithFixedDelay(new EmailTask(), 30, 30, TimeUnit.SECONDS);
    }
    
    /**
     * Queues an email for transport
     * @param emailTO The transfer object containing information of the message
     */
    public void queueEmail(EmailNotificationTO emailTO)
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Adding email notification to queue");
        
        emailQueue.add(emailTO);
    }
    
    /**
     * The thread schedulers periodic task to run
     */
    private class EmailTask implements Runnable
    {
        @Override
        public void run() 
        {
            Properties mailProp = new Properties();
            mailProp.put("mail.transport.protocol", "smtps");
            mailProp.put("mail.smtp.port", smtpPort);
            mailProp.put("mail.smtp.auth", "true");
            mailProp.put("mail.smtp.starttls.enable", "true");
            mailProp.put("mail.smtp.starttls.required", "true");
            
            Transport trans = null;
            EmailNotificationTO emailTO;
            while((emailTO = emailQueue.poll()) != null)
            {                
                for(String recipient : emailTO.getToList())
                {
                    if(LOG.isDebugEnabled())
                        LOG.debug("Sending email notification to: " + recipient);
                    
                    try
                    {
                        Session mailSession = Session.getDefaultInstance(mailProp);
                        MimeMessage message = new MimeMessage(mailSession);
                        trans = mailSession.getTransport();
                        
                        message.setFrom(new InternetAddress(kudosFrom));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                        message.setSubject(emailTO.getSubject());
                        message.setContent(emailTO.getComments(), "text/plain");
                        
                        trans.addTransportListener(new EmailTransportListener());
                        trans.connect(smtpHost, smtpUser, smtpAuth);
                        trans.sendMessage(message, message.getAllRecipients());
                    }
                    catch(MessagingException e)
                    {
                        LOG.error(e);
                    }
                    finally
                    {
                        safeCloseTrans(trans);
                    }
                }
            }
        }
        
        private void safeCloseTrans(Transport trans)
        {
            try
            {
                if(trans != null) trans.close();
            }
            catch(MessagingException e)
            {
                LOG.error(e);
            }
        }
    }
}
