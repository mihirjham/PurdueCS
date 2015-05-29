public class QueueStack<Item>
{
	Stack<Item> inbox;
	Stack<Item> outbox;

	public QueueStack()
	{
		inbox = new Stack<Item>();
		outbox = new Stack<Item>();
	}

	public void enqueue(Item item)
	{
		while(!outbox.isEmpty())
		{
			inbox.push(outbox.pop());
		}
		outbox.push(item);
		while(!inbox.isEmpty())
		{
			outbox.push(inbox.pop());
		}
	}

	public Item dequeue()
	{
		while(!inbox.isEmpty())
		{
			outbox.push(inbox.pop());
		}
		Item item = outbox.pop();
		return item;
	}
	
	public static void main(String args[])
	{
		QueueStack<String> queue = new QueueStack<String>();
		queue.enqueue("Mihir");
		queue.dequeue();
		queue.enqueue("is");
		queue.enqueue("a");
		queue.enqueue("student");
		queue.enqueue("at");
		
		for(int i = 0; i<4; i++)
		{
			System.out.println(queue.dequeue());
		}

	}
}
