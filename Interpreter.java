import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Interpreter {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "";
        ArrayList<String> program = new ArrayList<String>();

        if (args.length > 0) {
            path = args[0];
            Scanner fileInput = new Scanner(new File(path));

            while(fileInput.hasNext()) {
                program.add(fileInput.nextLine());
            }

            fileInput.close();

            System.out.println("--- START FILESTREAM ---");
            for (int x = 0; x < program.size(); x++) {
                System.out.println(x + " " + program.get(x));
            }
            System.out.println("--- END FILESTREAM ---\n");

            Parser script = new Parser(program);
        } else {
            System.out.println("Error: Could not find path to script.");
        }
    }
}