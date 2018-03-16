#include <iostream>
#include <stdio.h>
#include <fstream>

using namespace std;

int stack[20000000];
int stack_min[20000000];

int length = 0;

int min(int a, int b)
{
    if (a < b) return a;
    else return b;
}

void push(int next)
{
    if (length == 0)
        stack_min[length] = next;
    else
        stack_min[length] = min(stack_min[length-1], next);
    stack[length++] = next;
}

int extract_min()
{
    return stack_min[length-1];
}

int pop()
{
    return stack[--length];

}

int main()
{
    ifstream cin("stack-min.in");
     ofstream cout("stack-min.out");

     int n;
     cin >> n;

     for (int i = 0; i < n; i++)
     {
         int number;
         cin >> number;

         if (number == 1)
         {
             int next;
             cin >> next;
             push(next);
         }

         else if (number == 2)
         {
             pop();
         }

         else if (number == 3)
         {
             cout << extract_min() << "\n";
         }


     }

    return 0;
}
