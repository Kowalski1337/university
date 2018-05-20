#include <iostream>
#include <bits/stdc++.h>

using namespace std;

int main() {
    cin.tie();
    ios_base::sync_with_stdio(0);
    int n;
    cin >> n;

    vector<bool> ans;

    ans.assign(1000001, true);
    ans[1] = false;

    for (int i = 2; i < 1000; i++) {
        if (ans[i]) {
            for (int j = 2 * i; j <= 1000000; j += i) {
                ans[j] = false;
            }
        }
    }

    for (int i = 0; i < n; i++) {
        int x;
        cin >> x;

        cout << (ans[x] ? "YES" : "NO") << '\n';
    }

    return 0;
}