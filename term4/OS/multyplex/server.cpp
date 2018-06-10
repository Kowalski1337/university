#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <dirent.h>
#include <errno.h>
#include <cstdint>
#include "utils.cpp"

const int max_number_of_clients = 3;

/*
 * arg - port
 */

char const *message = "hello, please help me, replace lower case with upper case! thx, bro!\n";
char const *expected = "HELLO, PLEASE HELP ME, REPLACE LOWER CASE WITH UPPER CASE! THX, BRO!\n\n";

int main(int argc, char *argv[]) {
    //signal(SIGPIPE, SIG_IGN);
    char *message_buf = new char[buffer_length];
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

    for (int &client : clients) {
        client = 0;
    }

    if ((masterSock = socket(AF_INET, SOCK_STREAM, 0)) <= 0) {
        my_error("Can't create socket\n");
    }
    if (setsockopt(masterSock, SOL_SOCKET, SO_REUSEADDR, (char *) &opt, sizeof(opt)) < 0) {
        my_error("Couldn't set socket option\n");
    }

    address.sin_family = AF_INET;
    address.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
    address.sin_port = htons(static_cast<uint16_t>(port));
    /*sprintf(message_buf, "sin_port - %d\n", address.sin_port);
    write_str(1, message_buf);*/
    if (bind(masterSock, (struct sockaddr *) &address, sizeof(address)) < 0) {
        sprintf(message_buf, "Bind error %d \n", errno);
        my_error(message_buf);
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

            sprintf(message_buf, "Client %d connected\n Addres - %s\n", clientSock, inet_ntoa(address.sin_addr));
            write_str(1, message_buf);
            //send message
            //write_str(clientSock, message, "Can't send data\n");
            int sent = send_to_sock(clientSock, message);
            if (sent < 0) {
                my_error("Error was occurred while reading\n");
            }
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
                int result = read_from_sock(curSock, buffer);
                if (getpeername(curSock, (struct sockaddr *) &address, (socklen_t *) &addrlen) == -1) {
                    my_error("Can't get address of the peer connected to the socket\n");
                }
                close(curSock);
                client = 0;
                buffer[result - 1] = '\n';
                if (result >= 0) {
                    if (strcmp(buffer, expected) == 0) {
                        sprintf(message_buf, "Client %d with address - %s replied correctly\n", curSock,
                                inet_ntoa(address.sin_addr));
                        write_str(1, message_buf);
                    } else {
                        //printf("%d %d\n", static_cast<int>(strlen(buffer)), static_cast<int>(strlen(expected)));
                        sprintf(message_buf, "Client %d with address - %s replied wrong\nexpected: %sfound: %s",
                                curSock, inet_ntoa(address.sin_addr), expected, buffer);
                        write_str(1, message_buf);
                        isEnd = true;
                    }
                } else {
                    sprintf(message_buf, "Client %d with address - %s is dead\n", curSock, inet_ntoa(address.sin_addr));
                    write_str(1, message_buf);
                }
            }
        }
        if (isEnd) {
            for (int &client : clients) {
                close(client);
            }
            close(masterSock);
            write_str(1, "One of clients give bad annswer\nI'm out of this!\n");
            break;
        }
    }
    return 0;
}