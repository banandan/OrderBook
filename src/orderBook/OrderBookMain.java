package orderBook;

import java.util.*;

/**
 * This class is the main entry point which parses the input and sent
 * to the Order Book for further processing
 * @author Balamurugan Anandan
 *
 */
public class OrderBookMain {
	public static void main(String args[]) throws Exception {
		OrderBook ob = new OrderBook();
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