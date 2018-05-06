#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <cstring>
#include <unistd.h>
#include <string.h>
#include <iostream>
#include <cstdlib>

using namespace std;

int main(int argc, char **argv) {
    char *message = static_cast<char *>(malloc(100 * sizeof(char)));
    char buf[sizeof(message)];
    int port;

    int my_socket;
    struct sockaddr_in addr{};
    port = static_cast<int>(strtol(argv[1], nullptr, 10));
    my_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (my_socket < 0) {
        perror("socket error");
        exit(EXIT_FAILURE);
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(static_cast<uint16_t>(port));
    addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);

    if (connect(my_socket, (struct sockaddr *) &addr, sizeof(addr)) < 0) {
        perror("connection error");
        exit(EXIT_FAILURE);
    }

    while (true) {
        scanf("%s", message);
        send(my_socket, message, sizeof(message), 0);
        recv(my_socket, buf, sizeof(message), 0);
        if (!strcmp(message, "0")) {
            close(my_socket);
            return 0;
        }
        printf("%s\n", buf);
    }
}