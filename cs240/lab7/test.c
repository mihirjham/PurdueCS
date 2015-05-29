#include <stdio.h>

int bar(int a, int b, int c) {
    int d;
    d = a + b + c;
    d = 10;
    return d;
}

int foo() {
    int foo_local = 42;
    return bar(1, 2, 3);
}

int main() {
    printf("%d\n", foo());
    return 0;
}
