package orderBook;

import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

class OrderTable {
	private PriorityQueue<Double> prices;
	private HashMap<Double, OrderList> priceMap;
	
	public OrderTable(boolean reverse){
		if(reverse){
			prices = new PriorityQueue<>(Collections.reverseOrder());
		}else{
			prices = new PriorityQueue<>();
		}
		priceMap = new HashMap<>();
	}
	
	public void addPrice(double price){
		if (!priceMap.containsKey(price)){
			prices.add(price);
			priceMap.put(price, new OrderList());
		}
	}
	
	public void removePrice(double price){
		if(priceMap.containsKey(price)){
			priceMap.remove(price);
			prices.remove(price);
		}
	}
	
	public void addOrder(Order order){
		addPrice(order.getPrice());
		OrderList orderList = priceMap.get(order.getPrice());
		order.setOrderList(orderList);
		orderList.addOrder(order);
	}
	
	public boolean isEmpty(){
		return priceMap.isEmpty();
	}
	
	public double getMinPrice(){
		return prices.peek();
	}
	
	public double getMaxPrice(){
		return prices.peek();
	}
	
	public OrderList getOrderList(double price){
		return priceMap.get(price);
	}
	
}
