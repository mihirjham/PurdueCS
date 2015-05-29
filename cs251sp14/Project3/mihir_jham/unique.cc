#include <iostream>
#include <stdlib.h>
#include "key.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

using namespace std;

void swap(Key *a, Key  *b)
{
	Key temp = *a;
	*a = *b;
	*b = temp;
}

void randomize(Key arr[], int n)
{
	srand(time(NULL));
	for(int i = n-1; i > 0; i--)
	{
		int j = rand() % (i+1);
		swap(&arr[i], &arr[j]);
	}

}

int main(int argc, char *argv[]){
   
  //Add code here
  
	char increment[C+1];
	for(int i = 0; i < C-1; i++)
		increment[i] = ALPHABET[0];
	increment[C-1] = ALPHABET[1];
	increment[C] = '\0';
	
	Key *keys = (Key *)malloc(B*C*sizeof(Key));
	Key key;
	key.keyAdd(increment);
	
	
	for(int i = 0; i < B*C; i++)
	{
		keys[i].keySet(key);;
		key.keyAdd(key);
	}
	
	randomize(keys, B*C);
	
	for(int i = 0; i < B*C; i++)
		keys[i].keyShow();
}



