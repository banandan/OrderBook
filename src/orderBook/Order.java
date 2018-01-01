package orderBook;

enum Side {
	BUY, SELL;
}

enum OrderType{
	LIMIT, MARKET, MODIFY, CANCEL;
}

class Order {
	int orderId;
	int volume;
	double price;
	OrderType orderType;
	Side side;
	OrderList orderList;
	Order next;
	Order prev;
	
	public Order(int orderId, OrderType orderType, Side side, int volume, double price){
		this.orderId = orderId;
		this.volume = volume;
		this.price = price;
		this.orderType = orderType;
		this.side = side;
	}
	
	public int getOrderId(){
		return this.orderId;
	}
	
	public int getVolume(){
		return this.volume;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public OrderType getOrderType(){
		return this.orderType;
	}
	
}
