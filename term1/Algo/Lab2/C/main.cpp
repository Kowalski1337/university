#include <iostream>
#include <stdio.h>
#include <fstream>

using namespace std;

int stack[20000000];

int length = 0;


void push(int next)
{
    stack[length++] = next;
}

int pop()
{
    return stack[--length];
}

int main()
{


    ifstream cin("postfix.in");
     ofstream cout("postfix.out");

     char next;
     cin >> next;

    while(!cin.eof())
    {
        if(next == '+')
        {
            int a = pop();
            int b = pop() + a;
            push(b);
        }

        else

        if(next == '-')
        {
            int a = pop();
            int b = pop() - a;
            push(b);
        }

        else

        if(next == '*')
        {
            int a = pop();
            int b = pop() * a;
            push(b);
        }

        else

            push(next-'0');
        cin >> next;

    }

    cout << pop();
    return 0;
}
