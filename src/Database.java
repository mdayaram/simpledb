import java.io.*;
import java.util.*;

public class Database {

	private final HashMap<String, Integer> db;
	private final Stack<Rollback> rollbacks;
	
	public Database() {
		this.db = new HashMap<String, Integer>();
		this.rollbacks = new Stack<Rollback>();
	}

	public void set(String var, Integer val) {
		Integer prev = db.get(var);
		if (!rollbacks.isEmpty()) {
			rollbacks.push(new Rollback(var, prev));
		}
		db.put(var, val);
	}

	public Integer get(String var) {
		return db.get(var);
	}

	public void begin() {
		rollbacks.push(null); // marks the beginning of a transaction.
	}

	public void rollback() throws InvalidRollbackException {
		if (rollbacks.isEmpty()) {
			throw new InvalidRollbackException();
		}
		while(rollbacks.peek() != null) {
			Rollback roll = rollbacks.pop();
			db.put(roll.getVar(), roll.getVal());
		}
		rollbacks.pop();
	}

	public void commit() {
		rollbacks.clear();
	}

}

