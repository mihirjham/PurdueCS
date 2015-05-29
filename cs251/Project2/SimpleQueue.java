import java.util.Iterator;

class SimpleQueue<Item> implements Iterable<Item>
{
	Node head, tail;
	int size;

	public SimpleQueue()
	{
		head = null;
		tail = null;
		size = 0;
	}

	public boolean isEmpty()
	{
		return head == null;
	}

	public void enqueue(Item item)
	{
		Node newNode = new Node(item, null);

		if(isEmpty())
		{
			head = newNode;
		}
		else
		{
			tail.next = newNode;
		}
		tail = newNode;
		size++;
	}

	public Item dequeue()
	{
		if(isEmpty())
		{
			throw new RuntimeException("error");
		}
		
		Item returnVal = head.data;
		head = head.next;
		size--;
		if(size == 0)
		{
			tail = null;
		}
		return returnVal;
	}

	public int size()
	{
		return size;
	}

	class Node
	{
		Node next;
		Item data;

		public Node(Item data)
		{
			this(data, null);
		}

		public Node(Item data, Node next)
		{
			this.data = data;
			this.next = next;
		}
	}

	class QueueIterator implements Iterator
	{
		Node current = head;

		public boolean hasNext()
		{
			return current != null;
		}

		public Item next()
		{
			if(!hasNext())
			{
				throw new RuntimeException("error");
			}

			Item returnVal = current.data;
			current = current.next;
			return returnVal;
		}

		public void remove()
		{
			throw new RuntimeException();
		}
	}

	public Iterator iterator()
	{
		return new QueueIterator();
	}
}
