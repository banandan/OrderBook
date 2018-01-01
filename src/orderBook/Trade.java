package orderBook;

public class Trade {
	int orderId1;
	int orderId2;
	int volume;
	double price;
	
	public void Init(int orderId1, int orderId2, int volume, double price){
		this.orderId1 = orderId1;
		this.orderId2 = orderId2;
		this.volume = volume;
		this.price = price;
	}
	
	
	public String toString(){
		String result = String.format("match %d %d %d %.02f", this.orderId1, this.orderId2, this.volume, this.price);
		return result;
	}
}
