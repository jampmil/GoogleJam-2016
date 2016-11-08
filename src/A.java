
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class A {

	public static void main(String[] args) throws Exception {
		final String EXCERCISE = "A";

		boolean sysInput = true;

		InputStream is = sysInput ? System.in : new FileInputStream(EXCERCISE + File.separator + "input.txt");
		String outString = "";

		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(is)));
		int t = in.nextInt();

		for (int i = 1; i <= t; ++i) {
			int n = in.nextInt();

			boolean[] nums = new boolean[10];
			for (int j = 0; j < nums.length; j++) {
				nums[j] = false;
			}

			int max = 99999999;
			boolean found = false;
			int lastNum = n;
			for (int j = 1; j < max && !found; j++) {
				String sNumber = "" + (n * j);

				boolean notYet = false;
				for (int k = 0; k < nums.length; k++) {
					if (sNumber.contains("" + k))
						nums[k] = true;

					if (nums[k] == false)
						notYet = true;
				}

				if (!notYet) {
					found = true;
					lastNum = n * j;
				}
			}

			String ans = found ? "" + lastNum : "INSOMNIA";

			String line = "Case #" + i + ": " + ans;
			System.out.println(line);
			outString += line + "\r\n";
		}
		if (!sysInput) {
			PrintWriter writer = new PrintWriter(EXCERCISE + File.separator + "output.txt", "UTF-8");
			writer.println(outString);
			writer.close();
		}
	}

}
