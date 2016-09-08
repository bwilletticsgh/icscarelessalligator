/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.dhs.kudos.rest.v1.to;

/**
 *
 * @author tdickerson
 */
public class SearchResultTO
{
    private String title;
    private String description;
    private String type;
    private String id;
    private String iconOrImage;
    private String color;


    public SearchResultTO(String title, String description, String type, String id, String iconOrImage, String color)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        this.id = id;
        this.iconOrImage = iconOrImage;
        this.color = color;
    }
    
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIconOrImage() {
        return iconOrImage;
    }

    public void setIconOrImage(String iconOrImage) {
        this.iconOrImage = iconOrImage;
    }

    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }
    
    
}
