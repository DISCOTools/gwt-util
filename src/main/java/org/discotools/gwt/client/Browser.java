/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.discotools.gwt.client;

import com.google.gwt.user.client.Window;

/**
 * Provides information about a browser (vendor,version,operating system,etc...)
 * based on user agent and other easily accessible information.
 * 
 * This is not meant to be a "detect script" to implement browser workarounds,
 * but rather a "pretty printer" for the browser information.
 * 
 * This code is a derivation of Browser Detect v2.1.6 documentation:
 * http://www.dithered.com/javascript/browser_detect/index.html license:
 * http://creativecommons.org/licenses/by/1.0/ code by Chris Nott
 * (chris[at]dithered[dot]com)
 * 
 * It has been transliterated from JavaScript to Java with additional changes
 * along the way.
 */
public class Browser {

	public static final String LT = "lt";
	public static final String LTE = "lte";
	public static final String GT = "gt";
	public static final String GTE = "gte";
	public static final String EQ = "eq";
	public static final String NOT = "not";

	public static final String NETSCAPE = "Netscape";
	public static final String FIREFOX = "Firefox";
	public static final String MOZILLA = "Mozilla";
	public static final String IE = "IE";
	public static final String OPERA = "Opera";
	public static final String SAFARI = "Safari";
	public static final String KONQUEROR = "Konqueror";
	public static final String APPLE_WEB_KIT = "Apple WebKit";
	public static final String GECKO = "Gecko";
	public static final String ICAB = "Icab";
	public static final String OMNI_WEB = "OmniWeb";

	public static boolean isIE() {
		return isIE(Window.Navigator.getUserAgent());
	}

	public static boolean isIE(String userAgent) {
		userAgent = userAgent.toLowerCase();
		return userAgent.indexOf("msie") != -1 && !isOpera(userAgent)
				&& (userAgent.indexOf("webtv") == -1);
	}

	public static boolean isGecko() {
		return isGecko(Window.Navigator.getUserAgent());
	}

	public static boolean isGecko(String userAgent) {
		return userAgent.toLowerCase().indexOf("gecko") != -1
				&& userAgent.indexOf("safari") == -1;
	}

	public static boolean isFirefox() {
		return isFirefox(Window.Navigator.getUserAgent());
	}

	public static boolean isFirefox(String userAgent) {
		userAgent = userAgent.toLowerCase();
		return userAgent.indexOf("firefox/") != -1
				|| userAgent.indexOf("firebird/") != -1;
	}

	public static boolean isMozilla() {
		return isMozilla(Window.Navigator.getUserAgent());
	}

	public static boolean isMozilla(String userAgent) {
		return isGecko(userAgent)
				&& userAgent.indexOf("gecko/") + 14 == userAgent.length();
	}

	public static boolean isOpera() {
		return isOpera(Window.Navigator.getUserAgent());
	}

	public static boolean isOpera(String userAgent) {
		return userAgent.toLowerCase().indexOf("opera") != -1;
	}

	public static boolean isSafari() {
		return isSafari(Window.Navigator.getUserAgent());
	}

	public static boolean isSafari(String userAgent) {
		return userAgent.toLowerCase().indexOf("safari") != -1;
	}

	public static boolean isAppleWebKit() {
		return isAppleWebKit(Window.Navigator.getUserAgent());
	}

	public static boolean isAppleWebKit(String userAgent) {
		return userAgent.toLowerCase().indexOf("applewebkit") != -1;
	}

	public static boolean isKonqueror() {
		return isKonqueror(Window.Navigator.getUserAgent());
	}

	public static boolean isKonqueror(String userAgent) {
		return userAgent.toLowerCase().indexOf("konqueror") != -1;
	}

	public static boolean isNetscape() {
		return isNetscape(Window.Navigator.getUserAgent());
	}

	public static boolean isNetscape(String userAgent) {
		userAgent = userAgent.toLowerCase();
		return isGecko(userAgent) ? userAgent.indexOf("netscape") != -1
				: userAgent.indexOf("mozilla") != -1
						&& !(isOpera(userAgent) || isSafari(userAgent))
						&& userAgent.indexOf("spoofer") == -1
						&& userAgent.indexOf("compatible") == -1
						&& userAgent.indexOf("webtv") == -1
						&& userAgent.indexOf("hotjava") == -1;
	}

	public static boolean isIcab() {
		return isIcab(Window.Navigator.getUserAgent());
	}

	public static boolean isIcab(String userAgent) {
		return userAgent.toLowerCase().indexOf("icab") != -1;
	}

	public static boolean isOmniWeb() {
		return isOmniWeb(Window.Navigator.getUserAgent());
	}

	public static boolean isOmniWeb(String userAgent) {
		return userAgent.toLowerCase().indexOf("omniweb") != -1;
	}

	public static boolean isIECompatible() {
		return isIECompatible(Window.Navigator.getUserAgent());
	}

	public static boolean isIECompatible(String userAgent) {
		return userAgent.toLowerCase().indexOf("msie") != -1
				&& !isIE(userAgent);
	}

	public static boolean isNSCompatible() {
		return isNSCompatible(Window.Navigator.getUserAgent());
	}

	public static boolean isNSCompatible(String userAgent) {
		return userAgent.toLowerCase().indexOf("mozilla") != -1
				&& !(isNetscape(userAgent) || isMozilla(userAgent));
	}

	public static boolean isMatch(String condition) {
		return isMatch(Window.Navigator.getUserAgent(), condition);
	}

	public static boolean isMatch(String userAgent, String condition) {
		
		// Initialize
		condition = condition.toLowerCase();
		String args[] = condition.split(" ");

		// Condition sanity check
		if (args.length < 1)
			return false;

		// Negate browser?
		boolean negate = false;
		if (NOT.equals(args[0])) {
			String tmp[] = new String[3];
			tmp[0] = args[1];
			if(args.length>1) tmp[1] = args[2];
			if(args.length>2) tmp[2] = args[3];
			args = tmp;
			negate = true;
		} else if (args[0].startsWith("!")) {
			args[0] = args[0].substring(1);
			negate = true;
		}
		
		// Check browser condition
		boolean match = getBrowser(userAgent).toLowerCase().startsWith(args[0]);
		if (!match && args.length==1 || !(match || negate))
			return negate;

		// Version syntax sanity check
		if (args.length == 2 && !args[1].startsWith("!"))
			throw new IllegalArgumentException("Illegal syntax");
		
		// Negate version match?
		if (args.length == 2 && args[1].startsWith("!")) {
			args[1] = args[1].substring(1);
			return getVersion(userAgent).compareTo(args[1]) != 0;
		}
		
		// Calculate difference
		int delta = negate ? args[2].compareTo(getVersion(userAgent))
				: getVersion(userAgent).compareTo(args[2]);

		// Check version
		if (LT.equals(args[1]) || "<".equals(args[1]))
			return delta < 0;

		else if (LTE.equals(args[1]) || "<=".equals(args[1]))
			return delta <= 0;

		else if (GT.equals(args[1]) || ">".equals(args[1]))
			return delta > 0;

		else if (GTE.equals(args[1]) || ">=".equals(args[1]))
			return delta >= 0;

		else if (EQ.equals(args[1]) || "=".equals(args[1]))
			return delta == 0;

		return false;
	}

	public static String getVersion() {
		return getVersion(Window.Navigator.getUserAgent());
	}

	public static String getVersion(String userAgent) {

		String version = "";
		userAgent = userAgent.toLowerCase();

		// correct version number
		if (isGecko(userAgent) && !isMozilla(userAgent)) {
			version = userAgent.substring(userAgent.indexOf("/",
					userAgent.indexOf("gecko/") + 6) + 1);
		} else if (isMozilla(userAgent)) {
			version = userAgent.substring(userAgent.indexOf("rv:") + 3);
		} else if (isIE(userAgent)) {
			version = userAgent.substring(userAgent.indexOf("msie ") + 5);
		} else if (isKonqueror(userAgent)) {
			version = userAgent.substring(userAgent.indexOf("konqueror/") + 10);
		} else if (isSafari(userAgent)) {
			version = userAgent.substring(userAgent.lastIndexOf("safari/") + 7);
		} else if (isOmniWeb(userAgent)) {
			version = userAgent
					.substring(userAgent.lastIndexOf("omniweb/") + 8);
		} else if (isOpera(userAgent)) {
			version = userAgent.substring(userAgent.indexOf("opera") + 6);
		} else if (isIcab(userAgent)) {
			version = userAgent.substring(userAgent.indexOf("icab") + 5);
		}
		return toVersion(version);
	}

	/**
	 * Retrieves a "pretty" version of the browser version information.
	 * 
	 * @return A pretty-printed version of the browser including the a) vendor
	 *         b) version c) and operating system
	 */
	public static String getBrowser() {
		return getBrowser(Window.Navigator.getUserAgent());
	}

	/**
	 * Retrieves a "pretty" version of the browser version information.
	 * 
	 * @param userAgent
	 *            - The HTTP user agent string.
	 * @return A pretty-printed version of the browser including the a) vendor
	 *         b) version c) and operating system
	 */
	private static String getBrowser(String userAgent) {

		userAgent = userAgent.toLowerCase();

		// browser engine name
		boolean isGecko = isGecko(userAgent);
		boolean isAppleWebKit = isAppleWebKit(userAgent);

		// browser name
		boolean isKonqueror = isKonqueror(userAgent);
		boolean isSafari = isSafari(userAgent);
		boolean isOpera = isOpera(userAgent);
		boolean isIE = isIE(userAgent);
		boolean isMozilla = isMozilla(userAgent);
		boolean isFirefox = isFirefox(userAgent);
		boolean isNS = isNetscape(userAgent);

		// Correct version number
		String version = getVersion(userAgent);

		// dom support
		// boolean isDOM1 = (document.getElementById);
		// boolean isDOM2Event = (document.addEventListener &&
		// document.removeEventListener);

		// css compatibility mode
		// this.mode = document.compatMode ? document.compatMode : "BackCompat";

		// platform
		boolean isWin = userAgent.indexOf("win") != -1;
		// boolean isWin32 = isWin && userAgent.indexOf("95") != -1
		// || userAgent.indexOf("98") != -1
		// || userAgent.indexOf("nt") != -1
		// || userAgent.indexOf("win32") != -1
		// || userAgent.indexOf("32bit") != -1
		// || userAgent.indexOf("xp") != -1;

		boolean isMac = userAgent.indexOf("mac") != -1;
		boolean isUnix = userAgent.indexOf("unix") != -1
				|| userAgent.indexOf("sunos") != -1
				|| userAgent.indexOf("bsd") != -1
				|| userAgent.indexOf("x11") != -1;

		boolean isLinux = userAgent.indexOf("linux") != -1;

		// specific browser shortcuts
		/*
		 * this.isNS4x = (this.isNS && this.versionMajor == 4); this.isNS40x =
		 * (this.isNS4x && this.versionMinor < 4.5); this.isNS47x = (this.isNS4x
		 * && this.versionMinor >= 4.7); this.isNS4up = (this.isNS &&
		 * this.versionMinor >= 4); this.isNS6x = (this.isNS &&
		 * this.versionMajor == 6); this.isNS6up = (this.isNS &&
		 * this.versionMajor >= 6); this.isNS7x = (this.isNS &&
		 * this.versionMajor == 7); this.isNS7up = (this.isNS &&
		 * this.versionMajor >= 7);
		 * 
		 * this.isIE4x = (this.isIE && this.versionMajor == 4); this.isIE4up =
		 * (this.isIE && this.versionMajor >= 4); this.isIE5x = (this.isIE &&
		 * this.versionMajor == 5); this.isIE55 = (this.isIE &&
		 * this.versionMinor == 5.5); this.isIE5up = (this.isIE &&
		 * this.versionMajor >= 5); this.isIE6x = (this.isIE &&
		 * this.versionMajor == 6); this.isIE6up = (this.isIE &&
		 * this.versionMajor >= 6);
		 * 
		 * this.isIE4xMac = (this.isIE4x && this.isMac);
		 */

		String name = isGecko ? GECKO : isAppleWebKit ? APPLE_WEB_KIT
				: isKonqueror ? KONQUEROR : isSafari ? SAFARI : isOpera ? OPERA
						: isIE ? IE : isMozilla ? MOZILLA : isFirefox ? FIREFOX
								: isNS ? NETSCAPE : "";

		name += " "
				+ version
				+ " on "
				+ (isWin ? "Windows" : isMac ? "Mac" : isUnix ? "Unix"
						: isLinux ? "Linux" : "Unknown");

		return name;
	}

	// Reads the version from a string which begins with a version number
	// and contains additional character data
	private static String toVersion(String versionPlusCruft) {
		for (int index = 0; index < versionPlusCruft.length(); ++index) {
			char c = versionPlusCruft.charAt(index);
			if (c != '.' && !Character.isDigit(c)) {
				return versionPlusCruft.substring(0, index);
			}
		}
		return versionPlusCruft;
	}
}
