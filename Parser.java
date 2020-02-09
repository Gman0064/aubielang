import java.util.ArrayList;

public class Parser {
    
    //ArrayList<String> ;
    
    public Parser(ArrayList<String> script) {
        for (String line : script) {
            if (line.contains("print")) {
                if (!line.endsWith(";")) {
                    System.out.println("Error: Missing ; at end of line [" + script.indexOf(line) + ", " + (line.length() - 1) + "]\n");
                    System.out.println(line);
                    String errorPos = "";
                    for (int x = 0; x < line.length(); x++) {
                        errorPos += " ";
                    }
                    System.out.println(errorPos + "^");
                    return;
                } else {
                    String parameter = line.trim().substring(5);
                    System.out.println(parameter);
                }
                
            } else if (line.contains("")) {

            }
        }
    }
}