#include<stdio.h>

int main()
{
	int c;
	char msg[140];
	char words[50][100];
	int i=0;
	int nc;
	int j;

	while(1)
	{
		scanf("%s", &words[i++]);

		if((scanf("%s", &words[i++])) == EOF)
			break;
	}

	for(j=0;j<i; j++)
	{
		printf("%s\n", words[j]);
	}
	return 0;
}
