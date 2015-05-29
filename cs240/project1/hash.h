#include "list.h"

/**
 * struct hashStorage represents 1 hash structure.
 */ 
struct hashStorage {
	int    (*funcHash)  (int);
	void   (*printItem) (struct order *, FILE *);
	struct onode**      table;
	int    size;
};

/**
 * Create a new instance of struct hashStorage and returns it. It sets the size of the
 * table to be of length "size" which will be the number of the entries in the hash. It takes also an
 * argument to specify the format of the order printed to an output stream. If myHash parameter
 * is NULL then this means that the hash should be a linked list. When myHash is NULL the size
 * parameter should be ignored.
 */
struct hashStorage* createHash(int  size, 
                			   int  (*myHash)    (int),
                			   void (*printOrder)(struct order *, FILE *));
                
/*
 * Add an order to the hash structure. Remember that you should copy the data before
 * adding it to the hash as data can be modied (hint: look at newNode in list).  
 * It returns the new onode.               
 */ 
struct onode* addOrder       (struct hashStorage* hash, struct order* data);

/**
 * Cancel an order from the hash. It will look for the onode having order with the same
 * data->id and remove it from the list, then destroy its allocation.
 */
void          cancelOrder    (struct hashStorage* hash, struct order* data);

/**
 * Reduce the quantity of an order by the amount stored in the data->quantity. It
 * will look for the onode having the same order id as data->id then subtract the data->quantity
 * from node->data->quantity. If (node->data->quantity==0) the node should be removed from
 * the hash and its memory allocation should be destroyed.
 */
void          reduceOrderQty (struct hashStorage* hash, struct order* data);

/**
 * Change the quantity and the price of an order. It will look for the onode having 
 * the same order id as data->id then set the (node->data->quantity=data->quantity) and
 * (node->data->price=data->quantity) node->quantity. If (node->data->quantity==0) the
 * node should be removed from the hash and its memory allocation should be destroyed.
 **/
void 		  changeOrder    (struct hashStorage* hash, struct order* data);

/*
 * Given the hash and the output stream, print the order book. The function should
 * print each item given the function hash->printItem and the output stream required 
 * for the print.
 */
void 		  printOrderBook (struct hashStorage* hash, FILE *out);

/**
 * Delete the order book and free all the memory allocated for that hash.
 */
void          freeOrderBook  (struct hashStorage** hash);

/* returns the size of the hash table */
int           getHashSize    (struct hashStorage* hash);

/* returns the table of the hash.*/
struct onode** getHashTable  (struct hashStorage* hash);