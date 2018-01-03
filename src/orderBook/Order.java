package orderBook;

enum Side {
	BUY, SELL;
}

enum OrderType{
	LIMIT, MARKET, MODIFY, CANCEL;
}

/**
 * This class stores the meta information associated with an order
 * @author Balamurugan Anandan
 *
 */
class Order {
	private int orderId;
	private int volume;
	private double price;
	private OrderType orderType;
	private Side side;
	private OrderList orderList;
	private Order next;
	private Order prev;
	
	/**
	 * This constructor creates a new order
	 * @param orderId unique order id
	 * @param orderType type of the order
	 * @param side buy/sell order
	 * @param volume quantity of the order
	 * @param price price of the order
	 */
	public Order(int orderId, OrderType orderType, Side side, int volume, double price){
		this.orderId = orderId;
		this.volume = volume;
		this.price = price;
		this.orderType = orderType;
		this.side = side;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

	public Order getNext() {
		return next;
	}

	public void setNext(Order next) {
		this.next = next;
	}

	public Order getPrev() {
		return prev;
	}

	public void setPrev(Order prev) {
		this.prev = prev;
	}

}
