import java.util.HashMap;
import java.util.Scanner;

public class Function {
	HashMap<Integer,String[]> vars;
	String sc;

	public Function(HashMap<Integer,String[]> vars, String cmds) {
		this.vars = vars;
		this.sc = cmds;
	}
}