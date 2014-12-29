#include <iostream>
#include <vector>
using namespace std;

int main() {
        int n;
        cin >> n;
        vector<bool> sieve(n+10, true);
        sieve[0] = sieve[1] = false;
        for (int i = 2; i <= n; i++) {
                if (!sieve[i]) continue;
                cout << i << endl;
                for(long long a = i * i; a < sieve.size(); a += i) {
                        sieve[a] = false;
                }
        }
}
