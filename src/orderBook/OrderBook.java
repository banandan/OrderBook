package orderBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OrderBook {
	private OrderTable buys;
	private OrderTable sells;
	private Map<Integer, Order> orderMap;
	
	public OrderBook(){
		this.buys = new  OrderTable(true);
		this.sells = new OrderTable(false);
		this.orderMap = new HashMap<>();
	}
	
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
	
	public void matchOrder(Order order){
		List<Trade> trades;
		if(order.side == Side.BUY){
			trades = matchBuyOrder(order);
			if(order.volume > 0){
				this.orderMap.put(order.orderId, order);
				this.buys.addOrder(order);
			}
		}else{
			trades = matchSellOrder(order);
			if(order.volume > 0){
				this.orderMap.put(order.orderId, order);
				this.sells.addOrder(order);
			}
		}
		printTrades(trades);
	}
	
	public void modifyOrder(int orderId, OrderType orderType, int volume, double price){
		if(orderMap.containsKey(orderId)){
			Order order = orderMap.get(orderId);
			if(price == order.price){
				order.volume = volume;
			}else{
				this.removeOrder(order);
				order.price = price;
				order.volume = volume;
				this.matchOrder(order);
			}
		}
	}
	
	public void cancelOrder(int orderId){
		if(orderMap.containsKey(orderId)){
			Order order = orderMap.get(orderId);
			this.removeOrder(order);
		}
	}
	
	public void removeOrder(Order order){
		OrderList ol = order.orderList;
		double price = order.price;
		this.orderMap.remove(order.orderId);
		ol.removeOrder(order);
		if(ol.isEmpty()){
			switch(order.side){
				case BUY:
					buys.removePrice(price);
					break;
				case SELL:
					sells.removePrice(price);
					break;	
			}
		}
	}
	
	public void printTrades(List<Trade> trades){
		for(Trade trade: trades){
			System.out.println(trade);
		}
	}
	
	public List<Trade> matchSellOrder(Order order){
		List<Trade> trades = new ArrayList<Trade>();
		while(order.volume > 0 && !buys.isEmpty()){
			double maxPrice = buys.getMaxPrice();
			if(order.orderType == OrderType.LIMIT && maxPrice < order.price){
				break;
			}
			OrderList ol = buys.getOrderList(maxPrice);
			Order curOrder = ol.head;
			while(order.volume > 0 && curOrder != null){
				Trade trade = new Trade();
				if(curOrder.volume <= order.volume){
					order.volume -= curOrder.volume;
					trade.Init(order.orderId, curOrder.orderId, curOrder.volume, curOrder.price);
					this.removeOrder(curOrder);
					curOrder = ol.head;
				}else{
					trade.Init(order.orderId, curOrder.orderId, order.volume, curOrder.price);
					curOrder.volume -= order.volume;
					order.volume = 0;
				}
				trades.add(trade);
			}
		}
		return trades;
	}
	
	public List<Trade> matchBuyOrder(Order order){
		List<Trade> trades = new ArrayList<Trade>();
		while(order.volume > 0 && !sells.isEmpty()){
			double minPrice = sells.getMinPrice();
			if(order.orderType == OrderType.LIMIT &&  minPrice > order.price){
				break;
			}
			OrderList ol = sells.getOrderList(minPrice);
			Order curOrder = ol.head;
			while(order.volume > 0 && curOrder != null){
				Trade trade = new Trade();
				if(curOrder.volume <= order.volume){
					order.volume -= curOrder.volume;
					trade.Init(order.orderId, curOrder.orderId, curOrder.volume, curOrder.price);
					this.removeOrder(curOrder);
					curOrder = ol.head;
				}else{
					trade.Init(order.orderId, curOrder.orderId, order.volume, curOrder.price);
					curOrder.volume -= order.volume;
					order.volume = 0;
				}
				trades.add(trade);
			}
		}
		return trades;
	}
}
