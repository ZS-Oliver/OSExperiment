import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static File now = new File("root", "NULL", null, 0, null);
    static List<File> fileList = new ArrayList<>();


    public static void showNow(String name) {
        boolean exist = false;
        for (File file : fileList) {
            if (file.getFather().equals(name)) {
                System.out.print(file.getName() + " ");
                exist = true;
            }
        }
        if (!exist) {
            System.out.print("NULL");
        }
        System.out.println();
    }


    public static void delete(File file) {
        for (File file1 : fileList) {
            if (file1.getFather().equals(file.getName())) {
                System.out.println("unexpected command");
                return;
            }
        }
        fileList.remove(file);
    }

//    static Queue<String> deleteList = new LinkedList<>();
//    public static void delete(String name) {
//        if (deleteList.size() != 0) {
//            for (File file : fileList) {
//                if (file.getFather().equals(name)) {
//                    deleteList.add(file.getName());
//                }
//            }
//            for (File file : fileList) {
//                if (file.getName().equals(name)) {
//                    fileList.remove(file);
//                    break;
//                }
//            }
//            deleteList.poll();
//            delete(deleteList.peek());
//        }
//    }

    public static void main(String[] args) {
        fileList.add(now);
        while (true) {
            System.out.print(now.getName() + " > ");
            Scanner sc = new Scanner(System.in);
            String command = sc.next();
            if ("mkdir".equals(command)) {
                String name = sc.next();
                File newFile = new File(name, now.getName(), null, 0, null);
                List<String> sonList = now.getSon();
                if (sonList == null) {
                    List<String> newSonList = new ArrayList<>();
                    newSonList.add(newFile.getName());
                    now.setSon(newSonList);
                } else {
                    sonList.add(newFile.getName());
                    now.setSon(sonList);
                }
                fileList.add(newFile);
            } else if ("ls".equals(command)) {
                showNow(now.getName());
            } else if ("cd".equals(command)) {
                String next = sc.next();
                if ("..".equals(next)) {
                    for (File file : fileList) {
                        if (file.getName().equals(now.getFather())) {
                            now = file;
                            break;
                        }
                    }
                } else {
                    for (File file : fileList) {
                        if (file.getName().equals(next) && file.getState() == 0) {
                            now = file;
                            break;
                        }
                        if (file.getName().equals(next) && file.getState() == 1) {
                            System.out.println("unexpected command");
                        }
                    }
                }
            } else if ("create".equals(command)) {
                String name = sc.next();
                File newFile = new File(name, now.getName(), null, 1, null);
                List<String> sonList = now.getSon();
                if (sonList == null) {
                    List<String> newSonList = new ArrayList<>();
                    newSonList.add(newFile.getName());
                    now.setSon(newSonList);
                } else {
                    sonList.add(newFile.getName());
                    now.setSon(sonList);
                }
                fileList.add(newFile);
            } else if ("write".equals(command)) {
                String name = sc.next();
                boolean exist = false;
                for (File file : fileList) {
                    if (file.getName().equals(name) && file.getState() == 1) {
                        exist = true;
                        String content = sc.next();
                        file.setContent(content);
                        break;
                    }
                }
                if (!exist) {
                    System.out.println("unexpected command");
                }
            } else if ("read".equals(command)) {
                String name = sc.next();
                boolean exist = false;
                for (File file : fileList) {
                    if (file.getName().equals(name) && file.getState() == 1) {
                        exist = true;
                        System.out.println(file.getContent());
                        break;
                    }
                }
                if (!exist) {
                    System.out.println("unexpected command");
                }
            } else if ("rmdir".equals(command)) {
                String name = sc.next();
                if (name.equals(now.getName())) {
                    System.out.println("unexpected command");
                } else {
                    for (File file : fileList) {
                        if (file.getName().equals(name)) {
                            delete(file);
                            break;
                        }
                    }
                }
            } else if ("rm".equals(command)) {
                String name = sc.next();
                if (name.equals(now.getName())) {
                    System.out.println("unexpected command");
                } else {
                    for (File file : fileList) {
                        if (file.getName().equals(name) && file.getState() == 1) {
                            fileList.remove(file);
                            break;
                        }
                    }
                }
            }else if("exit".equals(command)){
                FileReader.write(JSON.toJSON(fileList).toString());
                break;
            }
            else {
                System.out.println("unexpected command");
            }
        }

    }
}
