#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <stdlib.h>


const size_t _buffer = 64;
const char *_splitter = " \t\n\r\a";

//TODO write structure that works like vector at least for char*, that contains functions:
//TODO void add(const char*(T) element), void reallocateMemory(), char*(T) get(const int index)
//TODO char** getData().
//TODO important: it should allocate memory dynamically and free it in  
/*struct simpleVector(){

}*/

/**
 * Read program args
 * throws reading exception if reading is impossible
 * @return read line
 */
char *read_args() {
    char *line = NULL;
    size_t b = 0;
    if (getline(&line, &b, stdin) == -1) {
        if (errno != 0) {
            perror("Error was occurred while reading");
            exit(1);
        } else {
            exit(0);
        }
    }
    return line;
}

/**
 * Parse program args by <tt>_splitter </tt>
 * throws allocate exception if memory can't be allocated
 * @param __line program args
 * @return array of splitted args
 */
char **parse_args(char *__line) {
    size_t bufsize = _buffer;
    size_t pos = 0;
    char **tokens;
    char *token;
    if (!(tokens = malloc(_buffer * sizeof(char *)))) {
        perror("Can't allocate memory. ");
        exit(1);
    }

    token = strtok(__line, _splitter);
    while (token != NULL) {
        tokens[pos] = token;
        pos++;
        if (pos >= bufsize) {
            bufsize += _buffer;
            if (!(tokens = realloc(tokens, bufsize * sizeof(char *)))) {
                perror("Can't reallocate memory. ");
                exit(1);
            }
        }
        token = strtok(NULL, _splitter);
    }
    tokens[pos] = NULL;
    return tokens;
}

/**
 * Execute command(only system calls)
 * throws forking error if problems with forking was occurred
 * throws running command error if command doesn't exist, illegal number of arguments, e.t.c.
 * throws waiting child process exception
 * @param __command command to execute(absolute path)
 * @param __args command args
 * @param __environ state of environment
 */
void exec_command(const char *__command, char *const *__args, char *const *__environ) {
    const pid_t pid = fork();
    switch (pid) {
        case -1: {
            perror("Error was occurred while forking");
            break;
        }

        case 0: {
            if (execve(__command, __args, __environ) == -1) {
                perror("Error was occurred while running command: ");
            }
            exit(1);
        }

        default: {
            int status = 0;
            do {
                if (wait(&status) == -1) {
                    perror("Error was occurred while waiting child process");
                    exit(1);
                }
            } while (!WIFSIGNALED(status) && !WIFEXITED(status));
        }
    }
}

/**
 * Execute command which presented in <tt>tokens</tt>
 * @param __tokens contains path to command and command args
 * @return 0 if was command to finish working, 1 otherwise
 */
int exec_args(char **__tokens) {
    if (__tokens[0] != NULL) {
        if (strcmp(__tokens[0], "exit") == 0) {
            return 0;
        }
        exec_command(__tokens[0], __tokens, NULL);
        return 1;
    }
    return 1;
}

//TODO realize generating the same start line as in terminal
//TODO sample: "computerName"@"userName": ~"curPath"
/*char *getStartLine() {

}*/


int main() {
    int status = 1;
    char *_cur_line;
    char **_cur_args;
    while (status) {
        printf("$ ");
        _cur_line = read_args();
        _cur_args = parse_args(_cur_line);
        status = exec_args(_cur_args);
        free(_cur_line);
        free(_cur_args);
    }
    return 0;

}
