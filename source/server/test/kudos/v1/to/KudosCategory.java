package kudos.v1.to;

/**
 * The entity representing a KudosCategory object
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class KudosCategory extends BaseEntity
{
    private String name;    
    private String desc;
    private String icon;
    private String color;

    public KudosCategory(String name, String desc, String icon, String color) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.color = color;
    }

    public KudosCategory(String name, String desc) 
    {
        this.name = name;
        this.desc = desc;
    }

    public KudosCategory(String name) 
    {
        this.name = name;
    }

    public KudosCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
    
    
}
