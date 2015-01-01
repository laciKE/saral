#include <stdio.h>

void printInt(int a) {
	printf("%d\n", a);
}

void printFloat(float f) {
	printf("%.4f\n", f);
}

void printBool(char b) {
	switch (b) {
		case -1: printf("ošaľ\n"); break;
		case  0: printf("skoroošaľ\n"); break;
		case  1: printf("pravda\n"); break;
	}
}

void printChar(char c) {
	printf("%c\n", c);
}

void printString(char *s) {
	printf("%s\n", s);
}

/*
int main() {
	printInt(47);
	printFloat(12345.123456789);
	printBool(-1);
	printChar('@');
	printString("Hello, World!");

	return 0;
}
*/
