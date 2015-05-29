#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include"vulnerable.h"

char* name = "abc";

void print_secret(char *secret){
  printf("Secret is %s!\n",secret);
  exit(0);
}

void wrong() {
  char buf[4];
  strcpy(buf,name);
  return;
}

void fence_secret(){
  printf("fence\n");
}

int main(){
  char *secret = "dragonwarrior";
  init();
  fence_secret();
  wrong();
  return 0;
}


