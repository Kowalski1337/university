#include <bits/stdc++.h>

using namespace std;

vector<int> mabel, dipper;
const int n = 500001;
const int logN = 20;
int dp[n][logN];

int lca(int x, int y)
{
    if (dipper[y] > dipper[x]){
        swap(x, y);
    }
    for (int i = logN - 1; i >= 0; i--){
        if (dipper[dp[x][i]] >= dipper[y]){
            x = dp[x][i];
        }
    }
    if (x == y){
        return x;
    }
    for (int i = logN - 1; i >= 0; i--){
        if (dp[x][i] != dp[y][i]){
            x = dp[x][i];
            y = dp[y][i];
        }
    }
    //cout << "lol" << endl;
    return mabel[x];
}

int main()
{
    ios_base::sync_with_stdio(0);
    cin.tie();
    freopen("lca.in", "r", stdin);
    freopen("lca.out", "w", stdout);

    int m;
    cin >> m;

    mabel.push_back(0);
    dipper.push_back(0);

    for (int i = 0; i < m; i++){
        char ch;
        cin >> ch;
        if (ch == 'A'){
            cin >> ch >> ch;
            int parent, next;
            cin >> parent >> next;
            parent--;
            mabel.push_back(parent);
            dipper.push_back(dipper[mabel[mabel.size()-1]] +1);
            dp[mabel.size()-1][0] = parent;
            for (int j = 1; j < logN; j++){
                dp[mabel.size()-1][j] = dp[dp[mabel.size()-1][j - 1]][j - 1];
            }
        } else {
            cin >> ch >> ch;
            int x, y;
            cin >> x >> y;
      //      cout << "calc for " << x << " " << y << endl;
            cout << lca(x-1, y-1) + 1 << endl;
        }
    }

//    for (int i = 0; i < mabel.size(); i++){
//        cout << mabel[i] << " ";
//    }
//    cout << endl;
//    for (int i = 0; i < dipper.size(); i++){
//        cout << dipper[i] << " ";
//    }
//    cout << endl;
//
//    for (int i = 0; i < mabel.size(); i++){
//        for (int j = 0; j < logN; j++){
//            cout << dp[i][j] << " ";
//        }
//        cout << endl;
//    }

    return 0;
}
