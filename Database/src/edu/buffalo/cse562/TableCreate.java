package edu.buffalo.cse562;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.sf.jsqlparser.expression.Expression;
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


public class TableCreate implements StatementVisitor{
	private String tableName;
	private List<String> attributes = new ArrayList<String>();
	//private TreeMap<String, Attr> attrs;
	private List<ColDataType> type = new ArrayList<ColDataType>();
	
	
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
		//createTable.getColumnDefinitions().
		//setAttribues(att);
		tableName = createTable.getTable().toString();
		for (Iterator iter = createTable.getColumnDefinitions().iterator(); iter.hasNext();) {
			ColumnDefinition colDef = (ColumnDefinition) iter.next();
			attributes.add(colDef.getColumnName());
			type.add(colDef.getColDataType());
			//System.out.println(((ColumnDefinition) iter.next()).getColumnName());
		}
	}

	public List<ColDataType> getType() {
		return type;
	}

	public void setType(List<ColDataType> type) {
		this.type = type;
	}

	public List<String> getAttribues() {
		return attributes;
	}

	public void setAttribues(List<String> attribues) {
		this.attributes = attribues;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
