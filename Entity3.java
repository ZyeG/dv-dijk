public class Entity3 extends Entity
{    
    // initialization in the constructor
    public Entity3()
    {
        int s = 3;

        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                distanceTable[i][j] = 999;
            }
        }

        // 3-0:7, 3-2:2
        distanceTable[s][3] = 0;
        
        distanceTable[s][0] = 7;
        distanceTable[s][2] = 2;

        distanceTable[0][s] = 7;
        distanceTable[2][s] = 2;

       
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[s][i] != 999 && i != s) {
                // all neighbors
                System.out.println("e3 init toLayer2(): source=" + s + 
                               " dest=" + i);
                NetworkSimulator.toLayer2(new Packet(s, i, this.distanceTable[s]));
            }
        }
    }

    public void update(Packet p)
    {
        // check
        int c = 3;

        if (c != p.getDest()) {
            System.out.println("Entity3 got invalid update packet");
            return;
        }
        System.out.println("e3 update, before update");
        printDT();
        int s = p.getSource();
        boolean change = false;
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (NetworkSimulator.cost[c][s]!=999 && this.distanceTable[c][i] > this.distanceTable[c][s] + p.getMincost(i)) {
                this.distanceTable[c][i] = this.distanceTable[c][s] + p.getMincost(i);
                change = true;
            }
        }

        if(change){
            System.out.println("e3 update, after update");
            printDT();
            System.out.println();
        }
        else {System.out.println("e3 no update");System.out.println();}

        if (change) {
            for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                if (NetworkSimulator.cost[c][i] != 999 && i != c) {
                    System.out.println("e3 update, toLayer2(): source=" + c + 
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
        System.out.println(" D3 |   0   1   2   3");
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
