#include <iostream>

using namespace std;

int main() {
    //freopen("aplusb.out", "w", stdout);

    cout << "start: s\n"
            "accept: ac\n"
            "reject: rj\n"
            "blank: _\n";

    //move to start algorithm
    cout << "s 0 -> s 0 >\n";
    cout << "s 1 -> s 1 >\n";
    cout << "s + -> finish + >\n";
    cout << "finish + -> finish + >\n";
    cout << "finish 0 -> continue 0 >\n";
    cout << "finish 1 -> continue 1 >\n";
    cout << "continue 1 -> continue 1 >\n";
    cout << "continue 0 -> continue 0 >\n";
    cout << "continue + -> continue + >\n";
    cout << "continue _ -> counter_1 _ <\n";
    cout << "finish _ -> clear _ <\n";
    cout << "clear + -> clear _ <\n";
    cout << "clear 1 -> clear 1 <\n";
    cout << "clear 0 -> clear 0 <\n";
    cout << "clear _ -> as _ >\n";

    //count number of first raised bit in second number
    for (int i = 1; i <= 16; i++) {
        cout << "counter_" << i << " + -> " << "counter_" << (i + 1) << " + <\n";
        cout << "counter_" << i << " 0 -> " << "counter_" << (i + 1) << " + <\n";
        cout << "counter_" << i << " 1 -> " << "move_" << i << " + <\n";
    }

    //move to start of first number
    for (int i = 1; i <= 16; i++) {
        cout << "move_" << i << " 0 -> " << "move_" << i << " 0 <\n";
        cout << "move_" << i << " 1 -> " << "move_" << i << " 1 <\n";
        cout << "move_" << i << " + -> " << "add_" << i << " + <\n";
    }

    //move to start adding
    for (int i = 2; i <= 16; i++) {
        cout << "add_" << i << " 0 -> " << "add_" << (i - 1) << " 0 <\n";
        cout << "add_" << i << " 1 -> " << "add_" << (i - 1) << " 1 <\n";
        cout << "add_" << i << " _ -> " << "add_" << (i - 1) << " 0 <\n";
    }

    cout << "add_1 0 -> s 1 ^\n";
    cout << "add_1 1 -> add_1 0 <\n";
    cout << "add_1 _ -> s 1 ^\n";



    return 0;
}