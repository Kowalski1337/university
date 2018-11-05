#include <iostream>
#include <bits/stdc++.h>

using namespace std;


long long gcd(long long a, long long b, long long &x, long long &y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    long long x1, y1;
    long long d = gcd(b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}


int main() {

    long long n, e, m;
    cin >> n >> e >> m;

    long long p, q;

    long long i = 2;
    while (true) {
        if (n % i == 0) {
            p = i;
            q = n / p;
            break;
        }
        i++;
    }

    long long eu = (p - 1) * (q - 1);

    long long d, y;

    gcd(e, eu, d, y);

    while (d < 0) {
        d += eu;
    }

    long long ans = 1;

    while (true) {
        if (d % 2 == 0){
            m *= m;
            m %= n;
            d >>= 1;
        } else {
            ans *= m;
            ans %= n;
            d--;
            if (d == 0){
                break;
            }
        }
    }
    cout << ans;

    return 0;
}