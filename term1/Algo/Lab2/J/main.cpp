#include <iostream>
#include <stdio.h>

using namespace std;

int l[100000];
int r[100000];

int length = 0;

int first = 0;
int last = 0;
int mid = 0;

void push(int next)
{
    if (length == 0)
    {
        first = next;
        last = next;
        mid = next;
        length++;
    }

    else

    {
        r[last] = next;
        l[next] = last;
        last = next;

        length++;
        if ((length % 2 == 0)&&(length != 2)) mid = r[mid];
    }
}

void pop()
{
    if (length != 0)
        {
            length--;
            last = l[last];
            if ((length % 2 == 1)&&(length != 1)) mid=l[mid];
    }
}

void mum()
{
    r[last] = first;
    l[first] = last;

    first = r[mid];

    int x = mid;

    if (length % 2 == 1) mid = l[last];
    else mid = last;


    last = x;
}



int main()
{
    freopen("kenobi.in", "r", stdin);
    freopen("kenobi.out", "w", stdout);

    int n;
    cin >> n;

    char c;

    for(int i = 0; i < n; i++)
    {
        cin >> c;

        if (c == 'a')
        {
            cin >> c >> c;
            int next;
            cin >> next;
            push(next);
        }

        else if (c == 't')
        {
            cin >> c >> c >> c;
            pop();
        }


        else
        {
            cin >> c >> c >> c;
            mum();
        }
    }

    cout << length << "\n";

    for(int i = 0; i < length; i++)
    {
        cout << first << " ";
        first = r[first];
    }
    return 0;
}
