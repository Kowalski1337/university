#include <iostream>
#include <stdio.h>

using namespace std;

int h[1000000];
int length = 0;

void push(int next)
{
    h[length++] = next;
}

int sum(int col)
{
    int result = 0;
    for (int i = 0; i < col; i++)
        result += h[length - i -1];
    return result;
}

int leave()
{
   return  h[--length];
}

int main()
{
    freopen("hemoglobin.in", "r", stdin);
    freopen("hemoglobin.out", "w", stdout);

    int n;
    cin >> n;

    for(int i = 0; i < n; i++)
    {
        char c;
        cin >> c;

        if(c == '+')
        {
            int next;
            cin >> next;
            push(next);
        }

        else if(c == '-')
            cout << leave() << "\n";

        else
        {
            int s;
            cin >> s;
            cout << sum(s) << "\n";
        }
    }



    return 0;
}
