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
char const *expected = "HELLO, PLEASE HELP ME, REPLACE LOWER CASE WITH UPPER CASE! THX, BRO!\n";

int main(int argc, char *argv[]) {
    signal(SIGPIPE, SIG_IGN);
    int opt = true;
    int masterSock, addrlen, clientSock, clients[max_number_of_clients], activity, curSock;
    int maxSocketDescriptor;
    struct sockaddr_in address;
    char buffer[buffer_length];
    fd_set clientsSet;

    if (argc != 2) {
        my_error("Incorrect number of args\n");
    }
    long port = strtol(argv[1], nullptr, 10);

    if (errno == ERANGE || port > UINT16_MAX || port <= 0) {
        my_error("Number of port should be uint16_t\n");
    }

    for (int i = 0; i < max_number_of_clients; i++) {
        clients[i] = 0;
    }

    if ((masterSock = socket(AF_INET, SOCK_STREAM, 0)) <= 0) {
        my_error("Can't create socket\n");
    }
    if (setsockopt(masterSock, SOL_SOCKET, SO_REUSEADDR, (char *) &opt, sizeof(opt)) < 0) {
        my_error("Couldn't set socket option\n");
    }

    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(static_cast<uint16_t>(port));
    if (bind(masterSock, (struct sockaddr *) &address, sizeof(address)) < 0) {
        my_error("Bind error\n");
        //add more intellectual behavior when error was occurred
    }

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
            write_str(1, "Client ");
            char *clientSock_str = new char[11];
            sprintf(clientSock_str, "%d", clientSock);
            write_str(1, clientSock_str);
            write_str(1, " connected\n Address - ");
            write_str(1, inet_ntoa(address.sin_addr));
            write_str(1, "\n");
            //send message
            write_str(clientSock, message, "Can't send data\n");
            for (int &client : clients) {
                if (client == 0) {
                    client = clientSock;
                    break;
                }
            }
        }

        bool isEnd = false;
        for (int &client : clients) {
            curSock = client;
            if (FD_ISSET(curSock, &clientsSet)) {
                int result = read_str(curSock, buffer);
                if (getpeername(curSock, (struct sockaddr *) &address, (socklen_t *) &addrlen) == -1) {
                    my_error("Can't get address of the peer connected to the socket\n");
                }
                close(curSock);
                client = 0;
                char *clientSock_str = new char[11];
                sprintf(clientSock_str, "%d", curSock);
                if (result >= 0) {
                    if (strcmp(buffer, expected) == 0) {
                        write_str(1, "Client ");
                        write_str(1, clientSock_str);
                        write_str(1, " with address - ");
                        write_str(1, inet_ntoa(address.sin_addr));
                        write_str(1, "replied correctly\n");
                    } else {
                        write_str(1, "Client ");
                        write_str(1, clientSock_str);
                        write_str(1, " with address - ");
                        write_str(1, inet_ntoa(address.sin_addr));
                        write_str(1, "replied wrong\n");
                        write_str(1, "expected: ");
                        write_str(1, expected);
                        write_str(1, "\nfound: ");
                        write_str(1, buffer);
                        write_str(1, "\n");
                        isEnd = true;
                    }
                } else {
                    write_str(1, "Client ");
                    write_str(1, clientSock_str);
                    write_str(1, " with address - ");
                    write_str(1, inet_ntoa(address.sin_addr));
                    write_str(1, "is dead :(\n");
                }
            }
        }
        if (isEnd){
            for (int &client : clients){
                close(client);
            }
            close(masterSock);
            write_str(1, "One of clients give bad annswer\nI'm out of this!\n");
            break;
        }
    }
    return 0;
}