all: clear server.o client.o

server.o:
	g++ -std=c++11 -Wall -c server.cpp -o server.o

client.o:
	g++ -std=c++11 -Wall -c client.cpp -o client.o

server:
	./server.o

client:
	./client.o 1337

clear:
	rm -f *.o
