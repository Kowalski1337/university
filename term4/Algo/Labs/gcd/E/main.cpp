#include <iostream>

using namespace std;

int main() {
    int a, b, m, n;
    cin >> a >> b >> n >> m;
    long long x = a;
    int i = 0;
    while (x % m != b) {
        x += n;
    }
    cout << x;
    return 0;
}