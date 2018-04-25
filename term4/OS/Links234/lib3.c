//
// Created by vadim on 25.04.18.
//
int the_most_intellectual_foo_ever_seen_linked(int a) {
    int l = -1;
    int r = 46341;
    while (r - l > 1) {
        int m = l + (r - l) / 2;
        if (m * m <= a) {
            l = m;
        } else {
            r = m;
        }
    }
    return l;
}
