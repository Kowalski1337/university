#include <cstdio>
#include <cstdlib>
#include <csignal>
#include <cstring>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <cerrno>
#include <cstdint>
#include "utils.cpp"

const int max_number_of_clients = 3;

/*
 * arg - port
 */

char const *message = "hello, please help me, replace lower case with upper case! thx, bro!\n";

int main(int argc, char *argv[]) {
    signal(SIGPIPE, SIG_IGN);
    int opt = true;
    int masterSock, addrlen, clientSock, clients[max_number_of_clients], activity, curSock;
    int maxSocketDescriptor;
    struct sockaddr_in address;
    char buffer[buffer_length];
    fd_set clientsSet;

    if (argc != 2) {
        my_error("Incorrect number of args");
    }
    long port = strtol(argv[2], nullptr, 10);
    if (errno == ERANGE || port > UINT16_MAX || port <= 0) {
        my_error("Number of port should be uint16_t");
    }

    for (int i = 0; i < max_number_of_clients; i++) {
        clients[i] = 0;
    }

    if ((masterSock = socket(AF_INET, SOCK_STREAM, 0)) <= 0) {
        my_error("Can't create socket\n");
    }
    if (setsockopt(masterSock, SOL_SOCKET, SO_REUSEADDR, (char *) &opt, sizeof(opt)) < 0) {
        my_error("Couldn't set socket option");
    }
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(static_cast<uint16_t>(port));
    if (bind(masterSock, (struct sockaddr *) &address, sizeof(address)) < 0) {
        my_error("Bind error\n");
        //add more intellectual behavior when error was occurred
    }

    printf("Listening to incoming connections on port %d\n", port);
    if (listen(masterSock, max_number_of_clients) < 0) {
        my_error("Can't listen\n");
    }
    addrlen = sizeof(address);
    while (true) {
        FD_ZERO(&clientsSet);
        FD_SET(masterSock, &clientsSet);
        maxSocketDescriptor = masterSock;
        for (int client : clients) {
            curSock = client;
            if (curSock > 0) {
                FD_SET(curSock, &clientsSet);
            }
            if (curSock > maxSocketDescriptor) {
                maxSocketDescriptor = curSock;
            }
        }
        activity = select(maxSocketDescriptor + 1, &clientsSet, nullptr, nullptr, nullptr);
        if (activity == 0) {
            my_error("Oops... timeout\n");
        }
        if (activity < 0) {
            switch (errno) {
                case EBADF: {
                    my_error("Problems with file descriptor\n");
                }
                case EINVAL: {
                    my_error("Sockets should be positive\n");
                }
                case ENOMEM: {
                    my_error("Unable to allocate memory for internal tables\n");
                }
                default:
                    break;
            }
        }

        if (FD_ISSET(masterSock, &clientsSet)) {
            if ((clientSock = accept(masterSock, (struct sockaddr *) &address, (socklen_t *) &addrlen)) < 0) {
                my_error("Can't accept client connection\n");
            }
            printf("Client (fd %d) connected [%s:%d]\n", clientSock, inet_ntoa(address.sin_addr),
                   ntohs(address.sin_port));
            //send message
            write_str(clientSock, message, "Can't send data\n");
            for (int &client : clients) {
                if (client == 0) {
                    client = clientSock;
                    break;
                }
            }
        }
        //IO
        for (int &client : clients) {
            curSock = client;
            if (FD_ISSET(curSock, &clientsSet)) {
                int length = 0;
                int result = read_str(curSock, buffer);
                getpeername(curSock, (struct sockaddr *) &address, (socklen_t *) &addrlen);
                close(curSock);
                client = 0;
                if (result >= 0) {
                    //remove this annoying \n
                    buffer[length - 1] = '\0';
                    printf("Client (fd %d) [%s:%d] replied and disconnected: '%s'\n", curSock,
                           inet_ntoa(address.sin_addr), ntohs(address.sin_port), buffer);
                } else {
                    printf("Client (fd %d) [%s:%d] has been violently disintegrated and it's unsatisfied soul is still wandering around the globe to find peace and soup\n",
                           curSock, inet_ntoa(address.sin_addr), ntohs(address.sin_port));
                }
            }
        }
    }
    return 0;
}