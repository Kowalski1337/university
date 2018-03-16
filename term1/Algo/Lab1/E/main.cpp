#include <iostream>
#include <stdio.h>

using namespace std;

string s[10000];
string s1[10000];

int main()
{
    freopen("radixsort.in", "r", stdin);
    freopen("radixsort.out", "w", stdout);

    int n, m, k;
    cin >> n >> m >> k;

    for (int i = 0; i < n; i++)
        cin >> s[i];

    //for (int i = 0; i < n; i++)
      //  for (int j = 0; j < m; j++)
        //     cout << s[i][j] << " ";

            // cout << "\n";



    for (int i = 0; i < k; i++)
    {

           int c[26] = {0};
           int l[26] = {0};

        for (int j = 0; j < n; j++)
             c[(int)s[j][m-i-1]-(int)'a']++;

        l[0] = 0;

        for (int j = 1; j < 26; j++)
            l[j] = l[j-1] + c[j-1];

        for (int j = 0; j < n; j++)
        {
             s1[l[(int)s[j][m-i-1]-(int)'a']++] = s[j];
        }


        for (int j = 0; j < n; j++)
            s[j] = s1[j];
    }




    for (int i = 0; i < n; i++)
    {

            cout << s[i] << "\n";

    }







    return 0;
}
