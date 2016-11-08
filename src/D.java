
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class D {

	public static String[] getAllLists(String[] elements, int lengthOfList) {
		// initialize our returned list with the number of elements calculated
		// above
		String[] allLists = new String[(int) Math.pow(elements.length, lengthOfList)];

		// lists of length 1 are just the original elements
		if (lengthOfList == 1)
			return elements;
		else {
			// the recursion--get all lists of length 3, length 2, all the way
			// up to 1
			String[] allSublists = getAllLists(elements, lengthOfList - 1);

			// append the sublists to each element
			int arrayIndex = 0;

			for (int i = 0; i < elements.length; i++) {
				for (int j = 0; j < allSublists.length; j++) {
					// add the newly appended combination to the list
					allLists[arrayIndex] = elements[i] + allSublists[j];
					arrayIndex++;
				}
			}

			return allLists;
		}
	}

	public static void main(String[] args) throws Exception {
		final String EXCERCISE = "D";

		class Tile {

			private String dist;

			public Tile(String dist) {
				this.dist = dist;
			}

			public String getSons(int complex) {
				if (complex == 1) {
					return dist;
				} else {
					char[] distChar = dist.toCharArray();
					String res = "";
					for (int i = 0; i < distChar.length; i++) {
						if (distChar[i] == 'G') {
							char[] current = new char[distChar.length];
							Arrays.fill(current, 'G');
							Tile newTile = new Tile(String.copyValueOf(current));
							res += newTile.getSons(complex - 1);

						} else {
							Tile newTile = new Tile(dist);
							res += newTile.getSons(complex - 1);
						}
					}
					return res;
				}
			}

		}

		class Artwork {
			String originalDist;
			int complexity;
			String fullArtwork;

			public Artwork(String originalDist, int complexity) {
				this.originalDist = originalDist;
				this.complexity = complexity;
				this.fullArtwork = calculateFullArtwork();
			}

			public String calculateFullArtwork() {
				Tile tile = new Tile(originalDist);
				return tile.getSons(complexity);
			}

		}

		boolean sysInput = false;

		InputStream is = sysInput ? System.in : new FileInputStream(EXCERCISE + File.separator + "input.txt");
		String outString = "";

		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(is)));
		int t = in.nextInt();

		for (int i = 1; i <= t; ++i) {
			int k = in.nextInt();
			int c = in.nextInt();
			int s = in.nextInt();

			int numRows = (int) Math.pow(2, k);
			int numCols = (int) Math.pow(k, c);

			// char[][] matrix = new char[numRows][numCols];
			ArrayList<char[]> matrix = new ArrayList<char[]>();
			String[] permutations = D.getAllLists(new String[] { "G", "L" }, k);
			for (int row = 0; row < permutations.length; row++) {
				Artwork art = new Artwork(permutations[row], c);
				char[] curretnArt = art.fullArtwork.toCharArray();
				// for (int col = 0; col < curretnArt.length; col++) {
				// matrix[row][col] = curretnArt[col];
				// }
				matrix.add(curretnArt);

			}

			int[] numLs = new int[numCols];
			for (int col = 0; col < numCols; col++) {
				int numL = 0;
				for (int row = 0; row < numRows; row++) {
					if (matrix.get(row)[col] == 'L')
						numL++;
				}
				numLs[col] = numL;
			}

			int tries = 1;
			ArrayList<Integer> finalRes = null;
			while (tries <= s && finalRes == null) {

				ArrayList<Integer> res = new ArrayList<>();
				for (int j = 0; j < numLs.length; j++) {
					if (numLs[j] <= tries)
						res.add(j);
				}
				if (res.size() >= tries) {
					finalRes = res;
				} else
					tries++;
			}

			String line = "Case #" + i + ":";
			if (finalRes == null)
				line += " IMPOSSIBLE";
			else
				for (int j = 0; j < tries; j++) {
					line += " " + (finalRes.get(j) + 1);
				}
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
