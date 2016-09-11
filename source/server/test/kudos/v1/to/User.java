package kudos.v1.to;



/**
 * The entity representing a User object
 * @author bsuneson
 */
public class User extends BaseEntity
{ 
    private String email;
    private String firstName;
    private String lastName;    
    private String password;
    private String avatarUrl;
    private boolean isAdmin;
    private boolean isHr;
    private boolean isDeleted;
    private Organization organization;

    public User(String email, String firstName, String lastName, String password, String avatarUrl, boolean isAdmin, boolean isDeleted, Organization organization) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
        this.organization = organization;
    }

    public User(String email, String firstName, String lastName, String password, boolean isAdmin) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    public boolean isIsAdmin()
    {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isIsHr()
    {
        return isHr;
    }

    public void setIsHr(boolean isHr)
    {
        this.isHr = isHr;
    }
    
    
}
