import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // 存储调页顺序
    public static int[] pageList = new int[320];

    // 存储当前内存页数情况
    public static List<PSM> inPsmList = new ArrayList<>();

    // 调页顺序集合
    public static List<PSM> outPsmList = new ArrayList<>();

    /**
     * 随机生成调页页数
     * 要求：
     * 50%的指令是顺序执行的
     * 25%的指令是均匀分布在前地址部分
     * 25%的指令时均匀分布在后地址部分
     */
    public static void randomNum() {
        int seed = 320 / 2;
        int count = 319;
        int num = (int) (Math.random() * 320);
        while (count >= 0) {
            if (num % 2 == 1) {
                if (num > 0 && count > 0) {
                    pageList[count--] = num--;
                }
            } else {
                num = num + 320 / 2;
                if (num < 320 && count > 0) {
                    pageList[count--] = num--;
                }
            }
            pageList[count] = num;
            num = (int) (Math.random() * seed);
            count--;
        }
    }

    // 调页顺序初始化
    public static void outPsm() {
        for (int i = 0; i < pageList.length; i++) {
            PSM psm = new PSM(pageList[i], pageList[i] / 10, 0);
            outPsmList.add(psm);
        }
    }

    // 当前存储情况初始化
    public static void inPsm() {
        int count = 0;
        for (int i = 0; i < outPsmList.size(); i++) {
            if (inPsmList.size() != 0) {
                if (!isExist(outPsmList.get(i))) {
                    inPsmList.add(outPsmList.get(i));
                    count++;
                }
            } else {
                inPsmList.add(outPsmList.get(i));
                count++;
            }
            if (count == 4) {
                break;
            }
        }
    }

    // 展示当前存储情况
    public static void showInPsm() {
        System.out.print("当前内存分配情况： ");
        for (int i = 0; i < inPsmList.size() - 1; i++) {
            System.out.print(inPsmList.get(i).getGroup() + " ");
        }
        System.out.println(inPsmList.get(inPsmList.size() - 1).getGroup());
    }

    // 判断页项是否存在
    public static boolean isExist(PSM psm) {
        for (int i = 0; i < inPsmList.size(); i++) {
            if (psm.getGroup() == inPsmList.get(i).getGroup()) {
                return true;
            }
        }
        return false;
    }

    // LRU缺页调度
    public static void pageLru() {
        int max = -1;
        for (int i = 0; i < inPsmList.size(); i++) {
            if (inPsmList.get(i).getUseTime() > max) {
                max = inPsmList.get(i).getUseTime();
            }
        }
        for (int i = 0; i < inPsmList.size(); i++) {
            if (max == inPsmList.get(i).getUseTime()) {
                inPsmList.remove(i);
                break;
            }
        }
        updatePageUse(inPsmList);
    }

    // OPT缺页调度
    public static void pageOpt() {
        int max = -1;
        for (int i = 0; i < inPsmList.size(); i++) {
            if (inPsmList.get(i).getUseTime() > max) {
                max = inPsmList.get(i).getUseTime();
            }
        }
        for (int i = 0; i < inPsmList.size(); i++) {
            if (max == inPsmList.get(i).getUseTime()) {
                inPsmList.remove(i);
                break;

            }
        }
    }

    // OPT去除命中的页项,将对应该页项的页重新调入
    public static void pageOptRetransfer(PSM psm) {
        for (int i = 0; i < inPsmList.size(); i++) {
            if (inPsmList.get(i).getGroup() == psm.getGroup()) {
                inPsmList.remove(i);
                break;
            }
        }
    }

    // 更改页面使用情况
    public static void updatePageUse(List<PSM> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUseTime(list.get(i).getUseTime() + 1);
        }
    }

    // OPT页面调度
    public static void setUseTime() {
        for (int i = 0; i < inPsmList.size(); i++) {
            for (int j = 0; j < outPsmList.size(); j++) {
                if (inPsmList.get(i).getID() == outPsmList.get(j).getID()) {
                    for (int q = j + 1; q < outPsmList.size(); q++) {
                        if (outPsmList.get(q).getGroup() == inPsmList.get(i).getGroup()) {
                            inPsmList.get(i).setUseTime(q - j);
                            break;
                        }
                        // 当页项不会再次调用,将他的再次使用时间设为1000,即无穷大
                        inPsmList.get(i).setUseTime(1000);
                    }
                    break;
                }
            }
        }
    }

    // 由于OPT和LRU共用useTime字段,因此要在使用前置零
    public static void cleanUseTime() {
        for (PSM psm : outPsmList) {
            psm.setUseTime(0);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        randomNum();
        outPsm();
        inPsm();

        while (true) {
            int count = 0;
            int missingPage = 0;
            System.out.println("请输入要执行的调页算法,0为FIFO,1为LRU,2为OPT");
            int module = sc.nextInt();
            if (module == 0) {
                while (count < 320) {
                    showInPsm();
                    System.out.println("调入页面：" + outPsmList.get(count).getID());
                    if (!isExist(outPsmList.get(count))) {
                        System.out.println("缺页");
                        inPsmList.remove(0);
                        inPsmList.add(outPsmList.get(count));
                        missingPage++;
                    }
                    count++;
                    System.out.println();
                }
                System.out.println("=====================================");
                System.out.println("缺页次数：" + missingPage + "次");
                System.out.println("缺页率：" + (missingPage) * 100 / 320 + "%");
                System.out.println("=====================================");
                System.out.println();
            } else if (module == 1) {
                cleanUseTime();
                while (count < 320) {
                    showInPsm();
                    System.out.println("调入页面：" + outPsmList.get(count).getID());
                    if (!isExist(outPsmList.get(count))) {
                        System.out.println("缺页");
                        pageLru();
                        inPsmList.add(outPsmList.get(count));
                        missingPage++;
                    } else {
                        updatePageUse(inPsmList);
                        for (int i = 0; i < inPsmList.size(); i++) {
                            if (inPsmList.get(i).getGroup() == outPsmList.get(count).getGroup()) {
                                inPsmList.get(i).setUseTime(0);
                                break;
                            }
                        }
                    }
                    count++;
                    System.out.println();
                }
                System.out.println("=====================================");
                System.out.println("缺页次数：" + missingPage + "次");
                System.out.println("缺页率：" + (missingPage) * 100 / 320 + "%");
                System.out.println("=====================================");
                System.out.println();
            } else {
                cleanUseTime();
                while (count < 320) {
                    setUseTime();
                    showInPsm();
                    System.out.println("调入页面：" + outPsmList.get(count).getID());
                    if (!isExist(outPsmList.get(count))) {
                        System.out.println("缺页");
                        pageOpt();
                        inPsmList.add(outPsmList.get(count));
                        missingPage++;
                    } else {
                        pageOptRetransfer(outPsmList.get(count));
                        inPsmList.add(outPsmList.get(count));
                    }
                    setUseTime();
                    count++;
                    System.out.println();
                }
                System.out.println("=====================================");
                System.out.println("缺页次数：" + missingPage + "次");
                System.out.println("缺页率：" + (missingPage) * 100 / 320 + "%");
                System.out.println("=====================================");
                System.out.println();
            }
        }
    }
}



