public class SimpleStack<Item>
{	
	private Node first = null;
	private int size = 0;

	public SimpleStack()
	{
		this.first = null;
		this.size = 0;
	}

	public int size()
	{
		return size;
	}

	public void push(Item item)
	{
		first = new Node(item, first);
		size++;
	}
	
	public boolean isEmpty()
	{
		return first == null;
	}

	public Item pop()
	{
		if(isEmpty())
		{
			throw new RuntimeException("error");
		}
		else
		{
			Item temp = first.value;
			first = first.next;
			size--;
			return temp;
		}
	}

	class Node
	{
       		Item value;
		Node next;

		public Node(Item value)
		{	
			this(value, null);
		}

		public Node(Item value, Node next)
		{
			this.value = value;
	        	this.next = next;
		}
	}
}
