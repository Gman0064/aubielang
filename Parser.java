import java.util.*;

public class Parser {
    public Scanner commands;
    public static HashMap<String, Object[]> variables = new HashMap<String, Object[]>();

    LangMath math = new LangMath();
    LangUtil util = new LangUtil();

    // Variable Example: {'var1': ['num',12.0], 'var2': ['string','Hello']}
    public Parser(Scanner input) {
        commands = input;
    }

    public void main() {
        while (commands.hasNext()) {
            System.out.println(parse(commands.nextLine().trim()));
        }
    }

    public Object parse(String statement) {
        String[] stateSplit = statement.split(" ");
        String command = stateSplit[0];
        if (command.equals("def")) {
            if (def(statement.substring(command.length() + 1))) {
                return "Defined";
            } else {
                return "Incorrect statement";
            }
        } else if (command.equals("sum")) {
            return math.sum(statement.substring(command.length() + 1));
        } else if (command.equals("sub")) {
            return math.subtract(statement.substring(command.length() + 1));
        } else if (command.equals("mult")) {
            return math.mult(statement.substring(command.length() + 1));
        } else if (command.equals("pow")) {
            return math.power(statement.substring(command.length() + 1));
        } else if (command.equals("mod")) {
            return math.mod(statement.substring(command.length() + 1));
        } else if (command.equals("log")) {
            return math.log(statement.substring(command.length() + 1));
        } else if (command.equals("div")) {
            return math.div(statement.substring(command.length() + 1));
        } else if (command.equals("fact")) {
            return math.fact(statement.substring(command.length() + 1));
        } else if (command.startsWith("#")) {
            return "Commented Line";
        } else if (command.equals("")) {
            return "";
        }

        throw new Error();
    }

    public boolean def(String args) {
        String[] arguments = args.split(" ");
        if (arguments.length < 3) {
            return false;
        }

        String name = arguments[0];
        String type = arguments[1];
        if (!(type.equals("num") || type.equals("string"))) {
            System.out.println("   Parsing Error: Type \"" + type + "\" not recognized");
            throw new Error();
        }
        Object value;
        if (arguments.length == 3) {
            value = arguments[2];
            if (variables.containsKey(value)) {
                if (!type.equals(variables.get(value)[0])) {
                    System.out.println("   Parsing Error: Types do not match");
                    throw new Error();
                } else {
                    Object[] arr = { type, variables.get(value)[1] };
                    variables.put(name, arr);
                }
            } else {
                if (type.equals("num")) {
                    Double numVal = Double.parseDouble(value.toString());
                    variables.put(name, new Object[] { type, numVal });
                    return true;
                } else {
                    variables.put(name, new Object[] { type, value });
                    return true;
                }
            }
        } else {
            String nArgs = "";
            String[] newArgs = new String[arguments.length - 2];
            for (int i = 2; i < arguments.length; i++) {
                newArgs[i - 2] = arguments[i];
            }
            for (String arg : newArgs) {
                nArgs += arg + " ";
            }
            value = parse(nArgs.trim());
            variables.put(name, new Object[] { type, value });
            return true;
        }
        return false;
    }
}