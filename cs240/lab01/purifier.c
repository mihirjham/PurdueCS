#include<stdio.h>

int isLetter(int);
void purify(char[], char[]);
int isUpper(int);

int main()
{
	char keywords[5][5] = {"heck","crap","suck","bull","drat"};
	char words[50][10];
	int c;
	int nc = 0;
	int i;
	char msg[140];
	int atPos[140];
	int j,k;

	while(1)
	{
		if((scanf("%s", words[i])) == EOF || nc > 140)
		{
			break;
		}

		++i;
	}
	
	//printf("%d\n",i);
	//for(k=0; k<i; k++)
	//{
//		printf("%s\n", words[k]);
//	}

	for(j=0; j<i; j++)
	{
		for(k=0; k<5;k++)
		{
			purify(&words[j], keywords[k]);
		}
				
	}
	
	//purify(&words[0],keywords[1]);


	for(k=0; k<i-1; k++)
	{
		printf("%s ", words[k]);
	}
	printf("%s\n", words[k]);
	return 0;
}

int isLetter(int c)
{
	return (c >= 'a' && c <= 'z');
}

int isUpper(int c)
{
	return (c >= 'A' && c <= 'Z');
}

void purify(char word[], char keyword[])
{
	//printf("Inside purify\n");

	int c = 0;
	int j = 0;
	int i;

	//printf("%c\n%c\n", word[c], keyword[j]);

	while(!(isLetter(word[c])) && !(isUpper(word[c])))
	{
		++c;
	}
	
//	printf("%d\n",c);

	while(word[c] == keyword[j])
	{
		c++;
		j++;
	}
	
	if((word[c] == '\0') && (keyword[j] != '\0'))
	{
	//	printf("Exiting purify\n");
		return;
	}

	//printf("%d\n", word[c]);
	//printf("%d\n%d\n", c,j);

//	while(word[c] != '\0')
	//{
		if(isLetter(word[c]) || isUpper(word[c]))
		{
			//printf("Checking to see if following character is a letter\n");
			j=0;
			return;
		}
		else
		{
			for(i=0;i<c;i++)
			{
				while(!(isLetter(word[i])))
				{
					++i;
				}
				word[i] = 'X';
			}
		}
		//printf("%d\n%d\n", c,j);
	//}
}
