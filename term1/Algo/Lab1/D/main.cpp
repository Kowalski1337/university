#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

using namespace std;

int arr[50000000];

long long kth(int l,int r,int k)
{
    long long x = arr[(l+r) / 2];
    int i=l,j=r;
    while(i<=j)
    {
        while(arr[i] < x) i++;
        while(arr[j] > x) j--;

        if(i<=j)
        {
            swap(arr[i],arr[j]);
            i++;
            j--;
        }
    }
    if(l<=k && k<=j)
        return kth(l,j,k);
    if( i<=k && k<=r)
        return kth(i,r,k);
    return arr[k];
}

int main()
{
    int n,k;

    int A,B,C;

    freopen("kth.out", "w", stdout);
    freopen("kth.in", "r", stdin);

    cin >> n >> k >> A >> B >> C >> arr[0] >> arr[1];

    for (int i = 2; i < n; i++)
        arr[i] = A*arr[i-2] + B*arr[i-1] + C;

   // for (int i = 0; i < n; i++)
    //    cout << a[i] << " ";


    //for (int i = 0; i < n; i++)
      //  cout << a[i] << " ";

    cout << kth(0, n-1, k-1);

    return 0;
}
