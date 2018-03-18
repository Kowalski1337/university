#include <iostream>
#include <stdio.h>


using namespace std;

int main()
{
    cin.tie();
    ios_base::sync_with_stdio(0);
    freopen("lottery.in" ,"r", stdin);
    freopen("lottery.out" , "w", stdout);

    int n, m;
    cin >> n >> m;
    float cur_chance = 1, a, b = 0;
    float res = 0;

    for (int i = 0; i < m; i++)
    {
        cin >> a;
        cur_chance *= a;
        res += (n - b)*(a-1)/cur_chance;
        cin >> b;
    }
    cout << res + (n-b)/cur_chance;
    return 0;
}
