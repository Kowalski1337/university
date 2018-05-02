#include <stdio.h>
#include <dlfcn.h>
#include <unistd.h>

int the_most_intellectual_foo_ever_seen_static(int a);

int the_most_intellectual_foo_ever_seen_dynamic(int a);


int main() {
    printf("%d\n", the_most_intellectual_foo_ever_seen_dynamic(3));
    printf("%d\n", the_most_intellectual_foo_ever_seen_static(8));

    void *dynlib;
    int (*func)(int);
    dynlib = dlopen("./lib3.so", RTLD_LAZY);
    if (!dynlib) {
        fprintf(stderr, "Error: can't open lib3.so: %s\n", dlerror());
        return 0;
    };
    dlerror();
    func = dlsym(dynlib, "the_most_intellectual_foo_ever_seen_linked");
    if (dlerror()) {
        fprintf(stderr, "Dlsym error\n");
        dlclose(dynlib);
        return 0;
    }
    printf("%d\n", (*func)(3));
    dlclose(dynlib);
    return 0;
}