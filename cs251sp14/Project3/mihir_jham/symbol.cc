#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include "key.h"
#include "symbol.h"
#include <vector>
#include <string.h>
#include "KeyValue.h"
#include <algorithm>
#include <time.h>

using namespace std;

int binarySearch(vector<KeyValue> vector, Key value)
{
	int lo = 0;
	int hi = vector.size() - 1;
	while(lo <= hi)
	{
		int mid = lo + (hi - lo) / 2;
		if(value.keyGreaterThan(vector.at(mid).value, value))
			hi = mid - 1;
		else if(value.keyGreaterThan(value, vector.at(mid).value))
			lo = mid + 1;
		else
			return mid;
	}
	
	return -1;
}

bool compare(KeyValue a, KeyValue b)
{
	Key key;
	if(key.keyGreaterThan(a.value, b.value))
		return false;
	else
		return true;;
}

int main(int argc, char *argv[]){
   
   if (argc != 2) exit(EXIT_FAILURE);
   
   // Create a Key from the input password
   Symbol symKey((char*) argv[1]);
   
   //initialize the table
   symKey.initializeTable();

   //Add code here
   symKey.decrypt();
}

Symbol::Symbol(char s[]){
 
   char buffer[C+1];
   
   //initialze to encrypted key
   encrypted.keySetString(s);
   
}

void Symbol::initializeTable(){
   
   char buffer[C+1];   

   // Read in table T
   for (int i = 0; i < N; i++) {
      cin.getline(buffer,C+1,'\n');
      buffer[C]=0;
      T[i].keySetString(buffer);
   }
}

void Symbol::decrypt(){
	
	int subSize = 1 << (N/2);
	vector<KeyValue> firstHalf;
	for(int count = 0; count < subSize; count++)
	{
		int mask = count;
		Key value;
		char *key = (char *)malloc((N/2 + 1)*sizeof(char));
		for(int k = 0; k < N/2; k++)
		{
			if(mask & 1)
			{
				value.keyAdd(T[k]);		
				key[k] = '1';
			}
			else
				key[k] = '0';
			mask = mask >> 1;
		}
		key[N/2] = '\0';
		KeyValue newKeyValue(key, value);
		firstHalf.push_back(newKeyValue);
	}
	
	vector<KeyValue> secondHalf;
	if(C % 2)
		subSize = 1 << (N/2 + 1);
	else
		subSize = 1 << (N/2);
	
	for(int count = 0; count < subSize; count++)
	{
		int mask = count;
		Key value;
		char *key = (char *)malloc((N/2 + 2)*sizeof(char));
		int index = 0;
		for(int k = N/2; k < N; k++)
		{
			if(mask & 1)
			{
				value.keyAdd(T[k]);
				key[index++] = '1';
			}
			else
				key[index++] = '0';
			
			mask = mask >> 1;
		}
		if(C % 2)
			key[N/2 + 1] = '\0';
		else
			key[N/2] = '\0';
		KeyValue newKeyValue(key, value);
		secondHalf.push_back(newKeyValue);
	}
	
	std::sort(secondHalf.begin(), secondHalf.end(), compare);
	
	for(int i = 0; i < firstHalf.size(); i++)
	{
		Key diff;
		diff.keySet(encrypted);
		diff.keySub(firstHalf.at(i).value);
		
		int indexKey = binarySearch(secondHalf, diff);
		if(indexKey != -1)
		{
			
			char *decrypted = (char *)malloc((N+1)*sizeof(char));
			strcpy(decrypted, firstHalf.at(i).key);
			strcat(decrypted, secondHalf.at(indexKey).key);
			char *password = (char *)malloc((C+1)*sizeof(char));
			
			int index = 0;
			int val = 0;
			
			for(int k = 0; k < strlen(decrypted); k = k + 5)
			{
				k = k + (B-1);
				while((k % B) != 0)
				{
					if(decrypted[k] == '1')
					{
						val = val + pow(2,((B-1) - (k % B)));
					}
					k--;
				}
				
				if(decrypted[k] == '1')
				{
					val = val + pow(2,((B-1) - (k % B)));
				}
				password[index++] = ALPHABET[val];
				val = 0;
			}
			password[index] = '\0';
			printf("%s\n", password);
		}
	}
}
		
