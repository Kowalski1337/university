#include <iostream>
#include <stdio.h>

using namespace std;

char shifr[2000000];
int length = 0;

void push(char next)
{
    shifr[length++] = next;
}

char pop()
{
    return shifr[--length];

}

int main()
{
    freopen("decode.in", "r", stdin);
    freopen("decode.out", "w", stdout);

    char current;

    cin >> current;

    while (!cin.eof())
    {

        if (length != 0)
        {


        char prev = pop();


         if (current != prev)
         {

            push(prev);
            push(current);


         }


         }

         else
         {

            push(current);
         }

        cin >> current;

    }


    for (int i = 0; i < length; i++)
        cout << shifr[i];



    return 0;
}
