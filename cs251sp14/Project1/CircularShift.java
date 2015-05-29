class CircularShift
{
	public static void main(String args[])
	{
		String input = StdIn.readLine();
		String function[] = input.split(" ");
		int test = Integer.parseInt(function[0]);
		
		if(test == 1)
		{
			int n = Integer.parseInt(function[1]);
			Deque<String> sequence = new Deque();

			for(int i=2; i<function.length; i++)
			{
				sequence.addLast(function[i]);
			}
			
			if(n >= sequence.size())
				throw new RuntimeException("Circular Shift value cannot be greater than number of items.");

			for(int i=0; i<sequence.size(); i++)
			{
				Node current = sequence.head;
				for(int j=((i+n)%sequence.size()); j > 0 ; j--)
					current=current.next;
				System.out.print(current.item+" ");
			}
			System.out.println("");
		}
		else if(test == 2)
		{
			if(function.length%2 == 1)
			{	
				System.out.println("No");
				System.exit(0);
			}
			
			int len = ((function.length)/ 2)-1;

			Deque<String> originalSequence = new Deque<String>();	
			Deque<String> circularSequence = new Deque<String>();
			Deque<String> tempSequence = new Deque<String>();
			int shift = 0;

			int i = 1;

			while(!function[i].equals("-"))
			{
				originalSequence.addLast(function[i]);
				i++;
			}
			
			i++;

			while(i < function.length)
			{
				circularSequence.addLast(function[i]);
				i++;
			}
			
			if(originalSequence.size() != circularSequence.size())
			{
				System.out.println("No");
				return;
			}

			for(shift = 0; shift < len ; shift++)
			{
				if(!tempSequence.isEmpty())
					tempSequence.removeFirst();;
				
				Node<String> current = originalSequence.head;
				for(int j=((0+shift)%originalSequence.size()); j>0; j--)
					current = current.next;
				tempSequence.addLast(current.item);
				
				if(tempSequence.head.item.equals(circularSequence.head.item))
						break;
			}
			
			for(int j=1; j < originalSequence.size(); j++)
			{
				Node<String> current = originalSequence.head;
				for(int k=((j+shift)%originalSequence.size()); k>0; k--)
					current = current.next;

				tempSequence.addLast(current.item);
			}
			
			boolean circular = true;
			
			Node<String> tempSequenceCurrent = tempSequence.head;
			Node<String> circularSequenceCurrent = circularSequence.head;
				
			for(int k=0; k< circularSequence.size(); k++)
			{
				if(!(tempSequenceCurrent.item.equals(circularSequenceCurrent.item)))
				{
					circular = false;
					break;
				}
				
				tempSequenceCurrent = tempSequenceCurrent.next;
				circularSequenceCurrent = circularSequenceCurrent.next;
			}
			
			if(circular == true)
				System.out.println("Yes");
			else
				System.out.println("No");
		}
		else
		{
			System.out.println("Not a valid test.");
		}
	}
}
