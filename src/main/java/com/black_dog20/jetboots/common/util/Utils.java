package com.black_dog20.jetboots.common.util;

import java.util.Locale;

public class Utils {
	public static String format(int value) {
        if (value < 1000)
            return String.valueOf(value);
        
        return String.format(Locale.US, "%,d", value);
    }
}
