#include <iostream>
#include <stdio.h>
#include <queue>
#include <fstream>

using namespace std;



int main()
{
        ifstream cin("bureaucracy.in");
        ofstream cout("bureaucracy.out");

    int n,m;

    queue<int> roflers;

    cin >> n >> m;

    long long sum = 0;
    int a;

    for (int i = 0; i < n; i++)
    {
        cin >> a;
        sum += a;
        roflers.push(a);
    }

    if (m >= sum) cout << "-1";

    else

    {


        int value = m/n;

        m -= value*n;

        for(int i = 0; i < n; i++)
        {
            int next = roflers.front();
            roflers.pop();




            if (next > value)
            {
                roflers.push(next - value);
            }

            else

            {
                m += value-next;

            }
        }



        for(int i = 0; i < m; i++)
        {
            int next = roflers.front();
            roflers.pop();
            if (next > 1) roflers.push(next-1);
        }




    cout << roflers.size() << "\n";

    n = roflers.size();

    for(int i = 0; i < n; i++)
    {
        cout << roflers.front() << " ";
        roflers.pop();
    }

}




    return 0;
}
