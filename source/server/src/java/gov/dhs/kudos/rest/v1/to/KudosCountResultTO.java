package gov.dhs.kudos.rest.v1.to;

import gov.dhs.kudos.rest.v1.model.User;

/**
 *
 * @author bsuneson
 */
public class KudosCountResultTO 
{
    private User user;
    private Long count;

    public KudosCountResultTO(User user, Long count) {
        this.user = user;
        this.count = count;
    }

    public KudosCountResultTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
