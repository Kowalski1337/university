#include <bits/stdc++.h>

using ll = int64_t;
using namespace std;

struct Edge {
private:
    int backId, flow, capacity, cost;
public:
    Edge(int capacity, int flow, int cost, int backId) {
        this->backId = backId;
        this->capacity = capacity;
        this->cost = cost;
        this->flow = flow;
    }

    int getBackId() {
        return backId;
    }

    int getCapacity() {
        return capacity;
    }

    int getFlow() {
        return flow;
    }

    void increaseFlow(int df) {
        flow += df;
    }

    int getCost() {
        return cost;
    }
};

int main() {
//    cin.tie();
//    ios_base::sync_with_stdio(0);
//    freopen("mincost.in", "r", stdin);
//    freopen("mincost.out", "w", stdout);
    int n, m;
    cin >> n >> m;

    int N = 2 * (n + 1);

    int s = 0;
    int t = n - 1;
    vector<Edge> edges;
    vector<vector<pair<int, int>>> g(static_cast<unsigned int>(N));

    s = 2 * n;
    t = 2 * n + 1;
    int temp = 0;
    for (int i = 0; i < n; i++) {
        int x;
        cin >> x;
        edges.emplace_back(1, 0, x, temp + 1);
        edges.emplace_back(0, 0, -x, temp);
        g[i * 2 + 1].emplace_back(i * 2, temp);
        g[i * 2].emplace_back(i * 2 + 1, temp + 1);
        temp += 2;
        edges.emplace_back(n - 1, 0, 0, temp + 1);
        edges.emplace_back(0, 0, 0, temp);
        g[i * 2].emplace_back(i * 2 + 1, temp);
        g[i * 2 + 1].emplace_back(i * 2, temp + 1);
        temp += 2;
        edges.emplace_back(1, 0, 0, temp + 1);
        edges.emplace_back(0, 0, 0, temp);
        g[s].emplace_back(i * 2 + 1, temp);
        g[i * 2 + 1].emplace_back(s, temp + 1);
        temp += 2;
        edges.emplace_back(1, 0, 0, temp + 1);
        edges.emplace_back(0, 0, 0, temp);
        g[i * 2].emplace_back(t, temp);
        g[t].emplace_back(i * 2, temp + 1);
        temp += 2;
    }

    for (int i = 0; i < m; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        a--;
        b--;
        edges.emplace_back(n, 0, c, temp + 1);
        edges.emplace_back(0, 0, -c, temp);
        g[b * 2 + 1].emplace_back(2 * a, temp);
        g[2 * a].emplace_back(2 * b + 1, temp + 1);
        temp += 2;
    }

    n = N;
    ll flow = 0;
    ll cost = 0;
    ll maxFlow = INT64_MAX;

    while (flow < maxFlow) {
        vector<int> id(n, 0), p(static_cast<unsigned int>(n)), rib(static_cast<unsigned int>(n));
        vector<int> q(static_cast<unsigned int>(n));
        vector<ll> d(static_cast<unsigned int>(n), INT64_MAX);
        int qh = 0, qt = 0;
        q[qt++] = s;
        d[s] = 0;
        while (qh != qt) {
            int v = q[qh++];
            id[v] = 2;
            if (qh == n) qh = 0;
            for (int i = 0; i < g[v].size(); ++i) {
                Edge r = edges[g[v][i].second];
                if (r.getFlow() < r.getCapacity() && d[v] + r.getCost() < d[g[v][i].first]) {
                    d[g[v][i].first] = d[v] + r.getCost();
                    if (id[g[v][i].first] == 0) {
                        q[qt++] = g[v][i].first;
                        if (qt == n) qt = 0;
                    } else if (id[g[v][i].first] == 2) {
                        if (--qh == -1) qh = n - 1;
                        q[qh] = g[v][i].first;
                    }
                    id[g[v][i].first] = 1;
                    p[g[v][i].first] = v;
                    rib[g[v][i].first] = g[v][i].second;
                }
            }
        }
        if (d[t] == INT64_MAX) break;
        ll addflow = INT64_MAX;
        for (int v = t; v != s; v = p[v]) {
            int pr = rib[v];
            if (edges[pr].getCapacity() - edges[pr].getFlow() < addflow) {
                addflow = edges[pr].getCapacity() - edges[pr].getFlow();
            }
        }
        for (int v = t; v != s; v = p[v]) {
            int pr = rib[v], r = edges[pr].getBackId();
            edges[pr].increaseFlow(addflow);
            edges[r].increaseFlow(-addflow);
            cost += edges[pr].getCost() * addflow;
        }
        flow += addflow;
    }

    cout << cost;
    return 0;
}