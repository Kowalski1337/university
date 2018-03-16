#include <iostream>
#include <fstream>
#include <stdio.h>

using namespace std;

struct Node
{
   int x;
   int heigth;
   Node *l,*r;
};


int get_heigth(Node *&Tree)
{
    if(Tree != NULL) return Tree->heigth;
    else return 0;
}

void calculate_heigh(Node *&Tree)
{
    Tree->heigth = max(get_heigth(Tree->r),get_heigth(Tree->l))+1;
}

Node* big_russian_right_rotate(Node *&a)
{
    Node* b = a->l;
    a->l = b->r;
    b->r = a;

    calculate_heigh(a);
    calculate_heigh(b);
    return b;
}

Node* big_russian_left_rotate(Node *&a)
{
    Node* b = a->r;
    a->r = b->l;
    b->l = a;

    calculate_heigh(a);
    calculate_heigh(b);
    return b;
}


Node* Balance(Node *&Tree)
{
    calculate_heigh(Tree);
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

Node* get_min(Node *&Tree)
{
    Node* result = Tree;
    while (result->l != NULL)
        result = result->l;
    return result;
}

int main()
{
    ifstream cin("parking.in");
    ofstream cout("parking.out");

    Node *Tree = NULL;
    int n;
    int m;
    cin >> n;

    for (int i = 1; i<= n; i++)
        Tree = insert(i,Tree);

    cin >> m;

    char ch;

    cin >> ch >> ch;

    for (int i = 0; i < m; i++)
    {


        if (ch == 'x')
        {
            cin >> ch >> ch;
            int current;
            cin >> current;
            Tree = insert(current, Tree);

        }

        else

        {
            cin >> ch >> ch>> ch;
            int current;
            cin >> current;
            if (exists(current,Tree))
                {
                  Tree = Delete(current, Tree);
                  cout << current << "\n";
                }

                else

                    {
                        Node *place = next(current, Tree);
                        if (place != NULL)
                        {
                            cout << place->x << "\n";
                            Tree = Delete(place->x, Tree);

                        }

                        else

                        {

                            Node* place = get_min(Tree);
                            cout << place->x << "\n";
                                Tree = Delete(place->x, Tree);

                        }
                    }
        }

        cin >> ch >> ch;
    }


    return 0;
}
