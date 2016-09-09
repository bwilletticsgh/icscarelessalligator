package kudos.v1.to;


/**
 * The entity representing the KudosSubComment
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class KudosSubComment extends BaseEntity
{
    private User fromUser;
    
    private String comments;

    public KudosSubComment(User fromUser, String comments) {
        this.fromUser = fromUser;
        this.comments = comments;
    }

    public KudosSubComment() {
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}