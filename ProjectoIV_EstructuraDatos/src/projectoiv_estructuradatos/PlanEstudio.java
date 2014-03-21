/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import java.util.ArrayList;

/**
 *
 * @author Kevin Barahona
 */
public class PlanEstudio {

    private DirectedGraph<Nodo,String> Plan;
    private final String Name;
    private final String Codigo;
    
    public PlanEstudio(String c, String n){
        Codigo=c;
        Name=n;
        Plan = new DirectedSparseMultigraph<>();
    }

    public String getCodigo() {
        return Codigo;
    }

    
    
    //public void FillPlan(ArrayList<Clase> Clases){
    //}
    public DirectedGraph getPlan() {
        return Plan;
    }

    public void setPlan(DirectedGraph Plan) {
        this.Plan = Plan;
    }
    
    public String getName(){
        return Name;
    }
}
