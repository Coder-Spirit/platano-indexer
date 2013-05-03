package com.bananity.settings;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author Andreu Correa Casablanca
 */
public class SettingsReader
{
	/**
	 *
	 */
	private final static Pattern barPattern = Pattern.compile("/");

	/**
	 *
	 */
	private final static Pattern fromPattern = Pattern.compile("\\A\\w+\\.from\\z");


	/**
	 *
	 */
	private Map<String, Object> settingsMap;

	/**
	 *
	 */
	public SettingsReader (final Map<String, Object> settingsMap) {
		this.settingsMap = settingsMap;
	}

	/**
	 *
	 */
	public LinkedHashMap<String, Object> getExpandedSettings (final String route) {
		return getExpandedSettings(route, settingsMap, new ArrayList<String>());
	}

	/**
	 *
	 */
	private LinkedHashMap<String, Object> getExpandedSettings (final String route, final Map<String, Object> baseMap, final ArrayList<String> actualPath) {
		final LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

		ArrayList<String> routeNodes = getExpandedRoute(route, actualPath);

		Map<String, Object> workMap = baseMap;
		for (int i=0, n=routeNodes.size(); i<n; i++) {
			workMap = (Map<String, Object>)workMap.get(routeNodes.get(i));
		}

		for (Map.Entry<String, Object> e : workMap.entrySet()) {
			if (fromPattern.matcher(e.getKey()).matches()) {

			} else {

			}
		}

		return result;
	}

	private ArrayList<String> getExpandedRoute (final String richRoute, final ArrayList<String> actualPath) {
		ArrayList<String> expandedRoute = null;

		if (richRoute.charAt(0) == '/') {

		} else if (richRoute.charAt(0) == '$') {

		} else {
			expandedRoute = new ArrayList<String>(actualPath);
			expandedRoute.addAll(Arrays.asList(barPattern.split(richRoute)));
		}

		return expandedRoute;
	}
}