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
		tree[v] = tree[v*2] + tree[v*2+1];
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
		tree[v] = tree[v*2] + tree[v*2+1];
	}
}



long long get_sum(int v, int l, int r, int L, int R) {
	if (L > R)
		return 0;
	if (L == l && R == r)
		return tree[v];

	return get_sum(v*2, l, (l + r) / 2, L, min(R,(l + r) / 2))
		+ get_sum(v*2+1, (l + r) / 2+1, r, max(L,(l + r) / 2+1), R);
}





int main()
{
     ifstream cin("rsq.in");
     ofstream cout("rsq.out");

     int n;
     cin >> n;
     tree.resize(4*n);

     long long a[n];

     for (int i = 0; i < n; ++i)
        cin >> a[i];

     create(a,1,0,n-1);

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
            cout << get_sum(1,0,n-1, l-1,r-1) << "\n";
         }

         cin >> ch;

     }

    return 0;
}
