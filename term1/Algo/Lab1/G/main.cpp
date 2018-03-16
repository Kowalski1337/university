#include <iostream>
#include <stdio.h>
#include <cmath>
#include <algorithm>

using namespace std;

int h_count, s_count, p_count, b_count;

int h[200000];
int s[200000];
int p[200000];
int b[200000];


int maximum(int a, int b)
{
    if (a > b) return a;
    else return b;
}

int minimum(int a, int b)
{
    if (a > b) return b;
    else return a;
}


int main()
{
    freopen("style.in", "r", stdin);
    freopen("style.out", "w", stdout);



    cin >> h_count;
    for (int i = 0; i < h_count; i++)
        cin >> h[i];

    cin >> s_count;
    for (int i = 0; i < s_count; i++)
        cin >> s[i];

    cin >> p_count;
    for (int i = 0; i < p_count; i++)
        cin >> p[i];

    cin >> b_count;
    for (int i = 0; i < b_count; i++)
        cin >> b[i];


    sort(h, h + h_count);
    sort(s, s + s_count);
    sort(p, p + p_count);
    sort(b, b + b_count);



    int i = 0;
    int j = 0;
    int k = 0;
    int l = 0;

    int best = maximum(maximum(b[0],s[0]),maximum(h[0],p[0])) - minimum(minimum(b[0],s[0]),minimum(h[0],p[0]));

    int h_result = 0;
    int s_result = 0;
    int p_result = 0;
    int b_result = 0;

    bool flag = true;

    while (flag == true)
    {
        int mi = minimum(minimum(h[i],s[j]),minimum(p[k],b[l]));

        if (h[i] == mi)
        {
            if (i == h_count - 1) flag = false;
            else i++;
        }

         if (s[j] == mi)
        {
            if (j == s_count - 1) flag = false;
            else j++;
        }

         if (p[k] == mi)
        {
            if (k == p_count - 1) flag = false;
            else k++;
        }

         if (b[l] == mi)
        {
            if (l == b_count - 1) flag = false;
            else l++;
        }

        int current = maximum(maximum(h[i],s[j]),maximum(p[k],b[l])) - minimum(minimum(h[i],s[j]),minimum(p[k],b[l]));

        if (current < best)
        {
            best = current;
            h_result = i;
            s_result = j;
            p_result = k;
            b_result = l;
        }

    }

    cout << h[h_result] << " " << s[s_result] << " " << p[p_result] << " " << b[b_result];


    return 0;
}
