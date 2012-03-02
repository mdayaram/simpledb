/**
 * Enum class used for running the appropriate actions on the given
 * database according to the given command string and arguments.
 */
public enum ParserElem {
	SET("SET", 2) {
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			int data;
			try {
				data = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return "ERROR:  '" + args[1] + "' is not an integer.";
			}
			db.set(args[0], data);
			return null;
		}
	},

	GET("GET", 1) {
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			Integer value = db.get(args[0]);
			if (value == null) return "NULL";
			else return value.toString();
		}
	},

	UNSET("UNSET", 1) {
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			db.set(args[0], null);
			return null;
		}
	},

	BEGIN("BEGIN", 0) { 
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			db.begin();
			return null;
		}
	},

	ROLLBACK("ROLLBACK", 0) {
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			try {
				db.rollback();
			} catch (InvalidRollbackException e) {
				return "INVALID ROLLBACK";
			}
			return null;
		}
	},

	COMMIT("COMMIT", 0) {
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			db.commit();
			return null;
		}
	},

	END("END", 0) {
		@Override
		public String runOn(Database db, String...args) {
			checkArgsLength(args);
			return "It's over!  Goodbye! ::killing myself::";
		}
	};

	private final String parseString;
	private final int numArgs;
	
	private ParserElem(String parseString, int numArgs) {
		this.parseString = parseString;
		this.numArgs = numArgs;
	}

	public abstract String runOn(Database db, String...args);

	public void checkArgsLength(String...args) {
		if (args == null || args.length != numArgs) {
			throw new IllegalArgumentException(
				parseString + " requires " + numArgs + " number of arguments.");
		}
	}

	public static ParserElem getElem(String cmdString) {
		if (cmdString == null) return null;

		cmdString = cmdString.trim();
		for(ParserElem elem : values()) {
			if (cmdString.equals(elem.parseString)) {
				return elem;
			}
		}

		return null;
	}
}
