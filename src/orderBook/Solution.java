package orderBook;

import java.util.*;

public class Solution {
	public static void main(String args[]) throws Exception {
		OrderBook ob = new OrderBook();
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.length() == 0) {
				break;
			}
			String[] orderString = line.split(" ");
			ob.processOrder(Integer.parseInt(orderString[1]), orderString[2],
					orderString[0], Integer.parseInt(orderString[3]),
					Double.parseDouble(orderString[4]));
		}
		scan.close();
	}
}