#include <iostream>
#include <Printer.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>

using namespace std;

const int MaxN = 34;

int depth;

int c[MaxN][MaxN];


class char_stack
{
private:
    char arr[1000];
    int size = 0;
public:
    void  push(char next)
    {
        arr[size++] = next;
    }
    char element()
    {
        return arr[size - 1];
    }
    bool isEmpty()
    {
        return size == 0;
    }
    char pop()
    {
        if (size == 0) throw 1;
        return arr[--size];
    }
};

class int_stack
{
private:
    int arr[1000];
    int size = 0;
public:
    void push(int next)
    {
        arr[size++] = next;
    }
    int element()
    {
        return arr[size - 1];
    }
    bool isEmpty()
    {
        return size == 0;
    }
    int pop()
    {
        if (size == 0) throw 3; else
        return arr[--size];
    }
};

void calc_c()
{
    for (int i = 0; i <= MaxN; i++)
    {
        c[i][0] = c[i][i] = 1;
        for(int j=1; j<i; j++)
        {
            c[i][j] = c[i-1][j-1]+c[i-1][j];
        }
    }
}

int get_c(int n, int k)
{
    if (n < 0 || k < 0 || n > 33 || k > 33)
    {
        throw 4;
    }
    return c[n][k];
}

void process (int_stack & st, char op, int &cur_depth)
{
    if (op == ',') return;
    if (op < 0)
    {
        int l = st.pop();
        switch (-op)
        {
        case '+':
            st.push(l);
            break;
        case '-':
            st.push(-l);
            break;
        }
    }
    else
    {
        int r = st.pop();
        int l = st.pop();

        switch (op)
        {
        case '+':
            st.push(l + r);
            break;
        case '-':
            st.push(l - r);
            break;
        case '*':
            st.push(l * r);
            break;
        case '/':
            r == 0 ? throw 1337 :
            st.push(l/r);
            break;
        case 'C':
            cur_depth--;
            st.push(get_c(r,l));
            break;


        }
    }
}

int priority (char op)
{
    if (op < 0)
        return  4;
    return
        op == '+' || op == '-' ? 1 :
        op == '*' || op == '/' ? 2 :
        op == 'C' ? 3:
        -1;
}

bool isOp(char a)
{
    return (a == '+' || a == '/' || a == '*' || a == '-' || a == 'C');
};



int calc (string s)
{
    int par_balance = 0;
    int C_balance = 0;
    int CP_balance = 0;
    int cur_depth = 0;
    bool may_unary = true;
    char_stack op;
    int_stack num;
    int n = s.length();
    for (int i=0; i < n; i++)
    {
        char cur = s[i];
        if (cur == ' ')
        {
            continue;
        }
        if (cur == '(')
        {
            if (i != 0 && s[i-1] == 'C')
            {
                CP_balance++;
            }
            else
            {
                par_balance++;
            }
            if (s[i+1] == ')') throw 5;
            op.push('(');
            may_unary = true;
        }
        else if (cur == ')' || cur == ',')
        {
            if (cur == ')')
            {
                if (par_balance == 0)
                {
                    if (CP_balance == 0)
                    {
                        throw 8;
                    }
                    CP_balance--;
                }
                else
                {
                    par_balance--;
                }
            }
            if (cur == ',' && C_balance == 0) throw 7;
            while (op.element() != '(')
            {
                process (num, op.pop(), cur_depth);
            }
            if (cur == ')')
            {
               op.pop();
               may_unary = false;
            }
            else
            {
                C_balance--;
                may_unary = true;
            }
        }
        else if (isOp(cur))
        {
            if (cur == 'C')
            {
                C_balance++;
                if (s[i+1] != '(') throw 6;
                cur_depth++;
                depth = max(depth, cur_depth);
            }
            if (may_unary && cur != 'C')  cur = -cur;
            while (!op.isEmpty() && ( cur >= 0 && priority(op.element()) >= priority(cur)
                                      || cur < 0 && priority(op.element()) > priority(cur)))
            {
                process (num, op.pop(), cur_depth);
            }
            op.push(cur);
            may_unary = true;
        }
        else if (isdigit(s[i]))
        {
            string operand;
            while (i < n && isdigit(s[i]))
                operand += s[i++];
            --i;
            num.push(atoi (operand.c_str()));
            may_unary = false;
        }
        else
        {
            throw 2;
        }
    }

    if (CP_balance > 0 || par_balance > 0) throw 9;
    if (C_balance > 0) throw 7;



    while (!op.isEmpty())
    {
        process (num, op.pop(), cur_depth);
    }

    int ans = num.pop();

    if (num.isEmpty()) return ans;
    throw 1;
}

void intro()
{
    cout << "               EZ CALC\n";
    cout << "________________________________________________\n";
    cout << "        Created by Kowalski1337\n\n";
    cout << "1) Type \"instr\" to read instructions\n";
    cout << "2) Type \"calc\" and expression to calculate it\n";
    cout << "3) Type \"exit\" to finish work\n\n";
}

void instractions()
{
    cout << "Current version of calculator - calc 2.0\n";
    cout << "________________________________________________\n";
    cout << "Now it has:\n";
    cout << "-two unary operations(NEG(\"-\"), POS(\"+\") )\n";
    cout << "         You can use it without \"()\", so you if expression would be \"2*+--3\" for example, solution would be \"6\"\n";
    cout << "-five binary operations(ADD(\"+\"), SUB(\"-\"), MUL(\"*\"), DIV(\"/\"), COMB(\"C(k,n)\")\n";
    cout << "         Each operation must have two operands\n";
    cout << "         First before operation, second after, if it \"+\", \"-\", \"*\" or \"/\"\n";
    cout << "         And if you want to calculate combination you should use this form: \"C(f,s)\"\n";
    cout << "         Where \"f\" - first operand, \"s\" - second\n";
    cout << "         !!!IMPORTANT!!! use only \"C\" not \"c\" and don't miss \",\"\n";
}


int main()
{
    intro();
    instractions();
    string in;
    getline(cin, in);

    calc_c();



    int ans = 0;

    try
    {
        ans = calc(in);
    }
    catch(int i)
    {
        if (i == 1337) cout << "division by zero isn't allowed";
        if (i == 9) cout << "\"(\" without \")\"";
        if (i == 8) cout << "\")\" without \"(\"";
        if (i == 7) cout << "if you want to calc combination you should type C(first_arg, second arg)";
        if (i == 6) cout << "expected \"(\" after \"C\"";
        if (i == 5) cout << "expression between \"(\" and \")\" not found";
        if (i == 4) cout << "uncorrect operands of combination";
        if (i == 3) cout << "operand not found";
        if (i == 2) cout << "unexpected symbol";
        if (i == 1) cout << "operation not found";
        return 0;
    }

    Printer p (in, depth);
    p.putChar('=');
    p.putInt(ans);
    p.print();


    return 0;
}
