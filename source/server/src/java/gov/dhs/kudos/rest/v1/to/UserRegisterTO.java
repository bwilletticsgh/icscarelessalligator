package gov.dhs.kudos.rest.v1.to;

/**
 *
 * @author bsuneson
 */
public class UserRegisterTO 
{
    private String email;
    private String firstName;
    private String lastName;    
    private String password;
    private String orgId;
    private String startDate;

    public UserRegisterTO(String email, String firstName, String lastName, String password, String orgId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.orgId = orgId;
    }

    public UserRegisterTO() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
