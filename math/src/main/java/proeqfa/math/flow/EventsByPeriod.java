package proeqfa.math.flow;

import java.util.ArrayList;

/**
 * Created by moroz on 29.03.17.
 */
public class EventsByPeriod {

    private int equipmentCount, timeUnitsCount;
    private double periodSize;
    private int totalEvents;
    private ArrayList events=new ArrayList<>();


    public EventsByPeriod(int equipmentCount, int timeUnitsCount) {
        this.equipmentCount = equipmentCount;
        this.timeUnitsCount = timeUnitsCount;
    }


    public static int calcPeriodCountBySturgesRule(int eventsCount) {
        double k = Math.round(1 + 3.3 * Math.log10(eventsCount));
        return (int) k;
    }





}
