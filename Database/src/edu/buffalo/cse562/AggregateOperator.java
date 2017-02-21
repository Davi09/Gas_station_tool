package edu.buffalo.cse562;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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

public class AggregateOperator implements Operator, ExpressionVisitor, SelectVisitor, StatementVisitor, SelectItemVisitor, ItemsListVisitor, FromItemVisitor {
	TableCreator input;
	Expression condition;
	int count;
	double weight;
	List<Column> groupBy; 
	HashMap<Object, Integer> aggregatorHolder;
	String colName;
	String funcName;
	ExpressionList funcParam;
	String fromTable;
	TableCreator newTable = new TableCreator();
	ArrayList<Object> tuples = new ArrayList<Object>();
	
	public AggregateOperator(TableCreator  input, Expression condition) {
		this.input = input;
		this.condition = condition;
	}

	public void tableAggregate() {

	}

	@Override
	public void resetStream() {
		// TODO Auto-generated method stub

	}

	@Override
	public Tuple readOneTuple() {
		// TODO Auto-generated method stub
		Tuple t = null;
		Tuple t2 = null;
		//t = input.readOneTuple();
		/*
		 * do { //filescan.readOneTuple t2 = input.readOneTuple();
		 * if(!evaluate(t,condition)){ t = null; } if((int)t.getValue("WEIGHT")
		 * == (int)t2.getValue("WEIGHT")){ count++; // input.removeTupple(t2);
		 * 
		 * //remove this tuple //(Table) input.removeTupple(1);
		 * 
		 * }
		 * 
		 * // //t.add("Count(id)", 123); } while (input.hasNext());
		 * input.removeTupple(t);
		 */// input.printOut();
			// TODO Auto-generated method stub

		return t;
	}

	private boolean evaluate(Tuple t, Expression cond) {
		// TODO Auto-generated method stub
		cond.accept(this);

		return false;
	}

	@Override
	public void visit(NullValue arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Function func) {
		// TODO Auto-generated method stub
		funcName = func.getName();
		funcParam = func.getParameters();
		//funcParam.getExpressions().get(index)
		if (func.isAllColumns()) {
			count++;
		}

		if (func.isDistinct()) {

		}
		if (func.isEscaped()) {

		}

		aggregatorHolder = new HashMap<Object, Integer>();
		
		String aggregateCol = funcParam.toString();

		// hold the column values eg. Group By state
		StringBuilder key= new StringBuilder();
		
		switch (func.getName()) {
		case "COUNT":
			Integer val;
			Object obj=null;
			for(int i = 0; i < input.getSize(); i++){
					key.delete(0, key.length());
					if (groupBy.size() > 0) {
						for(Column s:groupBy){
							
							obj = input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(s.toString()));
							key.append(obj);
							//key.append(",");
						}
					} else {
						key.append("full");
					}
					if ((val = aggregatorHolder.get(key.toString())) != null) {
						aggregatorHolder.put(key.toString(), val + 1);
					} else {
						for(int j = 0; j < input.getAttribues().size(); j++){
							tuples.add(input.getTable().get(i * input.getAttribues().size() + j));
						}
						aggregatorHolder.put(key.toString(), 1);
					}
			}


			break;
		case "MAX":
			Integer maximumValue;
			int retreivedValue;
			
			for(int i = 0; i < input.getSize(); i++){
				// cast the object value to int
				retreivedValue = (Integer) input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(aggregateCol));
				if (groupBy.size() > 0) {
					obj = input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(groupBy.get(0)));
				} else {
					obj = "Full";
				}

				if ((maximumValue = aggregatorHolder.get(obj)) != null) {
					if (retreivedValue > maximumValue) {
						maximumValue = retreivedValue;
						aggregatorHolder.put(obj, retreivedValue);
					}

				} else {
					for(int j = 0; j < input.getAttribues().size(); j++){
						tuples.add(i * input.getAttribues().size() + j);
					}
					aggregatorHolder.put(obj, retreivedValue);
				}

		}

			// can return Maximum value
			// TODO

			break;
		case "AVG":
			//int numberOfTuples = input.table.size();
			Integer sum;
			Integer prevSum;
			//keep the count of records for each state
			HashMap<Object, Integer> counterMap = new HashMap<Object, Integer>();
			
			for(int i = 0; i < input.getSize(); i++){
				// cast the object value to int
				if (groupBy.size() > 0) {
					obj = input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(groupBy.get(0)));
				} else {
					obj = "Full";
				}
				sum = (Integer) input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(aggregateCol));
				if ((prevSum = aggregatorHolder.get(obj)) != null) {
					
					
					aggregatorHolder.put(obj, sum+prevSum);
					counterMap.put(obj, counterMap.get(obj)+1);

				} else {
					for(int j = 0; j < input.getAttribues().size(); j++){
						tuples.add(input.getTable().get(i * input.getAttribues().size() + j));
					}
					aggregatorHolder.put(obj, sum);
					counterMap.put(obj, 1);
				}

			}
			Iterator<Entry<Object, Integer>> avgCount = aggregatorHolder.entrySet()
					.iterator();
			while (avgCount.hasNext()) {
				Entry<Object, Integer> r = avgCount.next();
				aggregatorHolder.put(r.getKey(), (r.getValue()/counterMap.get(r.getKey())));
			}
			// for efficiency and memory clear the hashmap
			counterMap.clear();
			// TODO
			break;
		case "MIN":
			Integer minValue;
			int retreiveValue;
			
			for(int i = 0; i < input.getSize(); i++){
				// cast the object value to int
				retreiveValue = (Integer) input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(aggregateCol));
				if (groupBy.size() > 0) {
					obj = input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(groupBy.get(0)));
				} else {
					obj = "Full";
				}
				if ((minValue = aggregatorHolder.get(obj)) != null) {
					if (retreiveValue < minValue) {
						// minValue = retreiveValue;
						aggregatorHolder.put(obj, retreiveValue);
					}
				} else {
					for(int j = 0; j < input.getAttribues().size(); j++){
						tuples.add(i * input.getAttribues().size() + j);
					}
					aggregatorHolder.put(obj, retreiveValue);
				}

			}
			
			break;
		case "SUM":
	
			//keep the count of records for each state
		

			for(int i = 0; i < input.getSize(); i++){
				// cast the object value to int
				if (groupBy.size() > 0) {
					obj = input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(groupBy.get(0)));
				} else {
					obj = "Full";
				}
				sum = (Integer) input.getTable().get(i * input.getAttribues().size() + input.getAttribues().get(aggregateCol));
				if ((prevSum = aggregatorHolder.get(obj)) != null) {
					aggregatorHolder.put(obj, sum+prevSum);
				} else {
					for(int j = 0; j < input.getAttribues().size(); j++){
						tuples.add(i * input.getAttribues().size() + j);
					}
					aggregatorHolder.put(obj, sum);
				}
			}
			
			break;
		default:
			break;
		}
		//System.out.println(aggregatorHolder.size());
		//System.out.println(tuples.size() / input.getAttribues().size());
		Iterator<Entry<Object, Integer>> itCount = aggregatorHolder.entrySet()
				.iterator();

		int counter=0;
		int count = 0;
		int recordSize = tuples.size() / input.getAttribues().size();
		for(int i = 0; i < recordSize; i++) {
			//for(int i = 0; i < tuples.size() / input.getAttribues().size() + aggregatorHolder.size(); i++){
				for(int j = 0; j < input.getAttribues().size() + 1; j++){
					if(j == input.getAttribues().size()){
						//System.out.println(groupBy.get(0));
						int a = input.getAttribues().get(groupBy.get(0).toString());
						//System.out.println(a);
						int b = input.getAttribues().size() * i;
						//System.out.println(b);
						//int weight = (int) input.getTable().get(a + b);
						//aggregatorHolder.get("WEIGHT");
						
						Object one = aggregatorHolder.get(tuples.get(a + b));
						Object two = tuples.get(a + b);
						StringBuilder subKey = new StringBuilder();
						subKey.append(two.toString());
						//subKey.append(",");
						//int lol = aggregatorHolder.get(two);
						newTable.getTable().put(count,aggregatorHolder.get(subKey.toString()));
					}
					else
						newTable.getTable().put(count,tuples.get(counter * input.getAttribues().size() + j));
					
					count++;
					
				}
				counter++;
			//}
			//System.out.println(++counter+":"+r.getKey() + "::" + r.getValue());
		}
		HashMap<String,Integer> tempAttr = new HashMap<String,Integer>();
		tempAttr = input.getAttribues();
		tempAttr.put("COUNT(ID)", tempAttr.size());
		newTable.setAttribues(tempAttr);
		newTable.setSize(count/tempAttr.size());
		int cc=0;
		for(int i = 0; i < tuples.size() / input.getAttribues().size(); i++){
			for(int j = 0; j < input.getAttribues().size(); j++){
				//System.out.print(tuples.get(cc));
				cc++;
			}
			//System.out.print("\n");
		}
		//newTable.printOut();
		//System.out.println(count/8);
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
	public void visit(Parenthesis arg0) {
		// TODO Auto-generated method stub

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
	public void visit(Column col) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub
		subSelect.getSelectBody().accept(this);

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
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		// TODO Auto-generated method stub
		TableCreator newTable = new TableCreator();
		if(plainSelect.getFromItem() != null){
			plainSelect.getFromItem().accept(this);
		}
		if (plainSelect.getDistinct() != null) {

		}
		/*if (plainSelect.getSelectItems() != null) {
			SelectExpressionItem sei;
			for (int i = 0; i < plainSelect.getSelectItems().size(); i++) {
				sei = (SelectExpressionItem) plainSelect.getSelectItems()
						.get(i);
				if (sei.getExpression() != null) {
					sei.getExpression().accept(this);
					System.out.println(funcName);
				}
			}

		}*/
		
		if (plainSelect.getGroupByColumnReferences() != null) {
			SelectExpressionItem sei;
			groupBy = plainSelect.getGroupByColumnReferences();
			List<Object> tmp = new ArrayList<Object>();
			Column col;
			for (int i = 0; i < groupBy.size(); i++) {
				//Column col2 = (Column) groupBy.get(i);
				
				//col2.accept(this);
				for (int j = 0; j < plainSelect.getSelectItems().size(); j++) {
					sei = (SelectExpressionItem) plainSelect.getSelectItems().get(j);
					if (sei.getExpression() != null) {
						sei.getExpression().accept(this);
					}

				}
			}

		}
		if (plainSelect.getDistinct() != null) {

		}
		if (plainSelect.getDistinct() != null) {

		}

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
	public void visit(CreateTable arg0) {
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
		selectExpressionItem.accept(this);

	}

	@Override
	public void visit(ExpressionList expList) {
		// TODO Auto-generated method stub

		expList.accept(this);
	}

	// @Override
	public LinkedList<Tuple> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(Table table) {
		// TODO Auto-generated method stub
		fromTable = table.getName();
	}

	@Override
	public void visit(SubJoin arg0) {
		// TODO Auto-generated method stub
		
	}

}