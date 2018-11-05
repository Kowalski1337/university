#include <iostream>
#include <bits/stdc++.h>

using namespace std;

int main() {
    cin.tie();
    ios_base::sync_with_stdio(0);
    int n;
    cin >> n;

    vector<int> prime;
    vector<int> min_prime;
    min_prime.assign(1000001, -1);

    for (int i = 2; i <= 1000000; i++) {
        if (min_prime[i] == -1) {
            min_prime[i] = i;
            prime.push_back(i);
        }
        int x;
        for (int j = 0; j < prime.size() && prime[j] <= min_prime[i] && ((x = i*prime[j]) <= 1000000); j++){
            min_prime[x] = prime[j];
        }
    }


    for (int i = 0; i < n; i++) {
        int x;
        cin >> x;
        while (true) {
            cout << min_prime[x] << " ";
            x /= min_prime[x];
            if (x == 1) {
                break;
            }
        }
        cout << endl;
    }

    return 0;
}