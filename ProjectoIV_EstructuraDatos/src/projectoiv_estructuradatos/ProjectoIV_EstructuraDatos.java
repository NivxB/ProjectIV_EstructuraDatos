/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

import java.util.ArrayList;

/**
 *
 * @author Kevin Barahona
 */
public class ProjectoIV_EstructuraDatos {

    private static ArrayList<PlanEstudio> Planes;
    private static ArrayList<ArrayList<Nodo>> PlanesOrdenados;
    //private static int PlanActual = 0;

    public static void main(String[] args) {
        // TODO code application logic here
    }

    private static void fillPlanes() {
        //Llenado por cada plan de estudio que este guardado
            //Archivo TXT
            //Archivo BIN
            //Base de Datos
        //
    }

    private static void fillPlanesOrdenados() {
        for (int i = 0; i < Planes.size(); i++) {
            TopologicalSort(i);
        }
    }

    private static void TopologicalSort(int i) {
        Nodo[] Vertices = (Nodo[]) Planes.get(i).getPlan().getVertices().toArray(new Nodo[0]);
        for (Nodo tmp : Vertices) {
            if (!tmp.isVISITED()) {
                VisitNode(tmp, i);
            }
        }
    }

    private static void VisitNode(Nodo Cookie, int i) {
        Cookie.setVISITED(true);
        Nodo[] Vecino = (Nodo[]) (Planes.get(i).getPlan().getNeighbors(Cookie)).toArray(new Nodo[0]);
        for (Nodo tmp : Vecino) {
            if (!tmp.isVISITED()) {
                tmp.setFather(Cookie);
                VisitNode(tmp, i);
            }
        }

        PlanesOrdenados.get(i).add(Cookie);
    }
}
