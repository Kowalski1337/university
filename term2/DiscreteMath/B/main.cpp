#include <iostream>
#include <stdio.h>

using namespace std;

int main()
{
    freopen("shooter.in" ,"r", stdin);
    freopen("shooter.out" , "w", stdout);

    int n, m, k;
    cin >> n >> m >> k;
    double cur = 1.0, a;
    double res = 0.0, temp;

    for (int i = 0; i < k-1; i++)
    {
        cin >> a;
        temp = 1;
        for (int j = 0; j < m; j++)
        {
            temp *= (1-a);
        }
        res += temp;
    }
    cin >> a;
    for (int j = 0; j < m; j++)
    {
        cur *= (1-a);
    }
    res += cur;
    for (int i = k; i < n; i++)
    {
        cin >> a;
        temp = 1;
        for (int j = 0; j < m; j++)
        {
            temp *= (1-a);
        }
        res += temp;
    }


    printf("%.13f" , cur/res);
    return 0;
}
