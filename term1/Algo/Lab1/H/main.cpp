#include <iostream>
#include <stdio.h>
#include <cmath>

using namespace std;

int s_count, p_count;
int shirt[200000];
int pants[200000];

int main()
{
    freopen("style.in", "r", stdin);
    freopen("style.out", "w", stdout);



    cin >> s_count;
    for (int i = 0; i < s_count; i++)
        cin >> shirt[i];

    cin >> p_count;
    for (int i = 0; i < p_count; i++)
        cin >> pants[i];

    shirt[s_count] = 30000000;
    pants[p_count] = 50000000;



    int i = 0;
    int j = 0;

    int x = 0;
    int y = 0;

    while ((i < s_count) && (j < p_count))
    {
        if ((abs(shirt[i] - pants[j+1]) <= abs(shirt[i+1] - pants[j+1])) && (abs(shirt[i] - pants[j+1]) <= abs(shirt[i+1] - pants[j])))
        {
            j++;

        }

        else

        if ((abs(shirt[i+1] - pants[j]) <= abs(shirt[i+1] - pants[j+1])) && (abs(shirt[i+1] - pants[j+1]) <= abs(shirt[i] - pants[j+1])))
        {

            i++;
        }

        else

        if ((abs(shirt[i+1] - pants[j+1]) <= abs(shirt[i] - pants[j+1])) && (abs(shirt[i+1] - pants[j+1]) <= abs(shirt[i+1] - pants[j])))
        {

            j++;
            i++;
        }



        if (abs(shirt[i] - pants[j]) <= abs(shirt[x] - pants[y]))
        {

            x = i;
            y = j;
        }




    }

    cout << shirt[x] << " " << pants[y];


    return 0;
}
