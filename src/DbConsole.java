import java.io.*;
import java.util.*;

public class DbConsole {

	public static String parse(String input, Database db) {
		if (input == null || input.isEmpty()) return null;
		String[] cmd = input.split("\\s+");
		if (cmd.length == 0) return null;

		ParserElem elem = ParserElem.getElem(cmd[0]);
		if (elem == null) return "INVALID COMMAND";

		String[] args = Arrays.copyOfRange(cmd, 1, cmd.length);
		return elem.runOn(db, args);
	}

	public static void main(String...args) {
		Database simpledb = new Database();
		System.out.println("READY!");
	}
}

