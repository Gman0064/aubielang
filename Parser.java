import java.util.ArrayList;

public class Parser {
    
    ArrayList<String> keywords = new ArrayList<String>() {
        "add",
        "sub",
        "mult",
        "div",
        "mod",
        "fact",
        "pow",
        "pi",
        "log",
        "rand"
    };

    ArrayList<String> std_functions = new ArrayList<String>() {
        "print"
    };
    
    public Parser(ArrayList<String> script) {
        for (String line : script) {
            // Seperate all of our words in a line
            String[] words = line.split(" ");
            // Look for keyword at first word, split away the parentheses
            //System.out.println(words);
            System.out.println(words[0]);
            if (words[0].equals("print")) {
                //We have a printfunction
                System.out.println("This is a print statement");
            } else {
                System.out.println("This is NOT a print statement");
            }
            
            // if (line.contains("printme")) {
                
            // } else {
            //     String parameter = line.trim().substring(5);
            //     System.out.println(parameter);
            // }
                
            // } else if (line.contains("")) {

            // }
        }
    }
}