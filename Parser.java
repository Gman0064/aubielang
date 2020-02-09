import java.util.*;

public class Parser {
    public AubieError err = new AubieError();
    static int programCounter = 0;
    String[] arr1 = {"done","next","loop","def","func","sum","sub","mult","pow","fact","div","if","elif","else","end","equals","nequals","lequals","gequals","lthan","gthan","print","mod","log"};
    String[] arr2 = {"num","string"};
    public HashSet<String> kwds = new HashSet<String>(Arrays.asList(arr1));
    public HashSet<String> types = new HashSet<String>(Arrays.asList(arr2));

    // Input stream of AUBIE commands
    public Scanner commands;
    // Variable storage
    public static HashMap<String,Variable> vars;
    // Function storage
    public static HashMap<String,Function> funcs = new HashMap<String,Function>();

    // Constructor, provides input stream for file parser
    public Parser(Scanner input, HashMap<String,Variable> varIns) {
        commands = input;
        vars = varIns;
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
        System.out.println(statement);
        String[] stateSplit = statement.split(" ");
        String command = stateSplit[0];
        String rest = "";
        if (statement.startsWith("#")) return "Commented Line";
        if (statement.contains("\"")) {
            if (statement.charAt(0) == '"' && statement.charAt(0) == statement.charAt(statement.length()-1)) {
                String result = statement.substring(1,statement.length()-1);
                return result;
            }
        } else if (stateSplit.length == 1 && !statement.equals("")) {
            if (isDefined(command)) {
                if (this.vars.get(command).type.equals("num")) {
                    return parse(this.vars.get(command).value);
                } else {
                    return this.vars.get(command).value;
                }
            } else {
                try {
                    return Double.parseDouble(command);
                } catch (Exception e) {
                    throw new Error("Parsing Error");
                }
            }
        }
        rest = statement.substring(command.length()).trim();
        if (command.equals("print")) {
            Object result = parse(rest);
            System.out.println(result.toString());
            return result;
        } else if (command.equals("def")) {
            def(rest);
            return "Variable Defined";

        } else if (command.equals("func")) {
            makeFunction(rest);
            return "Function Defined";

        } else if (funcs.containsKey(command)) {
            runFunc(command,rest);
            return "Function Ran";

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

        } else if (command.equals("loop")){
            return forLoop(rest);
        
        } else if (command.equals("if")) {
            ifMethod(rest);
            return "Yahoosie";

        }
        throw new Error("Command Not Found");
    }

    // Checks if a variable is defined
    public boolean isDefined(String name) {
        if (this.vars.containsKey(name)) return true;
        return false;
    }

    // Defines a variable
    public void def(String args) {
        // name type value
        String[] arguments = args.split(" ");
        if (arguments.length < 3) throw new Error("Invalid Arguments");
        
        String name = arguments[0];
        String type = arguments[1];

        if (!types.contains(type)) throw new Error("Invalid Type");

        if (kwds.contains(name)) throw new Error("Invalid Name");

        String args3 = "";
        for (int i = 2; i < arguments.length; i++) {
            args3 += arguments[i] + " ";
        }
        if (type.equals("num")) {
            Double value = (Double) parse(args3.trim());
            this.vars.put(name,new Variable(type,value.toString()));
        } else {
            String value = (String) parse(args3.trim());
            this.vars.put(name,new Variable(type,value.toString()));
        }
    }

    // Allows users to define functions with parameters
    // Parameters are given as: type name type name . . .
    public void makeFunction(String statement) {
        HashMap<Integer,String[]> varis = new HashMap<Integer,String[]>();
        String cmds = "";

        String[] args = statement.split(" ");

        if (args.length % 2 != 1) throw new Error("Bad Arguments");

        String name = args[0];

        if (kwds.contains(name)) throw new Error("Invalid Name");

        String curr;
        while (commands.hasNext()) {
            curr = commands.nextLine().trim();
            if (curr.equals("end")) break;
            cmds += curr + "\n";
        }

        for (int i = 1; i < args.length; i += 2) {
            String vt = args[i];
            String vn = args[i+1];

            if (!types.contains(vt)) throw new Error("Invalid Type");
            if (kwds.contains(vn)) throw new Error("Invalid Name");

            varis.put(i/2,new String[]{vn,vt});
        }

        Scanner funcScan = new Scanner(cmds);
        funcScan.useDelimiter("\n");
        funcs.put(name,new Function(varis,funcScan));
    }

    public void runFunc(String name, String args) {
        String[] argSplit = args.split(" ");
        Function f = funcs.get(name);
        HashMap<String,Variable> myVars = new HashMap<String,Variable>();

        if (argSplit.length != f.vars.size()) throw new Error("Baddio");
        for (int i = 0; i < argSplit.length; i++) {
            String vName = f.vars.get(i)[0];
            String vType = f.vars.get(i)[1];
            String vVal = argSplit[i];

            // Temp
            if (vType.equals("num")) {
                Double.parseDouble(vVal);
            }

            myVars.put(vName, new Variable(vType, vVal));
        }

        Parser p = new Parser(f.sc,myVars);
        while (p.commands.hasNext()) p.parse(p.commands.nextLine());
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
            if (numB <= 0) throw new Error();
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

    public boolean forLoop(String input)
    {
        String[] Input = input.split(" ");
        Double high = 0.0;
        if(Input.length == 1)
        {
            high = (Double) parse(Input[0]);
        }
        else
        {
            err.throwError(input, "syntax");
        }
        String nextInput = commands.nextLine().trim();
        ArrayList<String> totalCommands = new ArrayList<String>();
        while(!nextInput.equals("next"))
        {
            totalCommands.add(nextInput);
            nextInput = commands.nextLine().trim();
        }
        for(Double i = 0.0; i < high; i++)
        {
            for(int j = 0; j < totalCommands.size(); j++)
            {
                parse(totalCommands.get(j));
            }
        }
        return true;
    }
    public void ifMethod(String rest) {
        ArrayList<String> ifs = new ArrayList<String>();
        String curr = "";
        while (this.commands.hasNext()) {
            curr = this.commands.nextLine().trim();
            System.out.println("Curr: " + curr);
            if (curr.equals("done") || curr.equals("else") || curr.split(" ")[0].equals("elif")) {
                System.out.println("I Broke");
                break;
            }
            ifs.add(curr);
        }
        if ((Boolean) parse(rest)) {
            for (String cmd : ifs) {
                parse(cmd);
            }
            while (this.commands.hasNext()) {
                if (this.commands.nextLine().trim().equals("done")) break;
            }
        } else {
            if (curr.split(" ")[0].equals("elif")) {
                String expression = curr.substring(curr.split(" ")[0].length()).trim();
                ifMethod(expression);
            } else {
                ifMethod("equals 1 1");
            }
        }
    }
}