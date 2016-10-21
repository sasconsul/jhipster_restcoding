package com.sasconsul.restcoding.utils;

public class RegistryUtils {

	public RegistryUtils() {
		// TODO Auto-generated constructor stub
	}

    public static long validateGenerateStringId(String s) {
        long ans = 0;

        if(s == null) {
            return 0;
        }

        for(int i=0; i <s.length(); i++) {
            if (i == 0) {
                ans = s.codePointAt(0);
            } else {
                if (s.codePointAt(i) == s.codePointAt(i-1)) {
                    ans += s.codePointAt(i);
                } else {
                    ans += s.codePointAt(i) + s.codePointAt(i-1);
                }
            }
        }

        return ans;
    }
}
