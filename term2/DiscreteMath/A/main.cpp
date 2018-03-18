#include <iostream>
#include <fstream>
#include <stdio.h>

using namespace std;

int main()
{
    cin.tie();
    ios_base::sync_with_stdio(0);
    freopen("exam.in", "r", stdin);
    freopen("exam.out", "w", stdout);

    int k;
    int n;

    cin >> k >> n;

    double res = 0;
    float s;
    for (int i = 0; i < k; i++)
    {
        int a, b;
        cin >> a >> b;
        res += a*b;
    }
    s = res*0.01/n;
    cout << s;

    return 0;
}
