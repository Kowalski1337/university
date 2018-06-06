#include <stddef.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <sys/mman.h>
#include <stdlib.h>

/**
 * Type error message
 * @param message describe error
 */
void error(char *message) {
    fprintf(stderr, "%s", message);
    exit(EXIT_FAILURE);
}

/**
 * Compile machine code int runtime
 * machine code increment arg1 by arg2
 * arg1 - int
 * arg2 - [0..15]
 * @return
 */
int main(int argN, char **args) {
    if (argN < 3) {
        error("Not enough args\n");
    }

    int arg1 = atoll(args[1]);
    int arg2 = atoll(args[2]);

    char machine_code[] = {0x55, 0x48, 0x89, 0xe5, 0x89, 0x7d, 0xfc, 0x8b, 0x45, 0xfc, 0x83, 0xc0, 0x00 , 0x5d, 0xc3};
    char to_hex[] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    machine_code[12] = to_hex[arg2];

    void *mem = mmap(NULL, sizeof(machine_code), PROT_READ | PROT_WRITE | PROT_EXEC, MAP_ANONYMOUS | MAP_PRIVATE, -1,
                     0);
    if (mem == MAP_FAILED) {
        error("Problems was occurred while calling mmap\n");
    }
    memcpy(mem, machine_code, sizeof(machine_code));

    if (mprotect(mem, sizeof(machine_code), PROT_READ | PROT_EXEC) == -1) {
        error("Problems was occurred while calling mprotect\n");
    }
    int c = ((int (*)(int)) mem)(arg1);
    printf("%d\n", c);

    if (munmap(mem, sizeof(machine_code)) == -1) {
        error("Problems was occurred while calling munmap\n");
    }

    return EXIT_SUCCESS;
}