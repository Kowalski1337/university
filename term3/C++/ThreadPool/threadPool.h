//
// Created by vadim on 21.09.2017.
//

#ifndef INC_3_THREADPOOL_H
#define INC_3_THREADPOOL_H


#include <functional>
#include <queue>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <atomic>

class threadPool {
public:
    threadPool(size_t num);

    void execute(const std::function<void()> &func);

    ~threadPool();

private:
    std::queue<std::function<void()> > q;
    std::mutex q_mutex;
    std::vector<std::thread> threads;
    std::condition_variable cond;
    std::atomic<bool> isEnd;

};


#endif //INC_3_THREADPOOL_H
