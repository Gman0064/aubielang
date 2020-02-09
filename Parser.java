import java.util.*;

public class Parser {
    public Scanner commands;
    public HashMap<String, Object[]> variables = new HashMap<String, Object[]>();

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
                return "Fuck";
            }
        } else if (command.equals("sum")) {
            return sum(statement.substring(command.length() + 1));
        } else if (command.equals("sub")) {
            return subtract(statement.substring(command.length() + 1));
        } else if (command.equals("mult")) {
            return mult(statement.substring(command.length() + 1));
        } else if (command.equals("pow")) {
            return power(statement.substring(command.length() + 1));
        } else if (command.equals("mod")) {
            return mod(statement.substring(command.length() + 1));
        } else if (command.equals("log")) {
            return log(statement.substring(command.length() + 1));
        } else if (command.equals("div")) {
            return div(statement.substring(command.length() + 1));
        } else if (command.equals("fact")) {
            return fact(statement.substring(command.length() + 1));
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

    public double mult(String statement) {
        double product = 1;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        for (int x = 0; x < words.length; x++) {
            // Check if this is defined as a variable
            if (variables.get(words[x]) != null) {
                // Check if the variable value is not null
                if (variables.get(words[x])[1] != null) {
                    Object value = variables.get(words[x])[1];
                    if (value instanceof Double) {
                        nums.add(Double.parseDouble(value.toString()));
                    } else {
                        System.out.println("Variable is not a number");
                    }
                } else {
                    System.out.println("Variable is null");
                }
            // This is not a variable, so try to parse it as a number
            } else {
                nums.add(Double.parseDouble(words[x]));
            }
        }

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
            // Check if this is defined as a variable
            if (variables.get(words[x]) != null) {
                // Check if the variables value is not null
                if (variables.get(words[x])[1] != null) {
                    nums.add(Double.parseDouble(variables.get(words[x])[1].toString()));
                } else {
                    System.out.println("Variable is null");
                }
                // This is not a variable, so try to parse it as a number
            } else {
                nums.add(Double.parseDouble(words[x]));
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
        for (int x = 0; x < words.length; x++) {
            // Check if this is defined as a variable
            if (variables.get(words[x]) != null) {
                // Check if the variables value is not null
                Object value = variables.get(words[x])[1];
                if (variables.get(words[x])[1] != null) {
                    nums.add(Double.parseDouble(value.toString()));
                } else if (words.length > 2) {
                    System.out.println("Error! Too many exponents!");
                } else {
                    System.out.println("Variable is null");
                }
                // This is not a variable, so try to parse it as a number
            } else {
                nums.add(Double.parseDouble(words[x]));
            }
        }
        pow = Math.pow(nums.get(0), nums.get(1));
        return pow;
    }

    public double subtract(String statement) {
        double result = 0;
        String[] words = statement.split(" ");
        ArrayList<Double> nums = new ArrayList<Double>();
        // Iterate through our string for numbers to add
        for (int x = 0; x < words.length; x++) {
            // Check if this is defined as a variable
            if (variables.get(words[x]) != null) {
                // Check if the variable value is not null
                if (variables.get(words[x])[1] != null) {
                    Object value = variables.get(words[x])[1];
                    if (value instanceof Double) {
                        nums.add(Double.parseDouble(value.toString()));
                    } else {
                        System.out.println("Variable is not a number");
                    }
                } else {
                    System.out.println("Variable is null");
                }
                // This is not a variable, so try to parse it as a number
            } else {
                nums.add(Double.parseDouble(words[x]));
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
            System.out.print("2 numbers please!");
            throw new Error();
        } else {
            ArrayList<Double> nums = new ArrayList<Double>();
            // Iterate through our string for numbers to add
            for (int x = 0; x < 2; x++) {
                // Check if this is defined as a variable
                if (variables.get(words[x]) != null) {
                    // Check if the variables value is not null
                    if (variables.get(words[x])[1] != null) {
                        nums.add(Double.parseDouble(variables.get(words[x])[1].toString()));
                    } else {
                        System.out.println("Variable is null");
                        throw new Error();
                    }
                    // This is not a variable, so try to parse it as a number
                } else {
                    nums.add(Double.parseDouble(words[x]));
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
            // Check if this is defined as a variable
            if (variables.get(words[x]) != null) {
                Object value = variables.get(words[x])[1];
                // Check if the variables value is not null
                if (variables.get(words[x])[1] != null) {
                    nums.add(Double.parseDouble(value.toString()));
                } else {
                    System.out.println("Variable is null");
                }
                // This is not a variable, so try to parse it as a number
            } else {
                nums.add(Double.parseDouble(words[x]));
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
            System.out.print("2 numbers please!");
            throw new Error();
        } else {
            ArrayList<Double> nums = new ArrayList<Double>();
            // Iterate through our string for numbers to add
            for (int x = 0; x < 2; x++) {
                // Check if this is defined as a variable
                if (variables.get(words[x]) != null) {
                    // Check if the variables value is not null
                    if (variables.get(words[x])[1] != null) {
                        nums.add(Double.parseDouble(variables.get(words[x])[1].toString()));
                    } else {
                        System.out.println("Variable is null");
                        throw new Error();
                    }
                    // This is not a variable, so try to parse it as a number
                } else {
                    nums.add(Double.parseDouble(words[x]));
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
            //Check if this is defined as a variable
            if(variables.get(words[x]) != null) {
                //Check if the variable value is not null
                if(variables.get(words[x])[1] != null) {
                    Object value = variables.get(words[x])[1];
                    if (value instanceof Double) {
                        nums.add(Double.parseDouble(value.toString()));
                    } else {
                        System.out.println("Variable is not a number");
                    }
                } else {
                    System.out.println("Variable is null");
                }
            //This is not a variable, so try to parse it as a number
            } else {
                nums.add(Double.parseDouble(words[x]));
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
        }
        return result;     
    }
}