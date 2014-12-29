#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int main() {
        int n;
        cin >> n;
        vector<int> A(n);
        for (int i = 0; i < n; i++) {
                cin >> A[i];
        }
        sort(A.begin(), A.end());
        for(unsigned int i = 0; i < n; i++) {
                cout << A[i] << endl;
        }
}
