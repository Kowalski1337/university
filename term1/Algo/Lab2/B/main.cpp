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
    freopen("brackets.in", "r", stdin);
    freopen("brackets.out", "w", stdout);

    char current;

    cin >> current;

    while (!cin.eof())
    {

        if (length != 0)
        {


        char prev = pop();




         if (!(((current == ')') && (prev  == '(')) || ((current == ']') && (prev  == '[')) || ((current == '}') && (prev  == '{'))))
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


    if (length == 0) cout << "Yes";
    else cout << "No";



    return 0;
}
