package gov.dhs.kudos.rest.v1.to;

/**
 *
 * @author bsuneson
 */
public class UserUpdateProfileTO 
{
    private String userId;
    private String firstName;
    private String lastName;
    private String avatarUrl;

    public UserUpdateProfileTO(String userId, String firstName, String lastName, String avatarUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarUrl = avatarUrl;
    }

    public UserUpdateProfileTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
