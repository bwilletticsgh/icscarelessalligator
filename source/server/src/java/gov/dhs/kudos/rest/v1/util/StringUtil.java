package gov.dhs.kudos.rest.v1.util;

/**
 * Utility class for String operations
 * @author tdickerson
 */
public class StringUtil
{
    /**
     * Regexifies a string
     * @param string The String to regexify
     * @return A regexified String
     */
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
