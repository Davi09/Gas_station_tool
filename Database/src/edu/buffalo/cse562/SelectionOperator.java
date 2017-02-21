package edu.buffalo.cse562;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

import org.ietf.jgss.Oid;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
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
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;


public class SelectionOperator implements SelectVisitor, ExpressionVisitor,ItemsListVisitor, Expression{
	
	String columnChecker;
	String columnName;
	String columnWholeName;
	
	String mainType;
	String mainAction;
	String mainCompare;
	
	String mainString;
	String mainLeftString;
	String mainRightString;
	
	Date mainDate;
	Date mainLeftDate;
	Date mainRightDate;
	
	int mainInt;
	int mainLeftInt;
	int mainRightInt;
	
	long mainLong;
	long mainLeftLong;
	long mainRightLong;
	
	double mainDouble;
	double mainLeftDouble;
	double mainRightDouble;
	
	private HashMap<String,Integer> attributes = new HashMap<String,Integer>();
	private HashMap<Integer,String> type = new HashMap<Integer,String>();
	int colPos;
	boolean andExpression = true;
	boolean checkExpression = false;
	boolean Multiple = false;
	//Global Counter
	int globalTableCounter;
	//Record Position
	int startPos;
	TableCreator input;
	Expression condition;
    LinkedHashMap<Integer,String> table;
    TableCreator newTable=new TableCreator();
    HashMap<String, TableCreator> tables = new HashMap<String, TableCreator>();
    
    public TableCreator getTableCreator() {
		return newTable;
	}

	public void setTableCreator(TableCreator tableCreator) {
		this.newTable = tableCreator;
	}

    ArrayList flagForTuple=new ArrayList();
    boolean originalFlag;
	public SelectionOperator(TableCreator tableCreate,Expression condition){
		
		this.input=tableCreate;
		this.condition=condition;
		this.table = tableCreate.getTable();
		attributes = tableCreate.getAttribues();
		type = tableCreate.getType();
		
	}
	public SelectionOperator(HashMap<String, TableCreator> tables,Expression condition){
		this.Multiple = true;
		this.tables=tables;
		this.condition=condition;
		//this.table = tables.getTable();
		//attributes = tableCreate.getAttribues();
		//type = tableCreate.getType();
		
	}
	
	public void selectionStarter()
	{
		int size=input.getSize();
	    int count;
	    newTable.setAttribues(input.getAttribues());
	    newTable.setType(input.getType());
	    
	     for(int i=0;i<size;i++)
	     {
	    	 startPos=i*attributes.size();
	    	if(evaluate())
	    	{
	    		for(int j = 0; j < input.getAttribues().size(); j++){
	    			newTable.getTable().put(globalTableCounter,table.get(startPos+j));
	    			globalTableCounter++;
	    		}
	    	}
	     }
	     
	     newTable.setSize(globalTableCounter/attributes.size());
	     
	}
	public void selectionJoinStarter()
	{
		int totalTable = tables.size();
	    for(int i = 0; i < totalTable; i++){
	    	if(evaluate())
	    	{
	    		
	    	}
	    }
	     
	}
	
	private boolean evaluate() {
		condition.accept(this);
		return checkExpression;
	
	}
	@Override
	public void visit(PlainSelect plainSelect) {
		// TODO Auto-generated method stubarg0
		//plainSelect.accept(this);
		if(plainSelect.getWhere() != null){
			
			plainSelect.getWhere().accept(this);
		}
	}
	@Override
	public void visit(Union arg0) {
		// TODO Auto-generated method stubField field = org.springframework.util.ReflectionUtils.findField(clazz, "value");
		
	}

	@Override
	public void visit(NullValue arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Function function) {
		// TODO Auto-generated method stub
		switch (function.getName().toLowerCase()) {
		case "date":
			if(function.getParameters() != null){
				ExpressionList ExpressionList = function.getParameters();
				Expression expression;
				ExpressionVisitor expVisitor;				
				for(int i = 0; i < ExpressionList.getExpressions().size(); i++){
					 
					 DateValue tmp = new DateValue(ExpressionList.getExpressions().get(i).toString());
					 tmp.accept(this);
				}
			}
			break;
		}
		
	}

	@Override
	public void visit(InverseExpression arg0) {
		// TODO Auto-generated method stubarg0
		
	}

	@Override
	public void visit(JdbcParameter arg0) {
		// TODO arg0Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleValue doubleValue) {
		// TODO Auto-generated method stub
		mainType = "double";
		mainDouble = doubleValue.getValue();
	}

	@Override
	public void visit(LongValue longValue) {
		// TODO Auto-generated method stub
		mainType = "long";
		mainLong = longValue.getValue();
	}

	@Override
	public void visit(DateValue dateValue) {
		// TODO Auto-generated method stub
		mainType = "date";
		mainDate = dateValue.getValue();
	}

	@Override
	public void visit(TimeValue timeValue) {
		// TODO Auto-generated method stub
		mainType = "time";
	}

	@Override
	public void visit(TimestampValue timeStampValue) {
		// TODO Auto-generated method stub
		mainType = "timestamp";
	}

	@Override
	public void visit(Parenthesis paranthesis) {
		// TODO Auto-generated method stub
		mainType = "()";
	}

	@Override
	public void visit(StringValue stringValue) {
		// TODO Auto-generated method stub
		mainType = "string";
	}

	@Override
	public void visit(Addition add) {
		// TODO Auto-generated method stub
		visitBinaryExpression(add);
		mainDouble = mainLeftDouble + mainRightDouble;
	}

	@Override
	public void visit(Division div) {
		// TODO Auto-generated method stub
		visitBinaryExpression(div);
	}

	@Override
	public void visit(Multiplication mul) {
		// TODO Auto-generated method stub
		visitBinaryExpression(mul);
	}

	@Override
	public void visit(Subtraction subtraction) {
		// TODO Auto-generated method stub
		visitBinaryExpression(subtraction);
		mainDouble = mainLeftDouble - mainRightDouble;
	}

	@Override
	public void visit(AndExpression andExp) {
		// TODO Auto-generated method stub
		visitBinaryExpression(andExp);
		
	}

	@Override
	public void visit(OrExpression orExpression) {
		// TODO Auto-generated method stub
		visitBinaryExpression(orExpression);
	    
	}

	@Override
	public void visit(Between between) {
		// TODO Auto-generated method stub
		between.getBetweenExpressionStart().accept(this);
		between.getBetweenExpressionEnd().accept(this);
	}

	@Override
	public void visit(EqualsTo eqTo) {
		// TODO Auto-generated method stub
		visitBinaryExpression(eqTo);
		switch(mainType.toLowerCase()){
		case "date":
			if(mainLeftDate.equals(mainRightDate)){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "int":
			if(mainLeftInt == mainRightInt){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "decimal":
			if(mainLeftDouble == mainRightDouble){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "string":
			if(mainLeftString.equals(mainRightString) ){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "var":
			if(mainLeftString.equals(mainRightString) ){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "varchar":
			if(mainLeftString.equals(mainRightString) ){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;	
		case "long":
			if(mainLeftLong == mainRightLong){
				andExpression = true;
			}else{
				andExpression = false;
			}
		}
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		// TODO Auto-generated method stub
		visitBinaryExpression(greaterThan);
		switch(mainType.toLowerCase()){
		case "date":
			if(mainLeftDate.after(mainRightDate)){
				andExpression = true;
				checkExpression = true;
			}else{
				andExpression = false;
				checkExpression = false;
			}
			break;
		case "int":
			if(mainLeftInt > mainRightInt){
				andExpression = true;
				checkExpression = true;
			}else{
				andExpression = false;
				checkExpression = false;
			}
			break;
		case "decimal":
			if(mainLeftDouble > mainRightDouble){
				andExpression = true;
				checkExpression = true;
			}else{
				andExpression = false;
				checkExpression = false;
			}
			break;
		case "double":
			//localDouble = mainDouble;
			if(mainLeftDouble > mainRightDouble){
				andExpression = true;
				checkExpression = true;
			}else{
				andExpression = false;
				checkExpression = false;
			}
			break;
		case "string":
			break;
		case "var":
			break;
		case "varchar":
			break;	
		case "long":
			if(mainLeftLong > mainRightLong){
				checkExpression = true;
				andExpression = true;
			}else{
				andExpression = false;
				checkExpression = false;
			}
			break;	
		}
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		// TODO Auto-generated method stub
		visitBinaryExpression(greaterThanEquals);
		switch(mainType.toLowerCase()){
		case "date":
			if(mainLeftDate.after(mainRightDate) || mainLeftDate.equals(mainRightDate)){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "int":
			if(mainLeftInt >= mainRightInt){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "decimal":
			if(mainLeftDouble >= mainRightDouble){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "long":
			if(mainLeftLong >= mainRightLong){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		}
		
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
		// TODO Auto-generated method Estub
		
	}

	@Override
	public void visit(MinorThan minorThan) {
		// TODO Auto-generated method stub
		visitBinaryExpression(minorThan);
		switch(mainType.toLowerCase()){
		case "date":
			if(mainLeftDate.before(mainRightDate)){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "int":
			if(mainLeftInt < mainRightInt){
				andExpression = true;
			}else{
				andExpression = false;
			}
			
			break;
		case "decimal":
			if(mainLeftDouble < mainRightDouble){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "long":
			if(mainLeftLong < mainRightLong){
				andExpression = true;
			}else{
				andExpression = false;
			}
		case "double":
			if(mainLeftDouble < mainRightDouble){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;	
		}
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		// TODO Auto-generated method stub
		visitBinaryExpression(minorThanEquals);
		switch(mainType.toLowerCase()){
		case "date":
			if(mainLeftDate.before(mainRightDate) || mainLeftDate.equals(mainRightDate)){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "int":
			if(mainLeftInt <= mainRightInt){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "decimal":
			if(mainLeftDouble <= mainRightDouble){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;	
		case "long":
			if(mainLeftLong <= mainRightLong){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;	
		}
	}

	@Override
	public void visit(NotEqualsTo notEq) {
		// TODO Auto-generated method stub
		visitBinaryExpression(notEq);
		switch(mainType.toLowerCase()){
		case "date":
			if(!mainLeftDate.equals(mainRightDate)){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "int":
			if(mainLeftInt != mainRightInt){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "decimal":
			if(mainLeftDouble != mainRightDouble){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		case "long":
			if(mainLeftLong != mainRightLong){
				andExpression = true;
			}else{
				andExpression = false;
			}
			break;
		}
	}

	@Override
	public void visit(Column column) {
		// TODO Auto-generated method stub
		//Don't accept here
		columnName = column.getColumnName();
		columnWholeName = column.getWholeColumnName();
		if(Multiple){
			String tableName;
			tableName = column.getTable().toString();
			colPos = tables.get(tableName.toUpperCase()).getAttribues().get(columnName);
			type = tables.get(tableName.toUpperCase()).getType();
			table = tables.get(tableName.toUpperCase()).getTable();
		}else{
			colPos = attributes.get(columnName);
		}
		
		String localColumn;
		switch(type.get(colPos).toLowerCase()){
		case "int":
			localColumn = table.get(startPos + colPos);
			LongValue tmpInt = new LongValue(localColumn);
			tmpInt.accept(this);
			break;
		case "date":
			localColumn = table.get(startPos + colPos);
			StringBuilder sb = new StringBuilder(localColumn);
			sb.setLength(sb.length() + 1);
			sb.insert(0, " ");
			DateValue tmpDate = new DateValue(sb.toString());
			tmpDate.accept(this);
			break;
		case "long":
			localColumn = table.get(startPos + colPos);
			LongValue tmpLong = new LongValue(localColumn);
			tmpLong.accept(this);
			break;
		case "double":
			localColumn = table.get(startPos + colPos);
			break;
		case "var":
			localColumn = table.get(startPos  + colPos);
			break;
		case "varchar":
			localColumn = table.get(startPos  + colPos);
			break;
		case "string":
			localColumn = table.get(startPos + colPos);
			break;
		case "decimal":
			localColumn = table.get(startPos  + colPos);
			DoubleValue tmpDouble = new DoubleValue(localColumn);
			tmpDouble.accept(this);
			break;
		}
		
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
	public void visit(AnyComparisonExpression anyComp) {
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

	//@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	//@Override
	public LinkedList<Tuple> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(ExpressionList expressionList) {
		// TODO Auto-generated method stub
	}
	public void visitBinaryExpression(BinaryExpression binaryExpression) {
			//System.out.println(binaryExpression.toString());
			Date localDate = null;
			int localInt = 0;
			long localLong = 0;
			double localDouble = 0;
			String localString = null;
			
			binaryExpression.getLeftExpression().accept(this);
			
			switch(mainType.toLowerCase()){
			case "date":
				localDate = mainDate;
				mainLeftDate = mainDate;
				break;
			case "int":
				localInt = mainInt;
				mainLeftInt = mainInt;
				break;
			case "decimal":
				localDouble = mainDouble;
				mainLeftDouble = mainDouble;
				break;
			case "double":
				localDouble = mainDouble;
				mainLeftDouble = mainDouble;
				break;
			case "string":
				break;
			case "var":
				localString = mainString;
				localString = mainString;
				break;
			case "varchar":
				localString = mainString;
				mainLeftString = mainString;
				break;	
			case "long":
				localLong = mainLong;
				mainLeftLong = mainLong;
				break;	
			}
			//localDate = date;
			if(andExpression){
				//set false to stop the other checkings
				binaryExpression.getRightExpression().accept(this);	
				switch(mainType.toLowerCase()){
				case "date":
					mainLeftDate = localDate;
					mainRightDate = mainDate;
					break;
				case "int":
					mainLeftInt = localInt;
					break;
				case "decimal":
					mainLeftDouble = localDouble;
					mainRightDouble = mainDouble;
					break;
				case "double":
					mainLeftDouble = localDouble;
					//localDouble = mainDouble;
					mainRightDouble = mainDouble;
					break;
				case "string":
					mainLeftString = localString;
					mainRightString = mainString;
					break;
				case "var":
					mainLeftString = localString;
					mainRightString = mainString;
					
					break;
				case "varchar":
					mainLeftString = localString;
					mainRightString = mainString;
					break;	
				case "long":
					mainLeftLong = localLong;
					mainRightLong = mainLong;
					break;	
				}
			}
			
	}

	@Override
	public void accept(ExpressionVisitor expVisitor) {
		// TODO Auto-generated method stub
	}
	

}