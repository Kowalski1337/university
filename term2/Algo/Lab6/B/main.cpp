#include <iostream>
#include <bits/stdc++.h>

using namespace std;

const long long inf = LLONG_MAX;

struct graph
{
    int e,w;
};

int main()
{
    freopen("pathbgep.in", "r", stdin);
    freopen("pathbgep.out", "w", stdout);

    cin.tie();

    ios_base::sync_with_stdio(false);

    //freopen("pathmgep.in ", "r", stdin);
    //freopen("pathmgep.out", "w", stdout);

    int n, m, e;

    cin >> n >> m;

    vector<vector<graph> > g(n);

    /*for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            int next;
            cin >> next;
            if (next != -1){
                graph cur;
                cur.e = j;
                cur.w = next;
                g[i].push_back(cur);
            }
        }
    }*/


    for (int i = 0; i < m; i++)
    {
        int a, b, c;
        cin >> a >> b >> c;
        a--;
        b--;
        graph cur;
        cur.e = b;
        cur.w = c;
        g[a].push_back(cur);
        graph cur1;
        cur1.e = a;
        cur1.w = c;
        g[b].push_back(cur1);
    }

    long long d[n];

    bool used[n] = {};

    for (int i = 0; i < n; i++)
    {
        d[i] = inf;
    }

    d[0] = 0;

    set<pair<long long,int> > heap;

    heap.insert(make_pair(0, 0));

    while (heap.size() != 0)
    {
        int bi = heap.begin()->second;
        heap.erase(heap.begin());

        used[bi] = true;

        if (d[bi] == inf)
        {
            break;
        }

        for (size_t j = 0; j < g[bi].size(); j++)
        {
            int ei = g[bi][j].e;
            int wi = g[bi][j].w;

            if (d[ei] > d[bi] + wi)
            {
                heap.erase(make_pair(d[ei],ei));
                d[ei] = d[bi] + wi;
                heap.insert(make_pair(d[ei],ei));
            }
        }
    }

    for (int i = 0; i < n; i++)
    {
        cout << d[i] << " ";
    }

    /*if (d[e-1] == inf){
        cout << "-1";
    } else {
        cout << d[e-1];
    }*/

    return 0;
}
