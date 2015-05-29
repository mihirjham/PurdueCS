public class RandomCollection<Item>
{
	Node<Item> head;
	int size;

	public RandomCollection()
	{
		this.head = null;
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return this.head == null;
	}
	
	public int size()
	{
		return this.size;
	}
	
	public void addAnywhere(Item item)
	{
		Node newNode = new Node(item);

		if(isEmpty())
			head = newNode;
		else
		{
			Node temp = head;
			newNode.next = head;
			temp.prev = newNode;
			head = newNode;;
		}
		size++;
	}

	public Item removeRandomly()
	{
		if(isEmpty())
			throw new RuntimeException("RandomCollection is empty");

		int randomAccess = StdRandom.uniform(this.size);
		
		Node<Item> current = head;

		for(int i=0; i < randomAccess; i++)
		{
			current = current.next;
		}
		
		Item item = current.item;
		
		Node prev = current.prev;
		Node next = current.next;
		
		if(prev != null)
			prev.next = next;
		if(next != null)
			next.prev = prev;
		
		if(current == head)
		{
			head = next;
		}
		size--;
		
		return item;
	}
	
	public Item callout()
	{
		if(isEmpty())
			throw new RuntimeException("RandomCollection is empty");

		int randomAccess = StdRandom.uniform(this.size);

		Node<Item> current = head;

		for(int i=0; i < randomAccess; i++)
		{
			current = current.next;
		}
		
		Item item = current.item;
		
		return item;
	}
	
}

class Node<Item>
{
	Item item;
	Node<Item> next;
	Node<Item> prev;

	public Node(Item item)
	{
		this.item = item;
		this.next = null;
		this.prev = null;
	}
}
