import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class AubieLang {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length == 0) return;

		String fileName = args[0];

		if (fileName.length() < 4 || !fileName.substring(fileName.length()-4).equals(".aub")) {
			System.out.println("   Incorrect File Type: .aub file needed");
			return;
		}

		try {
			ArrayList<String> commands = new ArrayList<String>();
			Scanner code = new Scanner(new File(fileName));
			Parser parser = new Parser(code);
			parser.main();
		} catch(FileNotFoundException e) {
			System.out.println("   File \"" + fileName + "\" Not Found");
		}
	}
}