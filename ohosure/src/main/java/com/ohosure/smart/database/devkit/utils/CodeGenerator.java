package com.ohosure.smart.database.devkit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGenerator {

	public static void generateProviderMetaData(StringBuilder strBuilder) {

		String temString;
		Pattern p = Pattern.compile("CREATE(.*?)INTEGER\\);", Pattern.MULTILINE
				| Pattern.DOTALL);
		Pattern pin = Pattern.compile("\\[(.*?)\\]", Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher m = p.matcher(strBuilder.toString());
		Matcher min;
		StringBuilder resulBuilder = new StringBuilder();
		while (m.find()) {
			temString = m.group();
			min = pin.matcher(temString);

			while (min.find()) {
				String item = min.group().replace("[", "").replace("]", "");
				if (item.startsWith("dt_")) {
					String tablename = item;
					item = item.replace("dt_", "");
					resulBuilder.append("public static class "
							+ item.substring(0, 1).toUpperCase()
							+ item.substring(1) + " { ");
					resulBuilder.append("public final static String TABLE = \""
							+ tablename + "\"; ");
					resulBuilder
							.append("public final static Uri CONTENT_URI = Uri.parse(\"content://\"+ AUTHORITY + \"/\" + TABLE); ");
				} else if (item.startsWith("pt_")) {
					String tablename = item;
					item = item.replace("pt_", "");
					resulBuilder.append("public static class "
							+ item.substring(0, 1).toUpperCase()
							+ item.substring(1) + " { ");
					resulBuilder.append("public final static String TABLE = \""
							+ tablename + "\"; ");
					resulBuilder
							.append("public final static Uri CONTENT_URI = Uri.parse(\"content://\"+ AUTHORITY + \"/\" + TABLE); ");
				} else {
					resulBuilder.append("public final static String "
							+ item.toUpperCase() + "=\"" + item + "\" ;");
				}
			}
			resulBuilder.append("}");
		}

		System.out.println(resulBuilder.toString());
		
	}
}
