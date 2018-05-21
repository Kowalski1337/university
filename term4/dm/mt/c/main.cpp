#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ofstream cout("mirror.out");

    cout << "start: s\n"
            "accept: ac\n"
            "reject: rj\n"
            "blank: _\n";

    cout << "s 1 -> s 1 >\n";
    cout << "s 0 -> s 0 >\n";
    cout << "s _ -> go _ <\n";

    cout << "go 0 -> move_0_1 0 >\n";
    return 0;
}