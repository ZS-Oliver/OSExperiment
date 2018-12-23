public class PSM {
    private Integer ID; // 页号
    private Integer group; // 页号所属分组
    private Integer useTime; // 记录距离上次使用时间(LRU);记录距离下次使用的距离(OPT)

    public PSM(Integer ID, Integer group, Integer useTime) {
        this.ID = ID;
        this.group = group;
        this.useTime = useTime;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }
}
