package com.ohosure.smart.database.devkit.sqlite;

public interface ProviderParam {

	Class<?> getChildClass();

	Class<?> getChildMetaDataClass();

	Object getChildMetaData();

	/*
	 * return the implementation class tag.
	 */
	String getChildTag();
}
