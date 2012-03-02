
public enum ParserElem {
	SET("SET", 2) {
		@Override
		public String runOn(Database db, String...args) {
			super.runOn(db, args);
			// throw the exception if it can't be parsed
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
			super.runOn(db, args);
			Integer value = db.get(args[0]);
			if (value == null) return "NULL";
			else return value.toString();
		}
	},

	UNSET("UNSET", 1) {
		@Override
		public String runOn(Database db, String...args) {
			super.runOn(db, args);
			db.set(args[0], null);
			return null;
		}
	},

	BEGIN("BEGIN", 0) { 
		@Override
		public String runOn(Database db, String...args) {
			super.runOn(db, args);
			db.begin();
			return null;
		}
	},

	ROLLBACK("ROLLBACK", 0) {
		@Override
		public String runOn(Database db, String...args) {
			super.runOn(db, args);
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
			super.runOn(db, args);
			db.commit();
			return null;
		}
	},

	END("END", 0) {
		@Override
		public String runOn(Database db, String...args) {
			super.runOn(db, args);
			return "It's over!  Goodbye! ::killing myself::";
		}
	};

	private final String parseString;
	private final int numArgs;
	private ParserElem(String parseString, int numArgs) {
		this.parseString = parseString;
		this.numArgs = numArgs;
	}

	public String getParseString() {
		return parseString;
	}

	public String runOn(Database db, String...args) {
		if (args == null || args.length != numArgs) {
			throw new IllegalArgumentException(
				parseString + " requires " + numArgs + " number of arguments.");
		}
		return null;
	}

	public static ParserElem getElem(String cmdString) {
		cmdString = cmdString.trim();
		for(ParserElem elem : values()) {
			if (elem.getParseString().equals(cmdString)) {
				return elem;
			}
		}
		return null;
	}
}
