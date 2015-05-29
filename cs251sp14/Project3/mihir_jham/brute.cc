#include <iostream>
#include <stdlib.h>
#include "key.h"
#include "brute.h"
#include <stdio.h>
#include <string.h>
#include <math.h>

using namespace std;


int main(int argc, char *argv[]){

	if (argc != 2) exit(EXIT_FAILURE);

	// Create a Key from the input password
	Brute bruteKey((char*) argv[1]);

	//initialize the table
	bruteKey.initializeTable();

	//Add code here
	bruteKey.decrypt();
}

Brute::Brute(char s[]){

	char buffer[C+1];

	//initialze to encrypted key
	encrypted.keySetString(s);

}

void Brute::initializeTable(){

	char buffer[C+1];   

	// Read in table T
	for (int i = 0; i < N; i++) {
		cin.getline(buffer,C+1,'\n');
		buffer[C]=0;
		T[i].keySetString(buffer);
	}
}

void Brute::decrypt()
{
	unsigned long long int limit = pow(2, B*C);
	
	Key possibleKey;
	char increment[C+1];
	int index;
	for(index = 0; index < C-1; index++)
	{
		increment[index] = ALPHABET[0];;
	}
	increment[index++] = ALPHABET[1];;
	increment[index] = '\0';

	Key incrementer(increment);
	
	for(unsigned long long int i = 0; i < limit; i++)
	{
		subsetSum.keySet(possibleKey.keySubsetSum(T));
		if(encrypted.keyEquals(subsetSum))
			possibleKey.keyShow();

		possibleKey.keyAdd(incrementer);
	}
}

Key Brute::getEncrypted(){
	return encrypted;
}
