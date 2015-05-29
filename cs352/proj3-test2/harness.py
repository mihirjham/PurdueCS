#!/usr/bin/python3
import os
import re
import subprocess
import sys
import time

TESTDIR = os.getcwd() + "/tests/"
OUTDIR = TESTDIR + "out/"
ERRDIR = TESTDIR + "err/"
PARSER_EXE = "./parser"

CORRECT = 0
WHITESPACE = 1
ERROR = 2

def colorize(color_code, text):
    return "\033[%dm%s\033[0m" % (color_code, text)

def red(text):
    return colorize(31, text)
def green(text):
    return colorize(32, text)
def yellow(text):
    return colorize(33, text)

def line_diff(a, b):
    a = a.splitlines()
    b = b.splitlines()
    ws = False
    if len(a) != len(b):
        return ERROR
    
    for i in range(len(a)):
        if a[i] != b[i]:
            if a[i].strip() == b[i].strip():
                ws = True
            else:
                return ERROR

    if ws:
        return WHITESPACE
    else:
        return CORRECT
            

def getCommand(filename):
    return [PARSER_EXE, filename]

def getFileContents(filename):
    s = ""
    with open(filename) as f:
        s = f.read()
    return s

def maybe_atoi(c):
    return int(c) if c.isdigit() else c

def natural_keys(word):
    return [ maybe_atoi(c) for c in re.split('(\d+)', word) ]

def getFilesOnly(path):
    for f in sorted(os.listdir(path), key=natural_keys):
        if os.path.isfile(os.path.join(path, f)):
                yield f

def main():
    num_tests = 0
    incorrect = []
    ws_wrong = []
    syntax_only = False

    if len(sys.argv) == 2 and sys.argv[1] == 'syntax':
        syntax_only = True

    # Ensure the program has been built
    out = subprocess.call("make")

    nl = True
    start = time.time()
    files = getFilesOnly(TESTDIR)
    for filename in files:
        sys.stdout.flush()
        num_tests += 1
        command = getCommand(TESTDIR + filename)

        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out,err = process.communicate()

        out = out.decode("utf-8")
        err = err.decode("utf-8")

        # Correct output
        correct_out = getFileContents(OUTDIR + filename)
        correct_err = getFileContents(ERRDIR + filename)

        # If the user only wants to check their grammar, just look for "syntax error"
        if syntax_only:
            # If the program should throw a syntax error, check for syntax error in the user's output
            if correct_err == "syntax error":
                if err == "syntax error":
                    print(green("."), end="")
                else:
                    print(red("."), end="")
                    incorrect.append(filename)
            # If the user said there was a syntax error, but there SHOULDN'T be, they failed the test
            elif err == "syntax error":
                print(red("."), end="")
                incorrect.append(filename)
            # No syntax error and the user didn't say so either; correct
            else:
                print(green("."), end="")
        # Standard testing procedure, check for correct output and error
        else:
            out_diff = line_diff(out, correct_out)
            err_diff = line_diff(err, correct_err)
            if process.returncode > 1:
                print(red("."), end="")
                incorrect.append(filename)
            elif out_diff == CORRECT and err_diff == CORRECT:
                print(green("."), end="")
            elif out_diff != ERROR and err_diff != ERROR:
                print(yellow("."), end="")
                ws_wrong.append(filename)
            else:
                print(red("."), end="")
                incorrect.append(filename)
            """
            if out == correct_out and err == correct_err:
                print(green("."), end="")
            elif out.strip() == correct_out and err.strip() == correct_err:
                print(yellow("."), end="")
                ws_wrong.append(filename)
            else:
                print(red("."), end="")
                incorrect.append(filename)
            """

        if num_tests % 25 == 0:
            print(" {}".format(num_tests))
    #end for
    end = time.time()
    elapsed = end - start

    print()
    if len(ws_wrong) > 0:
        print("You had whitespace issues in the following test cases:")
        ws_wrong.sort(key=natural_keys)
        print(", ".join(ws_wrong))
        print()
    if len(incorrect) > 0:
        print("Failed {} tests.".format(len(incorrect)))
        incorrect.sort(key=natural_keys)
        print(", ".join(incorrect))
    
    if len(ws_wrong) == 0 and len(incorrect) == 0:
        print("All tests passed.")
    print("Ran {} tests in {:.2f} seconds.".format(num_tests, elapsed))

main()
