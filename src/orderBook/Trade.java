package orderBook;

/**
 * This class stores the meta information associated with a trade
 * 
 * @author Balamurugan Anandan
 *
 */
public class Trade {
	private int orderId1;
	private int orderId2;
	private int volume;
	private double price;

	/**
	 * @param orderId1
	 *            order id involved in the trade
	 * @param orderId2
	 *            order id involved in the trade
	 * @param volume
	 *            volume transferred in the trade
	 * @param price
	 *            price at which the trade is executed
	 */
	public void Init(int orderId1, int orderId2, int volume, double price) {
		this.orderId1 = orderId1;
		this.orderId2 = orderId2;
		this.volume = volume;
		this.price = price;
	}

	public String toString() {
		String result = String.format("match %d %d %d %.02f", this.orderId1,
				this.orderId2, this.volume, this.price);
		return result;
	}
}
