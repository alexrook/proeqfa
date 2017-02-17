package proeqfa.math.estimation;

/**
 * @author moroz
 */
public class OrderNum {

    private float more, less, same;

    private static OrderNum view1, view2, view3;

    public OrderNum(float more, float less, float same) {
        this.more = more;
        this.less = less;
        this.less = same;
    }

    public static OrderNum getView1() {
        if (view1 != null) {
            return view1;
        } else {
            view1 = new OrderNum(2, 0, 1);
            return view1;
        }

    }

    public static OrderNum getView2() {
        if (view2 != null) {
            return view2;
        } else {
            view2 = new OrderNum(1, -1, 0);
            return view2;
        }

    }

    public static OrderNum getView3() {
        if (view3 != null) {
            return view2;
        } else {
            view3 = new OrderNum(1, 0, 0.5f);
            return view3;
        }

    }

    public float getMore() {
        return more;
    }

    public float getLess() {
        return less;
    }

    public float getSame() {
        return same;
    }

}
