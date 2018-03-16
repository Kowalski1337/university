#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

using namespace std;

int a[500000];

void quickSort(int l, int r)
{
    int x = a[rand() %(r-l+1) + l];


    int i = l;
    int j = r;

    while(i <= j)
    {
        while(a[i] < x) i++;
        while(a[j] > x) j--;
        if(i <= j)
        {
            swap(a[i], a[j]);
            i++;
            j--;
        }
    }
    if (i<r)
                quickSort(i, r);

    if (l<j)
        quickSort(l, j);
}

int main()
{
    int n;
    freopen("sort.out", "w", stdout);
    freopen("sort.in", "r", stdin);

    cin >> n;

    for (int i = 0; i < n; i++)
        cin >> a[i];

    quickSort(0, n-1);

     for (int i = 0; i < n; i++)
     {
       cout << a[i];
       if (i < n-1) cout << " ";
     }



    return 0;
}
