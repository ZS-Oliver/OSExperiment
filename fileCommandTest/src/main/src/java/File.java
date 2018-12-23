import java.util.List;

public class File {
    String name;
    String father;
    List<String> son;
    int state; // 0为目录,1为文件
    String content; // 文件内容


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public List<String> getSon() {
        return son;
    }

    public void setSon(List<String> son) {
        this.son = son;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    File() {

    }

    public File(String name, String father, List<String> son, int state, String content) {
        this.name = name;
        this.father = father;
        this.son = son;
        this.state = state;
        this.content = content;
    }
}
