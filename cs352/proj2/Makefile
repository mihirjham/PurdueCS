parser:	y.tab.o lex.yy.o HashTable.o
	gcc y.tab.o lex.yy.o HashTable.o -o parser -lfl

y.tab.o: parser.y
	bison -y -d -g -t --debug --report=state parser.y 
	gcc -c y.tab.c	

lex.yy.o: parser.l
	flex parser.l
	gcc -c lex.yy.c

HashTable.o: HashTable.c
	gcc -c HashTable.c

clean:
	rm -f lex.yy.c y.tab.c y.tab.h y.dot y.output parser lex.yy.o y.tab.o HashTable.o y.vcg 
