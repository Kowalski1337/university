#include <vector>
#include <string>
#include <iostream>

using namespace std;

class Printer {
private:

    vector<vector<string> > arr;
    vector<int> h;
    int curX = 0;
    void printStr(string & s);
    void init(int h);
    void fillX(int x, int y);//sz = 3
    void fillX(int x, int y, int sz);
    void fillY(int x, int y, int sz);
    pair<int, int> parse(string & s, int x, int y, int startI, int startBalance, int depth);
    void putChar(char c, int x, int y, int sz);
    int getCharWidth(char c);
    int getHeight(int hh);
public:
    Printer(string & s, int maxC);
    void putChar(char c);
    void putInt(int ii);
    void print();
};

