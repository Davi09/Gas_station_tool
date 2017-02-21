package edu.buffalo.cse562;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

public class Main {

	public static void main(String[] args) throws JSQLParserException {
		// TODO Auto-generated method stub
		String path = "", sqlfile = "", sql = "", tableName = "";
		HashMap<String, TableCreator> tables = new HashMap<String, TableCreator>();

		if (args.length > 0) {
			if (args[0].equals("--data") && args.length == 3) {
				path = args[1];
				sqlfile = args[2];
			}
		}
		CCJSqlParserManager pm = new CCJSqlParserManager();
		//FileScan fs = new FileScan("bin/" + path + "/" + sqlfile);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("bin/" + path + "/" + sqlfile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sCurrentline;
		int i = 0;
		try {
			while((sCurrentline = br.readLine()) != null){
				if(sCurrentline.endsWith(";")){
					i = 1;
					break;
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			br = new BufferedReader(new FileReader("bin/" + path + "/" + sqlfile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(i == 0){
			try {
				while((sql = br.readLine())!= null){
					Statement statement = pm.parse(new StringReader(sql));
					if (statement instanceof CreateTable) {
						CreateTable createStatement = (CreateTable) statement;
						tableName = createStatement.getTable().toString();
						TableCreator tableCreate = new TableCreator("bin/" + path + "/" + tableName + ".dat");
						tableCreate.tableCreate(createStatement);
						tableCreate.fillUp();
						tables.put(tableName, tableCreate);
					} else if (statement instanceof Select) {			
						Select selectStatement = (Select) statement;
						TableSelect ts=new TableSelect();
						ts.visit((PlainSelect) selectStatement.getSelectBody());
						SelectionOperator so = new SelectionOperator(tables.get("PLAYERS"), ts.getWhere);
						
						//tables.get("LINEITEM").printOut();
						so.selectionStarter();
						so.getTableCreator().printOut();
						System.out.println(so.getTableCreator().getSize());
					}
					
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String readLine ="";
			String sqlLine = "";
			Statement statement = null;
			try {
				while((readLine = br.readLine()) != null){
					
						if(!readLine.endsWith(";")){
							sqlLine += " " +  readLine;
						}else{
							sqlLine += " " + readLine;
							System.out.println(sqlLine);
							statement = pm.parse(new StringReader(sqlLine));
							sqlLine = "";
							
							if (statement instanceof CreateTable) {
								CreateTable createStatement = (CreateTable) statement;
								tableName = createStatement.getTable().toString();
								TableCreator tableCreate = new TableCreator("bin/" + path + "/" + tableName + ".dat");
								tableCreate.tableCreate(createStatement);
								tableCreate.fillUp();
								tables.put(tableName, tableCreate);
							} else if (statement instanceof Select) {			
								Select selectStatement = (Select) statement;
								TableSelect ts=new TableSelect();
								ts.visit((PlainSelect) selectStatement.getSelectBody());
								SelectionOperator so = new SelectionOperator(tables, ts.getGetWhere());
								//so.selectionStarter();
								//so.getTableCreator().printOut();
								//System.out.println(so.getTableCreator().getSize());
								so.selectionJoinStarter();
							}
							
						}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
					
					
					
				
		}
		
		

	}
}
