package edu.buffalo.cse562;
import java.beans.Visibility;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.select.Join;



public class JoinOperator implements Operator, SelectVisitor, ExpressionVisitor, StatementVisitor, SelectItemVisitor, ItemsListVisitor, FromItemVisitor, FromItem{
	Expression condition;
	HashMap<String, TableCreator> input;
	String simpleJoin;
	String simpleAlias;
	String fromTable;
	String colName;
	String colNameWithAlias;
	String onJoin;
	boolean leftSide;
	boolean rightSide;
	TableCreator joinedTable;
	public JoinOperator(){
		
	}
	public JoinOperator(HashMap<String, TableCreator> input, Expression condition){
		this.input = input;
		this.condition = condition;
	}
	@Override
	public void resetStream() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tuple readOneTuple() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Tuple> getTable() {
		// TODO Auto-generated method stub
		return null;
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
	public void visit(DoubleValue arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(LongValue arg0) {
		// TODO Auto-generated method stub
		
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
	public void visit(Parenthesis paren) {
		// TODO Auto-generated method stub
		//paren.accept(this);
		paren.getExpression().accept(this);
	}
	@Override
	public void visit(StringValue arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Addition arg0) {
		// TODO Auto-generated method stub
		
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
	public void visit(Subtraction arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndExpression and) {
		// TODO Auto-generated method stub
		boolean left = false;
		and.getLeftExpression().accept(this);
		
		if(left){
			
		}
		and.getRightExpression().accept(this);
		
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
	public void visit(GreaterThan gt) {
		String left;
		String right;
		// TODO Auto-generated method stub
		gt.getLeftExpression().accept(this);
		left = colName;
		
		gt.getRightExpression().accept(this);
		right = colName;
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
	public void visit(MinorThan minor) {
		// TODO Auto-generated method stub
		String left, right;
		
		minor.getLeftExpression().accept(this);
		left = colName;
		minor.getRightExpression().accept(this);
		right = colName;
		//if((int)input.getTable().get(0).getValue(left) < (int)input.getTable().get(0).getValue(left))
		//	leftSide = true;
		System.out.println(leftSide);
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
	public void visit(Column col) {
		// TODO Auto-generated method stub
		System.out.println(col);
		colName = col.getColumnName();
		colNameWithAlias = col.getWholeColumnName();
		//System.out.println(colName);
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
	@Override
	public void visit(PlainSelect plainSelect) {
		// TODO Auto-generated method stub
		//((AllComparisonExpression) plainSelect.getJoins().get(0)).accept(this);
		if(plainSelect.getFromItem() != null){
			plainSelect.getFromItem().accept(this);
			fromTable = simpleJoin;
		}
		//if(plainSelect.getJoins().)
		if(plainSelect.getJoins() != null){
			TableCreator newTable = new TableCreator();
			//LinkedList<LinkedHashMap<Integer, Object>> joinTable = new LinkedList<LinkedHashMap<Integer, Object>>();
			TableCreator joinTable = new TableCreator();
			int counter = 0;
			for(int i = 0; i < plainSelect.getJoins().size();i++){
				
				Join join = (Join) plainSelect.getJoins().get(i);
				
				if(join.getOnExpression() != null){
					join.getOnExpression().accept(this);
					
					onJoin = "PLAYERS";
					String tableName = onJoin;
					join.getRightItem().accept(this);

					TableCreator table = new TableCreator();
					table = input.get(fromTable);
					if(onJoin.equals(tableName) ){
						
						int count = 0;
						int counter2 = 0;
						System.out.println(count);
						//The loop
						String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						System.out.println(timeStamp1);
						int season;
						int season2;
						int season3 = 0;
						int season4 = 0;
						
						int size = input.get(fromTable).getTable().size();
						boolean jok;
						System.out.println(table.getSize());
						int[] intArray = new int[size];
						int[] intArray2 = new int[size];
						TableCreator table1 = new TableCreator();
						TableCreator table2 = new TableCreator();
						TableCreator tmp = new TableCreator();
						//table.printOut();
						for(int j = 0; j < table.getSize(); j++){
							for(int k = 0; k < table.getSize(); k++){						
								 season = (int)table.getTable().get(j * table.getAttribues().size() + 3);
								 //System.out.println(k * table.getAttribues().size() + 4);
								 season2 = (int)table.getTable().get(k * table.getAttribues().size() + 3);
								 season3 = (int)table.getTable().get(j * table.getAttribues().size() + 4);
								 season4 = (int)table.getTable().get(k * table.getAttribues().size() + 4);
								if(season  <  season2 &&  season3 > season4){
									
									count++;
									for(int l = 0; l < table.getAttribues().size(); l++){
										
										tmp.getTable().put(counter2 , table.getTable().get(j * table.getAttribues().size() + l));
										counter2++;					
									}
									for(int l = 0; l < table.getAttribues().size(); l++){
										
										tmp.getTable().put(counter2 , table.getTable().get(k * table.getAttribues().size() + l));
										counter2++;				
									}
								}
								
							}
						}
						System.out.println(count);
						System.out.println(counter2);
						tmp.setSize(counter2);
						
						String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						System.out.println(timeStamp2);
					}
				}
				if(join.isNatural()){
					System.out.println(join.getRightItem());
				}
				if(join.isOuter()){
					System.out.println(join.getRightItem());
				}
				if(join.isInner()){
					System.out.println(join.getRightItem());
				}
				if(join.isFull()){
					System.out.println(join.getRightItem());
				}
				if(join.isLeft()){
					System.out.println(join.getRightItem());
				}
				if(join.isRight()){
					System.out.println(join.getRightItem());
				}
				if(join.isSimple()){
					TableCreator table = new TableCreator();
					table = input.get(fromTable);
					String tableName = simpleJoin;
					join.getRightItem().accept(this);
					if(simpleJoin.equals(tableName) ){
						int count = 0;
						String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						System.out.println(timeStamp1);
						for(int j = 0; j < table.getSize(); j++){
							for(int k = 0; k < table.getSize(); k++){
								
							}
						}
						String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						System.out.println(timeStamp2);
					}else{
						String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						System.out.println(timeStamp1);
						TableCreator table1 = new TableCreator();
						TableCreator table2 = new TableCreator();
						TableCreator tmp = new TableCreator();
						
						if(!newTable.isEmpty())
							table1 = newTable;
						else
							table1 = input.get(fromTable);
						
						
						table2 = input.get(simpleJoin);
						LinkedHashMap<Integer, Object> tuple;
						int count = 0;
						
						LinkedHashMap<Integer, Object> tabless = new LinkedHashMap<Integer, Object>();
						int table1attrsize = table1.getAttribues().size();
						int table2attrsize = table2.getAttribues().size();
						int table1size = table1.getSize();
						int table2size = table2.getSize();
						int simpleCount = 0;
						int blockSize = 0;
						if(simpleJoin.toString().equals("LINEITEM"))
							blockSize = 120;
						else
							blockSize = 1;
						
						for(int j = 0; j < table1size; j++){
							int table1Pos = 0 * table1size + j;
							for(int k = 0; k < table2size/blockSize; k++){
								int table2Pos = 0 * table2attrsize + k;
								for(int l = 0; l < table2attrsize; l++){
									tmp.getTable().put(simpleCount, table2.getTable().get(table2Pos + l));
									simpleCount++;
								}
								for(int l = 0; l < table1attrsize; l++){
									tmp.getTable().put(simpleCount, table1.getTable().get(table1Pos + l));
									simpleCount++;
								}
								
								//counter++;
								//tmp.getTable().put(counter, table1.getTable().get(olo2));
								//tmp.getTable().put(counter, table2.getTable().get(olo));
								
								//}
								//count++;
								//counter++;
								//System.out.println(counter);
								/* Total amount of free memory available to the JVM */
								  //System.out.println("Free memory (bytes): " + 
								  //Runtime.getRuntime().freeMemory());
							}
							
						}
						//tmp.getTable().put(simpleCount, table2.getTable().get(1));
						tmp.setSize(simpleCount);
						System.out.println(simpleCount);
						input.get(fromTable).getTable().clear();
						input.get(simpleJoin).getTable().clear();
						newTable = tmp;
						tmp = null;
						table1=null;
						table2= null;
						String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
					}
					
				}
				
			}
			joinedTable = newTable;
		}
		System.out.println(joinedTable.getTable().get(1));
	}
	@Override
	public void visit(Union arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ExpressionList arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AllColumns arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AllTableColumns table) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SelectExpressionItem arg0) {
		// TODO Auto-generated method stub
		
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
	public void visit(CreateTable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Table table) {
		// TODO Auto-generated method stub
		simpleJoin = table.getName();
		if(table.getAlias() != null){
			simpleAlias = table.getAlias();
		}
		//System.out.println(table.get);	
	}
	@Override
	public void visit(SubJoin join) {
		// TODO Auto-generated method stub
		System.out.println(join.getAlias());
	}
	@Override
	public void accept(FromItemVisitor arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAlias(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public TableCreator getJoinedTable(){
		return joinedTable;
	}

}
