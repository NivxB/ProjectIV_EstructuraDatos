/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

/**
 *
 * @author Kevin Barahona
 */
public class Nodo {
    
    private Clase Data;
    private boolean VISITED;
    private Nodo Father;

    public Nodo getFather() {
        return Father;
    }

    public void setFather(Nodo Father) {
        this.Father = Father;
    }

    
    
    public Clase getData() {
        return Data;
    }

    public void setData(Clase Data) {
        this.Data = Data;
    }

    public boolean isVISITED() {
        return VISITED;
    }

    public void setVISITED(boolean VISITED) {
        this.VISITED = VISITED;
    }
    
    
    
}
