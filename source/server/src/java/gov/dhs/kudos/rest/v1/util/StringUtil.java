/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
