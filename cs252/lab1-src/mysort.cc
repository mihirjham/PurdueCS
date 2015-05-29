#include "mysort.h"
#include <alloca.h>
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
//
// Sort an array of element of any type
// it uses "compFunc" to sort the elements.
// The elements are sorted such as:
//
// if ascending != 0
//   compFunc( array[ i ], array[ i+1 ] ) <= 0
// else
//   compFunc( array[ i ], array[ i+1 ] ) >= 0
//
// See test_sort to see how to use mysort.
//
void mysort( int n,                      // Number of elements
	     int elementSize,            // Size of each element
	     void * array,               // Pointer to an array
	     int ascending,              // 0 -> descending; 1 -> ascending
	     CompareFunction compFunc )  // Comparison function.
{
  // Add your code here. Use any sorting algorithm you want.
	int i,j;
	int swapped = 1;

	for(i= n-1; i > 0 && swapped ; i--)
	{
		swapped = 0;
		for(j=0; j < i; j++)
		{
			if(ascending)
			{
				char *ary =(char *)array;
				char *aryJ = ary + (elementSize*j);
				char *aryJ2 = ary + (elementSize*(j+1));
				char *temp;
				if(compFunc(aryJ, aryJ2) >= 0)
				{
					temp =(char *) malloc(elementSize);
					memcpy(temp, aryJ, elementSize);
					memcpy(aryJ, aryJ2, elementSize);
					memcpy(aryJ2, temp, elementSize);
					swapped = 1;
					free(temp);	
				}
			}
			else
			{
				char *ary = (char *)array;
				char *aryJ = ary + (elementSize*j);
				char *aryJ2 = ary + (elementSize*(j+1));
				char *temp;
				if(compFunc(aryJ,aryJ2) <= 0)
				{
					temp = (char *)malloc(elementSize);
					memcpy(temp, aryJ2, elementSize);
					memcpy(aryJ2, aryJ, elementSize);
					memcpy(aryJ, temp, elementSize);
					swapped = 1;
					free(temp);
				}
			}
		}
	}
}
