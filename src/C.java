
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class C {

	public static void main(String[] args) throws Exception {
		final String EXCERCISE = "C";

		class Jamcoin {

			String number;
			int[] divisors;
			boolean isJamcoin;

			public Jamcoin(String number) {
				this.number = number;
				divisors = new int[9];
				isJamcoin = verifyJamcoin();
			}

			public boolean verifyJamcoin() {
				if (number.charAt(0) != '1' || number.charAt(number.length() - 1) != '1')
					return false;
				for (int i = 2; i <= 10; i++) {
					BigInteger inBase = new BigInteger(number, i);
					if (isPrime(inBase, i))
						return false;
				}
				return true;
			}

			public boolean isPrime(BigInteger num, int base) {
				if (num.compareTo(BigInteger.valueOf(2)) > 0 && num.mod(BigInteger.valueOf(2)) == BigInteger.ZERO) {
					// System.out.println(num + " is not prime");
					divisors[base - 2] = 2;
					// System.out.println(num + "->" + 2 + " | " + base);
					return false;
				}
				int top = num.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0 ? (int) Math.sqrt(num.intValue()) + 1
						: Integer.MAX_VALUE;
				for (int i = 3; i < top; i += 2) {
					if (num.mod(BigInteger.valueOf(i)) == BigInteger.ZERO) {
						divisors[base - 2] = i;
						// System.out.println(num + "->" + i + " | " + base);
						return false;
					}
				}
				// System.out.println(num + " is prime");
				return true;
			}

			public String toString() {
				String toString = number;
				if (!this.isJamcoin)
					toString += " NA";
				else
					for (int divisor : divisors) {
						toString += " " + divisor;
					}
				return toString;
			}

		}

		boolean sysInput = true;

		InputStream is = sysInput ? System.in : new FileInputStream(EXCERCISE + File.separator + "input.txt");
		String outString = "";

		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(is)));
		int t = in.nextInt();

		for (int i = 1; i <= t; ++i) {
			String line = "Case #" + i + ":";
			System.out.println(line);
			outString += line + "\r\n";

			int size = in.nextInt();
			int limit = in.nextInt();

			int round = 0;
			String initial = "1";
			for (int j = 0; j < size - 2; j++) {
				initial += "0";
			}
			initial += "1";
			String end = "";
			for (int j = 0; j < size; j++) {
				end += "1";
			}
			BigInteger iInitial = new BigInteger(initial, 2);
			BigInteger iEnd = new BigInteger(end, 2);

			int count = 0;
			for (BigInteger j = iInitial; j.compareTo(iEnd) <= 0 && count < limit; j = j.add(BigInteger.ONE)) {
				Jamcoin current = new Jamcoin(j.toString(2));
				if (current.isJamcoin) {
					count++;
					String line2 = current.toString();
					System.out.println(line2);
					outString += line2 + "\r\n";
				}
			}

		}
		if (!sysInput) {
			PrintWriter writer = new PrintWriter(EXCERCISE + File.separator + "output.txt", "UTF-8");
			writer.println(outString);
			writer.close();
		}
	}

}
