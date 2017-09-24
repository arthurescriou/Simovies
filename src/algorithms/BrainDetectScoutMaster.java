/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import java.util.ArrayList;

public class BrainDetectScoutMaster {

   private static BrainDetectScoutMaster instance = new BrainDetectScoutMaster();
    private ArrayList<BrainDetectScout> scouts;

    private BrainDetectScoutMaster(){
        scouts = new ArrayList<>();
    }

    public static BrainDetectScoutMaster getInstance() {
        return instance;
    }
    public void add(BrainDetectScout bds) {
        scouts.add(bds);
    }

    public BrainDetectScout get(int i) {
        return scouts.get(i);
    }

    public void remove(int i) {
        scouts.remove(i);
    }

    public void remove(BrainDetectScout bds) {
        scouts.remove(bds);
    }
}
