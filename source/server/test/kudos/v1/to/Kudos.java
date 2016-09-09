package kudos.v1.to;

import java.util.ArrayList;
import java.util.List;

/**
 * The entity representing a Kudos object
 * @author bsuneson
 */
public class Kudos extends BaseEntity
{
    private KudosCategory kudosCat;
    private User fromUser;
    private User toUser;
    private List<KudosSubComment> subComments;
    
    private String comments;

    public Kudos(KudosCategory kudosCat, User fromUser, User toUser, List<KudosSubComment> subComments, String comments) {
        this.kudosCat = kudosCat;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.subComments = subComments;
        this.comments = comments;
    }

    public Kudos(KudosCategory kudosCat, User fromUser, User toUser, String comments) {
        this.kudosCat = kudosCat;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.comments = comments;
    }

    public Kudos(String comments) {
        this.comments = comments;
    }

    public Kudos() {
    }

    public KudosCategory getKudosCat() {
        return kudosCat;
    }

    public void setKudosCat(KudosCategory kudosCat) {
        this.kudosCat = kudosCat;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<KudosSubComment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<KudosSubComment> subComments) {
        this.subComments = subComments;
    }
    
    public void addSubComment(KudosSubComment subComment){
        if(this.subComments == null)
            this.subComments = new ArrayList<>();
        
        this.subComments.add(subComment);
    }
}
