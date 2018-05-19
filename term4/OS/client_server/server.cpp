#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <arpa/inet.h>
#include <unistd.h>
#include "sender_getter.cpp"


int main() {
    int sock, listener;
    struct sockaddr_in addr{};

    listener = socket(AF_INET, SOCK_STREAM, 0);
    if (listener < 0) {
        perror("socket error");
        exit(EXIT_SUCCESS);
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(1337);
    addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);

    if (bind(listener, (struct sockaddr *) &addr, sizeof(addr)) < 0) {
        perror("bind error");
        exit(EXIT_SUCCESS);
    }

    if (listen(listener, 1) < 0) {
        perror("listen error");
        exit(EXIT_SUCCESS);
    }

    while (true) {
        sock = accept(listener, nullptr, nullptr);
        if (sock < 0) {
            perror("new socket error");
            exit(EXIT_SUCCESS);
        }

        while (true) {

            printf("(Server)Waiting...\n");
            char *buf = static_cast<char *>(malloc(1000));
            if (sender_getter::get_mesage(sock, buf) == -1) {
                printf("I.m realy out\n");
                break;
            }
            if (!strcmp(buf, "0")) {
                printf("I'm out\n");
                close(sock);
                close(listener);
                return 0;
            };
            if (sender_getter::send_message(sock, const_cast<char *>("good"), sizeof("good") == -1)) {
                break;
            }
            printf("(Server)Success!\n");

        }

        close(sock);
    }
}
