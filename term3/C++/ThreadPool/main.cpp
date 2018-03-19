#include <iostream>
#include <chrono>
#include "threadPool.h"

void f1() {
    for (int i = 0; i < 10; i++) {
        std::cout << "f1" << "f2" << "f3";
    }
}

void f2() {
    for (int i = 0; i < 10; i++) {
        std::cout << "s1" << "s2" << "s3";
    }
}

void f3() {
    for (int i = 0; i < 10; i++) {
        std::cout << "t1" << "t2" << "t3";
    }
}


int main() {
    threadPool pool(2);
    pool.execute(f1);
    pool.execute(f2);
    pool.execute(f3);
    return 0;
}
