package gitlet;

import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable { // directed graph
    private List<ArrayList<Integer>> adjList;
    private Map<String, Integer> labels;
    public Graph(String initialCommitID){
        labels = new HashMap<>();
        labels.put(initialCommitID, 0);
        adjList = new ArrayList<>();
        adjList.add(new ArrayList<>());
    }
    public void addEdge(String newCommit, String pastCommit){ // pastCommit point to newCommit
        int w = labels.get(pastCommit);
        int v = updateLabels(newCommit);
        while (adjList.size() - 1 < v){
            adjList.add(new ArrayList<>());
        }
        adjList.get(w).add(v);
    }
    private int updateLabels(String s){
        Integer v = labels.get(s);
        if (v == null){
            v = labels.size();
            labels.put(s, v);
        }
        return v;
    }
    public Iterable<Integer> adj(int v){
        if (v >= adjList.size()) return new ArrayList<>();
        return adjList.get(v);
    }
    public int V(){
        return labels.size();
    }
    /** get ShortestPathTree rooted with initial commit */
    public String getSplitPointID(String id1, String id2){
        BreadthFirstPath generateTree = new BreadthFirstPath(this, 0);
        int[] paths = generateTree.getPath();
        String path1 = getOnePath(paths, labels.get(id1));
        String path2 = getOnePath(paths, labels.get(id2));
        int minLength = Math.min(path1.length(), path2.length());
        for(int i = 0; i < minLength; i++){
            if (path1.charAt(i) != path2.charAt(i)) {
                return getIdUsingLabels(i - 1);
            }
        }
        return getIdUsingLabels(path1.charAt(minLength - 1));
    }
    private String getIdUsingLabels(int label){
        for (Map.Entry<String, Integer> entry : labels.entrySet()){
            if (label == entry.getValue()){
                return entry.getKey();
            }
        }
        return null;
    }

    private String getOnePath(int[] paths, int v){
        String path = "";
        while (v != 0){
            path = v + path;
            v = paths[v];
        }
        return 0 + path;
    }
    private static class BreadthFirstPath {
        private boolean[] marked;
        private int[] edgeTo;
        public BreadthFirstPath(Graph G, int s){
            int initialSize = G.V();
            marked = new boolean[initialSize];
            edgeTo = new int[initialSize];
            bfs(G, s);
        }
        public int[] getPath(){ return edgeTo;}
        private void bfs(Graph G, int s){
            Queue<Integer> fringe = new LinkedList<>();
            fringe.offer(s);
            marked[s] = true;
            while (!fringe.isEmpty()) {
                int v = fringe.poll();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        fringe.offer(w);
                        marked[w] = true;
                        edgeTo[w] = v;
                    }
                }
            }
        }
    }
}
