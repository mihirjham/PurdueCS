#include "KeyValue.h"

KeyValue::KeyValue(char s[], Key v)
{
	value.keySet(v);
	
	int i;
	for(i = 0; i < strlen(s); i++)
	{
		key[i] = s[i];
	}
	key[i] = '\0';
}
