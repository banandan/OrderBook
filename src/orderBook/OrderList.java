package orderBook;

class OrderList {
	Order head;
	Order tail;
	
	public OrderList(){
		this.head = null;
		this.tail = null;
	}

	public void addOrder(Order order){
		if(this.head == null){
			this.head = order;
			this.tail = order;
		} else{
			this.tail.next = order;
			order.prev = this.tail;
			this.tail = order;
		}
	}
	
	public void removeOrder(Order order){
		if(order.prev == null){
			this.head = order.next;
		}else{
			order.prev.next = order.next;
		}
		if(order.next == null){
			this.tail = order.prev;
		}else{
			order.next.prev = order.prev;
		}
	}
	
	public boolean isEmpty(){
		return this.head==null;
	}
}
