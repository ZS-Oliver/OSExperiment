import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<DPA> dpaList = new ArrayList<>();
    public static List<DPA> bestList = new ArrayList<>();

    public static void show(List<DPA> list) {
        for (DPA dpa : list) {
            if (dpa.isAllocate()) {
                System.out.println("作业 " + dpa.getID() + ",起始地址：" + dpa.getBegin() + ",结束地址：" + dpa.getEnd() + ",内存大小" + dpa.getSize() + "KB");
            }
        }
    }

    public static void showFree(List<DPA> list){
        for(DPA dpa:list){
            if(!dpa.isAllocate()){
                System.out.println("空闲空间" + ",起始地址" + dpa.getBegin() + ",结束地址" + dpa.getEnd() + ",内存大小" + dpa.getSize() + "KB");
            }
        }
    }

//    public static void merge(DPA dpa) {
//        boolean isMergeLeft = false;
//        boolean isMergeRight = false;
//        for (int i = 0; i < dpaList.size(); i++) {
//            if (dpa.getID() == dpaList.get(i).getID()) {
//                // 判断i是否为0,避免第一个位置数组越界
//                if (i != 0 && !dpaList.get(i - 1).isAllocate() && dpaList.get(i - 1).getEnd() == dpa.getBegin()) {
//                    DPA newDpa = new DPA(0, false, dpaList.get(i - 1).getBegin(), dpa.getEnd(), dpa.getSize() + dpaList.get(i - 1).getSize());
//                    dpaList.add(newDpa);
//                    dpa = newDpa;
//                    isMergeLeft = true;
//                }
//                // 判断i是否为dpaList.size()-1,避免最后一个位置数组越界
//                if (i != dpaList.size() - 1 && !dpaList.get(i + 1).isAllocate() && dpaList.get(i + 1).getBegin() == dpa.getEnd()) {
//                    DPA newDpa = new DPA(0, false, dpa.getBegin(), dpaList.get(i + 1).getEnd(), dpa.getSize() + dpaList.get(i + 1).getSize());
//                    dpaList.add(newDpa);
//                    dpaList.remove(dpaList.get(i + 1));
//                    isMergeRight = true;
//                }
//                if (isMergeLeft) {
//                    dpaList.remove(dpaList.get(i));
//                    dpaList.remove(dpaList.get(i - 1));
//                }
//                if (!isMergeLeft && isMergeRight) {
//                    dpaList.remove(dpaList.get(i));
//                }
//
//                break;
//            }
//        }
//    }

    public static void merge(DPA dpa) {
        boolean isMergeLeft = false;
        boolean isMergeRight = false;
        for (int i = 0; i < dpaList.size(); i++) {
            if (dpa.getID() == dpaList.get(i).getID()) {
                // 判断i是否为0,避免第一个位置数组越界
                if (i != 0 && !dpaList.get(i - 1).isAllocate() && dpaList.get(i - 1).getEnd() == dpa.getBegin()) {
                    DPA newDpa = new DPA(0, false, dpaList.get(i - 1).getBegin(), dpa.getEnd(), dpa.getSize() + dpaList.get(i - 1).getSize());
                    dpaList.add(newDpa);
                    dpa = newDpa;
                    isMergeLeft = true;
                }
                // 判断i是否为dpaList.size()-1,避免最后一个位置数组越界
                if (i != dpaList.size() - 1 && !dpaList.get(i + 1).isAllocate() && dpaList.get(i + 1).getBegin() == dpa.getEnd()) {
                    DPA newDpa = new DPA(0, false, dpa.getBegin(), dpaList.get(i + 1).getEnd(), dpa.getSize() + dpaList.get(i + 1).getSize());
                    dpaList.add(newDpa);
                    dpaList.remove(dpaList.get(i + 1));
                    isMergeRight = true;
                }
                if (isMergeLeft) {
                    dpaList.remove(dpaList.get(i));
                    dpaList.remove(dpaList.get(i - 1));
                }
                if (!isMergeLeft && isMergeRight) {
                    dpaList.remove(dpaList.get(i));
                }

                break;
            }
        }
    }


    public static void bestMerge(DPA dpa) {
        boolean isMerge = false;
        for (DPA dpaTemp : dpaList) {
            if (dpaTemp.getBegin() == dpa.getEnd()) {
                DPA newDpa = new DPA(0, false, dpa.getBegin(), dpaTemp.getEnd(), dpa.getSize() + dpaTemp.getSize());
                dpaList.remove(dpaTemp);
                dpaList.add(newDpa);
                dpa = newDpa;
                isMerge = true;
            }
            if (dpaTemp.getEnd() == dpa.getBegin()) {
                DPA newDpa = new DPA(0, false, dpaTemp.getBegin(), dpa.getEnd(), dpa.getSize() + dpaTemp.getSize());
                dpaList.remove(dpaTemp);
                dpaList.add(newDpa);
                isMerge = true;
            }
            if (isMerge) {
                return;
            }
        }
        dpaList.add(dpa);
    }


    public static void main(String[] args) {
        DPA dpaRoot = new DPA(0, false, 0, 640, 640);
        dpaList.add(dpaRoot);
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要使用的分配算法,0为首次适应算法,1为最佳适应算法");
        int method = sc.nextInt();
        if (method == 0) {
            while (true) {
                System.out.println("清依次输入作业ID,申请(1)还是释放(2),以及大小");
                int ID = sc.nextInt();
                int mode = sc.nextInt();
                int size = sc.nextInt();
                DPA dpa = new DPA();
                // 分配内存
                if (mode == 1) {
                    System.out.println("作业 " + ID + " 申请了 " + size + "KB");
                    for (DPA dpaTemp : dpaList) {
                        if (!dpaTemp.isAllocate()) {
                            if (dpaTemp.getSize() == size) {
                                dpa = dpaTemp;
                                dpaList.remove(dpaTemp);
                                break;
                            } else if (dpaTemp.getSize() > size) {
                                dpa.setSize(size);
                                dpa.setBegin(dpaTemp.getBegin());
                                dpa.setEnd(dpa.getBegin() + dpa.getSize());
                                DPA dpaFree = new DPA(0, false, dpa.getEnd(), dpaTemp.getEnd(), dpaTemp.getSize() - dpa.getSize());
                                dpaList.add(dpaFree);
                                dpaList.remove(dpaTemp);
                                break;
                            }
                        }
                    }
                    dpa.setID(ID);
                    dpa.setAllocate(true);
                    dpaList.add(dpa);
                    Collections.sort(dpaList);
                } else {
                    // 释放内存
                    System.out.println("作业 " + ID + " 释放了 " + size + "KB");
                    for (DPA dpaTemp : dpaList) {
                        if (dpaTemp.getID() == ID) {
                            dpaTemp.setAllocate(false);
                            merge(dpaTemp);
                            break;
                        }
                    }
                    Collections.sort(dpaList);
                }
                System.out.println("当前内存分配情况为:");
                show(dpaList);
                System.out.println("空闲内存分配情况为:");
                showFree(dpaList);
                System.out.println("==================================");
                System.out.println();
            }

        } else {
            while (true) {
                System.out.println("清依次输入作业ID,申请(1)还是释放(2),以及大小");
                int ID = sc.nextInt();
                int mode = sc.nextInt();
                int size = sc.nextInt();
                DPA dpa = new DPA();
                // 分配内存
                if (mode == 1) {
                    System.out.println("作业 " + ID + " 申请了 " + size + "KB");
                    if (bestList == null) {
                        bestList.add(new DPA(ID, true, 0, size, size));
                    } else {
                        for (DPA dpaTemp : dpaList) {
                            if (dpaTemp.getSize() == size) {
                                dpa = dpaTemp;
                                dpaList.remove(dpaTemp);
                                break;
                            } else if (dpaTemp.getSize() > size) {
                                dpa.setSize(size);
                                dpa.setBegin(dpaTemp.getBegin());
                                dpa.setEnd(dpa.getBegin() + dpa.getSize());
                                DPA dpaFree = new DPA(0, false, dpa.getEnd(), dpaTemp.getEnd(), dpaTemp.getSize() - dpa.getSize());
                                dpaList.add(dpaFree);
                                dpaList.remove(dpaTemp);
                                break;
                            }
                        }
                    }

                    dpa.setID(ID);
                    dpa.setAllocate(true);
                    bestList.add(dpa);
                    Collections.sort(dpaList, new BestSort());
                } else {
                    // 释放内存
                    System.out.println("作业 " + ID + " 释放了 " + size + "KB");
                    for (DPA bestDpa : bestList) {
                        if (bestDpa.getID() == ID) {
                            bestDpa.setAllocate(false);
                            bestList.remove(bestDpa);
                            bestMerge(bestDpa);
                            break;
                        }
                    }
                    Collections.sort(dpaList, new BestSort());
                }
                System.out.println("当前内存分配情况为:");
                show(bestList);
                System.out.println("空闲内存分配情况为:");
                showFree(dpaList);
                System.out.println("==================================");
                System.out.println();
            }
        }
    }
}