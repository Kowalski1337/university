#include <iostream>
#include <fstream>

using namespace std;

struct Node
{
   int x;
   int heigth;
   int size;
   Node *l,*r;
};


int get_heigth(Node *&Tree)
{
    if(Tree != NULL) return Tree->heigth;
    else return 0;
}

int get_size(Node *&Tree)
{
    if (Tree != NULL) return Tree->size;
    else return 0;
}

void calculate_heigh_size(Node *&Tree)
{
    Tree->heigth = max(get_heigth(Tree->r),get_heigth(Tree->l))+1;
    Tree->size = get_size(Tree->r) + get_size(Tree->l) + 1;
}

Node* big_russian_right_rotate(Node *&a)
{
    Node* b = a->l;
    a->l = b->r;
    b->r = a;

    calculate_heigh_size(a);
    calculate_heigh_size(b);
    return b;
}

Node* big_russian_left_rotate(Node *&a)
{
    Node* b = a->r;
    a->r = b->l;
    b->l = a;

    calculate_heigh_size(a);
    calculate_heigh_size(b);
    return b;
}


Node* Balance(Node *&Tree)
{
    calculate_heigh_size(Tree);
    if (get_heigth(Tree->l) - get_heigth(Tree->r) == 2)
    {
        if (get_heigth(Tree->l->l) - get_heigth(Tree->l->r) >= 0)
            return big_russian_right_rotate(Tree);
        else
        {
            Tree->l = big_russian_left_rotate(Tree->l);
            return big_russian_right_rotate(Tree);
        }
    }
    else

    if (get_heigth(Tree->l) - get_heigth(Tree->r) == -2)
    {
        if (get_heigth(Tree->r->l) - get_heigth(Tree->r->r) <= 0)
            return big_russian_left_rotate(Tree);
        else
        {
            Tree->r = big_russian_right_rotate(Tree->r);
            return big_russian_left_rotate(Tree);
        }
    }


    return Tree;
}



void type(Node *&Tree)
{
	if (Tree != NULL)
	{

	   type(Tree->l);
	   cout<<Tree->x;
	   type(Tree->r);

	}
}

Node* insert(int x,Node *&Tree)
{
	if (Tree == NULL)
	{
        Tree=new Node;
		Tree->x = x;
		Tree->l = NULL;
		Tree->r = NULL;
		Tree->heigth = 0;
		Tree->size = 1;
		return Tree;
	}

	 if (x<Tree->x)


                             Tree->l = insert(x,Tree->l);

  else  if (x>Tree->x)

                            Tree->r = insert(x,Tree->r);


                  return Balance(Tree);
}

bool exists(int x, Node *&Tree)
{
    if (Tree == NULL) return false;
    if (Tree->x == x) return true;

    if(x > Tree -> x)
        exists(x, *&Tree -> r);
        else exists(x, *&Tree ->l);
}

Node* next(int x, Node *&Tree)
{
    Node *result = NULL;
    Node *current = Tree;

    while (current != NULL)
    {
        if (current -> x >x)
        {
            result = current;
            current = current ->l;
        }

        else

        current = current ->r;
    }

    return result;
}


Node* prev(int x, Node *&Tree)
{
    Node *result = NULL;
    Node *current = Tree;

    while (current != NULL)
    {
        if (current -> x < x)
        {
            result = current;
            current = current -> r;
        }

        else

        current = current ->l;
    }

    return result;
}

Node* Delete(int x, Node *&Tree)
{
    if (Tree == NULL) return NULL;

    if (x > Tree -> x)
        {
            Tree->r = Delete(x, Tree ->r);
        }

    else if (x < Tree -> x)
        {
            Tree->l = Delete(x, Tree ->l);
        }


    else if (x == Tree ->x)
        {
            if((Tree -> l != NULL) && (Tree -> r != NULL))
            {
                Node* New = next(Tree ->x, Tree);

                Tree -> x = New->x;
                Tree->r = Delete(New->x, Tree ->r);
            }

            else if (Tree -> l != NULL)
            {
                Tree = Tree ->l;
            }

            else if

            (Tree -> r != NULL)
            {
                Tree = Tree -> r;
            }

            else return NULL;

        }


    return Balance(Tree);
}

Node* Kth_order_statistic(int k, Node *Tree)
{
    while (Tree != NULL)
    {
        if (get_size(Tree->l) + 1 == k) return Tree;

            else

            {
                if (get_size(Tree->l) < k)
                {
                    k -= get_size(Tree->l) + 1;
                    Tree = Tree->r;
                }

                else

                {
                    Tree = Tree->l;
                }
            }
        }

    return NULL;


}






int main()
{
    ifstream cin("river.in");
    ofstream cout("river.out");

    Node *Tree = NULL;







    Tree = NULL;

    long long n;
    cin >> n;

    long long m;
    cin >> m;

    long long right = 0;
    long long sum = 0;

    for (long long i = 0; i < n; i++)
    {
        long long a;
        cin >> a;
        sum += a*a;
        right += a;
        Tree = insert(right, Tree);
    }

    cout << sum << "\n";

    int k;
    cin >> k;

    for(int i = 0; i < k; i++)
    {
        int event;
        cin >> event;

        int number;
        cin >> number;



        long long r = Kth_order_statistic(number, Tree)->x;

        Node *p = prev(r, Tree);



        long long l;

        if (p != NULL) l = p->x;
        else l = 0;

        long long m = (l+r)/2;

        if (event == 2)
        {
            sum -= (r-l)*(r-l);
            sum += (m-l)*(m-l);
            sum += (r-m)*(r-m);
            Tree = insert(m, Tree);
        }

        else

        {
            if (l == 0)
            {

                long long n = Kth_order_statistic(number+1, Tree)->x;

                sum -= (r-l)*(r-l);
                sum -= (n-r)*(n-r);
                sum += (n-l)*(n-l);
                Tree = Delete(r, Tree);
            }

            else

            {
                if (number != get_size(Tree))
                 {

                    long extra = number > 2 ? Kth_order_statistic(number-2,Tree)->x : 0;

                    long next = Kth_order_statistic(number+1,Tree)->x;

                    sum -= (l - extra) * (l - extra);
                    Tree = Delete(l,Tree);
                    sum -= (next - r) * (next - r);
                    Tree = Delete(r,Tree);
                    sum -= (r-l)*(r-l);
                    sum += (m - extra) * (m - extra);
                    Tree = insert(m,Tree);
                    sum += (next - m) * (next - m);
                 }


                 else

                 {

                     long extra = number > 2 ? Kth_order_statistic(number-2, Tree)->x : 0;

                     sum -= (r-l)*(r-l);
                     sum -= (l-extra)*(l-extra);
                     Tree = Delete(l, Tree);
                     sum += (r-extra)*(r-extra);
                 }
            }
        }

        cout << sum << "\n";
    }




    return 0;
}
