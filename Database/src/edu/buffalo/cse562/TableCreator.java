package edu.buffalo.cse562;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;


public class TableCreator implements StatementVisitor{
	private LinkedHashMap<Integer, String> table;
	private String tableName;
	private HashMap<String,Integer> attributes = new HashMap<String,Integer>();
	private HashMap<Integer,String> type = new HashMap<Integer,String>();
	static int count;
	private int size;
	private BufferedReader br = null;
	boolean isDate;
	public TableCreator(){
		this.table = new LinkedHashMap<Integer, String>();
		setSize(0);
	}
	public TableCreator(String filename){
		this.table = new LinkedHashMap<Integer, String>();
		setSize(0);
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void tableCreate(CreateTable createTable){
		createTable.accept(this);
		
	}
	public void fillUp(){
		String line = null;
		
		String fields[] = null;
		StringBuilder sb;
		int count = 0;
		try {
			while((line = br.readLine()) != null){
				fields = line.split("\\|");
				int count2 = 0;
				    	for (Entry<Integer, String> entry : type.entrySet()) {
				    	    String value = entry.getValue();
				    	
				        /*switch (value.toLowerCase()) {
			            case "int":
			            	int lineInt = Integer.parseInt(fields[count2]);
			            	table.put(count,lineInt);
			                     break;
			            case "double":  
			            	double lineDouble = Double.parseDouble(fields[count2]);
			            	table.put(count,lineDouble);
			                     break;
			            case "string":  
			            	String lineString = fields[count2];
			            	table.put(count,lineString);
			                     break;
			            case "date":  
			            	String lineDate = fields[count2];
			            	Date date = null;
							try {
								date = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(lineDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							table.put(count,date);
			                     break;
			            case "char":
			            	String charString = fields[count2];
			            	table.put(count,charString);
			            break;
			            case "varchar":
			            	String vcharString = fields[count2];
			            	table.put(count,vcharString);
				            break;
			            case "decimal":
			            	double decDouble = Double.parseDouble(fields[count2]);
			            	table.put(count,decDouble);
				            break;
			            default: 
			                     break;
						}*/
				    	table.put(count,fields[count2]);
				        count2++;
				        count++;
				    }
				    setSize(getSize() + 1);
				    //table.add(tuple);
				}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void partFill(int size){
		String line = null;
		
		String fields[] = null;
		StringBuilder sb;
		int count = 0;
		try {
			while((line = br.readLine()) != null && size < count){
				fields = line.split("\\|");
				int count2 = 0;
				    	for (Entry<Integer, String> entry : type.entrySet()) {
				    	    String value = entry.getValue();
				    	
				       /* switch (value.toLowerCase()) {
			            case "int":
			            	int lineInt = Integer.parseInt(fields[count2]);
			            	table.put(count,lineInt);
			                     break;
			            case "double":  
			            	double lineDouble = Double.parseDouble(fields[count2]);
			            	table.put(count,lineDouble);
			                     break;
			            case "string":  
			            	String lineString = fields[count2];
			            	table.put(count,lineString);
			                     break;
			            case "date":  
			            	String lineDate = fields[count2];
			            	Date date = null;
							try {
								date = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(lineDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
			            	//tuple.put(i, date);
							table.put(count,date);
			                     break;
			            case "char":
			            	String charString = fields[count2];
			            	table.put(count,charString);
			            break;
			            case "varchar":
			            	String vcharString = fields[count2];
			            	table.put(count,vcharString);
				            break;
			            case "decimal":
			            	double decDouble = Double.parseDouble(fields[count2]);
			            	table.put(count,decDouble);
				            break;
			            default: 
			                     break;
						}*/
				        count2++;
				        count++;
				    }
				    setSize(getSize() + 1);
				    //table.add(tuple);
				}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTuple(int pos, String obj){
		table.put(pos,obj);
	}
	public void joinAttributes(HashMap<String,Integer> a1, HashMap<String,Integer> a2){
		HashMap<String,Integer> temp = new HashMap<String,Integer>();
		temp.putAll(a1);
		int size = a1.size();
		 int newsize = size + a2.size();
		 for (Entry<String, Integer> entry : a2.entrySet()) {
			 temp.put(entry.getKey(),entry.getValue() + size);
		 }
		 attributes = temp;
	}
	public void joinTypes(HashMap<Integer,String> t1, HashMap<Integer,String> t2){
		HashMap<Integer,String> temp = new HashMap<Integer,String>();
		temp.putAll(t1);
		int size = t1.size();
		 int newsize = size + t2.size();
		 for (Entry<Integer,String> entry : t2.entrySet()) {
			 temp.put(entry.getKey() + size, entry.getValue());
		 }
		 type = temp;
	}
	public void printOut(){
		for(int i = 0; i < getSize(); i++){
			boolean first = true;
			for(int j = 0; j < attributes.size(); j++){
				if(first == false)
					System.out.print("|");
				if(isDate){
					SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-M-dd");
					System.out.print(dt1.format(table.get(i * attributes.size() + j)));
				}else{
					System.out.print(table.get(i * attributes.size() + j).toString());
				}
				first = false;
			}
			System.out.print("\n");
		}
		
	}
	
	public void printOut(ArrayList <Integer> project){
		for(int i = 0; i < getSize(); i++){
			boolean first = true;
			for(int j = 0; j < attributes.size(); j++){
				if(project.contains(j)){
					if(first == false)
						System.out.print("|");
					if(isDate){
						SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-M-dd");
						System.out.print(dt1.format(table.get(i * attributes.size() + j)));
					}else{
						System.out.print(table.get(i * attributes.size() + j).toString());
					}
					first = false;
				}
				
			}
			System.out.print("\n");
		}
		
	}

	/*public LinkedList<LinkedHashMap<Integer, Object>> getTable(){
		return table;
	}*/
	public LinkedHashMap<Integer, String> getTable(){
		return table;
	}
	
	public boolean removeTupple(Tuple t){
		//return table.remove(t);
		return false;
	}
	@Override
	public void visit(Select arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Delete arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Update arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Insert arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Replace arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Drop arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Truncate arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(CreateTable createTable) {
		// TODO Auto-generated method stub
		tableName = createTable.getTable().toString();
		count = 0;
		for (Iterator iter = createTable.getColumnDefinitions().iterator(); iter.hasNext();) {
			ColumnDefinition colDef = (ColumnDefinition) iter.next();
			attributes.put(colDef.getColumnName().toString(),count);
			type.put(count,colDef.getColDataType().getDataType());
			//System.out.println(((ColumnDefinition) iter.next()).getColumnName());
			count++;
		}
	}
	public HashMap<Integer,String> getType() {
		return type;
	}

	public void setType(HashMap<Integer,String> type) {
		this.type = type;
	}

	public HashMap<String,Integer> getAttribues() {
		return attributes;
	}

	public void setAttribues(HashMap<String,Integer> attribues) {
		this.attributes = attribues;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Object getValue(LinkedHashMap<Integer, Object> tuple, int colNumber){
		return tuple.get(colNumber);
	}
	public LinkedHashMap<Integer, Object> getTuple(int i){		
		//return table.get(i);
		return null;
	}
	public void addColumn(){
		
	}
	public boolean isEmpty(){
		
		return table.isEmpty();
		
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
