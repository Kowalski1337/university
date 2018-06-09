//
// Created by vadim on 08.06.18.
//

#include <cstdio>
#include <cstdlib>
#include <unistd.h>
#include <cstring>


const int buffer_length = 1024;

void my_error(char const *message) {
    fprintf(stderr, "%s", message);
    exit(EXIT_FAILURE);
}

/*
 * read from start of line to '\n' and write it to s
 */
int read_str(int fd, char *s, char const *error = "Error was occurred while reading\n") {
    int ans = 0;
    ssize_t len = static_cast<ssize_t >(strlen(s));
    ssize_t tmp;
    while (ans < len) {
        tmp = read(fd, s + ans, static_cast<size_t >(len - ans));
        if (tmp == -1) {
            my_error(error);
        }
        //end of file
        if (tmp == 0) {
            break;
        }
        ans += tmp;
    }
    return ans;
}


void write_str(int path, char const *s, char const *error) {
    ssize_t cur = 0;
    ssize_t len = static_cast<ssize_t>(strlen(s));
    ssize_t tmp;
    while (cur < len) {
        tmp = write(path, s + cur, static_cast<size_t>(len - cur));
        if (tmp == -1) {
            my_error(error);
        }
        cur += tmp;
    }
}

void write_str(int path, char const *s) {
    write_str(path, s, "Error was occurred while writing\n");
}


