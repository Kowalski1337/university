#include <iostream>
#include <stdio.h>
#include <iomanip>

using namespace std;

int main()
{
    cin.tie();
    ios_base::sync_with_stdio(0);
    freopen("absmarkchain.in", "r", stdin);
    freopen("absmarkchain.out", "w", stdout);


    int n, m;
    cin >> n >> m;

    int inpb[m];
    int inpe[m];
    double inpp[m];



    int absorb = 0;

    bool absorbing[m] = {false};


    for (int i = 0; i < m; i++)
    {
        cin >> inpb[i] >> inpe[i] >> inpp[i];
        inpb[i]--;
        inpe[i]--;
        if (inpb[i] == inpe[i] && inpp[i] == 1)
        {
            absorb++;
            absorbing[inpb[i]] = true;

        }
    }
    int notabsorb = n - absorb;

    double r[notabsorb][absorb];

    double q[notabsorb][notabsorb];

    double E[notabsorb][notabsorb];

    double N[notabsorb][notabsorb];

    double g[notabsorb][absorb];

    for (int i = 0; i < notabsorb; i++)
    {
        for (int j = 0; j < notabsorb; j++)
        {
            q[i][j] = E[i][j] = N[i][j] = 0.0;
        }
        for (int j = 0; j < absorb; j++)
        {
            r[i][j] = g[i][j] = 0.0;
        }
    }

    int position[n];



    int countr = 0, countq = 0;

    for (int i = 0; i < n; i++)
    {
        position[i] = absorbing[i] ? countr++ : countq++;
    }

    for (int i = 0; i < m; i++)
    {
        if (absorbing[inpe[i]])
        {
            if (!absorbing[inpb[i]])
            {
                r[position[inpb[i]]][position[inpe[i]]] = inpp[i];
            }
        }
        else
        {
            q[position[inpb[i]]][position[inpe[i]]] = inpp[i];
        }
    }



    for (int i = 0; i < notabsorb; i++)
    {
        E[i][i] = 1.0;
        N[i][i] = 1.0;
        for (int j = 0; j < notabsorb; j++)
        {
            E[i][j] -= q[i][j];
        }
    }

    double temp;



    for (int i = 0; i < notabsorb; i++)
    {
        if (E[i][i] != 1.0)
        {
            temp = E[i][i];
            for (int j = 0; j < notabsorb; j++)
            {
                E[i][j] /= temp;
                N[i][j] /= temp;
            }
        }
        for (int j = 0; j < notabsorb; j++)
        {
            if (i != j)
            {
                temp = E[j][i];
                for (int k = 0; k < notabsorb; k++)
                {
                    E[j][k] -= temp * E[i][k];
                    N[j][k] -= temp * N[i][k];
                }
            }
        }
    }



    for (int i = 0; i < notabsorb; i++)
        for (int j = 0; j < absorb; j++)
            for (int k = 0; k < notabsorb; k++)
                g[i][j] += N[i][k] * r[k][j];


    for (int i = 0; i < n; i++)
    {
        temp = 0.0;
        if (absorbing[i])
        {
            for (int j = 0; j < notabsorb; j++)
                temp += g[j][position[i]];
            temp  = temp + 1;
            temp /= n;
        }
        cout << setprecision(5) << temp << endl;
    }


    return 0;
}
