public class Process implements Comparable<Process> {
    private Integer ID; // 作业ID
    private Integer PRIORITY; // 优先权
    private Integer CPUTIME; // 使用cpu的时间
    private Integer ALLTIME; // 总占用时间
    private Integer STARTBLOCK; // 开始阻塞时间
    private Integer BLOCKTIME; // 阻塞总时长
    private String STATE; // 状态

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(Integer PRIORITY) {
        this.PRIORITY = PRIORITY;
    }

    public Integer getCPUTIME() {
        return CPUTIME;
    }

    public void setCPUTIME(Integer CPUTIME) {
        this.CPUTIME = CPUTIME;
    }

    public Integer getALLTIME() {
        return ALLTIME;
    }

    public void setALLTIME(Integer ALLTIME) {
        this.ALLTIME = ALLTIME;
    }

    public Integer getSTARTBLOCK() {
        return STARTBLOCK;
    }

    public void setSTARTBLOCK(Integer STARTBLOCK) {
        this.STARTBLOCK = STARTBLOCK;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public Integer getBLOCKTIME() {
        return BLOCKTIME;
    }

    public void setBLOCKTIME(Integer BLOCKTIME) {
        this.BLOCKTIME = BLOCKTIME;
    }

    public Process(Integer ID, Integer PRIORITY, Integer CPUTIME, Integer ALLTIME, Integer STARTBLOCK, Integer BLOCKTIME, String STATE) {
        this.ID = ID;
        this.PRIORITY = PRIORITY;
        this.CPUTIME = CPUTIME;
        this.ALLTIME = ALLTIME;
        this.STARTBLOCK = STARTBLOCK;
        this.BLOCKTIME = BLOCKTIME;
        this.STATE = STATE;
    }


    @Override
    public int compareTo(Process o) {
        if (this.getPRIORITY() != o.getPRIORITY()) {
            return o.getPRIORITY() - this.getPRIORITY();
        } else {
            return 0;
        }
    }
}