package Components;
public class Client {
    private int x;
    private int y;
    private int nbrClient;
    private int q; //quantité commandé

    public Client(int nbrClient, int x, int y, int q) {
        this.nbrClient = nbrClient;
        this.x = x;
        this.y = y;
        this.q = q;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getQ() {
        return q;
    }

    public void setNbrClient(int nbrClient) {
        this.nbrClient = nbrClient;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public static float calcDist(Client c1, Client c2) {
        return (float)Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
    }

    // public String toString(){ return "("+x+";"+y+")"; }
    public String toString(){ return ""+nbrClient; }
}
