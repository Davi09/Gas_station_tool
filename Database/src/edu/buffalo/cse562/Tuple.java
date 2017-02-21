package edu.buffalo.cse562;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jsqlparser.statement.create.table.ColDataType;


public class Tuple {
	//personInfo pi
	private LinkedHashMap<String, Object> tuple = new LinkedHashMap<String, Object>(); 
	public Tuple(){
		
	}
	public Tuple (TableCreate ct, String[] line) {
		
		
		for (int i=0; i<ct.getType().size(); i++){
			
			ColDataType type = ct.getType().get(i);
			switch (type.toString()) {
            case "int":
            	int lineInt = Integer.parseInt(line[i]);
            	tuple.put(ct.getAttribues().get(i), lineInt);
                     break;
            case "double":  
            	double lineDouble = Double.parseDouble(line[i]);
            	tuple.put(ct.getAttribues().get(i), lineDouble);
                     break;
            case "string":  
            	String lineString = line[i];
            	tuple.put(ct.getAttribues().get(i), lineString);
                     break;
            case "date":  
            	String lineDate = line[i];
            	Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(lineDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
            	tuple.put(ct.getAttribues().get(i), date);
                     break;
            default: 
                     break;
			}
		}
		
		
		
		/*
		 * We will now transform the fields of the tuple into the real type of object: int, string, date...
		 * The second parameter of this class is an array of strings that will just contain the names of the real types:
		 * int for Integer, char for Character, string for String, date for DATE, double for double...
		 */

		
	}
	public LinkedHashMap<String, Object> getTuple(){
		return tuple;
	}
	public Object getValue(String colName){
		return tuple.get(colName);
	}
	public void addAggregate(String[] line){
		tuple.put(line[0], line[1]);
	}
	public void addTuple(Tuple t, String alias){
		for (Map.Entry<String, Object> entry : t.getTuple().entrySet()) {
			this.tuple.put(alias + "." + entry.getKey(), entry.getValue());
		}
		
	}
}
