public class DPA implements Comparable<DPA>{
    private Integer ID; // 作业ID
    private boolean isAllocate; // true为已分配,false为未分配
    private Integer begin; // 起始位置
    private Integer end; // 终止位置
    private Integer size; // 内存大小

    public DPA() {
    }

    public DPA(Integer ID, boolean isAllocate, Integer begin, Integer end, Integer size) {
        this.ID = ID;
        this.isAllocate = isAllocate;
        this.begin = begin;
        this.end = end;
        this.size = size;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public boolean isAllocate() {
        return isAllocate;
    }

    public void setAllocate(boolean allocate) {
        isAllocate = allocate;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public int compareTo(DPA o) {
        if(this.getBegin() != o.getBegin()){
            return this.getBegin() - o.getBegin();
        }else {
            return 0;
        }
    }
}
