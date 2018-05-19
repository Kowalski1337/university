//
// Created by vadim on 17.05.18.
//

#include<iostream>
#ifndef CLIENT_SERVER_SENDER_GETER_H
#define CLIENT_SERVER_SENDER_GETER_H


class sender_getter {
private:
    static const int SHIFT = 4;
    static const int MAX_MESSAGE_SIZE = 1000;
    static const int RUN_SUCCESS = 0;
    static const int RUN_FAILURE = 1;
public:
    static int get_mesage(int socket, char * message);
    static int send_message(int socket, char* message, int message_size);
};


#endif //CLIENT_SERVER_SENDER_GETER_H
