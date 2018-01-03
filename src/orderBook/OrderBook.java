package orderBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class stores the order book of sells and buys for a specific symbol
 * @author Balamurugan Anandan
 *
 */
class OrderBook {
	private OrderTable buys;
	private OrderTable sells;
	private Map<Integer, Order> orderMap;
	

	/**
	 * constructs a new order book for a stock
	 */
	public OrderBook(){
		this.buys = new  OrderTable(true);
		this.sells = new OrderTable(false);
		this.orderMap = new HashMap<>();
	}
	
	
	/**
	 * @param orderId uniquely identifies/timestamps an order
	 * @param sideStr buy/sell order
	 * @param orderTypeStr limit/market/cancel/update order
	 * @param volume specifies the quantity
	 * @param price price of the stock
	 */
	public void processOrder(int orderId, String sideStr, String orderTypeStr, int volume, double price){
		Side side;
		if(sideStr.equals("buy")){
			side = Side.BUY;
		}else{
			side = Side.SELL;
		}
		OrderType orderType;
		if(orderTypeStr.equals("limit")){
			orderType = OrderType.LIMIT;
		}else if(orderTypeStr.equals("market")){
			orderType = OrderType.MARKET;
		}else if(orderTypeStr.equals("modify")){
			orderType = OrderType.MODIFY;
		} else{
			orderType = OrderType.CANCEL;
		}
		Order order;
		switch(orderType){
		case LIMIT:
			order = new Order(orderId, orderType, side, volume, price);
			matchOrder(order);
			break;
		case MARKET:
			order = new Order(orderId, orderType, side, volume, price);
			matchOrder(order);
			break;
		case MODIFY:
			modifyOrder(orderId, orderType, volume, price);
			break;
		case CANCEL:
			cancelOrder(orderId);
			break;
		}
	}
	
	/**
	 * This method matches the order with order book
	 * @param order the order to be processed
	 */
	private void matchOrder(Order order){
		List<Trade> trades;
		if(order.getSide() == Side.BUY){
			trades = matchBuyOrder(order);
			if(order.getOrderType() == OrderType.LIMIT && order.getVolume() > 0){
				//If the order is limit and there is an non-zero volume then add to order table
				this.orderMap.put(order.getOrderId(), order);
				this.buys.addOrder(order);
			}
		}else{
			trades = matchSellOrder(order);
			if(order.getOrderType() == OrderType.LIMIT && order.getVolume() > 0){
				//If the order is limit and there is an non-zero volume then add to order table
				this.orderMap.put(order.getOrderId(), order);
				this.sells.addOrder(order);
			}
		}
		printTrades(trades);
	}
	
	
	/**
	 * Modify an existing order
	 * @param orderId id of an existing order
	 * @param orderType type of the existing order
	 * @param volume quantity of the new order
	 * @param price price of the new order
	 */
	private void modifyOrder(int orderId, OrderType orderType, int volume, double price){
		if(orderMap.containsKey(orderId)){
			Order order = orderMap.get(orderId);
			if(price == order.getPrice()){
				//If the price is the same
				order.setVolume(volume);
			}else{
				//If the price is modified then add the order to a different list
				this.removeOrder(order);
				order.setPrice(price);
				order.setVolume(volume);
				this.matchOrder(order);
			}
		}
	}
	
	/**
	 * Cancels an existing order
	 * @param orderId Id of the order to be removed
	 */
	private void cancelOrder(int orderId){
		if(orderMap.containsKey(orderId)){
			Order order = orderMap.get(orderId);
			this.removeOrder(order);
		}
	}
	
	/**
	 * Removes an order from the order list
	 * @param order to be removed from order list
	 */
	private void removeOrder(Order order){
		OrderList ol = order.getOrderList();
		double price = order.getPrice();
		this.orderMap.remove(order.getOrderId());
		ol.removeOrder(order);
		if(ol.isEmpty()){
			switch(order.getSide()){
				case BUY:
					buys.removePrice(price);
					break;
				case SELL:
					sells.removePrice(price);
					break;	
			}
		}
	}
	
	/**
	 * Prints the trades that have been executed
	 * @param trades executed trades
	 */
	private void printTrades(List<Trade> trades){
		for(Trade trade: trades){
			System.out.println(trade);
		}
	}
	
	/**
	 * Matches the sell order
	 * @param order order to be executed
	 * @return list of trades executed
	 */
	private List<Trade> matchSellOrder(Order order){
		List<Trade> trades = new ArrayList<Trade>();
		while(order.getVolume() > 0 && !buys.isEmpty()){
			double maxPrice = buys.getMaxPrice();
			if(order.getOrderType() == OrderType.LIMIT && maxPrice < order.getPrice()){
				//If the order is limit order and max price is less than the limit price
				break;
			}
			OrderList ol = buys.getOrderList(maxPrice);
			Order curOrder = ol.getHead();
			while(order.getVolume() > 0 && curOrder != null){
				Trade trade = new Trade();
				if(curOrder.getVolume() <= order.getVolume()){
					order.setVolume(order.getVolume() - curOrder.getVolume());
					trade.Init(order.getOrderId(), curOrder.getOrderId(), curOrder.getVolume(), curOrder.getPrice());
					this.removeOrder(curOrder);
					curOrder = ol.getHead();
				}else{
					trade.Init(order.getOrderId(), curOrder.getOrderId(), order.getVolume(), curOrder.getPrice());
					curOrder.setVolume(curOrder.getVolume() - order.getVolume());
					order.setVolume(0);
				}
				trades.add(trade);
			}
		}
		return trades;
	}
	
	/**
	 * Matches the buy order
	 * @param order order to be executed
	 * @return the list of trades matched
	 */
	private List<Trade> matchBuyOrder(Order order){
		List<Trade> trades = new ArrayList<Trade>();
		while(order.getVolume() > 0 && !sells.isEmpty()){
			double minPrice = sells.getMinPrice();
			if(order.getOrderType() == OrderType.LIMIT &&  minPrice > order.getPrice()){
				//If the order is limit order and min price is greater than the limit price
				break;
			}
			OrderList ol = sells.getOrderList(minPrice);
			Order curOrder = ol.getHead();
			while(order.getVolume() > 0 && curOrder != null){
				Trade trade = new Trade();
				if(curOrder.getVolume() <= order.getVolume()){
					order.setVolume(order.getVolume() - curOrder.getVolume());
					trade.Init(order.getOrderId(), curOrder.getOrderId(), curOrder.getVolume(), curOrder.getPrice());
					this.removeOrder(curOrder);
					curOrder = ol.getHead();
				}else{
					trade.Init(order.getOrderId(), curOrder.getOrderId(), order.getVolume(), curOrder.getPrice());
					curOrder.setVolume(curOrder.getVolume() - order.getVolume());
					order.setVolume(0);
				}
				trades.add(trade);
			}
		}
		return trades;
	}
}
