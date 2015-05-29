calc1: y.tab.c lex.yy.c 
	gcc y.tab.c lex.yy.c -o calc1 -lfl 
y.tab.c : calc1.y 
	bison -y -d -g --verbose calc1.y 
lex.yy.c: calc1.l 
	lex calc1.l 
clean: 
	rm -f lex.yy.c y.tab.c calc1

