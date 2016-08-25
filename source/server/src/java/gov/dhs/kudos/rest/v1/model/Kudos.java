package gov.dhs.kudos.rest.v1.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * The entity representing a Kudos object
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class Kudos extends BaseEntity
{
    @DBRef
    private KudosCategory kudosCat;
    @DBRef
    private User fromUser;
    @DBRef
    private User toUser;
    
    private String comments;

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
}
