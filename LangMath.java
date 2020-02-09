import java.util.*;

public class LangMath {

    private HashMap<String, Object[]> variables = Parser.variables;

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