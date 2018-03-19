#include <iostream>
#include <vector>
#include "ThreadPool.h"

std::vector<std::string> a;

void foo(std::string i) {
    std::cout << " changed " << i << "\n";
}

int main() {
    ThreadPool pool(3);

    a.emplace_back("First word");
    a.emplace_back("Second word");
    a.emplace_back("Third word");

    auto beg = a.begin(), end = a.end();
    pool.parallel(beg, end, &foo);
    for (int i = 0; i < 3; i++) {
        std::cout << a[i] << std::endl;
    }

    return 0;
}