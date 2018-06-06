#include <iostream>
#include <fstream>

using namespace std;

int main() {

    ofstream cout("zero.out");

    cout << "start: s\n"
            "accept: ac\n"
            "reject: rj\n"
            "blank: _\n";

    cout << "s _ -> ac _ ^\n";
    cout << "s 0 -> n 0 >\n";
    cout << "n 0 -> s 0 >\n";
    cout << "n _ -> rj _ ^\n";
    return 0;
}