package egovframework.common.utils;

import java.lang.reflect.Field;
import java.util.*;

public class ConvertUtil{
	private ConvertUtil(){}
	
	public static List<Map<String, String>> getColumnInfo(Class<?> clazz){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(clazz==null) return new ArrayList<Map<String, String>>();
		Field[] fields = clazz.getDeclaredFields();
		for(int i=0; i<fields.length; i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("NAME", fields[i].getName());
			map.put("TYPE", fields[i].getType().getName());
			list.add(map);
		}
		return list;
	}
	
	public static short getDatasetType(String type) {
		short returnType = 1;
		String t = type;
		if (t.equals((java.lang.Long.class).getName()))
			returnType = 11;
		else if (t.equals((java.lang.Integer.class).getName()))
			returnType = 2;
		else if (t.equals((java.lang.Character.class).getName()))
			returnType = 12;
		else if (t.equals((java.lang.Float.class).getName())
				|| t.equals((java.lang.Double.class).getName()))
			returnType = 4;
		else if (t.equals((java.math.BigDecimal.class).getName()))
			returnType = 4;
		else if (t.equals("[B"))
			returnType = 9;
		else
			returnType = 1;
		return returnType;
	}	
}