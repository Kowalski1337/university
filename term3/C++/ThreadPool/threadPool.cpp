//
// Created by vadim on 21.09.2017.
//
#include "threadPool.h"

/*struct my_ul {
    my_ul(std::mutex &mm)
            : _mm(mm) {
        _mm.lock();
    }

    ~my_ul() {
        _mm.unlock();
    }

    std::mutex &_mm;

};*/

threadPool::threadPool(size_t num) {
    isEnd = false;
    for (size_t i = 0; i < num; i++) {
        threads.emplace_back([this] {
            while (true) {
                std::function<void()> task;
                {
                    std::unique_lock<std::mutex> l(q_mutex);
                    cond.wait(l, [this] { return isEnd || !q.empty(); });
                    if (isEnd) {
                        return;
                    }
                    task = std::move(q.front());
                    q.pop();
                };
                task();
            }
        });
    };
}

void threadPool::execute(const std::function<void()> &func) {
    {
        std::unique_lock<std::mutex> l(q_mutex);
        if (isEnd) {
            return;
        }
        q.push(func);
    }
    cond.notify_one();
}


threadPool::~threadPool() {
    {
        std::unique_lock<std::mutex> l(q_mutex);
        isEnd = true;
    }
    cond.notify_all();
    for (auto &thread : threads) {
        thread.join();
    }
}