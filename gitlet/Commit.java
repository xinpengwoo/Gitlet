package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Xinpeng WU
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    /** The timestamp when committing. */
    private Date timestamp;

    /** The parent commit of this Commit */
    private List<String> parents;

    /** The mapping of file names to blob references */
    private Map<String, String> blobs;

    public Commit (String message, String... parent){
        this.message = message;
        this.parents = new LinkedList<>();
        for (String p : parent){
            this.parents.add(p);
        }
        this.timestamp = parent == null ? new Date(0) : new Date();
        this.blobs  = new TreeMap<>();
    }

    public String getMessage(){ return this.message; }
    public Date getTimestamp(){ return this.timestamp; }
    public List<String> getParents() { return this.parents; }
    public Map<String, String> getBlobs() { return this.blobs; }
    public void setBlobs(Map<String, String> Blobs){ this.blobs = Blobs; }

}
