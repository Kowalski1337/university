all: links234.o lib4.a lib2.so lib3.so
	gcc -fsanitize=address -Wall -o links234 links234.o -L. lib4.a -ldl lib2.so -Wl,-rpath,.

links234.o: links234.c
	gcc -c links234.c

lib4.a: lib4.o
	ar cr lib4.a lib4.o

lib4.o: lib4.c
	gcc -c lib4.c

lib2.so: lib2.o
	gcc -shared -o lib2.so lib2.o

lib2.o: lib2.c
	gcc -c -fPIC lib2.c

lib3.so: lib3.o
	gcc -shared -o lib3.so lib3.o

lib3.o: lib3.c
	gcc -c -fPIC lib3.c

clean:
	-rm -f *.o *.so *.a