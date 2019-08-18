package com.buzzer.sqlbuilder.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuzzerUtil {

    public static List<String> getMatches(String source, String regex)
    {
        List<String> extracts=null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            if(ObjectUtils.isEmpty(extracts))
            {
                extracts=new ArrayList<>();
            }
            extracts.add(matcher.group());
        }
        return extracts;

    }

    public static String replaceAllInStringBuilder(StringBuilder source,String regex,String replacement)
    {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        return m.replaceAll(replacement);
    }
}
