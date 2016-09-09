package kudos.v1.to;

/**
 *
 * @author bsuneson
 */
public class UserUpdateAccountTO 
{
    private String userId;
    private String email;
    private String password;

    public UserUpdateAccountTO(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public UserUpdateAccountTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
