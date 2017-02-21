package edu.buffalo.cse562;
import java.util.LinkedList;


public interface Operator {
	public void resetStream();
	public Tuple readOneTuple();
	public boolean hasNext();
	public LinkedList<Tuple> getTable();
}
