package gov.dhs.kudos.rest.v1.to;

/**
 *
 * @author bsuneson
 */
public class SubKudoCommentTO 
{
    private String comment;

    public SubKudoCommentTO(String comment) {
        this.comment = comment;
    }

    public SubKudoCommentTO() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
