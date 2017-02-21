package edu.buffalo.cse562;
import java.beans.Visibility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;




public class ProjectionOperator implements SelectItemVisitor,SelectVisitor,ExpressionVisitor {
	
	TableCreator tableCreator;
	List<SelectItem> selectItems;
	int start;
	int counter;
	Object value;
	long longValue;
	double doubleValue;
	String columnAlias;
	boolean alias;
	LinkedHashMap<Integer,Object> table;
	TableCreator newTable=new TableCreator();
	public TableCreator getNewTable() {
		return newTable;
	}

	public void setNewTable(TableCreator newTable) {
		this.newTable = newTable;
	}

	String columnChecker;
	public ProjectionOperator(TableCreator table,List<SelectItem> items)
	{
		tableCreator=table;
		selectItems=items;
	}
	
	public void projectionStarter()
	{
		table=tableCreator.getTable();
		List<String> tableAttributes=new ArrayList<String>();
		List<String> tableTypes=new ArrayList<String>();
		int attributeCounter=0;
		int typeCounter=0;
		HashMap<String,Integer> attributes=new HashMap<String,Integer>();
		Iterator it=selectItems.iterator();
		while(it.hasNext())
		{
			SelectItem item1=(SelectItem)it.next();
			item1.accept(this);
			if(alias)
			{
				tableAttributes.add(columnAlias);
			}
			else
			{
				tableAttributes.add(item1.toString());
			}
		}
		Iterator iterator=selectItems.iterator();
		while(iterator.hasNext())
		{
			SelectItem item2=(SelectItem)iterator.next();
			int j=tableCreator.getAttribues().get(item2.toString());
			tableTypes.add(tableCreator.getType().get(j));
	    }
		while(tableAttributes.iterator().hasNext())
		{
			newTable.getAttribues().put(tableAttributes.iterator().next(),attributeCounter);
			attributeCounter++;
		}
		while(tableTypes.iterator().hasNext())
		{
			newTable.getType().put(typeCounter,tableTypes.iterator().next());
			typeCounter++;
		}
		for(int i=0;i<tableCreator.getSize();i++)
		{
		start=i*tableCreator.getAttribues().size();
		Iterator itr=selectItems.iterator();
		while(itr.hasNext())
		{
		  SelectItem item=(SelectItem)itr.next();
		  item.accept(this);
		  newTable.getTable().put(counter,value);
		  counter++;
		}
		
		}
		int totalSize=counter/newTable.getAttribues().size();
		newTable.setSize(totalSize);
	}

	@Override
	public void visit(PlainSelect arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Union arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AllColumns arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AllTableColumns arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		// TODO Auto-generated method stub
		if(selectExpressionItem.getAlias()!=null)
		{
			alias=true;
			columnAlias=selectExpressionItem.getAlias();
		}
		selectExpressionItem.getExpression().accept(this);
		
	}

	@Override
	public void visit(NullValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Function arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InverseExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JdbcParameter arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		// TODO Auto-generated method stub
		this.doubleValue=doubleValue.getValue();
		columnChecker="in doubleValue";
		
	}

	@Override
	public void visit(LongValue longValue) {
		// TODO Auto-generated method stub
		this.longValue=longValue.getValue();
		columnChecker="in longValue";
	}

	@Override
	public void visit(DateValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimeValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimestampValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Parenthesis paranthesis) {
		// TODO Auto-generated method stub
		paranthesis.getExpression().accept(this);
		
	}

	@Override
	public void visit(StringValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Addition subtraction) {
		// TODO Auto-generated method stub
		Object leftValue;
		Object rightValue;
		String leftType=null;
		subtraction.getLeftExpression().accept(this);
		if(columnChecker.equals("in column"))
		{
			  int j=tableCreator.getAttribues().get(subtraction.getLeftExpression().toString());
			  
			  leftValue=table.get(start+j);
			  leftType=tableCreator.getType().get(j); 
				
			  switch (leftType.toLowerCase()) {
		        case "int":
		        	int leftInt =(int)leftValue;
		            subtraction.getRightExpression().accept(this);
		            if(columnChecker.equals("in column"))
		            {
		              int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
		              rightValue=table.get(start+j);
		              int rightInt=(int)rightValue;
		              value=leftInt+rightInt;
		            }
		            else
		            {
		              value=leftInt+this.longValue;
		            }
		           
		        	break;
		        case "decimal":  
		        	double leftDouble =(double)leftValue;
		        	subtraction.getLeftExpression().accept(this);
		        	if(columnChecker.equals("in column"))
		        	{
		        		int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
			            rightValue=table.get(start+j1);
		        		double rightDouble=(double)rightValue;
		        		value=leftDouble+rightDouble;
		        		String s1=String.format("%.2f",value);
		        		value=Double.parseDouble(s1);
		        	}
		        	else
		        	{
		        		value=leftDouble+this.doubleValue;
		        		String s1=String.format("%.2f",value);
		        		value=Double.parseDouble(s1);
		        	}
		        	
		            break;
		        
		        default:break;
	     }
	    columnChecker=null;
		}
		if(columnChecker.equals("in longValue"))
		{
			long temp=this.longValue;
			subtraction.getRightExpression().accept(this);
			if(columnChecker.equals("in column"))
			{
				int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
	            rightValue=table.get(start+j1);
				int tempRight=(int)rightValue;
				value=temp+tempRight;
			}
			else
			{
				value=temp+this.longValue;
			}
			
			columnChecker=null;
		}
		if(columnChecker.equals("in double"))
		{
			double tempDouble=this.doubleValue;
			subtraction.getRightExpression().accept(this);
			if(columnChecker.equals("in column"))
			{
				int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
	            rightValue=table.get(start+j1);
				double tempRight=(double)rightValue;
				value=tempDouble+tempRight;
				String s1=String.format("%.2f",value);
        		value=Double.parseDouble(s1);
			}
			else
			{
				value=tempDouble+this.doubleValue;
				String s1=String.format("%.2f",value);
        		value=Double.parseDouble(s1);
			}
			
			columnChecker=null;
		}
		
	}

	@Override
	public void visit(Division arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Multiplication arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Subtraction subtraction) {
		// TODO Auto-generated method stub
		Object leftValue;
		Object rightValue;
		String leftType=null;
		subtraction.getLeftExpression().accept(this);
		if(columnChecker.equals("in column"))
		{
			  int j=tableCreator.getAttribues().get(subtraction.getLeftExpression().toString());
			  columnChecker=null;
			  leftValue=table.get(start+j);
			  leftType=tableCreator.getType().get(j); 
				
			  switch (leftType.toLowerCase()) {
		        case "int":
		        	int leftInt =(int)leftValue;
		            subtraction.getRightExpression().accept(this);
		            if(columnChecker.equals("in column"))
		            {
		              int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
		              rightValue=table.get(start+j);
		              int rightInt=(int)rightValue;
		              value=leftInt-rightInt;
		              
		            }
		            else
		            {
		              value=leftInt-this.longValue;
		            }
		           
		        	break;
		        case "decimal":  
		        	double leftDouble =(double)leftValue;
		        	subtraction.getLeftExpression().accept(this);
		        	if(columnChecker.equals("in column"))
		        	{
		        		int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
			            rightValue=table.get(start+j1);
		        		double rightDouble=(double)rightValue;
		        		value=leftDouble-rightDouble;
		        		String s1=String.format("%.2f",value);
		        		value=Double.parseDouble(s1);
		        	}
		        	else
		        	{
		        		value=leftDouble-this.doubleValue;
		        		String s1=String.format("%.2f",value);
		        		value=Double.parseDouble(s1);
		        	}
		        	
		            break;
		        
		        default:break;
	     }
	    columnChecker=null;
		}
		if(columnChecker.equals("in longValue"))
		{
			long temp=this.longValue;
			subtraction.getRightExpression().accept(this);
			if(columnChecker.equals("in column"))
			{
				int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
	            rightValue=table.get(start+j1);
				int tempRight=(int)rightValue;
				value=temp-tempRight;
			}
			else
			{
				value=temp-this.longValue;
			}
			
			columnChecker=null;
		}
		if(columnChecker.equals("in double"))
		{
			double tempDouble=this.doubleValue;
			subtraction.getRightExpression().accept(this);
			if(columnChecker.equals("in column"))
			{
				int j1=tableCreator.getAttribues().get(subtraction.getRightExpression().toString());
	            rightValue=table.get(start+j1);
				double tempRight=(double)rightValue;
				value=tempDouble-tempRight;
				String s1=String.format("%.2f",value);
        		value=Double.parseDouble(s1);
			}
			else
			{
				value=tempDouble-this.doubleValue;
				String s1=String.format("%.2f",value);
        		value=Double.parseDouble(s1);
			}
			
			columnChecker=null;
		}
	}

	@Override
	public void visit(AndExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OrExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Between arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EqualsTo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GreaterThan arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GreaterThanEquals arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IsNullExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LikeExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MinorThan arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MinorThanEquals arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NotEqualsTo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Column column) {
		// TODO Auto-generated method stub
		System.out.println("column name"+column.getColumnName());
		int j=tableCreator.getAttribues().get(column.getColumnName());
		value=table.get(start+j);
		columnChecker="in column";
	}

	@Override
	public void visit(SubSelect arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CaseExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhenClause arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExistsExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AllComparisonExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnyComparisonExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Matches arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseAnd arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseOr arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseXor arg0) {
		// TODO Auto-generated method stub
		
	}
	public void tableOrderBy(ArrayList <Integer> order){
		List keys;
		ArrayList<List> mykey = new ArrayList<List>();
		TreeMap<Integer, Object> myMap = new TreeMap<Integer, Object>(); 
		int size = tableCreator.getAttribues().size();
		for(int k = 0; k < order.size(); k++){
			keys = new ArrayList();
			for(int i = 0; i < tableCreator.getSize(); i++){
				//for(int j = 0; j < order.size(); j++){
				//keys.add(tableCreator.getTable().get(i * size + order.get(j)));
				myMap.put(i * size + order.get(k), tableCreator.getTable().get(i * size + order.get(k)));
			//}
				//keys.add(tableCreator.getTable().get(i * size + order.get(k)));
			}
			//mykey.add(keys);
		}
		/*Collections.sort(keys, new Comparator(){
		    public int compare(Object s1,  Object s2) {
		        return s1.toString().compareToIgnoreCase(s2.toString());
		    }
		});*/
		//Collections.sort(mykey.get(0));
		//Collections.sort(mykey.get(1));
		for(int i = 0; i < mykey.get(0).size(); i++){
			System.out.println(mykey.get(0).get(i));
		}
		for(int i = 0; i < mykey.get(1).size(); i++){
			System.out.println(mykey.get(1).get(i));
		}
		
		for(int i = 0; i <  mykey.get(0).size(); i++){
			//for(int j = 0; j < mykey.get(1).size(); j++){
			
			//}
		}
	}
	
}