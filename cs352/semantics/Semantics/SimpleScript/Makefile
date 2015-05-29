simple: y.tab.c lex.yy.c 
	gcc y.tab.c lex.yy.c -o simple -lfl 
y.tab.c : simple.y 
	bison -y -d -g --verbose simple.y 
lex.yy.c: simple.l 
	lex simple.l 
clean: 
	rm -f lex.yy.c y.tab.c simple

