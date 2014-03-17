/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

import edu.uci.ics.jung.graph.DirectedGraph;
import java.util.ArrayList;

/**
 *
 * @author Kevin Barahona
 */
public class PlanEstudio {

    private DirectedGraph Plan;
    private String Name;

    //public void FillPlan(ArrayList<Clase> Clases){
    //}
    public DirectedGraph getPlan() {
        return Plan;
    }

    public void setPlan(DirectedGraph Plan) {
        this.Plan = Plan;
    }
}
