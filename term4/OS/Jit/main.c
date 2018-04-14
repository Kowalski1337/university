#include <stddef.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <sys/mman.h>
#include <stdlib.h>

void error(char *message) {
    fprintf(stderr, "%s", message);
    exit(EXIT_FAILURE);
}

/**
 * Compile machine code int runtime
 * @return
 */
int main(int argN, char **args) {
    if (argN < 2) {
        error("Not enough args\n");
    }
    char machine_code[] = {0x55, 0x48, 0x89, 0xe5, 0x89, 0x7d, 0xfc, 0x8b, 0x45, 0xfc, 0xba, 0x01, 0x00, 0x00, 0x00,
                           0x89, 0xc1, 0xd3, 0xe2, 0x89, 0xd0, 0x5d, 0xc3};
    void *mem = mmap(NULL, sizeof(machine_code), PROT_READ | PROT_WRITE | PROT_EXEC, MAP_ANONYMOUS | MAP_PRIVATE, -1,
                     0);
    if (mem == MAP_FAILED) {
        error("Problems was occurred while calling mmap\n");
    }
    memcpy(mem, machine_code, sizeof(machine_code));

    if (mprotect(mem, sizeof(machine_code), PROT_READ | PROT_EXEC) == -1) {
        error("Problems was occurred while calling mprotect\n");
    }
    int c = ((int (*)(int)) mem)(atoll(args[1]));
    printf("%d\n", c);

    if (munmap(mem, sizeof(machine_code)) == -1) {
        error("Problems was occurred while calling munmap\n");
    }

    return EXIT_SUCCESS;
}