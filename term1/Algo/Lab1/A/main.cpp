#include <iostream>
#include <stdio.h>


using namespace std;

int main()
{
   int n;
   freopen("isheap.in", "r", stdin);

   cin >> n;

   bool flag = true;

   int a[n];
   for(int i = 0; i < n; i++)
   {
       cin >> a[i];
   }


    fclose(stdin);
    freopen("isheap.out", "w", stdout);



   for (int i = 0; i < n; i++)
   {
       if ((2*i+1 < n)&&(a[i] > a[2*i+1])) flag = false;

       if ((2*i+2 < n)&&(a[i] > a[2*i+2])) flag = false;
   }




   if (flag == true) cout << "Yes"; else cout << "No";

   return 0;


}
