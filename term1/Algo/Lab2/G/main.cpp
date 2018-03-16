#include <iostream>
#include <stdio.h>
#include <cmath>

using namespace std;

int line[10000000];
int last = 0;
int first = 0;

int roflers[1000000];
int enter[1000000];
int escape[1000000];

void push(int next)
{
    line[last++] = next;
}

int pop()
{
    return line[first++];
}

int main()
{
    freopen("saloon.in", "r", stdin);
    freopen("saloon.out", "w", stdout);

    int n;
    cin >> n;

    int hour;
    int minute;

    for(int i = 0; i < n; i++)
    {
        cin >> hour >> minute >> roflers[i];
        enter[i] = 60*hour + minute;
    }

    int next = 0;
    int current;

    while ((next < n) || (last - first != 0))
    {
        if (last - first == 0)
        {
            push(next);
            current = enter[next];
            next++;
        }

        else

        {
            while(abs(enter[next] - current) < 20)
            {
                if (roflers[next] >= last - first) push(next);
                else escape[next] = enter[next];
                next++;
            }

            current += 20;

            escape[pop()] = current;

        }

    }

    for(int i = 0; i < n; i++)
        cout << escape[i]/60 << " " << escape[i]-escape[i]/60*60 << "\n";



    return 0;
}
