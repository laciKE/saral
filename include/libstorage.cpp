#include <cstdlib>
#include <cstdio>
#include <cstring>

#include <map>
#include <string>

using namespace std;

map<string, string> storage;

#ifdef __cplusplus
extern "C"
{
#endif

void storage_put(char **ckey, char **cvalue) {
	string key(*ckey);
	string value(*cvalue);
	/*if (storage.count(key) > 0) {
		fprintf(stderr, "Storage already contains element %s, insert ignored\n", *ckey);
		return;
	}*/
	storage[key] = value;

	return;
}

char * storage_get(char **ckey) {
	string key(*ckey);
	if (storage.count(key) == 0) {
		fprintf(stderr, "Storage does not contain element %s, empty string returned\n", *ckey);
		char *ret = (char *)malloc(1);
		ret[0] = '\0';
		return ret;
	}
	string value = storage[key];
	char *cvalue = (char *)malloc(value.length() + 1);
	strcpy(cvalue, value.c_str());
	
	return cvalue;
}

int storage_count(char **ckey) {
	string key(*ckey);
	return storage.count(key);
}

#ifdef __cplusplus
}
#endif

/*
int main() {
	char *ckluc = (char *)malloc(16);
	strcpy(ckluc, "pokus_cislo");
	char *chodnota = (char *)malloc(4);
	strcpy(chodnota, "47");
	char **kluc = &ckluc;
	char **hodnota = &chodnota;

	printf("%d\n", storage_count(kluc));
	storage_put(kluc, hodnota);
	printf("%d\n", storage_count(kluc));
	printf("%s\n", storage_get(kluc));

	return 0;
}
*/	
