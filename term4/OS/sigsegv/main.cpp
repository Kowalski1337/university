#include <csignal>
#include <unistd.h>
#include <cstring>
#include <cstdint>
#include <csetjmp>
#include <iostream>
#include <vector>
#include <deque>


sigjmp_buf jmpbuf;

void my_error(char const * message){
    fprintf(stderr, "%s", message);
    exit(EXIT_FAILURE);
}

void write_str(char const *s) {
    ssize_t cur = 0;
    ssize_t len = static_cast<ssize_t>(strlen(s));
    ssize_t tmp;
    while (cur < len) {
        tmp = write(STDERR_FILENO, s + cur, static_cast<size_t>(len - cur));
        if (tmp == -1) {
            my_error("Error was occurred while writing");
        }
        cur += tmp;
    }
}

char get_digit(uint64_t x) {
    if (x < 10) {
        return static_cast<char>('0' + x);
    }
    switch (x) {
        case 10:
            return 'a';
        case 11:
            return 'b';
        case 12:
            return 'c';
        case 13:
            return 'd';
        case 14:
            return 'e';
        case 15:
            return 'f';
        default:
            exit(45);
    }
}

char *to_string(uint64_t a) {
    char* ans = new char[19];
    ans[0] = '0';
    ans[1] = 'x';
    for (size_t i = 0; i < 16; ++i) {
        ans[2 + i] = get_digit(a % 16);
        a = a / 16;
    }
    ans[18] = '\0';
    return ans;
}

char *byte_to_string(uint8_t a) {
    char *ans = new char[3];
    ans[0] = get_digit(static_cast<uint64_t>(a % 16));
    ans[1] = get_digit(static_cast<uint64_t>(a / 16));
    ans[2] = '\0';
    return ans;
}


char const * regs[] = {"R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "RDI", "RSI", "RBP", "RBX", "RDX", "RAX", "RCX", "RSP", "RIP", "RFL", "CSGSFS", "ERR", "TRAPNO", "OLDMASK", "CR2"};

void write_reg(mcontext_t *mcontext, size_t reg) {
    write_str(regs[static_cast<size_t >(reg)]);
    write_str(" = ");
    write_str(to_string(static_cast<uint64_t>(mcontext->gregs[reg])));
    write_str("\n");
}

void global_handler(int signum, siginfo_t *siginfo, void *context) {
    write_str("Address = ");
    uint64_t addr = reinterpret_cast<uint64_t>(siginfo->si_addr);
    write_str(to_string(addr));
    write_str("\n\n");

    mcontext_t *mcontext = &reinterpret_cast<ucontext_t *>(context)->uc_mcontext;

    for (size_t i = 0; i <= 22; i++) {
        write_reg(mcontext, i);
    }


    struct sigaction sa;
    memset(&sa, 0, sizeof(sa));
    sa.sa_handler = [](int signum) {  siglongjmp(jmpbuf, 1); };
    sa.sa_flags = SA_NOMASK;
    if (sigaction(SIGSEGV, &sa, nullptr) == -1) {
        my_error("Error was occurred during redefinition action that behaves to SIGSEGV signals");
    }

    write_str("\nMemory dump:\n");

    int cnt = 0;
    uint64_t start = static_cast<uint64_t>(addr & ~15ll);
    start = (start - 64) <= start ? (start - 64) : 0;
    for (uint64_t i = start; cnt < 9; i += 16, ++cnt) {
        for (uint64_t j = i; j < i + 16; ++j) {
            if (j == addr) {
                write_str("XX ");
                continue;
            }

            if (sigsetjmp(jmpbuf, 0)) {
                write_str("__ ");
            } else {
                uint8_t byte = *reinterpret_cast<uint8_t *>(j);
                write_str(byte_to_string(byte));
                write_str(" ");
            }
        }
        write_str("\n");
    }
    exit(EXIT_FAILURE);
}

void test1(){
    std::vector<int> a;
    a[0] = 3;
}

void test2(){
    int* a = new int[1];
    a[0] = 11111;
    size_t t = 1;
    while (true){
        a[t] = a[t-1];
        t++;
    }
}

void test3(){
    std::deque<int> d;
    size_t t;
    std::cout << d[t] << std::endl;
}

int main() {
    struct sigaction new_action;

    memset(&new_action, 0, sizeof(new_action));
    new_action.sa_sigaction = global_handler;
    new_action.sa_flags = SA_SIGINFO | SA_NOMASK;
    if (sigaction(SIGSEGV, &new_action, nullptr) == -1) {
        my_error("Error was occurred during redefinition action that behaves to SIGSEGV signals");
    }

    test2();

    return 0;
}