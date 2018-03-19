#include "Printer.h"
#include <sstream>

using namespace std;

void Printer::init(int hh)
{
    arr = vector<vector<string> >(hh);
    for(int i=0; i<hh; i++)
    {
        arr[i] = vector<string>();
        for(int j=0; j<130; j++)
        {
            arr[i].push_back(" ");
        }
    }
}

Printer::Printer(string & s, int maxC)
{
    h = vector<int>(maxC+1);
    h[0] = 5;
    for(int i=1; i<maxC+1; i++)
    {
        h[i] = 2*h[i-1]+1;
    }
    init(h[maxC]);
    pair<int, int> p = parse(s, 0, 0, 0, 0, 0);
    curX = p.first;
}

void Printer::fillX(int x, int y)
{
    fillX(x, y, 3);
}

void Printer::fillX(int x, int y, int sz)
{
    for(int xx=x; xx<x+sz; xx++)
    {
        arr[y][xx] = '#';
    }
}

void Printer::fillY(int x, int y, int sz)
{
    for(int yy=y; yy<y+sz; yy++)
    {
        arr[yy][x] = '#';
    }
}

void Printer::printStr(string & s)
{
    for(int i=0; i<s.length(); i++)
    {
        if(s.at(i)==' ')
        {
            cout << " ";
            //cout << "\x1B[30m\u25AA";
        }
        else
        {
            cout << "#";
            //cout << "\x1B[37m\u25AA";
        }
    }
}
int Printer::getCharWidth(char c)
{
    if(c=='+' || c=='=')
    {
        return 5;
    }
    else if(c=='1' || c=='*' || c=='/')
    {
        return 1;
    }
    return 3;
}
int Printer::getHeight(int depth)
{
    return h[h.size()-depth-1];
}
pair<int, int> Printer::parse(string & s, int x, int y, int startI, int startBalance, int depth)  //returns <dx, di>
{
    int balance = startBalance;
    int dx = 0;
    int di = 0;
    bool firstOpenBracket = false;
    for(int i=startI; i<s.length(); i++, di++)
    {
        if(s[i]!=' ')
        {
            if(s[i]=='(')
            {
                balance++;
            }
            else if(s[i]==')')
            {
                balance--;
            }
            if(!firstOpenBracket && startBalance==0 && depth>0)
            {
                firstOpenBracket = true;
            }
            else if(s[i]!=',' && !(startBalance==1 && balance==0 && s[i]==')'))
            {
                putChar(s[i], x, y, getHeight(depth));
                x += getCharWidth(s[i])+2;
                dx += getCharWidth(s[i])+2;
            }
            if(balance==0 && depth>0)
            {
                return make_pair(dx, di);
            }
            if(s[i]=='C')
            {
                pair<int, int> p1 = parse(s, x, y+getHeight(depth)-getHeight(depth+1), i+1, 0, depth+1);
                i += p1.second+2;
                di += p1.second+2;
                pair<int, int> p2 = parse(s, x, y, i, 1, depth+1);
                i += p2.second;
                di += p2.second;
                x += max(p1.first, p2.first);
                dx += max(p1.first, p2.first);
            }
            else if(s[i]==',')
            {
                return make_pair(dx, di);
            }
        }
    }
    return make_pair(dx, di);
    //throw "WOW SON";
}
void Printer::putInt(int ii)
{
    stringstream s;
    s << ii;
    string ss = s.str();
    for(int i=0; i<ss.length(); i++)
    {
        putChar(ss[i]);
    }
}
void Printer::putChar(char c)
{
    putChar(c, curX, 0, getHeight(0));
    curX += getCharWidth(c)+2;
}
void Printer::putChar(char c, int x, int y, int sz)
{
    if(c == '0')
    {
        fillX(x, y);
        fillX(x, y+sz-1);
        fillY(x, y, sz);
        fillY(x+2, y, sz);
    }
    else if(c == '1')
    {
        fillY(x, y, sz);
    }
    else if(c == '2')
    {
        int m = (2*y+sz)/2;
        fillX(x, y);
        fillX(x, y+sz-1);
        fillX(x, m);
        fillY(x+2, y, m-y);
        fillY(x, m, y+sz-m-1);
    }
    else if(c == '3')
    {
        int m = (2*y+sz)/2;
        fillX(x, y);
        fillX(x, y+sz-1);
        fillX(x, m);
        fillY(x+2, y, sz);
    }
    else if(c == '4')
    {
        int m = (2*y+sz)/2;
        fillY(x, y, m-y);
        fillX(x, m);
        fillY(x+2, y, sz);
    }
    else if(c == '5')
    {
        int m = (2*y+sz)/2;
        fillX(x, y);
        fillX(x, y+sz-1);
        fillX(x, m);
        fillY(x, y, m-y);
        fillY(x+2, m, y+sz-m);
    }
    else if(c == '6')
    {
        int m = (2*y+sz)/2;
        fillX(x, y);
        fillX(x, y+sz-1);
        fillX(x, m);
        fillY(x, y, sz);
        fillY(x+2, m, y+sz-m);
    }
    else if(c == '7')
    {
        fillX(x, y);
        arr[y+1][x+2] = '#';
        arr[y+2][x+1] = '#';
        fillY(x, y+3, sz-3);
    }
    else if(c == '8')
    {
        int m = (2*y+sz)/2;
        fillX(x, y);
        fillX(x, m);
        fillX(x, y+sz-1);
        fillY(x, y, sz);
        fillY(x+2, y, sz);
    }
    else if(c == '9')
    {
        int m = (2*y+sz)/2;
        fillX(x, y);
        fillX(x, m);
        fillX(x, y+sz-1);
        fillY(x, y, sz/2);
        fillY(x+2, y, sz);
    }
    else if(c == '+')
    {
        int m = (2*y+sz)/2;
        fillX(x, m, 5);
        fillY(x+2, y, sz);
    }
    else if(c == '-')
    {
        int m = (2*y+sz)/2;
        fillX(x, m, 3);
    }
    else if(c == '*')
    {
        int m = (2*y+sz)/2;
        fillX(x, m, 1);
    }
    else if(c == 'C')
    {
        fillY(x, y, sz);
        fillX(x, y);
        fillX(x, y+sz-1);
    }
    else if(c == '=')
    {
        int a = y+sz*2/5;
        int b = y+sz*3/5;
        fillX(x, a, 5);
        fillX(x, b, 5);
    }
    else if(c == '/')
    {
        int a = y+sz*2/5;
        int b = y+sz*3/5;
        fillX(x, a, 1);
        fillX(x, b, 1);
    }
    else if(c == '(')
    {
        int m = y+sz/2;
        for(int dy=sz/4+1; dy<=sz/2; dy++)
        {
            arr[m-dy][x+2] = arr[m+dy][x+2] = '#';
        }
        for(int dy=1; dy<=sz/4; dy++)
        {
            arr[m-dy][x+1] = arr[m+dy][x+1] = '#';
        }
        arr[m][x] = '#';
    }
    else if(c == ')')
    {
        int m = y+sz/2;
        for(int dy=sz/4+1; dy<=sz/2; dy++)
        {
            arr[m-dy][x] = arr[m+dy][x] = '#';
        }
        for(int dy=1; dy<=sz/4; dy++)
        {
            arr[m-dy][x+1] = arr[m+dy][x+1] = '#';
        }
        arr[m][x+2] = '#';
    }
}
void Printer::print()
{
    for(int i=0; i<arr.size(); i++)
    {
        for(int j=0; j<arr[i].size(); j++)
        {
            printStr(arr[i][j]);
        }
        cout << endl;
    }
}
