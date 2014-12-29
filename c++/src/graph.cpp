#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

vector<vector<int> > G;
vector<bool> was;

void dfs(int v) {
        if (was[v]) return;
        was[v] = true;
        for(unsigned int i = 0; i < G[v].size(); i++) {
                dfs(G[v][i]);
        }
}

int main() {
        int n, m;
        cin >> n >> m;
        G.resize(n);
        was.resize(n, false);
        for (int i = 0; i < m; i++) {
                int a, b;
                cin >> a >> b;
                G[a].push_back(b);
                G[b].push_back(a);
        }
        dfs(0);
        for(unsigned int i = 0; i < was.size(); i++) {
                if (!was[i]) {
                        cout << "NO" << endl;
                        return 0;
                }
        }
        cout << "YES" << endl;
}
