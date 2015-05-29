#include <stdio.h>
#include "list.h"
#include "hash.h"
#include "default_hash.h"

/*
 * Default print function. Custom printers should adhere
 * to this format.
 */
void printOrderData(struct order *order_data, FILE *out){
	fprintf(out, "%d %c %d %f\n", 
					 getOrderId(order_data), 
					 getOrderSide(order_data),
					 getOrderQty(order_data), 
					 getOrderPrice(order_data));
}

int main(int argc, char *argv[]) {
  /* FILL IN HERE */
  /* Doesn't mean that you should only fill here.
     You can write as many helper functions as
     you want. */
  return 0;
}
