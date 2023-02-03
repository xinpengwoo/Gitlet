package gitlet;

import static gitlet.Repository.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Xinpeng WU
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        if (firstArg.equals("init")){
            checkARGS(1, args.length);
            init();
            return;
        }
        checkDIR();
        switch (firstArg) {            
            case "add" -> {                
                checkARGS(2, args.length);
                add(args[1]);
            }
            case "commit" -> {
                if (args.length == 1){
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                }
                checkARGS(2, args.length);
                commit(args[1]);
            }
            case "rm" -> {
                checkARGS(2, args.length);
                remove(args[1]);
            }
            case "log" -> {
                checkARGS(1, args.length);
                log();
            }
            case "global-log" -> {
                checkARGS(1, args.length);
                globalLog();
            }
            case "find" -> {
                checkARGS(2, args.length);
                find(args[1]);
            }
            case "status" ->{
                checkARGS(1, args.length);
                status();
            }
            case "checkout" ->{
                if (args.length == 2){
                    checkoutBranch(args[1]);
                }else if (args.length == 3 && args[1].equals("--")){
                    checkoutFile(args[2]);
                }else if (args.length == 4 && args[2].equals("--")){
                    checkoutCommitFile(args[1], args[3]);
                }else {
                    checkARGS(-1, 0);
                }
            }
            case "branch" ->{
                checkARGS(2, args.length);
                branch(args[1]);
            }
            case "rm-branch" ->{
                checkARGS(2, args.length);
                rmBranch(args[1]);
            }
            case "reset" ->{
                checkARGS(2, args.length);
                reset(args[1]);
            }
            case "merge" ->{
                checkARGS(2, args.length);
                merge(args[1]);
            }
            default -> {
                System.out.println("No command with that name exists.");
                System.exit(0);
            }
        }
    }
}
