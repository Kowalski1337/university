#include <iostream>
#include <stdio.h>
#include <fstream>
#include <vector>

using namespace std;

vector<long long> tree;



void create (long long a[], int v, int l, int r) {
	if (l == r)
		tree[v] = a[l];
	else {
        create(a, v*2, l, (l + r) / 2);
		create(a, v*2+1, (l + r) / 2+1, r);
		tree[v] = min(tree[v*2], tree[v*2+1]);
	}
}

void s(int v, int l, int r, int pos, long long change) {
	if (l == r)
		tree[v] = change;
	else {
        if (pos <= (l + r) / 2)
			s(v*2, l, (l + r) / 2, pos, change);
		else
			s(v*2+1, (l + r) / 2+1, r, pos, change);
		tree[v] = min(tree[v*2], tree[v*2+1]);
	}
}



long long get_min(int v, int l, int r, int L, int R) {
	if (L > R)
		return 2147483647;
	if (L == l && R == r)
		return tree[v];

	return min(get_min(v*2, l, (l + r) / 2, L, min(R,(l + r) / 2)), get_min(v*2+1, (l + r) / 2+1, r, max(L,(l + r) / 2+1), R));
}





int main()
{
     ifstream cin("rmq.in");
     ofstream cout("rmq.out");

     int n;
     cin >> n;
     tree.resize(4*n);

     long long a[n];

     for (int i = 0; i < n; ++i)
        cin >> a[i];

     create(a,1,0,n-1);

     for (int i = 0; i < 4*n; i++)
        cout << tree[i] << " ";

        cout << "\n";

     char ch;

     cin >> ch;

     while (!cin.eof())
     {
         cin >> ch >> ch;

         if (ch == 't')
         {

             int index;
             long long next;

             cin >> index >> next;
             s(1,0,n-1,index-1, next);


         }

         else

         {
            int l,r;
            cin >> l >> r;
            cout << get_min(1,0,n-1, l-1,r-1) << "\n";
         }

         cin >> ch;

     }

    return 0;
}
