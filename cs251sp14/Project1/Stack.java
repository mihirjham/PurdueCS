import java.util.Iterator;

public class Stack<Item> implements Iterable<Item>
{
	private int n;
	private Node<Item> head;

	public Stack()
	{
		head = null;
		n = 0;
	}

	public boolean isEmpty()
	{
		return head == null;
	}

	public int size()
	{
		return n;
	}

	public void push(Item item)
	{
		Node<Item> old = head;
		Node<Item> newNode = new Node<Item>();
		newNode.item = item;
		newNode.next = null;

		head = newNode;
		head.next = old;
		n++;
	}

	public Item pop()
	{
		if(isEmpty())
			throw new RuntimeException("Stack is empty");
		else
		{
			Item temp = head.item;
			head = head.next;
			n--;
			return temp;
		}
	}

	public Item peek()
	{
		if(isEmpty())
			throw new RuntimeException("Stack is empty");
		Item temp = head.item;
		return temp;
	}

	public Iterator<Item> iterator()
	{
		return new ListIterator<Item>(head);
	}
}        

class Node<Item>
{
	Item item;
	Node<Item> next;
}

class ListIterator<Item> implements Iterator<Item>
{
	private Node<Item> current;

	public ListIterator(Node<Item> head)
	{
		this.current = head;
	}

	public boolean hasNext()
	{
		return current != null;
	}
	public void remove(){}

	public Item next()
	{
		if(!hasNext())
			throw new RuntimeException("No more items in list");
		else
		{
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}
