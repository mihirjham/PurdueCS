
//
// CS251 Data Structures
// Hash Table
//

#include <assert.h>
#include <stdlib.h>
#include <string.h>

typedef struct HashTableVoidEntry HashTableVoidEntry;
// Each hash entry stores a key, object pair
struct HashTableVoidEntry {
  const char * _key;
  void * _data;
  HashTableVoidEntry * _next;
  int isDefined;
  struct ASTNode *defNode;  
  struct argumentList *uses;
};

enum { TableSize = 2039};
  
// Array of the hash buckets.
struct HashTableVoidEntry **_buckets;
struct stackOfBuckets *top;

struct HashTableVoidEntry** HashTableInit();
// Obtain the hash code of a key
int hash(const char * key);
  
// Add a record to the hash table. Returns true if key already exists.
// Substitute content if key already exists.
int insertItem( const char * key, void * data);

 // Find a key in the dictionary and place in "data" the corresponding record
// Returns false if key is does not exist
int find( const char * key, void ** data);

// Removes an element in the hash table. Return false if key does not exist.
int removeElement(const char * key);

int isDefined(const char *key);

void setDefined(const char *key);

void unsetDefined(const char *key);

void removeSubKey(const char *key);

void setBucket(struct HashTableVoidEntry **bucket);

void newDef(const char *key, void *tree);

struct ASTNode* findReachingDef(const char *key);

void setReachingDef(const char *key, struct argumentList *reachingDefs);

struct argumentList* getUses(const char *key);
