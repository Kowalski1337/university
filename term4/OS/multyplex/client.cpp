#include <cstdio>
#include <cstdlib>
#include <csignal>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <cerrno>
#include <cstring>
#include <cstdint>
#include "utils.cpp"

const int number_of_clients = 3;

/*
 * first arg - address
 * second arg - port
 */

int main(int argc, char *argv[]) {
    signal(SIGPIPE, SIG_IGN);
    if (argc != 3) {
        my_error("Incorrect number of args\n");
    }
    char *address = argv[1];

    long port = strtol(argv[2], nullptr, 10);
    if (errno == ERANGE || port > UINT16_MAX || port <= 0) {
        my_error("Number of port should be uint16_t");
    }
    struct sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    int pton = inet_pton(AF_INET, address, &(server_addr.sin_addr.s_addr));
    if (pton == 0) {
        my_error("Invalid address\n");
    }
    if (pton == -1) {
        my_error("This address family isn't supported\n");
    }

    //sockaddr_in.sin_port - uint16_t
    server_addr.sin_port = htons(static_cast<uint16_t>(port));
    int clients[number_of_clients];
    for (int i = 0; i < number_of_clients; i++) {
        int sock;
        if ((sock = socket(AF_INET, SOCK_STREAM | SOCK_NONBLOCK, 0)) < 0) {
            my_error("Could not create socket\n");
        }
        connect(sock, reinterpret_cast<const sockaddr *>(&server_addr), sizeof(server_addr));

        struct timeval timeout;
        timeout.tv_sec = 60;
        timeout.tv_usec = 0;

        if (setsockopt(sock, SOL_SOCKET, SO_SNDTIMEO, (char *) &timeout, sizeof(timeout)) < 0) {
            my_error("Couldn't set socket option");
        }
        write_str(1, "Client ");
        char *sock_str = new char[11];
        sprintf(sock_str, "%d", sock);
        write_str(1, sock_str);
        write_str(1, " has connected to server\n");
        clients[i] = sock;
    }
    int activity, curSock, maxSocketDescriptor;
    fd_set clientsSet{};
    char buffer[buffer_length];
    int client_pool = 0;
    while (client_pool != number_of_clients) {
        __FD_ZERO(&clientsSet);
        maxSocketDescriptor = clients[0];
        for (int client : clients) {
            curSock = client;
            if (curSock > 0) {
                __FD_SET(curSock, &clientsSet);
            }
            if (curSock > maxSocketDescriptor) {
                maxSocketDescriptor = curSock;
            }
        }
        activity = select(maxSocketDescriptor + 1, &clientsSet, nullptr, nullptr, nullptr);

        //To be honest i don't set timeout, but in the bright days it might be useful
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
        for (int &client : clients) {
            curSock = client;

            if (FD_ISSET(curSock, &clientsSet)) {
                int result = read_str(curSock, buffer);
                char *curSock_str = new char[11];
                sprintf(curSock_str, "%d", curSock);
                if (result < 0) {
                    //promise to myself realize formatted reading/writing(using function read/write and parser of format) 
                    write_str(1, "Client ");
                    write_str(1, curSock_str);
                    write_str(1, " is dead :(\n");
                } else {
                    for (int i = 0; i < result; i++) {
                        if (buffer[i] >= 'a' && buffer[i] <= 'z') {
                            buffer[i] = 'A' + buffer[i] - 'a';
                        }
                    }
                    buffer[result] = '\n';
                    write_str(1, "Client ");
                    write_str(1, curSock_str);
                    write_str(1, " trying to answer\n");
                    write_str(curSock, buffer, "Can't replied\n");
                    write_str(1, "Client ");
                    write_str(1, curSock_str);
                    write_str(1, " replied\n");
                }
                close(curSock);
                client = 0;
                client_pool++;
            }
        }
    }
    return 0;
}