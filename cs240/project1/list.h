#ifndef LIST_H_
#define LIST_H_

struct order {
        int id;
        char side;
        int quantity;
        double price;
};

struct onode {
        struct order* data;
        struct onode* next;
        struct onode* prev;
};


//List management

/**
 * Returns a new linked list node filled in with the given order, The function
 * allocates a new order and copy the values stored in data then allocate a 
 * linked list node. If you are implementing this function make sure that you
 * duplicate, as the original data may be modified by the calling function.
 */
struct onode* newNode (struct order* data);

/**
 * In a linked list with *head as the head pointer, adds the given node to the
 * front of the list.
 */
void 		  pushNode (struct onode** head, struct onode* node) ;

/**
 * In a linked list with *head as the head pointer, returns the onode with the
 * given order id
 */
struct onode* getOrderNode (struct onode* head, int id);

/**
 * Insert the given node after the prevNode. If the  prevNode is NULL,
 * then the given node is inserted at the head of the list.
 */
void          insertNode  (struct onode** head, struct onode* prevNode,
                           struct onode* insertingNode);
void          evictNode   (struct onode** head, struct onode* node);

void          deleteNode  (struct onode** head, struct onode* node);
void		  deleteList  (struct onode** head);

void          printList   (struct onode *node, 
					      void(*printItem)(struct order *, FILE *), 
						  FILE *out);

//Content Management: Setters and Getters for linkedList
//int getNodeId(struct onode* order_node);
struct onode* getOrderNode(struct onode* head, int id);
struct onode* getNextOrder(struct onode* order_node);
struct onode* getPrevOrder(struct onode* order_node);
struct order* getOrderData(struct onode* order_node);

//Content Management: Setters and Getters for order data
int getOrderId (struct order* orderData);
int getOrderQty (struct order* orderData);
double getOrderPrice(struct order* orderData);
char getOrderSide(struct order* orderData);

void setOrderId (struct order* orderData, int id);
void setOrderQty (struct order* orderData, int newQty);
void setOrderPrice(struct order* orderData, double newPrice);
void setOrderSide(struct order* orderData, char side);

#endif //LIST_H_