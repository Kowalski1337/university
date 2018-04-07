#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include <sys/stat.h>
#include <ctype.h>
#include <sys/wait.h>

/**
 * Structure that contains command arguments
 */
struct {
    char *_path;

    bool _has_inode_mode;
    ino_t _inode;

    bool _has_link_mode;
    nlink_t _nlink;

    bool _has_name;
    char *_name;

    bool _has_executable_file;
    char *_executable_file;

    bool _has_size_mode;
    char _key;
    off_t _size;
} mode;

/**
 * Initialization of structure mode
 */
void initialize(void) {
    mode._has_inode_mode = false;
    mode._has_link_mode = false;
    mode._has_name = false;
    mode._has_executable_file = false;
    mode._has_size_mode = false;
    mode._inode = 0;
    mode._nlink = 0;
    mode._name = "";
    mode._executable_file = "";
    mode._size = 0;
    mode._key = '\0';
    mode._path = "";
}

/**
 * Type help menu
 */
void helpMenu(void) {
    printf("find : find [path] [-inum] [-name] [-size [[=]|[-]|[+]]] [-exec] [-nlinks]");
    printf("\n    Find files.");
    printf("\n\n    The variable \'path\' defines the search path");
    printf("\n\n    The variable \'path\' should be always present");
    printf("\n\n    Options: ");
    printf("\n      -inum\t\tspecifies inode of file");
    printf("\n           \t\tpositive number which describes inode");
    printf("\n           \t\tshould be presented");
    printf("\n      -name\t\tspecifies file name");
    printf("\n           \t\tstring that describes name of file");
    printf("\n           \t\tshould be presented");
    printf("\n      -size\t\tspecifies size of fileFilter");
    printf("\n           \t\tpositive number which describes size");
    printf("\n           \t\tshould be presented");
    printf("\n           \t\tKeys:");
    printf("\n\t\t            \'+\' - greater than \'size\'");
    printf("\n\t\t            \'-\' - less than \'size\'");
    printf("\n\t\t            \'=\' - equals to \'size\'");
    printf("\n      -nlink\t\tspecifies number of hardlinks");
    printf("\n            \t\tpositive number which number of hardlinks");
    printf("\n            \t\tshould be presented");
    printf("\n      -exec\t\tspecifies path to executable file");
    printf("\n           \t\tstring that describes path to executable file");
    printf("\n           \t\tshould be presented");
    printf("\n    Exit status:");
    printf("\n    Returns list of files in presented \'path\' with presented filters\n");
}


/**
 * Throws error which was occurred while running programme
 * @param message error message
 * @param what extra info
 * @param needHelp = true if need to suggest user
 */
void myError(const char *message, const char *what, bool needHelp) {
    fprintf(stderr, "%s %s\n", message, what);
    if (needHelp) {
        printf("Try \"./find --help\" to learn more\n");
    }
    exit(EXIT_FAILURE);
}

int skipFile(struct stat _file_stat, const char *file) {
    return (mode._has_name && strcmp(file, mode._name) != 0) ||
           (mode._has_inode_mode && mode._inode != _file_stat.st_ino) ||
           (mode._has_link_mode && mode._nlink != _file_stat.st_nlink) ||
           (mode._has_size_mode && ((mode._key == '=' && mode._size != _file_stat.st_size) ||
                                    (mode._key == '+' && mode._size < _file_stat.st_size) ||
                                    (mode._key == '-' && mode._size > _file_stat.st_size)));


}

bool isNumber(char *what) {
    for (; *what; what++) {
        if (*what != 0 && !isdigit(*what)) {
            return false;
        }
    }
    return true;
}

void setMode(const int argN, char **args) {
    if (strcmp(args[0], "--help")){
        helpMenu();
    }
    if (argN < 2) {
        myError("Path should be presented", "", true);
    }
    mode._path = args[1];
    int codeError = 0;
    for (int i = 2; i < argN; i += 2) {
        if (i + 1 == argN) {
            codeError = 1;
        }
        if (strcmp(args[i], "-name") == 0) {
            if (codeError == 0) {
                mode._has_name = true;
                mode._name = args[i + 1];
            }
        } else {
            if (strcmp(args[i], "-inum") == 0) {
                if (codeError == 0) {
                    if (!isNumber(args[i + 1])) {
                        codeError = 2;
                    } else {
                        mode._has_inode_mode = true;
                        mode._inode = strtoul(args[i + 1], 0L, 10);
                    }
                }
            } else {
                if (strcmp(args[i], "-size") == 0) {
                    if (codeError == 0) {
                        if (!isNumber(*(args + i + 1) + 1)) {
                            codeError = 2;
                        } else { ;
                            if (args[i+1][0] != '-' && args[i+1][0] != '+' && args[i+1][0] != '='){
                                myError("Invalid key for command -size", "", true);
                            }

                            mode._has_size_mode = true;
                            mode._size = atoll(args[i + 1]);
                            mode._key = args[i+1][0];
                        }
                    }
                } else {
                    if (strcmp(args[i], "-nlinks") == 0) {
                        if (codeError == 0) {
                            if (!isNumber(args[i + 1])) {
                                codeError = 2;
                            } else {
                                mode._has_link_mode = true;
                                mode._inode = strtoul(args[i + 1], 0l, 10);
                            }
                        }
                    } else {
                        if (strcmp(args[i], "-exec") == 0) {
                            if (codeError == 0) {
                                mode._has_executable_file = true;
                                mode._executable_file = args[i + 1];
                            }
                        } else {
                            codeError = 3;
                        }
                    }
                }
            }
        }

        switch (codeError) {
            case 1:
                myError("No argument for option", args[i], true);
            case 2:
                myError("Invalid argument for option", args[i], true);
            case 3:
                myError("Unexpected option", "", true);
        }
    }
}

void execComand(char *const *arg) {
    pid_t pid = fork();
    if (pid == 0) {
        if (execve(arg[0], arg, NULL) == -1) {
            myError("Error was occurred while running command", arg[0], false);
        }
        exit(EXIT_FAILURE);
    } else if (pid < 0) {
        myError("Error was occurred while forking", "", false);
    } else {
        int status;
        do {
            if (wait(&status) == -1) {
                myError("Error was occurred while waiting child process", "", false);
            }
        } while (!WIFEXITED(status) && !WIFSIGNALED(status));
    }
}

void listdir(char *dirpath) {
    DIR *directory;
    if ((directory = opendir(dirpath)) == NULL) {
        myError("Error was occurred while opening directory", dirpath, false);
        if (closedir(directory) == -1) {
            myError("Error was occurred while closing directory", dirpath, false);
        }
        return;
    }
    char directory_path[1024];

    strcpy(directory_path, dirpath);
    strcat(directory_path, "/");

    struct dirent *dir;
    while ((dir = readdir(directory)) != NULL) {
        struct stat file_stat;
        char file_path[1024];
        strcpy(file_path, directory_path);
        strcat(file_path, dir->d_name);
        bool stat_fail = false;
        if (stat(file_path, &file_stat) == -1) {
            myError("Failed to get stat of file", file_path, false);
            stat_fail = true;
        }
        if (dir->d_type == DT_DIR) {
            if (strcmp(dir->d_name, ".") != 0 && strcmp(dir->d_name, "..") != 0) {
                listdir(file_path);
            }
        } else if (stat_fail == false && !skipFile(file_stat, dir->d_name)) {
            if (mode._has_executable_file) {
                char *const a[3] = {mode._executable_file, file_path, NULL};
                execComand(a);
            } else {
                printf("%s\n", file_path);
            }
        }
    }

    if (closedir(directory) == -1) {
        myError("Failed to close directory", dirpath, false);
    }
}


int main(const int argN, char **args) {
    initialize();
    setMode(argN, args);
    listdir(mode._path);
    //error("message");
    return 0;
}