/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Kevin Barahona
 */
public class ProjectoIV_EstructuraDatos {

    private static ArrayList<PlanEstudio> Planes = new ArrayList();
    private static ArrayList<ArrayList<Nodo>> PlanesOrdenados = new ArrayList();
    //private static ArrayList<Clase> Clases = new ArrayList();
    //private static ArrayList<Requisito> Requisitos = new ArrayList();
    //private static int PlanActual = 0;

    public static void main(String[] args) throws Exception {
        fillPlanes();
        paintGraph(Planes.get(0).getPlan());
        paintGraph(Planes.get(1).getPlan()); 

        /*       for (int i = 0; i < Clases.size(); i++) {
         System.out.println(Clases.get(i).getName());
         }
        
         for(int i=0;i<Requisitos.size();i++){
         System.out.println(Requisitos.get(i).getCodigo()+": ");
         for (int j = 0; j < Requisitos.get(i).getCodigoAbre().size(); j++) {
         System.out.println("z"+Requisitos.get(i).getCodigoAbre().get(j)); 
         }
         }
         */


    }

    private static void fillPlanes() throws FileNotFoundException {
        //Llenado por cada plan de estudio que este guardado
        //Archivo TXT
        //Archivo BIN
        //Base de Datos
        //
        File Archivo = new File("./data/planes.txt");
        Scanner s = new Scanner(Archivo);
        while (s.hasNextLine()) {
            try {
                String[] arr = s.nextLine().split(";");
                PlanEstudio p = new PlanEstudio(arr[0], arr[1]);
                Planes.add(p);
                fillClases();
                fillEdges();
            } catch (Exception e) {
                System.err.println("No se encontró \"planes.txt\"");
            }
        }
    }

    private static void fillClases() throws FileNotFoundException {//cod es el código de la carrera que nos interesa
        //solo llenar clases carrera actual para no desperdiciar memoria, sin embargo hay que crear una clase para cada carrera
        //ejemplo: Español en Sistemas, Español en Derecho, Español en turismo etc... se podrá arreglar?
        File Archivo = new File("./data/clases.txt");
        Scanner s = new Scanner(Archivo);
        int i = 0;
        while (s.hasNext()) {
            try {
                String[] arr = s.nextLine().split(";");
                if (arr[2].equals(Planes.get(Planes.size() - 1).getCodigo())) {
                    boolean temp = false;
                    if (arr[3].equals("1")) {
                        temp = true;
                    }
                    //System.out.println("Antes Clase");
                    Clase NewC = new Clase(arr[0], arr[1], arr[2], temp);
                    //System.out.println("Antes Nodo Despues Clase");
                    Nodo NewV = new Nodo(NewC);
                    //System.out.println("Despues Nodo");
                    Planes.get(Planes.size() - 1).getPlan().addVertex(NewV);
                    //Clases.add(c);
                    //System.out.println(arr[3] + " Added");
                    //System.out.println(i++);
                    //System.out.println(Planes.size());
                }
            } catch (Exception e) {
                //System.out.println(e.toString());
                System.err.println("No se encontró \"clases.txt\"");
            }
        }
    }

    /*
     private static void fillRequisitos() throws FileNotFoundException {//similar a fillClases
     Clase[] ClasesP = (Clase[]) Planes.get(Planes.size() - 1).getPlan().getVertices().toArray(new Clase[0]);
     for (int i = 0; i < ClasesP.length; i++) {
     Clase procesa = ClasesP[i];//clase por procesar
     ArrayList<String> ca = new ArrayList();//arraylist de strings de los codigos que abre

     File Archivo = new File("./data/requisitos.txt");
     Scanner s = new Scanner(Archivo);
     while (s.hasNext()) {
     try {
     String[] arr = s.nextLine().split(";");
     if (arr[0].equals(procesa.getCodigo()) && arr[2].equals(Planes.get(Planes.size() - 1).getName())) {
     String abre = arr[1];
     ca.add(abre);
     }
     } catch (Exception e) {
     System.err.println("No se encontró \"requisitos.txt\"");
     }
     }
     //Requisitos.add(new Requisito(procesa.getCodigo(),ca));
     }
     }
     */
    private static void fillEdges() throws FileNotFoundException {

        File Archivo = new File("./data/requisitos.txt");
        Scanner s = new Scanner(Archivo);
        while (s.hasNext()) {
            try {
                String[] arr = s.nextLine().split(";");
                if (arr[2].equals(Planes.get(Planes.size() - 1).getCodigo())) {
                    Nodo FirstNode = getVertex(arr[0]);
                   // System.out.println(FirstNode + " Nodo 1");
                    Nodo SecondNode = getVertex(arr[1]);
                   // System.out.println(SecondNode + " Nodo2");

                    if (FirstNode != null && SecondNode != null) {
                        String G = Planes.get(Planes.size() - 1).getPlan().getEdgeCount() + "";
                        Planes.get(Planes.size() - 1).getPlan().addEdge(G, FirstNode, SecondNode);
                        //System.out.println("Add Edge");
                    }


                }


            } catch (Exception e) {
                //System.out.println(e);
                System.err.println("No se encontró \"requisitos.txt\"");
            }
        }

    }

    private static Nodo getVertex(String COD) {
        Nodo[] Vertices = (Nodo[]) Planes.get(Planes.size() - 1).getPlan().getVertices().toArray(new Nodo[0]);

        for (Nodo tmp : Vertices) {
            if (tmp.getData().getCodigo().equals(COD)) {
                return tmp;
            }
        }

        return null;
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

    private static void paintGraph(DirectedGraph X) {
        //SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
// Layout<V, E>, BasicVisualizationServer<V,E>
        Layout<Nodo, String> layout = new FRLayout(X);
        layout.setSize(new Dimension(650, 650));
        BasicVisualizationServer<Nodo, String> vv =
                new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(700, 700));
// Setup up a new vertex to paint transformer...
        Transformer<Nodo, Paint> vertexPaint = new Transformer<Nodo, Paint>() {
            @Override
            public Paint transform(Nodo i) {
                return Color.CYAN;
            }
        };
        Transformer<Nodo, Shape> vertexSize = new Transformer<Nodo, Shape>() {
            public Shape transform(Nodo i) {
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
                // in this case, the vertex is twice as large
                return circle;
            }
        };
// Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer =
                new Transformer<String, Stroke>() {
                    public Stroke transform(String s) {
                        return edgeStroke;
                    }
                };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);

        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        JFrame frame = new JFrame("Ciudades");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
