package gov.dhs.kudos.rest.v1.to;

/**
 *
 * @author bsuneson
 */
public class UserLoginTO 
{
    private String email;
    private String password;

    public UserLoginTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLoginTO() {
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
