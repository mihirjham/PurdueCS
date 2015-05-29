import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
	Node<Item> head;
	Node<Item> tail;
	int size;

	public Deque()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return head == null;
	}
	
	public int size()
	{
		return this.size;
	}
		
	public void addFirst(Item item)
	{
		Node newNode = new Node(item);
		
		if(head == null && tail == null)
		{
			head = newNode;
			tail= newNode;
		}
		else
		{
			Node temp = head;
			newNode.next = temp;
			temp.prev = newNode;
			head = newNode;
			
		}
		size++;
	}
	
	public void addLast(Item item)
	{
		Node newNode = new Node(item);

		if(head == null && tail == null)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			Node temp = tail;
			temp.next = newNode;
			newNode.prev = temp; 
			tail = newNode;
		}
		size++;
	}
	
	public Item removeFirst()
	{
		Item item = null;

		if(isEmpty())
			throw new RuntimeException("Deque is empty.");

		if(head == tail)
		{
			item = head.item;
			head = null;
			tail = null;
		}
		else
		{
			item = head.item;
			head = head.next;
			head.prev = null;
		}
		size--;
		return item;
	}
	
	public Item removeLast()
	{
		Item item = null;

		if(isEmpty())
			throw new RuntimeException("Deque is empty");

		if(head == tail)
		{
			item = tail.item;
			head = null;
			tail = null;
		}
		else
		{
			item = tail.item;
			tail = tail.prev;
			tail.next = null;
		}
		
		size--;
		return item;
	}
	
	public Item getLast()
	{
		Item item = null;

		if(isEmpty())
			throw new RuntimeException("Deque is empty");

		item = tail.item;
		return item;
	}
	
	public Iterator<Item> iterator()
	{
		return new ListIterator<Item>(head, tail);
	}
	
	public void print()
	{
		if(isEmpty())
			System.out.println("Deque is empty.");

		Iterator<Item> iterator = iterator();
		
		while(iterator.hasNext())
		{
			System.out.print(iterator.next());
			if(iterator.hasNext())
				System.out.print(",");
		}
		System.out.print("\n");
	}

	/*public static void main(String args[])
	{
		Deque<String> deque = new Deque<String>();
		
		while(StdIn.hasNextLine())
		{
			String input = StdIn.readLine();
			String function[] = input.split(" ");
			
			if(function[0].equals("addFirst"))
				deque.addFirst(function[1]);
			else if(function[0].equals("addLast"))
				deque.addLast(function[1]);
			else if(function[0].equals("removeFirst"))	
				System.out.println(deque.removeFirst());
			else if(function[0].equals("removeLast"))
				System.out.println(deque.removeLast());
			else if(function[0].equals("print"))
				deque.print();
			else if(function[0].equals("size"))
				System.out.println(deque.size());
			else if(function[0].equals("isEmpty"))
				System.out.println(deque.isEmpty());
		}
	}*/
}

class Node<Item>
{
	Item item;
	Node next;
	Node prev;
	
	public Node(Item item)
	{
		this.item = item;
		this.next = null;
		this.prev = null;
	}
}

class ListIterator<Item> implements Iterator<Item>
{
	Node<Item> head;
	Node<Item> tail;

	public ListIterator(Node<Item> head, Node<Item> tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public boolean hasNext()
	{
		return head != null;	
	}
	
	public Item next()
	{
		Item item = head.item;
		head = head.next;
		return item;
	}
	
	public void remove(){}
}
