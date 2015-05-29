#include<stdio.h>

int main()
{
	char c;
	int nc = 0;
	
	while(1)
	{
		c = getchar();
		++nc;				
		if(c == EOF || nc > 140)
			break;
		
		putchar(c);	
	}
	
}		
