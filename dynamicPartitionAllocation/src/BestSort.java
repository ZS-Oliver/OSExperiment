import java.util.Comparator;

public class BestSort implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        DPA d1 = (DPA) o1;
        DPA d2 = (DPA) o2;
        if(d1.getSize() > d2.getSize()){
            return 1;
        }
        return -1;
    }
}
