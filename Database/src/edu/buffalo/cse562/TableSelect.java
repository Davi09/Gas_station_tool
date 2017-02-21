package edu.buffalo.cse562;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;


public class TableSelect implements SelectVisitor, StatementVisitor, FromItemVisitor, ExpressionVisitor{
	private List tables;
	private List<String> attribute;
	String tableName;
	boolean isJoin;
	boolean isWhere;
	List<SelectItem> selectItems;
	public List<SelectItem> getSelectItems() {
		return selectItems;
	}
	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}
	public boolean isWhere() {
		return isWhere;
	}
	public void setWhere(boolean isWhere) {
		this.isWhere = isWhere;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	boolean isSubSelect;
	
	public boolean isSubSelect() {
		return isSubSelect;
	}
	public void setSubSelect(boolean isSubSelect) {
		this.isSubSelect = isSubSelect;
	}
	public SelectBody getSubSelectBody() {
		return subSelectBody;
	}
	public void setSubSelectBody(SelectBody subSelectBody) {
		this.subSelectBody = subSelectBody;
	}

	SelectBody subSelectBody;
	
	String colName;
	long longValue;
	String operator;
	Operator input;
	Expression getWhere;
	public Expression getGetWhere() {
		return getWhere;
	}
	public void setGetWhere(Expression getWhere) {
		this.getWhere = getWhere;
	}

	
	public List getTableList(Select select) {
		tables = new ArrayList();
		select.getSelectBody().accept(this);
		return tables;
	}
	public List<String> getAttribute(CreateTable createTable){
		createTable.accept(this);
		
		return attribute;
	}
	public void createTable(CreateTable createTable) {
		for (Iterator iter = createTable.getColumnDefinitions().iterator(); iter.hasNext();) {
			//Expression expression = (Expression) iter.next();
			//expression.accept(this);
			//System.out.println(((ColumnDefinition) iter.next()).getColumnName());
		}
		//createTable.getColumnDefinitions().get(0).getClass().get
		//createTable.accept(this);
		
		
	}
	public void tableProject(Select select) {
		
		select.getSelectBody().accept(this);
		
		//We know the table
	}
	
	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		binaryExpression.getLeftExpression().accept(this);
		//store left variable
		binaryExpression.getRightExpression().accept(this);
		//store right variable
		//readtuple
		
	}
	public void visit(ExpressionList expressionList) {
		for (Iterator iter = expressionList.getExpressions().iterator(); iter.hasNext();) {
			Expression expression = (Expression) iter.next();
			expression.accept(this);
		}

	}
	public void tableSelect(Select select, Operator input) {
		this.input = input;
		select.getSelectBody().accept(this);
		//select.
		//We know the table
	}
	
	@Override
	public void visit(PlainSelect plainSelect) {
		// TODO Auto-generated method stub
		plainSelect.getFromItem().accept(this);
		//System.out.println(tableName);
		if (plainSelect.getJoins() != null){
			isJoin=true;
		}
		if (plainSelect.getWhere() != null){
			isWhere=true;
			//plainSelect.getWhere().accept(this);
			//SelectionOperator so = new SelectionOperator(input,plainSelect.getWhere());
			//so.readOneTuple();
			getWhere = plainSelect.getWhere();
			
			//SELECT age >= 30 personInfo
			//System.out.println("SELECT " + colName + " " + operator + longValue + " " + tableName);
			System.out.println(tableName);
		}
		if(plainSelect.getSelectItems() != null){
			selectItems=plainSelect.getSelectItems();
		}
		//
	}

	@Override
	public void visit(Union arg0) {
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
	public void visit(CreateTable createTable) {
		// TODO Auto-generated method stub
		System.out.println("Create Table !!!");
		for (Iterator iter = createTable.getColumnDefinitions().iterator(); iter.hasNext();) {
			//Expression expression = (Expression) iter.next();
			//expression.accept(this);
			//System.out.println(((ColumnDefinition) iter.next()).getColumnName());
			attribute.add(((ColumnDefinition) iter.next()).getColumnName());
			//Pair<String,Integer> pair = Pair.with("hello", Integer.valueOf(23));
		}
		//System.out.println(createTable.getTable());
	}

	@Override
	public void visit(Table tn) {
		// TODO Auto-generated method stub
		//tableName = tn.getAlias();
		tableName = tn.getWholeTableName();
		//tables.add(tableWholeName);
		//System.out.println("Table");
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub
		isSubSelect=true;
		subSelectBody=subSelect.getSelectBody();
	}

	@Override
	public void visit(SubJoin arg0) {
		// TODO Auto-generated method stub
		
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
		System.out.println(arg0.getValue());
	}

	@Override
	public void visit(LongValue longv) {
		// TODO Auto-generated method stub
		System.out.println(longv.getValue());
		longValue = longv.getValue();
	}

	@Override
	public void visit(DateValue arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getValue());
	}

	@Override
	public void visit(TimeValue arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getValue());
		
	}

	@Override
	public void visit(TimestampValue arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getValue());
	}

	@Override
	public void visit(Parenthesis arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getExpression());
	}

	@Override
	public void visit(StringValue stringv) {
		// TODO Auto-generated method stub
		System.out.println(stringv.getValue());
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
		visitBinaryExpression(and);
		//and.getLeftExpression().accept(this);
		//and.getRightExpression().accept(this);
	}

	@Override
	public void visit(OrExpression or) {
		// TODO Auto-generated method stub
		visitBinaryExpression(or);
	}

	@Override
	public void visit(Between btw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EqualsTo equal) {
		// TODO Auto-generated method stub
		visitBinaryExpression(equal);
		System.out.println(equal.getStringExpression());
		//equal.getLeftExpression().accept(this);
		//equal.getRightExpression().accept(this);
	}

	@Override
	public void visit(GreaterThan gt) {
		// TODO Auto-generated method stub
		visitBinaryExpression(gt);
		//Compute here
		System.out.println(gt.getStringExpression());
		
		
	}

	@Override
	public void visit(GreaterThanEquals gte) {
		// TODO Auto-generated method stub
		//gte.
		visitBinaryExpression(gte);
		operator = gte.getStringExpression();
		System.out.println(gte.getStringExpression());
		/*pseudocode
		 * 
		 * if(tuple[colName] >= longValue){
		 * 		return true;
		 * }
		 * 
		 */
		Operator input = null;
		//SelectionOperator so = new SelectionOperator(input,gte);
		//so.readOneTuple();
	}

	@Override
	public void visit(InExpression arg0) {
		// TODO Auto-generated method stub
		System.out.println("1");
	}

	@Override
	public void visit(IsNullExpression arg0) {
		// TODO Auto-generated method stub
		System.out.println("2");
	}

	@Override
	public void visit(LikeExpression arg0) {
		// TODO Auto-generated method stub
		System.out.println("72");
	}

	@Override
	public void visit(MinorThan arg0) {
		// TODO Auto-generated method stub
		System.out.println("62");
	}

	@Override
	public void visit(MinorThanEquals arg0) {
		// TODO Auto-generated method stub
		System.out.println("52");
	}

	@Override
	public void visit(NotEqualsTo neq) {
		// TODO Auto-generated method stub
		System.out.println("42");
		visitBinaryExpression(neq);
	}

	@Override
	public void visit(Column col) {
		// TODO Auto-generated method stub
		System.out.println(col.getWholeColumnName());
		colName = col.getWholeColumnName();
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
		System.out.println("32");
	}

	@Override
	public void visit(AllComparisonExpression arg0) {
		// TODO Auto-generated method stub
		System.out.println("22");
	}

	@Override
	public void visit(AnyComparisonExpression arg0) {
		// TODO Auto-generated method stub
		System.out.println("12");
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
	
}