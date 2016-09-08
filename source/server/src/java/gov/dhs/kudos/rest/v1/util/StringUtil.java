package gov.dhs.kudos.rest.v1.util;

/**
 *
 * @author tdickerson
 */
public class StringUtil
{
    public static String regexify(String string)
    {
        String resultString = string;
        if(string != null && string.length() > 0)
        {
            string = string.replaceAll("\\s+", ".+");
            resultString = "^.*" + string + ".*$";
        }
        
        return resultString;
    }
}
