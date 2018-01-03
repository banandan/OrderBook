package orderBook;

import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class stores the order table for store buy/sell orders
 * @author Balamurugan Anandan
 *
 */
class OrderTable {
	private PriorityQueue<Double> prices;
	private HashMap<Double, OrderList> priceMap;
	
	/**
	 * This constructor creates a new Order Table for buy/sell
	 * @param reverse specifies if the heap is min/max heap
	 */
	public OrderTable(boolean reverse){
		if(reverse){
			prices = new PriorityQueue<>(Collections.reverseOrder());
		}else{
			prices = new PriorityQueue<>();
		}
		priceMap = new HashMap<>();
	}
	
	/**
	 * This method creates a new price if the prices is not present in the order table
	 * @param price to be added
	 */
	public void addPrice(double price){
		if (!priceMap.containsKey(price)){
			prices.add(price);
			priceMap.put(price, new OrderList());
		}
	}
	
	/**
	 * This method removes a price if it exists in the order table
	 * @param price the price to be removed
	 */
	public void removePrice(double price){
		if(priceMap.containsKey(price)){
			priceMap.remove(price);
			prices.remove(price);
		}
	}
	
	/**
	 * Add an order to the order table
	 * @param order to be added
	 */
	public void addOrder(Order order){
		addPrice(order.getPrice());
		OrderList orderList = priceMap.get(order.getPrice());
		order.setOrderList(orderList);
		orderList.addOrder(order);
	}
	
	public boolean isEmpty(){
		return priceMap.isEmpty();
	}
	
	/**
	 * @return the minimum price in the order table
	 */
	public double getMinPrice(){
		return prices.peek();
	}
	
	/**
	 * @return the max price in the order table
	 */
	public double getMaxPrice(){
		return prices.peek();
	}
	
	/**
	 * This method returns the list of orders (ordered according to the timestamp) for a specific price
	 * @param price price of an order
	 * @return the list of orders for a specific price
	 */
	public OrderList getOrderList(double price){
		return priceMap.get(price);
	}
	
}
