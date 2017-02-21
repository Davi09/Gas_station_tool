package edu.buffalo.cse562;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class FileScan implements Operator {
	Tuple t;
	String line;
	BufferedReader br = null;
	TableCreate tc;
	public  FileScan(String filename, TableCreate tc){
		this.tc = tc;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
	public FileScan(String filename){
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void resetStream() {
		try {
			br.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Tuple readOneTuple() {
		//Types
		//Colname
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String fields[] = null;
		fields = line.split("\\|");
		Tuple t = new Tuple(tc, fields);
		return t;
	}
	public String readOneLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean hasNext(){
		try {
			if(br.ready())
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public LinkedList<Tuple> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
