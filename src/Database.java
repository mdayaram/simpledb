import java.io.*;
import java.util.*;

/**
 * Simple Database class that handles the essentials of GET and SET
 * as well and Transaction blocks with BEGIN, COMMIT, and ROLLBACK.
 *
 * We use a stack to keep track of the rollbacks we need to do if someone
 * calls ROLLBACK.  We only want to rollback the changes up to the latest
 * start of a transaction.  Thus, at the start of each transaction, we
 * push a null element which we use to mark the start of the transaction.
 * Then when we need to rollback, we just keep popping from the stack and
 * executing the rollbacks until we reach our first null element, then we stop.
 */
public class Database {

	/* Our actual store of values for the database */
	private final HashMap<String, Integer> db;

	/* Used to keep track of the different transaction blocks and
	 * what we need to rollback for each one. */
	private final Stack<Rollback> rollbacks;
	
	public Database() {
		this.db = new HashMap<String, Integer>();
		this.rollbacks = new Stack<Rollback>();
	}

	/**
	 * Essentially puts a value in a hash map store.  We <i>could</i> 
	 * check to make sure that the variable name adhere to some standard 
	 * naming convention such as starting with a letter, no special chars, etc...,
	 * but I see no problem in having numbers behaving like variables or vars
	 * with special characters in them for this particular database 
	 * implementation, so I just used the input string as is for the var name.
	 */
	public void set(String var, Integer val) {
		Integer prev = db.get(var);
		// If the rollbacks stack is empty, it means we are not
		// inside a transaction block, so don't worry about it.
		if (!rollbacks.isEmpty()) {
			rollbacks.push(new Rollback(var, prev));
		}
		db.put(var, val);
	}

	public Integer get(String var) {
		return db.get(var);
	}

	/**
	 * Pushes a null element to the rollbacks stack.  A null element portrays
	 * the start of a transaction block.  
	 */
	public void begin() {
		rollbacks.push(null);
	}

	/**
	 * Rollbacks only the most current transaction block, in other words,
	 * only executes the elements of the rollback stack until it reaches the 
	 * first null element.
	 */
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

	/**
	 * In this design, a commit simply means clearing the rollback stack
	 * which makes all commands final.
	 */
	public void commit() {
		rollbacks.clear();
	}

}

