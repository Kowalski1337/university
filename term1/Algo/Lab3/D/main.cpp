#include <iostream>
#include <vector>
#include <stdio.h>
#include <fstream>


using namespace std;

struct Matrix
{
    int a00, a01, a10, a11;
};

int r;
int pow = 1;




vector< Matrix > tree;


Matrix neutral_element;




Matrix calculate(Matrix first, Matrix second)
{
    Matrix next;
    next.a00 = (first.a00 * second.a00 + first.a01 * second.a10)%r;
    next.a01 = (first.a00 * second.a01 + first.a01 * second.a11)%r;
    next.a10 = (first.a10 * second.a00 + first.a11 * second.a10)%r;
    next.a11 = (first.a10 * second.a01 + first.a11 * second.a11)%r;
    return next;
}

void create()
{
    for(int i = pow - 1; i >=1; i--)
    {
        tree[i] = calculate(tree[2*i], tree[2*i+1]);
    }
}

Matrix product(int node, int l, int r, int L, int R)
{
    if((L<=l) && (r<=R))
    {
        return tree[node];
    }
    else if((L<=r) && (R>=l))
    {
        int m = (l+r)/2;
        return calculate(product(node*2, l, m, L, R),product(node*2+1, m+1, r , L, R));
    }
    else
        return neutral_element;

}


int main()
{

    ifstream cin("crypto.in");
    ofstream cout("crypto.out");
    int n;

    cin >> r;
    cin >> n;


    int m;
    cin >> m;

    neutral_element.a00 = 1;
    neutral_element.a01 = 0;
    neutral_element.a10 = 0;
    neutral_element.a11 = 1;



    while(pow < n)
    {
        pow *= 2;
    }


    tree.resize(2*pow);

    for (int i = pow; i < pow+n; i++)
    {
       cin >> tree[i].a00 >> tree[i].a01 >> tree[i].a10 >> tree[i].a11;
    }



    for (int i = pow+n; i <2*pow; i++)
        tree[i] = neutral_element;

    create();

    cout << "lol";

    for(int i = 1; i < 2*pow; i++)
    {
        cout << tree[i].a00 << " " << tree[i].a01 << "\n" << tree[i].a10 << " " << tree[i].a11 << "\n\n";
    }

    for(int i = 0; i < m; i++)
    {
        int l,r;
        cin >> l >> r;

        Matrix result = product(1, 0, pow-1, l-1, r-1);

        cout << result.a00 << " " << result.a01 << "\n" << result.a10 << " " << result.a11 << "\n\n";
    }





    return 0;
}
