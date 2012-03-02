/**
 * Mostly a composition class used to store the variable and value to 
 * set it to in case we need to rollback.  We could've used any generic
 * Pair class for this, but there's none included in default Java, so I 
 * figured if I was going to have to write a class to this, might as well
 * make it specific to rollbacks in case I wanted to add something later.
 */
public class Rollback {

	/** The variable modified during a transaction */
	private final String var;
	/** The value this variable held before the operation that altered it ran */
	private final Integer val;

	public Rollback(String var, Integer val) {
		this.var = var;
		this.val = val;
	}

	public String getVar() { return var; }
	public Integer getVal() { return val; }
}

