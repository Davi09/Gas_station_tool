package edu.buffalo.cse562;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;


public class TableOperator implements Operator {
	//private List<Tuple> table; 
	//Queue<Tuple> table;
	LinkedList<Tuple> table;
	LinkedList<Tuple> reset;
	static int count;
	private int size;
	public TableOperator(){
		//this.table = new LinkedList<Tuple>();
		this.table = new LinkedList<Tuple>();
		size = 0;
	}
	@Override
	public void resetStream() {
		// TODO Auto-generated method stub
		count = 0;
	}

	@Override
	public Tuple readOneTuple() {
		// TODO Auto-generated method stub
		Tuple t = null;
		if(count < table.size())
			t = table.get(count);
		count++;
		return t;
	}
	
	public void addTuple(Tuple t){
		table.add(t);
	}
	public void printOut(){
		for (Iterator<Tuple> iter = table.iterator(); iter.hasNext();) {
			boolean first = true;
			for (Object value : iter.next().getTuple().values()) {
				if(first == false)
					System.out.print("|");
				if(value instanceof Date){
					SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-M-dd");
					System.out.print(dt1.format(value));
				}else{
				
					System.out.print(value.toString());
				}
				first = false;
			}
			System.out.print("\n");
		}
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		//return !table.isEmpty();
		return !(table.size() == count);
	}
	
	public LinkedList<Tuple> getTable(){
		return table;
	}
	
	public boolean removeTupple(Tuple t){
		return table.remove(t);
	}

}
