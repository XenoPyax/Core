package io.github.xenopyax.core.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {

	public static String format(Number number) {
		if(number.longValue() < 1000) return number.toString();
	    char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
	    long numValue = number.longValue();
	    String value = NumberFormat.getNumberInstance(Locale.US).format(numValue);
	    Integer t = value.split(",").length-1;
	    Integer sep = value.indexOf(",");
	    if(value.substring(sep+1, sep+2).equals("0")) return value.substring(0, sep) + suffix[t];
	    return value.substring(0, sep+2).replace(",", ".") + suffix[t];
	}

}
