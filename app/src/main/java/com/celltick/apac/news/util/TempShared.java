package com.celltick.apac.news.util;

import java.util.HashMap;
import java.util.Map;

public class TempShared {
	private static TempShared instance = new TempShared();
	
	private TempShared()
	{
		map_string_string = new HashMap<String,String>();
		map_string_string.clear();
	}
	
	public static TempShared getInstance()
	{
		return instance;
	}
	
	public void SetKey(String key, String value)
	{
		map_string_string.put(key, value);
	}
	
	public void SetKey(String key, long value)
	{
		map_string_string.put(key, String.valueOf(value));
	}
	
	public String GetKey(String key)
	{
		String string = null;
		try 
		{
			string =  map_string_string.get(key);
		} 
		catch (Exception e) 
		{
			return null;
		}
		if(string == null)	return "";
		return string;
	}
	
	public int GetKeyInt(String key)
	{
		try {
			String string = GetKey(key);
			return Integer.parseInt(string);
		} catch (Exception e) {
			return -1;
		}
	}

	private Map<String, String> map_string_string;
	
}