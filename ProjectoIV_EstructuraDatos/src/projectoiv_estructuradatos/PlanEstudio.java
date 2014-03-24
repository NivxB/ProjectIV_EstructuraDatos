/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;


import java.util.ArrayList;
import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

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
        Plan = new DirectedAcyclicGraph(DirectedAcyclicGraph.class);
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
