package kudos.v1.to;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bsuneson
 */
public class EmailNotificationTO 
{
    private final String from;
    private final String comments;
    private final String subject;
    private List<String> toList;

    public EmailNotificationTO(String from, String comments, String subject, List<String> toList) {
        this.from = from;
        this.comments = comments;
        this.subject = subject;
        this.toList = toList;
    }

    public EmailNotificationTO(String from, String comments, String subject) {
        this.from = from;
        this.comments = comments;
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public String getComments() {
        return comments;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getToList() {
        return toList;
    }
    
    public void addTo(String email)
    {
        if(toList == null)
            toList = new ArrayList<>();
        
        toList.add(email);
    }
}
