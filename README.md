@Source: https://sp21.datastructur.es/materials/proj/proj2/proj2
```
├── gitlet
│   ├── Commit.java
│   ├── Dumpable.java
│   ├── DumpObj.java
│   ├── GitletException.java
│   ├── Graph.java
│   ├── Main.java                       <==== Run Gitlet like "java gitlet.Main GIT_COMMAND"
│   ├── Makefile
│   ├── Repository.java
│   └── Utils.java
├── gitlet-design.md                    <==== Design document elaborates classes and data structures
├── Makefile                            <==== Script used by make command 
├── pom.xml                             <==== Intellij proj file
├── README.md
└── testing
    ├── Makefile
    ├── runner.py                       <==== Script to help debug your program
    ├── samples                         <==== Sample .in files provided
    │   ├── definitions.inc
    │   ├── test01-init.in
    │   ├── test02-basic-checkout.in
    │   ├── test03-basic-log.in
    │   └── test04-prev-checkout.in
    ├── src                             <==== Contains files used for testing
    │   ├── notnotwug.txt
    │   ├── notwug.txt
    │   └── wug.txt
    ├── staff-runner.py
    ├── student_tests                   <==== Self-written .in files 
    │   ├── commit_setup.inc
    │   ├── definitions.inc
    │   ├── test01-global-log.in
    │   ├── test02-find.in
    │   ├── test03-basic-status.in
    │   ├── test04-checkout-branch.in
    │   ├── test05-reset.in
    │   ├── test06-simple-merge.in
    │   └── test07-merge-conflict.in
    └── tester.py                        <==== Script that tests your program
```
+ Support 18 Git commands, such as init, add, commit, status, checkout, branch, merge, remote, push, pull, etc, some of which are slightly simplified compared to those in real Git.
+ Compute the git-SHA1 hash value of commit objects to construct a commit tree as Git basic data structure.
+ Deal with failure cases and dangerous commands, use serialization to persist objects, and manipulate files and dirs such as staging area, committed files and commit objects.
+ Utilize a standard unix tool called make to run integration tests which are written in domain-specific language, and make use of the python script and remote JVM to debug.



