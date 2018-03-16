#include <iostream>
#include <fstream>


using namespace std;

struct name
{
    bool ifEnter;
    long long index;
};


long long pow = 1;


long long tree[1000000];
name events[10000000];
long long answer[1000000];

void set(long long node, long long l ,long long r, long long pos ,long long value){
    if (l > r) return;
    if (l == r) tree[node] = value;
    else
    {
        long long m = (l + r)/2;
        if (m >= pos) set(2*node, l, m, pos, value);
        else set(2*node + 1, m + 1, r, pos, value);
        tree[node] = tree[2*node] + tree[2*node + 1];
    }
}

long long get_sum(long long node, long long l, long long r, long long L, long long R){
    if (L > R) return 0;
    if (l == L && r == R) return tree[node];
    else
    {
        long long m = (l + r)/2;
        return get_sum(2*node, l, m, L, min(m,R)) + get_sum(2*node + 1, m + 1, r, max(m + 1, L), R);

    }
}



int main()
{
    ifstream cin("taxibus.in");
    ofstream cout("taxibus.out");
    long long n;

    /*set(1, 0, 7, 0, 1);
    set(1, 0, 7, 1, 2);
    set(1, 0, 7, 2, 3);
    set(1, 0, 7, 3, 4);
    set(1, 0, 7, 4, 5);

    for (int i = 1; i < 16; i++)
        cout << tree[i] << " ";

    cout << "\n";

    cout << get_sum(1, 0, 7, 2-1, 3-1);*/

    cin >> n;

    while (pow < n)
        pow *= 2;

    for (long long i = 1; i <= n; i++)
    {
        long long a,b;
        cin >> a >> b;
        events[a].ifEnter = true;
        events[a].index = i;
        events[b].ifEnter = false;
        events[b].index = i;
    }




    long long place;

    if (pow > 100000) place = 100000;
    else place = pow;

    long long sum = 0;

    for (long long i = 1; i <= 2*n; i++)
    {
        if(events[i].ifEnter)
        {
            set(1, 0, pow-1, place-1, 1);
           // cout << "1 ";
            answer[events[i].index] = place--;
        }

        else

        {
            sum += get_sum(1, 0, pow - 1, 0, answer[events[i].index]-2);
           // cout << "2 ";
            set(1, 0, pow - 1, answer[events[i].index]-1, 0);
           // cout << "3 ";
        }
    }

    cout << sum << "\n";


    for (long long i = 1; i <= n; i++)
        cout << answer[i] << " ";

    return 0;
}
