#include <stdio.h>
#include <conio.h>
#include <math.h>
void dtoh(int d);
main()
{

	int d;
	clrscr();
	printf("Enter a no. in decimal system:-  ");
	scanf("%d",&d);
	dtoh(d);
	printf("




			HAVE A NICE DAY! BYE.");
	getch();
}

void dtoh(int d)
{
	int b,c=0,a[5],i=0;
	b=d;
	while (b>15)
	{
		a[i]=b%16;
		b=b/16;
		i++;
		c++;
	}
	a[i]=b;
	printf("
			Its hexadecimal equivalent is  ");
	for (i=c;i>=0;--i)
	{
		if (a[i]==10)
			printf("A");
		else if (a[i]==11)
			printf("B");
		else if (a[i]==12)
			printf("C");
		else if (a[i]==13)
			printf("D");
		else if (a[i]==14)
			printf("E");
		else if (a[i]==15)
			printf("F");
		else
			printf("%d",a[i]);
	}
	return;
}
