multiply: y.tab.c lex.yy.c 
	gcc y.tab.c lex.yy.c -o multiply -lfl 
y.tab.c : multiply.y 
	bison -y -d -g --verbose multiply.y 
lex.yy.c: calc1.l 
	lex calc1.l 
clean: 
	rm -f lex.yy.c y.tab.c multiply

