
//
// CS251
// Implementation of a HashTable that stores void *
//
#include "HashTable.h"
#include <stdio.h>
#include "ast.h"

// Obtain the hash code of a key
int hash(const char * key)
{
	// Add implementation here

	int n = 0;
	int length = strlen(key);

	int i;
	for(i = 0; i < length; i++)
	{
		n += (i+1) * key[i];
	}

	return n % TableSize;
}

// Constructor for hash table. Initializes hash table
struct HashTableVoidEntry ** HashTableInit()
{
	// Add implementation here

	struct HashTableVoidEntry **newBucket = (HashTableVoidEntry**)malloc(sizeof(HashTableVoidEntry *)*TableSize);
	//_buckets = new HashTableVoidEntry*[TableSize];

	int i;
	for(i = 0; i < TableSize; i++)
	{
		newBucket[i] = NULL;
	}
	
	return newBucket;
}

// Add a record to the hash table. Returns true if key already exists.
// Substitute content if key already exists.
int insertItem( const char * key, void * data)
{
	// Add implementation here
	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];

	while(current != NULL)
	{
		if(!strcmp(current->_key,key))
		{
			current->_data = data;
			return 1;
		}

		current = current->_next;
	}

	current = _buckets[hashKey];	
	if(current == NULL)
	{
		//HashTableVoidEntry *newEntry = new HashTableVoidEntry;
		HashTableVoidEntry *newEntry = (HashTableVoidEntry *)malloc(sizeof(HashTableVoidEntry));
		newEntry->_key = strdup(key);
		newEntry->_data = data;
		newEntry->_next = NULL;

		_buckets[hashKey] = newEntry;
		return 0;
	}

	while(current->_next != NULL)
		current = current->_next;

	//HashTableVoidEntry *newEntry = new HashTableVoidEntry;
	HashTableVoidEntry *newEntry = (HashTableVoidEntry *)malloc(sizeof(HashTableVoidEntry));
	newEntry->_key = strdup(key);
	newEntry->_data = data;
	newEntry->_next = NULL;

	current->_next = newEntry;
	return 0;
}

// Find a key in the dictionary and place in "data" the corresponding record
// Returns false if key is does not exist
int find( const char * key, void ** data)
{
	// Add implementation here

	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];

	while(current != NULL)
	{
		if(!strcmp(current->_key, key))
		{
			*data = current->_data;
			return 1;
		}

		current = current->_next;
	}

	return 0;
}

int isDefined(const char *key)
{
	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];
	
	while(current != NULL)
	{
		if(!strcmp(current->_key,key))
		{
			return current->isDefined;
		}

		current = current->_next;
	}
	
	return 0;
}

void setDefined(const char *key)
{
	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];
	
	while(current != NULL)
	{
		if(!strcmp(current->_key,key))
		{
			current->isDefined = 1;
			return;
		}

		current = current->_next;
	}
}

void unsetDefined(const char *key)
{
	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];
	
	while(current != NULL)
	{
		if(!strcmp(current->_key,key))
		{
			current->isDefined = 0;
			return;
		}

		current = current->_next;
	}
}

// Removes an element in the hash table. Return false if key does not exist.
int removeElement(const char * key)
{
	// Add implementation here
	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];

	if(current == NULL)
		return 0;

	if(!strcmp(current->_key, key))
	{
		HashTableVoidEntry *temp = current;
		current = current->_next;
		free(temp);
		_buckets[hashKey] = current;
		return 1;
	}
	else
	{
		HashTableVoidEntry *prev = current;
		HashTableVoidEntry *curr = current->_next;

		while(curr && strcmp(curr->_key, key))
		{
			prev = prev->_next;
			curr = curr->_next;
		}

		if(curr)
		{
			HashTableVoidEntry *next = curr->_next;
			prev->_next = next;
			free(curr);
			return 1;
		}
	}

	return 0;
}

void removeSubKey(const char *key)
{
	
	int i;
	for(i = 0; i < TableSize; i++)
	{
		HashTableVoidEntry *current = _buckets[i];
		
		while(current != NULL)
		{	
			if(strstr(current->_key, key))
			{
				if(strcmp(current->_key, key))
					removeElement(strstr(current->_key,key));
				//printf("Found %s\n", strstr(current->_key,key));	
			}
			current = current->_next;
		}
	}
}

void setBucket(struct HashTableVoidEntry **bucket)
{
	_buckets = bucket;
}

void newDef(const char *key, void *tree)
{
	//Use = type 0
	//Def = type 1
	int hashKey = hash(key);
	
	HashTableVoidEntry *current = _buckets[hashKey];
	
	while(current && strcmp(key, current->_key))
	{
		current = current->_next;
	}
	
	if(current)
		current->defNode = tree;	
		/*argumentList *arg = (argumentList *)malloc(sizeof(argumentList));
		arg->tree = (ASTNode *)tree;
		arg->next = NULL;
		
		if(!current->uses)
		{
			current->uses = arg;
		}
		else
		{
			argumentList *list = current->uses;
			while(list && list->next != NULL)
				list = list->next;

			list->next = arg;
		}
		
	}*/
}

ASTNode* findReachingDef(const char *key)
{
	int hashKey = hash(key);

	HashTableVoidEntry *current = _buckets[hashKey];

	while(current && strcmp(key, current->_key))
		current = current->_next;
	
	if(current)
		return current->defNode;
	
	return NULL;
}

void setReachingDef(const char *key, argumentList *reachingDefs)
{
	int hashkey = hash(key);

	HashTableVoidEntry *current = _buckets[hashkey];

	while(current && strcmp(current->_key, key))
		current = current->_next;
	
	if(current)
	{
		current->uses = reachingDefs;
	}
}

argumentList *getUses(const char *key)
{
	int hashkey = hash(key);
	HashTableVoidEntry *current = _buckets[hashkey];

	while(current && strcmp(current->_key, key))
		current = current->_next;
	
	if(current)
		return current->uses;
	
	return NULL;
}
void printUseDef()
{
	int i;
	for(i = 0; i < TableSize; i++)
	{
		HashTableVoidEntry *current = _buckets[i];

		while(current)
		{
			fprintf(stderr, "Key = %s\n", current->_key);
			fprintf(stderr, "def = %d\n", current->defNode->linenumber);
			fprintf(stderr, "ReachingDefs = ");
			argumentList *list = current->uses;
			while(list)
			{
				fprintf(stderr, "%d, ", list->tree->linenumber);	
				list = list->next;
			}
			fprintf(stderr, "\n\n");
			current = current->_next;		
		}
	}
}
