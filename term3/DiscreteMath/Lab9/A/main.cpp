#include<iostream>
#include<vector>
#include<algorithm>
#include<bits/stdc++.h>

using namespace std;

int n, m;
vector <vector <int> > g, gran, in;
vector <int> gam, coordVer;
vector< vector<float> > coordmind;

void lol (int kek[100][100]){
}

int main()
{
    freopen("test.in", "r", stdin);
    cin >> n >> m;
    in.resize(2);
    g.resize(m);
    coordVer.resize(n+1);
    gran.resize(m);
    coordmind.resize(m);
    for (int i = 0; i < m; i++)
    {
        g[i].assign(2, 0);
    }
    for (int i = 0; i < m; i++)
    {
        coordmind[i].assign(2, -1);
        int v, u;
        cin >> v >> u;
        g[i][0] = v;
        g[i][1] = u;
        gran[i].push_back(2);
        gran[i].push_back(0);
        gran[i].push_back(1);
    }
    for (int i = 0; i < n; i++)
    {
        in[0].push_back(i+1);
        in[1].push_back(i+1);
        int v;
        cin >> v;
        gam.push_back(v);
        coordVer[v] = i;
    }

    int rebmin = 0;

    for (int i = 0; i < m; i++)
    {
        int a = g[i][0], b = g[i][1];
        if (coordVer[a] + 1 == coordVer[b] || coordVer[b] + 1 == coordVer[a])
        {
            gran[i][0] = 1337;
            coordmind[i][0] = (float(coordVer[a]) + coordVer[b]) / 2;
            coordmind[i][1] = 0;
            if (rebmin == i) rebmin++;
        }
        if (coordVer[a] == 0 && coordVer[b] == n - 1 || coordVer[b] == 0 && coordVer[a] == n - 1)
        {
            gran[i][0] = 1337;
            coordmind[i][0] = (float(coordVer[a]) + coordVer[b]) / 2;
            coordmind[i][1] = coordmind[i][0];
            if (rebmin == i) rebmin++;
        }
    }

    cout << rebmin << endl;

    for (int i = 0; i < gran.size(); i++){
        cout << gran[i][0] << " " << gran[i][1] << " " << gran[i][2] << endl;
    }


    for (int i = 0; i < coordVer.size(); i++){
        cout << coordVer[i] << endl;
    }

    for (int i = 0; i < coordmind.size(); i++){
        cout << coordmind[i][0] << " " << coordmind[i][1] << endl;
    }

    int count = n;
    int gr;

    cout << "start" << endl;
    while (count != m)
    {
        int what = 0;
        int a = g[rebmin][0], b = g[rebmin][1];
        cout << a << " " << b << endl;
        coordmind[rebmin][0] = (float(coordVer[a]) + coordVer[b]) / 2;
        if (gran[rebmin][1] == -1)
        {

            coordmind[rebmin][1] = min((float(coordVer[a]) - coordVer[b]) / 2, (float(coordVer[b]) - coordVer[a]) / 2);
            gr = gran[rebmin][2];
            what = -1;
        }
        else
        {
            gr = gran[rebmin][1];
            coordmind[rebmin][1] = max((float(coordVer[a]) - coordVer[b]) / 2, (float(coordVer[b]) - coordVer[a]) / 2);
            what = 1;
        }
        cout << what << endl;
        cout << coordmind[rebmin][0] << " " << coordmind[rebmin][1] << endl;
        count++;
        gran[rebmin][0] = 1337;
        in.push_back(vector <int>());
        int newin = in.size() - 1;
        for (int i = 0; i < in[gr].size(); i++)
        {
            if (coordVer[in[gr][i]] >= coordVer[a] && coordVer[in[gr][i]] <= coordVer[b] || coordVer[in[gr][i]] >= coordVer[b] && coordVer[in[gr][i]] <= coordVer[a])
            {
                in[newin].push_back(in[gr][i]);
                if (coordVer[in[gr][i]] != coordVer[a] && coordVer[in[gr][i]] != coordVer[b])
                {
                    in[gr].erase(in[gr].begin() + i, in[gr].begin() + i + 1);
                    i--;
                }
            }

        }
        int l;
        if (what == -1)
            l = 2;
        else
            l = 1;
        for (int i = 0; i < m; i++)
        {
            int v = g[i][0], u = g[i][1];
            if (gran[i][0] != 1337)
            {
                if (gran[i][l] == gr)
                {
                    int ver_in_new = 0;
                    for (int j = 0; j < in[newin].size(); j++)
                    {
                        if (in[newin][j] == v || in[newin][j] == u)
                            ver_in_new++;
                    }
                    int ver_in_gr = 0;
                    for (int j = 0; j < in[gr].size(); j++)
                    {
                        if (in[gr][j] == v || in[gr][j] == u)
                            ver_in_gr++;
                    }
                    if (ver_in_new == 2)
                        gran[i][l] = newin;
                    else
                    {
                        if (ver_in_gr != 2)
                        {
                            gran[i][l] = -1;
                            gran[i][0]--;
                        }
                    }
                }
                if (gran[rebmin][0] > gran[i][0])
                    rebmin = i;
            }

        }
        if (gran[rebmin][0] == 0)
        {
            cout << "NO";
            return 0;
        }
    }
    cout << "YES" << endl;
    for (int i = 1; i <= n; i++)
        cout << coordVer[i] << " 0 ";
    cout << endl;
    for (int i = 0; i < m; i++)
    {
        cout << coordmind[i][0] << " " << coordmind[i][1] << endl;
    }
    return 0;
}
