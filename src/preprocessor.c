#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define INDENT "__INDENT"
#define DEDENT "__DEDENT"
#define INCLUDE "falda"

void preprocess_source(char *filename, char * includes_dir) {
	int indent_level = 0;
	char line[4096];
	int line_number = 0;
	char filename2[2064];
	strncpy(filename2, filename, 2048);
	if (strcmp(filename+strlen(filename)-4, ".srl") != 0) {
		strcat(filename2, ".srl");
	}

	void print_line() {
		//if (line_number > 1) { //insert newline character after previous line
		//	printf("\n%s", line);
		//} else {
			printf("%s", line);
		//}

		return;
	}

	void indent_dedent(){
		if (strspn(line, " \t") + 1 == strlen(line)) { //only whitespace line
			return;
		}
	
		if (strstr(line, INDENT) != NULL ){
			fprintf(stderr, "Error: illegal sequence %s on line %d in file %s\n", INDENT, line_number, filename2);
			exit(EXIT_FAILURE);
		}
		if (strstr(line, DEDENT) != NULL ){
			fprintf(stderr, "Error: illegal sequence %s on line %d in file %s\n", DEDENT, line_number, filename2);
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
			preprocess_source(filename, includes_dir);
		}


		return;
	}

	FILE *fp = fopen(filename2, "r");
	if (fp == NULL) {
		char filename3[4096];
		strncpy(filename3, includes_dir, 2016);
		strncat(filename3, filename2, 2064);
		fp = fopen(filename3, "r");
	}
	if (fp != NULL) {
				while (fgets(line, sizeof(line)-1, fp) != NULL) {
			line_number++;
			//line[strlen(line)-1] = '\0'; //replace trailing newline with string terminator
			indent_dedent();
			expand_includes();
		}
		strcpy(line, "\n\n");
		indent_dedent();
		printf("\n");
		fclose(fp);
	} else {
		fprintf(stderr, "Error in opening file %s for reading\n", filename2);
		exit(EXIT_FAILURE);		
	}
	return;
}

int main(int argc, char **argv){
	if (argc != 3) {
		fprintf(stderr, "Syntax: preprocessor input_file includes_dir\n");
		exit(EXIT_FAILURE);
	}

	preprocess_source(argv[1], argv[2]);

	return 0;
}
