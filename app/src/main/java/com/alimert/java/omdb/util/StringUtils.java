package com.alimert.java.omdb.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

public class StringUtils {

    public static SpannableStringBuilder getFormattedString(Context context, @StringRes int sResource, String content) {
        String textContent = isNotBlank(content) ? content : "N/A";
        String formattedString = String.format(context.getString(sResource), textContent);
        int boldLength = formattedString.length() - content.length();
        SpannableStringBuilder result = new SpannableStringBuilder(formattedString);
        result.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0,
                boldLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
                return true;
            }
        for (int i = 0; i < strLen; i++) {
                if ((Character.isWhitespace(str.charAt(i)) == false)) {
                        return false;
                    }
            }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
