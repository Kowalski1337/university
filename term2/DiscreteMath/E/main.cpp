#include <iostream>
#include <stdio.h>
#include <iomanip>

using namespace std;

double abs(double a)
{
    return a > 0 ? a : -a;
}

int main()
{
    cin.tie();
    ios_base::sync_with_stdio(0);
    freopen("markchain.in", "r", stdin);
    freopen("markchain.out", "w", stdout);

    int n;

    cin >> n;
    double prev[n][n];
    double ans[n][n];
    for (int i = 0; i < n ; i++)
    {
        for (int j = 0; j < n ; j++)
        {
            cin >> prev[i][j];
        }
    }

    while (true)
    {
        int counter = 0;
        double next[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                next[i][j] = 0;
                for (int k = 0; k < n ; k++)
                {
                    next[i][j]+=prev[i][k]*prev[k][j];
                }
            }
        }
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (abs(next[i][j]-prev[i][j])<=0.0001)
                {
                    counter++;
                }
            }
        }
        if (counter==n*n)
        {
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    ans[i][j] = next[i][j];
                }
            }
            break;
        }
        for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    prev[i][j] = next[i][j];
                }
            }

    }
    for (int i = 0; i <n ; i++)
    {
        cout << setprecision(5) << ans[0][i] << endl;
    }

    return 0;
}
