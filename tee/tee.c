#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

int help(char *cmd) {
    fprintf(stderr, "Usage: %s [-a] OUT_FILE\n", cmd);
    return EXIT_FAILURE;
}

int main(int argc, char *argv[]) {
    char *out_file;
    char *mode = "wb";
    FILE *fp;
    char buf[64 * 1024];
    switch (argc) {
    case 2:
        out_file = argv[1];
        break;
    case 3:
        if (strcmp(argv[1], "-a") != 0)
            return help(argv[0]);
        mode = "ab";
        out_file = argv[2];
        break;
    default:
        return help(argv[0]);
    }
    if ((fp = fopen(out_file, mode)) == NULL) {
        fprintf(stderr, "[ERROR] Can't open %s (%s)\n", out_file, strerror(errno));
        return EXIT_FAILURE;
    }
    while (fgets(buf, sizeof(buf), stdin) != NULL) {
        fputs(buf, fp);
        fflush(fp);
        fputs(buf, stdout);
    }
    fclose(fp);
    return 0;
}
