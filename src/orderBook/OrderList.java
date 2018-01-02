package orderBook;

class OrderList {
	private Order head;
	private Order tail;
	
	public Order getHead() {
		return head;
	}

	public void setHead(Order head) {
		this.head = head;
	}

	public Order getTail() {
		return tail;
	}

	public void setTail(Order tail) {
		this.tail = tail;
	}

	public OrderList(){
		this.head = null;
		this.tail = null;
	}

	public void addOrder(Order order){
		if(this.head == null){
			this.head = order;
			this.tail = order;
		} else{
			this.tail.setNext(order);
			order.setPrev(this.tail);
			this.tail = order;
		}
	}
	
	public void removeOrder(Order order){
		if(order.getPrev() == null){
			this.head = (order.getNext());
		}else{
			order.getPrev().setNext(order.getNext());
		}
		if(order.getNext() == null){
			this.tail = order.getPrev();
		}else{
			order.getNext().setPrev(order.getPrev());
		}
	}
	
	public boolean isEmpty(){
		return this.head==null;
	}
}
