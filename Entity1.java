public class Entity1 extends Entity {
    // initialization in the constructor
    public Entity1() {
        int s = 1;
        System.out.println("e1, s is set to:" + s);
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                distanceTable[i][j] = 999;
            }
        }

        // 1-0:1, 1-2:1
        distanceTable[s][1] = 0;

        distanceTable[s][0] = 1;
        distanceTable[s][2] = 1;

        distanceTable[0][s] = 1;
        distanceTable[2][s] = 1;
        System.out.println("e1 right after set [1][0]:" + distanceTable[s][0]);
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[s][i] != 999 && i != s) {
                System.out.println("e1 init toLayer2(): source=" + s +
                        " dest=" + i);
                // all neighbors
                NetworkSimulator.toLayer2(new Packet(s, i, this.distanceTable[s]));
            }
        }
    }

    public void update(Packet p) {
        // check
        int c = 1;

        if (c != p.getDest()) {
            System.out.println("Entity1 got invalid update packet");
            return;
        }
        System.out.println("e1 update, before update");
        printDT();
        int s = p.getSource();
        boolean change = false;
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[c][s] != 999
                    && this.distanceTable[c][i] > this.distanceTable[c][s] + p.getMincost(i)) {
                this.distanceTable[c][i] = this.distanceTable[c][s] + p.getMincost(i);
                change = true;
            }
        }
        if (change) {
            System.out.println("e1 update, after update");
            printDT();
            System.out.println();
        } else {
            System.out.println("e1 update, no change");
            System.out.println();
        }

        if (change) {
            for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                if (NetworkSimulator.cost[c][i] != 999 && i != c) {
                    System.out.println("e1 update, toLayer2(): source=" + c +
                            " dest=" + i);
                    NetworkSimulator.toLayer2(new Packet(c, i, this.distanceTable[c]));
                }
            }
        }
    }

    public void linkCostChangeHandler(int whichLink, int newCost) {
        int s = 1;
        this.distanceTable[s][whichLink] = newCost;
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                if (NetworkSimulator.cost[s][i] != 999 && i != s) {
                    System.out.println("e1 linkCostChangeHandler, toLayer2(): source=" + s +
                            " dest=" + i);
                    NetworkSimulator.toLayer2(new Packet(s, i, this.distanceTable[s]));
                }
            }
    }

    public void printDT() {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D1 |   0   1   2   3");
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
