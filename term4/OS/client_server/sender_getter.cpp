//
// Created by vadim on 17.05.18.
//

#include <unistd.h>
#include "sender_getter.h"

int sender_getter::get_mesage(int socket, char *message) {
    /*printf("start");
    char number[SHIFT];
    size_t got = 0;
    size_t expected = SHIFT;
    ssize_t step;
    while ((step = read(socket, number + got, expected - got)) > 0) {
        got += step;
        expected -= step;
        if (got == SHIFT) {
            break;
        }
    }

    if (step == -1) {
        throw 2;
    }

    int message_size;

    std::copy(&number[0], &number[0] + 4, reinterpret_cast<char *> (&message_size));

    printf("%d", message_size);

    message = static_cast<char *>(malloc(MAX_MESSAGE_SIZE * sizeof(char)));
    got = 0;
    expected = static_cast<size_t>(message_size);

    while (true) {
        step = read(socket, message + got, expected - got);
        if (step == -1) {
            throw 2;
        }

        if (step == 0) {
            break;
        }

        got += step;
        expected -= step;

        if (expected == message_size) {
            break;
        }
    }*/

    auto *ans = static_cast<char *>(malloc(MAX_MESSAGE_SIZE * sizeof(char)));
    size_t got = 0, expected = MAX_MESSAGE_SIZE;
    ssize_t step;

    while ((step = read(socket, ans + got, expected - got)) > 0) {
        got += step;
        expected -= step;
        if (expected == 0) {
            break;
        }
    }

    if (step == -1) {
        perror("Error was occurred while writing to socket\n");
        return RUN_FAILURE;
    }

    for (int i = 0; i < MAX_MESSAGE_SIZE; i++) {
        message[i] = ans[i];
    }

    free(ans);
    return RUN_SUCCESS;
}

int sender_getter::send_message(int socket, char *message, int message_size) {
    /*char number[SHIFT];
    std::copy(reinterpret_cast<char * >(&message_size), reinterpret_cast<char * >(&message_size) + SHIFT, &number[0]);
    size_t sent = 0;
    size_t need = SHIFT;
    ssize_t step = 0;
    while ((step = write(socket, number + sent, need - sent)) > 0) {
        need -= step;
        sent += step;
        if (step == SHIFT) break;
    }

    if (step < 0) {
        throw 1;
    }

    sent = 0;
    need = static_cast<size_t>(message_size);
    step = 0;

    while ((step = write(socket, message + sent, need - sent) > 0)) {
        need -= step;
        sent += step;
        if (sent == message_size) break;
    }

    if (step < 0) {
        throw 1;
    }*/


    //printf("start");
    size_t sent = 0, need = MAX_MESSAGE_SIZE;
    ssize_t step;

    while ((step = write(socket, message + sent, need - sent)) > 0) {
        sent += step;
        need -= step;
        if (need == 0) {
            break;
        }
    }

    if (step == -1) {
        perror("Error was occurred while writing to socket\n");
        return RUN_FAILURE;
    }

    return RUN_SUCCESS;

}

