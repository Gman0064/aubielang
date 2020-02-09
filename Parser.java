import java.util.*;

public class Parser {
    // Input stream of AUBIE commands
    public Scanner commands;
    // Variable storage
    public static HashMap<String,Variable> vars = new HashMap<String,Variable>();

    // Constructor, provides input stream for file parser
    public Parser(Scanner input) {
        commands = input;
    }

    // Processes commands
    public void main() {
        while (commands.hasNext()) {
            parse(commands.nextLine());
        }
    }

    // Parses lines for commands and values
    public Object parse(String statementt) {
        if (statementt == null) throw new Error("null");

        String statement = statementt.trim();
        String[] stateSplit = statement.split(" ");
        String command = stateSplit[0];
        String rest = "";
        
        if (statement.contains("\"")) {
            if (statement.charAt(0) == '"' && statement.charAt(0) == statement.charAt(statement.length()-1)) {
                String result = statement.substring(1,statement.length()-1);
                return result;
            }
        } else if (stateSplit.length == 1 && !statement.equals("")) {
            if (isDefined(command)) {
                if (vars.get(command).type.equals("num")) {
                    return parse(vars.get(command).value);
                } else {
                    return vars.get(command).value;
                }
            } else {
                try {
                    return Double.parseDouble(command);
                } catch (Exception e) {
                    throw new Error("Illegal parsing");
                }
            }
        }
        rest = statement.substring(command.length()).trim();
        if (command.equals("print")) {
            Object result = parse(rest);
            System.out.println(result.toString());
            return result;
        } else if (command.equals("def")) {
            if (def(rest)) {
               return "Defined";
            }

        } else if (command.equals("sum")) {
            return sum(rest);

        } else if (command.equals("sub")) {
            return subtract(rest);

        } else if (command.equals("mult")) {
            return mult(rest);

        } else if (command.equals("pow")) {
            return power(rest);

        } else if (command.equals("mod")) {
            return mod(rest);

        } else if (command.equals("log")) {
            return log(rest);

        } else if (command.equals("div")) {
            return div(rest);

        } else if (command.equals("fact")) {
            return fact(rest);

        } else if (command.startsWith("#")) {
            return "Commented Line";

        } else if (command.equals("")) {
            return "Empty Line";

        }else if (command.equals("equals")){
                return equals(rest);

        } else if (command.equals("nequals")){
                return nequals(rest);

        } else if (command.equals("lequals")){
                return lequals(rest);

        } else if (command.equals("gequals")){
                return gequals(rest);

        } else if (command.equals("gthan")){
                return gthan(rest);

        } else if (command.equals("lthan")){
                return lthan(rest);

        }
        throw new Error("Command Not Found");
    }

    // Checks if a variable is defined
    public boolean isDefined(String name) {
        if (vars.containsKey(name)) return true;
        return false;
    }

    // Defines a variable
    public boolean def(String args) {
        // name type value
        String[] arguments = args.split(" ");
        if (arguments.length < 3) throw new Error();
        
        String name = arguments[0];
        String type = arguments[1];

        if (!(type.equals("num") || type.equals("string"))) throw new Error();

        String args3 = "";
        for (int i = 2; i < arguments.length; i++) {
            args3 += arguments[i] + " ";
        }
        if (type.equals("num")) {
            Double value = (Double) parse(args3.trim());
            vars.put(name,new Variable(type,value.toString()));
            return true;
        } else {
            String value = (String) parse(args3.trim());
            vars.put(name,new Variable(type,value.toString()));
            return true;
        }
    }

    // Multiplies a list of numbers
    public double mult(String statement) {
        double product = 1;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        //Iterate through parameters of our operator
        for (int x = 0; x < words.length; x++) {
            try {
                Double num = (Double) parse(words[x]);
                nums.add(num);
            } catch(Exception e) {
                throw new Error("Invalid element type");
            }
        }
        //Multiply our numbers
        for (int x = 0; x < nums.size(); x++) {
            product *= nums.get(x);
        }
        return product;
    }

    // Adds a list of numbers
    public double sum(String statement) {
        double sum = 0;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        for (int x = 0; x < words.length; x++) {
            try {
                Double num = (Double) parse(words[x]);
                nums.add(num);
            } catch(Exception e) {
                throw new Error("Invalid element type");
            }
        }
        for (int x = 0; x < nums.size(); x++) {
            sum += nums.get(x);
        }
        return sum;
    }

    // Raises first argument to the power of second argument
    public double power(String statement) {
        double pow = 1;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        if (words.length == 2) {
            for (int x = 0; x < words.length; x++) {
                try {
                    Double num = (Double) parse(words[x]);
                    nums.add(num);
                } catch(Exception e) {
                    throw new Error("Invalid element type");
                }
            }
            pow = Math.pow(nums.get(0), nums.get(1));
            return pow;
        } else {
            throw new Error("Invalid arguments");
        }
        
    }

    // Subtracts the numbers in a list from the first given argument
    public double subtract(String statement) {
        double result = 0;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        for (int x = 0; x < words.length; x++) {
            try {
                Double num = (Double) parse(words[x]);
                nums.add(num);
            } catch(Exception e) {
                throw new Error("Invalid element type");
            }
        }
        for (int x = 0; x < nums.size(); x++) {
            if (x == 0) {
                result = nums.get(0);
            } else {
                result -= nums.get(x);
            }
        }
        return result;
    }

    // Performs x mod y given x as first argument and y as second argument
    public double mod(String statement) {
        double modulus = -1.0;
        String[] words = statement.split(" ");
        if (words.length != 2) {
            throw new Error("Invalid arguments");
        } else {
            ArrayList<Double> nums = new ArrayList<Double>();
            // Iterate through our string for numbers to add
            for (int x = 0; x < 2; x++) {
                try {
                    Double num = (Double) parse(words[x]);
                    nums.add(num);
                } catch(Exception e) {
                    throw new Error("Invalid element type");
                }
            }
            modulus = nums.get(0);
            Double numB = nums.get(1);
            while (modulus >= numB) {
                modulus = modulus - numB;
            }
        }
        return modulus;
    }

    // Divides the first number by a list of numbers 
    public double div(String statement) {
        double div = 1;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        for (int x = 0; x < words.length; x++) {
            try {
                Double num = (Double) parse(words[x]);
                nums.add(num);
            } catch(Exception e) {
                throw new Error("Invalid element type");
            }
        }

        for (int x = 0; x < nums.size(); x++) {
            if (x == 0) {
                div = nums.get(0);
            } else {
                div /= nums.get(x);
            }
        }
        return div;
    }

    // Performs log base x of y
    public double log(String statement) {
        double logVal = 0.0;
        String[] words = statement.split(" ");
        if (words.length != 2) {
            throw new Error("Invalid arguments");
        } else {
            ArrayList<Double> nums = new ArrayList<Double>();
            // Iterate through our string for numbers to add
            for (int x = 0; x < 2; x++) {
                try {
                    Double num = (Double) parse(words[x]);
                    nums.add(num);
                } catch(Exception e) {
                    throw new Error("Invalid element type");
                }
            }
            logVal = Math.log(nums.get(1)) / Math.log(nums.get(0));
        }
        return logVal;
    }

    // Performs x factorial, where x is treated as a whole number regardless of precision
    public double fact(String statement) {
        double result = 0;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();

        //Iterate through our string for numbers to add
        for (int x = 0; x < words.length; x++) {
            try {
                Double num = (Double) parse(words[x]);
                nums.add(num);
            } catch(Exception e) {
                throw new Error("Invalid element type");
            }
        }
        result = nums.get(0);
        if(result == 0) {
            return 0;
        } 
        else {
            result = 1;
            for (int i = 1; i <= nums.get(0); i++)
            result *= i;
        }
        return result;     
    }

    // Checks if two values are equal
    public boolean equals(String statement) {
        String[] words = statement.split(" ");
        if(words.length == 2)
        {
            Object a = parse(words[0]);
            Object b = parse(words[1]);
            return a.equals(b);
        }
        throw new Error("Invalid Arguments");
    }

    // Checks if two values are not equal
    public boolean nequals(String statement) {
        return !equals(statement);
    }

    // Checks if two 
    public boolean lequals(String statement) {
        String[] words = statement.split(" ");
        if(words.length == 2)
        {
            Object a = parse(words[0]);
            Object b = parse(words[1]);
            return comparing(a,b) <= 0;
        }
        throw new Error("Invalid arguments");
    }

    public boolean gequals(String statement) {
        String[] words = statement.split(" ");
        if(words.length == 2)
        {
            Object a = parse(words[0]);
            Object b = parse(words[1]);
            return comparing(a,b) >= 0;
        }
        throw new Error("Invalid arguments");
    }

    public boolean lthan(String statement) {
        String[] words = statement.split(" ");
        if(words.length == 2)
        {
            Object a = parse(words[0]);
            Object b = parse(words[1]);
            return comparing(a,b) < 0;
        }
        throw new Error("Invalid arguments");
    }

    public boolean gthan(String statement) {
        String[] words = statement.split(" ");
        if(words.length == 2)
        {
            Object a = parse(words[0]);
            Object b = parse(words[1]);
            return comparing(a,b) > 0;
        }
        throw new Error("Invalid arguments");
    }

    public int comparing(Object obj1, Object obj2) {
        if (obj1 instanceof Double && obj2 instanceof Double) {
            return Double.compare((Double) obj1, (Double) obj2);
        } else if (obj1 instanceof String && obj2 instanceof String) {
            return obj1.toString().compareTo(obj2.toString());
        } else {
            throw new Error("Type Mismatch Error");
        }
    }
}