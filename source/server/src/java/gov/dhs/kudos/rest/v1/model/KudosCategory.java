package gov.dhs.kudos.rest.v1.model;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * The entity representing a KudosCategory object
 * @author bsuneson
 */
@SuppressWarnings("serial")
public class KudosCategory extends BaseEntity
{
    @Indexed(unique=true)
    private String name;    
    private String desc;
    private String icon;
    private String color;
    private int points;

    public KudosCategory(String name, String desc, String icon, String color, int points) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.color = color;
        this.points = points;
    }

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
