#include <iostream>
#include <stdio.h>

using namespace std;

int l[100000];
int r[100000];

void enter_left(int i, int j)
{
    r[i] = j;
    l[i] = l[j];
    r[l[j]] = i;
    l[j] = i;

}

void enter_right(int i, int j)
{
    r[i] = r[j];
    l[i] = j;
    l[r[j]] =i;
    r[j] = i;
}

void leave(int i)
{
    r[l[i]] = r[i];
    l[r[i]] = l[i];
}

int return_right(int i)
{
    return r[i];
}

int return_left(int i)
{
    return l[i];
}

int main()
{

    freopen("formation.in", "r", stdin);
    freopen("formation.out", "w", stdout);

    int n,m;

    char c;

    cin >> n >> m;

    for (int k = 0; k < m; k++)
    {
        cin >> c;
        if(c == 'l')
        {
            cin >> c >> c;
            if (c == 'f')
            {
                cin >> c;
                int i,j;
                cin >> i >> j;
                enter_left(i,j);
            }

            else

            {
                cin >> c >> c;
                int i;
                cin >> i;
                leave(i);

            }
        }

        else if(c == 'r')
        {
            cin >> c >> c >> c >> c;
            int i, j;
            cin >> i >> j;
            enter_right(i,j);
        }

        else if (c == 'n')
        {
            cin >> c >> c >> c;
            int i;
            cin >> i;
            cout << return_left(i) << " " << return_right(i) << "\n";
        }
    }



    return 0;
}
