import java.util.*;

public class Parser {
    public Scanner commands;
    public static HashMap<String,Variable> vars = new HashMap<String,Variable>();


    public Parser(Scanner input) {
        commands = input;
    }

    public void main() {
        while (commands.hasNext()) {
            parse(commands.nextLine());
        }
    }

    public Object parse(String statementt) {
        if (statementt == null) throw new Error("null");
        String statement = statementt.trim();
        String[] stateSplit = statement.split(" ");
        String command = stateSplit[0];
        String rest = "";
        
        if (stateSplit.length == 1 && !statementt.equals("")) {
            if ((command.charAt(0) == command.charAt(command.length() - 1)) && (command.charAt(0) == '"')) {
                return command.substring(1,command.length()-1);
            }
            else if (isDefined(command)) {
                if (vars.get(command).type.equals("num")) {
                    return parse(vars.get(command).value);
                } else {
                    return vars.get(command).value;
                }
            }
            else {
                try {
                    return Double.parseDouble(command);
                } catch (Exception e) {}
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
            return "";
        }
        throw new Error("Hi");
    }

    public boolean isDefined(String name) {
        if (vars.containsKey(name)) return true;
        return false;
    }

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

    public double power(String statement) {
        double pow = 1;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        if (words.length <= 2) {
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

    public double mod(String statement) {
        double modulus = -1.0;
        String[] words = statement.split(" ");
        if (words.length > 2) {
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

    public double log(String statement) {
        double logVal = 0.0;
        String[] words = statement.split(" ");
        if (words.length > 2) {
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
}