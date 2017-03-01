package proeqfa.math.estimation;

import java.util.TreeSet;

/**
 * @author moroz
 */
public class ThreeLogicValues extends TreeSet<Double> {

    /*
        Методика определения критичности...
        Приложение А
        Таблица А.1  
     */
    private static ThreeLogicValues view1, view2, view3;

    public ThreeLogicValues(double more, double less, double same) {
        add(less);
        add(more);
        add(same);
        //TODO check order of given values and throw error if need ?
    }

    public static ThreeLogicValues getView1() {
        if (view1 != null) {
            return view1;
        } else {
            /*Таблица А.1*/
            view1 = new ThreeLogicValues(2, 0, 1);
            return view1;
        }

    }

    public static ThreeLogicValues getView2() {
        if (view2 != null) {
            return view2;
        } else {
            /*Таблица А.1*/
            view2 = new ThreeLogicValues(1, -1, 0);
            return view2;
        }

    }

    public static ThreeLogicValues getView3() {
        if (view3 != null) {
            return view3;
        } else {
            /*Таблица А.1*/
            view3 = new ThreeLogicValues(1, 0, 0.5d);
            return view3;
        }

    }

    public Double getMore() {
        return last();
    }

    public Double getLess() {
        return first();
    }

    public Double getSame() {
        return lower(getMore());
    }

}
