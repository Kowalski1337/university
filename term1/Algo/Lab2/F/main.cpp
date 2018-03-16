#include <iostream>
#include <stdio.h>

using namespace std;

int r[1000000];
int value[1000000];
int last = 0;
int mid = 0;
int first = 0;
int size_l = 0;

void push(int next, int number)
{
    if (size_l == 0)
    {
        first = number;
        last = number;
        mid = number;
        size_l++;
        value[first] = next;
    }

    else

    {
        r[last] = number;
        last = number;
        value[last] = next;
        size_l++;
        if (size_l % 2 == 1) mid = r[mid];
    }
}

int pop()
{
    int result = value[first];
    first = r[first];
    size_l--;
    if(size_l % 2 == 1) mid = r[mid];
    return result;
}

void cheat(int next, int number)
{
      if (size_l == 0)
    {
        first = number;
        last = number;
        mid = number;
        size_l++;
        value[first] = next;
    }

    else if (size_l == 1)

    {
        r[last] = number;
        last = number;
        value[last] = next;
        size_l++;
    }

    else

    {
       r[number] = r[mid];
       r[mid] = number;
       value[number] = next;
       size_l++;
       if(size_l % 2 == 1) mid = number;
    }
}


int main()
{
    freopen("hospital.in", "r", stdin);
    freopen("hospital.out", "w", stdout);

    int n;
    cin >> n;
    char c;

    for(int i = 0; i < n; i++)
    {
        cin >> c;

        if (c == '+')
        {
            int next;
            cin >> next;
            push(next,i);
        }

        else if (c == '*')
        {
            int next;
            cin >> next;
            cheat(next,i);
        }

        else if (c == '-')
        {
            cout << pop() << "\n";
        }
    }

    return 0;
}
