//
// Created by vadim on 07.06.18.
//

#include <iostream>
#include <sys/ucontext.h>
#include <map>


char* regs[16] = {"R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "RDI", "RSI", "RBP", "RBX", "RDX", "RAX", "RCX", "RSP"};

int main(){
    int* a;
    std::cout << a[-1] << std::endl;
}
