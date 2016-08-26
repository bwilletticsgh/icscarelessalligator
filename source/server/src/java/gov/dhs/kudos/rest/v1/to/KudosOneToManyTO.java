package gov.dhs.kudos.rest.v1.to;

import java.util.List;

/**
 *
 * @author bsuneson
 */
public class KudosOneToManyTO 
{
    private List<String> userIds;
    private String comments;    

    public KudosOneToManyTO(List<String> userIds, String comments) {
        this.userIds = userIds;
        this.comments = comments;
    }

    public KudosOneToManyTO() {
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
