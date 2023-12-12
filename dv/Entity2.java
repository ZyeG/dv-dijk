public class Entity2 extends Entity
{    
    // initialization in the constructor
    public Entity2()
    {
        int s = 2;

        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                distanceTable[i][j] = 999;
            }
        }

        // 2-0:3, 2-1:1, 2-3:2
        distanceTable[s][2] = 0;
        
        distanceTable[s][0] = 3;
        distanceTable[s][1] = 1;
        distanceTable[s][3] = 2;

        distanceTable[0][s] = 3;
        distanceTable[1][s] = 1;
        distanceTable[3][s] = 2;

       
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[s][i] != 999 && i != s) {
                // all neighbors
                System.out.println("e2 init, toLayer2(): source=" + s + 
                               " dest=" + i);
                NetworkSimulator.toLayer2(new Packet(s, i, this.distanceTable[s]));
            }
        }
    }
    
    public void update(Packet p)
    {
        // check
        int c = 2;

        if (c != p.getDest()) {
            System.out.println("Entity2 got invalid update packet");
            return;
        }
        System.out.println("e2 update, before update");
        printDT();
        int s = p.getSource();
        boolean change = false;
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (this.distanceTable[c][i] > this.distanceTable[c][s] + p.getMincost(i)) {
                this.distanceTable[c][i] = this.distanceTable[c][s] + p.getMincost(i);
                change = true;
            }
        }

        if(change){
            printDT();
            System.out.println("e2 update, after update");
            printDT();
            System.out.println();
        }
        else {
            System.out.println("e2 update, no change");
            System.out.println();
        }

        if (change) {
            for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                if (NetworkSimulator.cost[c][s]!=999 && NetworkSimulator.cost[c][i] != 999 && i != c) {
                    System.out.println("e2 update, toLayer2(): source=" + c + 
                               " dest=" + i);
                    NetworkSimulator.toLayer2(new Packet(c, i, this.distanceTable[c]));
                }
            }
        }
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }

    public void printDT() {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D2 |   0   1   2   3");
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
