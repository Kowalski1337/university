#include <iostream>
#include <vector>
#include <stdio.h>
#include <fstream>
#include <algorithm>


using namespace std;

struct candy_shop
{
    long long min = 0;
    long long max = 0;
    int add = 0;
};

int pow = 1;




vector< candy_shop > tree;






void create()
{
    for(int i = pow - 1; i >=1; i--)
    {
        tree[i].min = min(tree[2*i].min,tree[2*i+1].min);
        tree[i].max = max(tree[2*i].max,tree[2*i+1].max);
    }
}

void propagate(int node)
{
    if (node < pow)
    {
        tree[2*node].add += tree[node].add;
        tree[2*node+1].add += tree[node].add;
        tree[2*node].min -= tree[node].add;
        tree[2*node].max -= tree[node].add;
        tree[2*node+1].min -= tree[node].add;
        tree[2*node+1].max -= tree[node].add;
        tree[node].add = 0;
    }
}

int number(int node, int l, int r, int impudence)
{
    if(tree[node].min >= impudence)
    {
        tree[node].add++;
        tree[node].min--;
        tree[node].max--;
        return r-l+1;
    }
    else if (tree[node].max < impudence) return 0;
    else
    {
        propagate(node);
        int m = (l+r)/2;
        return number(node*2, l, m, impudence) + number(node*2+1, m+1, r , impudence);
    }

}


int main()
{

    ifstream cin("candies.in");
    ofstream cout("candies.out");
    int n;

    cin >> n;






    while(pow < n)
    {
        pow *= 2;
    }


    tree.resize(2*pow);

    long long elements[n];

    for (int i = 0; i < n; i++)
    {
       cin >> elements[i];
    }

    sort(elements, elements +n);

     /*  for (int i = 0; i < n; i++)
    {
       cout << elements[i] << " ";
    }

    cout << "\n\n";*/



    for (int i = pow; i < pow+n; i++)
    {
        tree[i].max = elements[i-pow];
        tree[i].min = elements[i-pow];
    }





    create();

    /* for (int i = 1; i < pow*2; i++)
        cout << tree[i].min << " " << tree[i].max  << tree[i].add<< "\n";*/


    int m;

    cin >> m;

    for(int i = 0; i < m; i++)
    {
        int current;
        cin >> current;

        cout << number(1, 0, pow-1, current) << "\n";
    }





    return 0;
}
