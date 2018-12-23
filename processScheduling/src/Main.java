import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * 9 0 3 2 3 READY
     * 38 0 3 -1 0 READY
     * 30 0 6 -1 0 READY
     * 29 0 3 -1 0 READY
     * 0 0 4 -1 0 READY
     */
    public static List<Process> processList = new ArrayList<>(); // 总进程

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入进程数:");
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.println("请依次输入 PRIORITY,CPUTIME,ALLTIME,STARTBLOCK,BLOCKTIME,STATE");
            int PRIORITY = sc.nextInt();
            int CUPTIME = sc.nextInt();
            int ALLTIME = sc.nextInt();
            int STARTBLOCK = sc.nextInt();
            int BLOCKTIME = sc.nextInt();
            String STATE = sc.next();
            Process process = new Process(i, PRIORITY, CUPTIME, ALLTIME, STARTBLOCK, BLOCKTIME, STATE);
            processList.add(process);
        }
        Collections.sort(processList);
        while (num > 0) {

            // 改变执行进程各状态的值
            int temp = 0; // 存在第一个为BLOCK状态且优先级最大
            boolean isSet = false; // 标记是否有值改变
            while (!isSet) {
                if ("READY".equals(processList.get(temp).getSTATE())) {
                    processList.get(temp).setSTATE("RUN");
                    isSet = true;
                } else {
                    temp++;
                }
            }

            System.out.print("RUNNING-PROG: ");
            for (int i = 0; i < processList.size(); i++) {
                if (processList.get(i).getSTATE().equals("RUN")) {
                    System.out.println(processList.get(i).getID());
                }
            }
            System.out.print("READY-QUEUE:");
            for (int i = 0; i < processList.size(); i++) {
                if (processList.get(i).getSTATE().equals("READY")) {
                    System.out.print("->");
                    System.out.print(processList.get(i).getID());
                }
            }
            System.out.println();
            System.out.print("BLOCK-QUEUE:");
            for (int i = 0; i < processList.size(); i++) {
                if (processList.get(i).getSTATE().equals("BLOCK")) {
                    System.out.print("->");
                    System.out.println(processList.get(i).getID());
                }
            }
            System.out.println();
            System.out.println("========================================================================");
            System.out.println("ID" + '\t' + "PRIORITY" + '\t' + "CPUTIME" + '\t' + "ALLTIME" + '\t' + "STARTBLOCK" + '\t' + "BLOCKTIME" + '\t' + "STATE");
            for (int i = 0; i < processList.size(); i++) {
                System.out.println(processList.get(i).getID() + "     "
                        + processList.get(i).getPRIORITY() + "            "
                        + processList.get(i).getCPUTIME() + "       "
                        + processList.get(i).getALLTIME() + "       "
                        + processList.get(i).getSTARTBLOCK() + "          "
                        + processList.get(i).getBLOCKTIME() + "        "
                        + processList.get(i).getSTATE());
            }
            System.out.println("========================================================================");
            System.out.println();

            // 改变执行进程各状态的值
            int temp1 = 0; // 存在第一个为BLOCK状态且优先级最大
            boolean isDelete = false; // 标记是否有值改变
            while (!isDelete) {
                if ("RUN".equals(processList.get(temp1).getSTATE())) {
                    processList.get(temp1).setPRIORITY(processList.get(temp1).getPRIORITY() - 3);
                    processList.get(temp1).setCPUTIME(processList.get(temp1).getCPUTIME() + 1);
                    processList.get(temp1).setALLTIME(processList.get(temp1).getALLTIME() - 1);
                    isDelete = true;
                } else {
                    temp1++;
                }
            }

            for (int i = 0; i < processList.size(); i++) {
                if (!"RUN".equals(processList.get(i).getSTATE())) {
                    processList.get(i).setPRIORITY(processList.get(i).getPRIORITY() + 1);
                }
                if ("BLOCK".equals(processList.get(i).getSTATE())) {
                    processList.get(i).setBLOCKTIME(processList.get(i).getBLOCKTIME() - 1);
                    if (processList.get(i).getBLOCKTIME() == 0) {
                        processList.get(i).setSTATE("READY");
                    }
                }
            }

            // 当前运行为要阻塞进程
            if (processList.get(0).getSTARTBLOCK() != -1) {
                processList.get(0).setSTARTBLOCK(processList.get(0).getSTARTBLOCK() - 1);
                if (processList.get(0).getSTARTBLOCK() == 0) {
                    processList.get(0).setSTATE("BLOCK");
                }
            }

            // 有进程结束执行
            if (processList.get(0).getALLTIME() == 0) {
                processList.remove(processList.get(0));
                num--;
            }

            // 初始化,将所有RUN状态初始化为READY
            Collections.sort(processList);
            for (int i = 0; i < processList.size(); i++) {
                if ("RUN".equals(processList.get(i).getSTATE())) {
                    processList.get(i).setSTATE("READY");
                    break;
                }
            }
        }
    }
}
