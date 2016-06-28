package com.aus.nlp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    private static final Pattern apostropheWithS = Pattern.compile("'s");

    public static Matcher getApostropheWithSMatcher(CharSequence target) {
        return apostropheWithS.matcher(target);
    }
}
