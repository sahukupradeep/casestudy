package com.user.util;
class Solution {
    public static final String[] COUNTRY = {"", "+*-", "+**-", "+***-"};
    public static String maskPII(String S) {
        int at = S.indexOf("@");
        // If S is an emial
        if (at > 0) {
            S = S.toLowerCase();
            return S.charAt(0) + "*****" + S.substring(at);
        }
        // If S is a phone number
        S = S.replaceAll("[^0-9]", "");
        return S.substring(S.length()-10,(S.length()+1-10)) + "********" + S.substring(S.length() - 1);
    }
    
    public static void main(String[] args) {
		System.out.println(maskPII("9999999999   "));
	}
}