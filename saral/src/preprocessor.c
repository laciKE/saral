#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define INDENT "__INDENT"
#define DEDENT "__DEDENT"
#define INCLUDE "falda"

void preprocess_source(char *filename) {
	int indent_level = 0;
	char line[4096];
	int line_number = 0;
	
	void print_line() {
		//if (line_number > 1) { //insert newline character after previous line
		//	printf("\n%s", line);
		//} else {
			printf("%s", line);
		//}

		return;
	}

	void indent_dedent(){
		if (strspn(line, " \t") == strlen(line)) { //only whitespace line
			return;
		}
	
		if (strstr(line, INDENT) != NULL ){
			fprintf(stderr, "Error: illegal sequence %s on line %d in file %s\n", INDENT, line_number, filename);
			exit(EXIT_FAILURE);
		}
		if (strstr(line, DEDENT) != NULL ){
			fprintf(stderr, "Error: illegal sequence %s on line %d in file %s\n", DEDENT, line_number, filename);
			exit(EXIT_FAILURE);
		}

		int new_indent_level = strspn(line, " \t");
		while (new_indent_level > indent_level) {
			indent_level++;
			printf(" %s ", INDENT);
		}
		while (new_indent_level < indent_level) {
			indent_level--;
			printf(" %s ", DEDENT);
		}

		return;
	}

	void expand_includes() {
		char *ptr_str = strstr(line, INCLUDE);
		if (ptr_str == NULL) { // no include, just print line to output
			print_line();
		} else {
			char filename[2048];
			sscanf(ptr_str, " %*s %s ", filename);
			strcpy(line, "");
			print_line(); //for appending newline character after previous line
			preprocess_source(filename);
		}


		return;
	}

	FILE *fp = fopen(filename, "r");
	if (fp != NULL) {
		while (fgets(line, 1023, fp) != NULL) {
			line_number++;
			//line[strlen(line)-1] = '\0'; //replace trailing newline with string terminator
			indent_dedent();
			expand_includes();
		}
		strcpy(line, "\n");
		indent_dedent();
		printf("\n");
		fclose(fp);
	} else {
		fprintf(stderr, "Error in opening file %s for reading\n", filename);
		exit(EXIT_FAILURE);		
	}
	return;
}

int main(int argc, char **argv){
	if (argc != 2) {
		fprintf(stderr, "Syntax: preprocessor input_file\n");
		exit(EXIT_FAILURE);
	}

	preprocess_source(argv[1]);

	return 0;
}
