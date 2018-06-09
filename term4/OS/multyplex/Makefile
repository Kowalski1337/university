all: compile_utils, compile_client, compile_server

compile_utils:
		g++ -std=c++11 -Wall -c -o utils.o utils.cpp
		
compile_client:
		g++ -std=c++11 -pthread -Wall -o client.o client.cpp
		
compile_server:
		g++ -std=c++11 -pthread -Wall -o server.o server.cpp
		
server:
		./server.o 3
		
client:
		./client.o 127.0.0.1 3

clean:
		rm *.o
