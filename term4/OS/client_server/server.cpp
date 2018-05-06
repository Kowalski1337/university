#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <arpa/inet.h>
#include <unistd.h>

int main() {
    int sock, listener;
    struct sockaddr_in addr{};
    int bytes_read;

    listener = socket(AF_INET, SOCK_STREAM, 0);
    if (listener < 0) {
        perror("socket error");
        exit(EXIT_FAILURE);
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(1337);
    addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);

    if (bind(listener, (struct sockaddr *) &addr, sizeof(addr)) < 0) {
        perror("bind error");
        exit(EXIT_FAILURE);
    }

    if (listen(listener, 1) < 0) {
        perror("listen error");
        exit(EXIT_FAILURE);
    }

    while (true) {
        sock = accept(listener, nullptr, nullptr);
        if (sock < 0) {
            perror("new socket error");
            exit(EXIT_FAILURE);
        }

        while (true) {
            printf("(Server)Waiting...\n");
            char* buf = static_cast<char *>(malloc(sizeof(char)*100));
            bytes_read = static_cast<int>(recv(sock, buf, 2048, 0));
            if (bytes_read <= 0) break;
            if (!strcmp(buf, "0")){
                close(sock);
                free(buf);
                close(listener);
                return 0;
            };
            strcpy(buf, "good");
            if (send(sock, buf, static_cast<size_t>(bytes_read), 0) < 0) break;
            printf("(Server)Success!\n");
        }

        close(sock);
    }
}
