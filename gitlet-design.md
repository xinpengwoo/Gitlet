# Gitlet Design Document

**Name**: Xinpeng WU

## Classes and Data Structures

### Utils

#### Methods modified and added

1. deleteDirFiles(File dir) -- Delete plain files in specific dir
2. plainFilenamesIn(File dir) -- return SortedSet<String> instead of sorted list

### Repository

#### Instance variables

1. CWD -- Current working directory
2. GITLET_DIR -- .gitlet dir
3. STAGING_AREA -- .gitlet/stagingArea dir for staging added files
4. ADDEDFILES -- File persists Map<String, String> Object instantiated with TreeMap. Key: Relative pathname of a file; value: SHA-1 hash
5. REMOVEDFILES -- File persists SortedSet<String> Object instantiated with TreeSet. Item: Relative pathname of a file
6. COMMITS -- The commits object dir, each commit stored in commitID.substring(0, 2)/commitID.substring(2)
7. BLOBS -- The blobs dir
8. HEAD -- The currently active pointer file
9. BRANCHES -- The branch pointers dir

#### Methods

##### Basic Methods

1. init() -- void, initiate dirs, branch, commit, commitsGraph
2. add(String source) -- void, only one file may be added at a time. Compare the file in CWD with the current commit version, if identical do not add it and delete it if it has been there. Finally, copy the added file to specific dir.
3. commit(String message) -- void, instantiate new commit object and use headCommit and staging area to update it.
4. remove(String target) -- void, unstage the corresponding file in staging area if it exists. Or, if the file is tracked, stage it for removal and delete it if it exists. Or, print error message.
5. log() -- void, start with head commit, following the first parent commit links, print commit's ID, timestamp and message. 
6. TOBEIMPROVED: globalLog() -- void, print all commits' info.
7. TOBEIMPROVED: find(String commitMsg) -- void, print commitIDs with the specified message.
8. status() -- Displays 5 categories: branches, files staged for addition and removal, modified but not staged files and untracked Files. **Natural ordering implementation: new TreeSet(collection); collection.sort(null). Method reference: .forEach(System.out::println).** 
9. checkoutFile(String fileName) -- Checkout the file of head commit, not staged it.
10. checkoutCommitFile(String commitID, String fileName) -- Similar to 9.
11. checkoutBranch(String branchName) -- Checkout all the files of new branch, remove all the untracked files in current branch.
12. branch(String branchName) -- Create a new branch, point it to head commit.
13. rmBranch(String branchName) -- Delete the specific branch (just the pointer associated with the branch)
14. reset(String commitID) -- Checkout the file of specific commit
15. merge(String givenBranchName) -- Merges files from the given branch into the current branch. Detect whether there are failure cases. Get splitPoint and compare it with given and current branch, turning out to be 3 cases.

##### Help Methods (Usage Frequency in different public method) 

1. (1)getCommitID(String shortenedID) -- return complete commit ID with shortened ID input, the commit object is stored in COMMITS/commitID.substring(0, 2)/commitID.substring(2) so that we can locate the object using shortened UID.
2. (6)getCommit(String commitID) -- return the specific commit object using commitID(maybe a shortened one)
3. (3)getHeadCommitID() -- get HEAD commitID
4. (5)getHeadCommit() -- get HEAD commit object
5. (2)storeNewCommit(Commit newCommit) -- store the new commit in COMMITS dir and update the branch pointer.
6. (2)printLogStyleSet() -- return a SimpleDateFormat object representing specific print format.
7. (2)printLogInfo(String commitID, Commit commit, SimpleDateFormat sdf) -- print commitID, plus 2 parents' commitID for merged commit, timestamp of commit using sdf and commit message.
8. (1)getCurrentBlobs() -- return current state blobs which is a Map<String, String> object
9. (1)printMNSFC(Map<String, String> currentBlobs, SortedSet<String> cwdFiles) -- iterate over current state blobs, return MNSFC List<String> (sorted) object, the rest of cwdFiles is Untracked Files
10. (2)checkoutFile(Commit commit, String fileName) -- Core method of checkout(). Put commit version file into cwd and do not stage it.
11. (3)recreateCwdWithCommit(Map<String, String> currentBlobs, SortedSet<String> cwdFiles) -- Fill the cwd with files of specific commit, the rest of cwdFiles is Untracked Files.
12. (2)getUntrackedFiles(Map<String, String> currentBlobs, SortedSet<String> cwdFiles) -- return List<String> object of untracked files.
13. (3 in 15)mergeConflict(String currentBranchFileID, String givenBranchFileID) -- merge conflicting files and return String object of mergedFileID.
14. checkDIR() -- check if cwd contains .gitlet dir
15. checkARGS(int correctNum, int actualNum) -- check if the number of OPERANDS is correct. Plus, for special case, set correctNum = -1, actualNum = 0 just to print error message.
16. TOBEIMPROVED: getAddedFiles, getRemovedFiles
### Commit

#### Instance variables

1. Message -- The message of a commit.
2. Timestamp -- The instant when committing. Assigned by the constructor.
3. Parent -- List<String> The parent commit of a commit.
4. blobs -- Map<String, String>

#### Constructor

1. Commit(String message, Commit parent) -- Assign the message, parent to corresponding instance variables and set the timestamp using Date(0) to represent “The (Unix) Epoch” and Date() to represent the instant when commit is created.

#### Methods

1. 4 getInstanceVariables methods -- return corresponding instance variables.
2. setBlobs(Map<String, String> Blobs) -- set the commit object with the specific blobs

### Graph

#### Instance variables

1. adjList -- List<ArrayList<Integer>>, store direct edges of vertices.
2. labels -- Map<String, Integer>, pair commit StringID with IntegerID

#### Constructor

1. Graph(String initialCommitID) -- initiate graph with initialCommitID and label it with 0.

#### Methods

1. addEdge(String newCommit, String pastCommit) -- establish an edge of pastCommit pointing to newCommit
2. updateLabels() -- help method to associate new commitID with Integer label.
3. adj(int v) -- return Iterable<Integer> object of adjacent edges of vertex v.
4. V() -- return the number of vertices
5. getSplitPointID(String id1, String id2) -- return splitPointID using currentBranchID and givenBranchID.
6. getIdUsingLabels(int label) -- help method to return commitID corresponding to label.
7. getOnePath(int[] paths, int v) -- help method to return String object of a path from 0 to v.

#### Nested Class -- BreadthFirstPath

##### Instance variables

1. marked -- boolean[i] indicates whether vertex i is iterated or not.
2. edgeTo -- int[i] stores the nearest vertex that vertex i points to.

##### Constructor

1. BreadthFirstPath(Graph G, int s) -- initiate and run bfs() to update the instance variables.

##### Methods

1. getPath() -- return edgeTo.
2. bfs(Graph G, int s) -- to solve shortest s-t path problem (no weight considered), t represents all vertices except for s.

## Algorithms
### Repository
1. init()
+ Failure case: check if the GITLET_DIR exits.
+ make 5 dirs
+ create master branch and store initial commit using storeNewCommit() method
+ initiate Graph object commitsGraph.
2. add (String source)
+ Failure case: check if the source file exits.
+ get the fileID with the same name as source(null indicates not existing), get the addedFiles object.
+ the real file and addedFile object update: Compare the file in CWD with the current commit version using their sha-1 value, if they are identical do not add it and delete it if it has been there. Otherwise, copy the added file to specific dir.
3. commit(String message)
+ Failure case: use addedFiles and removedFile to check if the staging area is empty.
+ instantiate a new commit with specified message and headCommit designated as parent. Plus, clone the headCommit's blobs object. 
+ iterate over addedFiles, modify blobs object of new commit and cut&paste staged files to blobs dir.
+ store new commit, update commitsGraph, clear staging area
4. remove(String target) 
remove the file with the same name as target
+ in addedFiles object and corresponding file in staging area if it exits.
+ else in CWD and modify the removedFile object
+ else failure case: No reason to remove the file.
5. log()
+ instantiate a SimpleDateFormat object with specific print format.
+ iterate over all commit objects following the "commitsGraph order" of current branch, print their info.
6. globalLog()
+ iterate over all commit objects by plainFilenamesIn(COMMITS), print their info.
7. find(String commitMsg)
+ iterate over all commit objects by plainFilenamesIn(COMMITS), print the commitIDs and print error if the method finds nothing.
8. status(), getCurrentBlobs(), printMNSFC()
+ Natural ordering implementation: new TreeSet(collection); collection.sort(null). 
+ Method reference: .forEach(System.out::println).
+ printMNSFC() at same time modifying cwdFiles to untrackedFiles, whether set uses remove or contains?
9. checkoutFile(), checkoutCommitFile()
+ Failure case: check if specific file exists
+ copy file from blobs to cwd
10. checkoutBranch()
+ Failure case: check if branchName is right
+ recreateCwdWithCommit() checkout all the files of new branch, at same time modifying cwdFiles to untrackedFiles
+ remove all the untracked files in current branch, clear staging area and update HEAD
11. branch(), rmBranch()
+ Failure case:
+ create a new branch file in branches, point it to head commit.
+ delete the specific branch (just the pointer associated with the branch)
12. reset(String commitID) 
+ Failure case:
+ checkout all the files of specific commit and modify branchHead
15. merge(String givenBranchName)
+ Failure cases:
+ use commitsGraph to get splitPoint
+ turn out to be 3 cases:
+ iterate over current/givenBranchCommitBlobs
## Persistence
1. COMMITSGRAPH
+ init() -- write initial Graph object.
+ commit() -- read, update and write.
+ merge() -- read, update and write.
2. ADDEDFILES
+ add() -- read, update and write.
+ commit() -- read and delete.
+ remove() -- read, update and write.
+ status() -- read.
+ merge() -- read.
3. REMOVEDFILES
+ commit() -- read and delete.
+ remove() -- read, update and write.
+ status() -- read.
+ merge() -- read.
4. COMMITS
+ storeNewCommit(Commit newCommit) -- write commit object
+ getCommit(String commitID) -- read commit object with commitID
+ getHeadCommit() -- same with getCommit()