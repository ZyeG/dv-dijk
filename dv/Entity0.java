public class Entity0 extends Entity {
    // initialization in the constructor
    public Entity0() {
        int s = 0;
        System.out.println("s is set to:"+s);

        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                distanceTable[i][j] = 999;
            }
        }

        // 0-1:1 0-2:3 0-3:7
        distanceTable[s][0] = 0;
        distanceTable[s][1] = 1;
        distanceTable[s][2] = 3;
        distanceTable[s][3] = 7;

        distanceTable[1][s] = 1;
        distanceTable[2][s] = 3;
        distanceTable[3][s] = 7;

        System.out.println("e0 right after set [0][3]:"+ distanceTable[s][3]+ "s:"+s);
        System.out.println("init table for e0");

        printDT();
        
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[s][i] != 999 && i != s) {
                // all neighbors
                System.out.println("e0 init,  toLayer2(): source=" + s + 
                               " dest=" + i);
                NetworkSimulator.toLayer2(new Packet(s, i, this.distanceTable[s]));
            }
        }

    }

    public void update(Packet p) {
        // check
        int c = 0;

        if (c != p.getDest()) {
            System.out.println("Entity0 got invalid update packet");
            return;
        }
        System.out.println("e0 update, before update");
        printDT();

        int s = p.getSource();
        boolean change = false;
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[c][s]!=999 && this.distanceTable[c][i] > this.distanceTable[c][s] + p.getMincost(i) ) {
                this.distanceTable[c][i] = this.distanceTable[c][s] + p.getMincost(i);
                change = true;
            }
        }

        if(change){
            System.out.println("e0 update, after update");
            printDT();
            System.out.println();
        }
        else {
            System.out.println("e0 update, no change");
            System.out.println();
        }

        if (change) {
            for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                if (NetworkSimulator.cost[c][i] != 999 && i != c) {
                    System.out.println("e0 update, toLayer2(): source=" + c + 
                               " dest=" + i);
                    NetworkSimulator.toLayer2(new Packet(c, i, this.distanceTable[c]));
                }
            }
        }
    }

    public void linkCostChangeHandler(int whichLink, int newCost) 
    {
        int s = 0;
        this.distanceTable[s][whichLink] = newCost;
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                if (NetworkSimulator.cost[s][i] != 999 && i != s) {
                    System.out.println("e0 linkCostChangeHandler, toLayer2(): source=" + s + 
                               " dest=" + i);
                    NetworkSimulator.toLayer2(new Packet(s, i, this.distanceTable[s]));
                }
            }
    }

    public void printDT() {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   0   1   2   3");
        System.out.println("----+----------------");
        for (int i = 0; i < 4; i++) {
            System.out.print("   " + i + "|");
            for (int j = 0; j < 4; j++) {
                if (distanceTable[i][j] < 10) {
                    System.out.print("   ");
                } else if (distanceTable[i][j] < 100) {
                    System.out.print("  ");
                } else {
                    System.out.print(" ");
                }
    
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }

}
