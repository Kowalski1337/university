//
// Created by vadim on 08.06.18.
//

#include <cstdio>
#include <cstdlib>
#include <unistd.h>
#include <cstring>
#include <sys/socket.h>


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
        tmp = recv(fd, s + ans, static_cast<size_t >(len - ans), 0);
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


void write_str(int path, char const *s, char const *error = "Error was occurred while writing\n") {
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

int send_to_sock(int socket, const char * buffer) {
    size_t length = strlen(buffer);
    ssize_t m = 0, bytesSent = 0;
    while (length-bytesSent > 0 && (m = send(socket, buffer+bytesSent, length-bytesSent, 0)) > 0) {
        bytesSent += m;
    }
    return static_cast<int>(m);
}

int read_from_sock(int socket, char * buffer) {
    ssize_t n = 0, bytesReceived = 0;
    while ((n = recv(socket, buffer+bytesReceived, static_cast<size_t>(buffer_length - bytesReceived), 0)) > 0) {
        bytesReceived += n;
        if(buffer[bytesReceived-1] == '\n') {
            break;
        }
    }
    return static_cast<int>(n);
}



