/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 *
 * @author Kevin Barahona
 */
public class ProjectoIV_EstructuraDatos {

    private static ArrayList<PlanEstudio> Planes = new ArrayList();
    private static User Usuario;
    private static JGraphModelAdapter m_jgAdapter;

    public static Graph Ordenados(DirectedGraph X) {
        TopologicalOrderIterator<Nodo, String> ordenados = new TopologicalOrderIterator(X);
        return ordenados.getGraph();
    }

    private static void positionVertexAt(Object vertex, int x, int y) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
        Map attr = cell.getAttributes();
        Rectangle b = GraphConstants.getBounds(attr).getBounds();

        GraphConstants.setBounds(attr, new Rectangle(x, y, b.width, b.height));

        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);
        m_jgAdapter.edit(cellAttr, null, null, null);
    }

    public static void Prin() throws Exception {
        //Usuario = new User("NX", "SISI");
        fillPlanes();
        fillApproved();
        run(Usuario);
        displayGraph(Ordenados(Planes.get(0).getPlan()));        
    }

    public static void displayGraph(Graph Xg) {
        m_jgAdapter = new JGraphModelAdapter(Xg);
        JFrame D = new JFrame();
        JGraph S = new JGraph(m_jgAdapter);
        Nodo[] NODO = (Nodo[]) Xg.vertexSet().toArray(new Nodo[0]);
        int X = 0;
        int Y = 0;
        for (Nodo tmp : NODO) {
            if (X == 400) {
                X = 0;
                Y += 50;
            }

            if (tmp.getData().getCodigo().equals("LCP104") || tmp.getData().getCodigo().equals("LCP105") || tmp.getData().getCodigo().equals("LCP208")) {
                X -= 100;
                if (X < 0){
                    X = 300;
                    Y-=50;
                }
                positionVertexAt(tmp, X, Y + 25);
            } else {
                positionVertexAt(tmp, X, Y);
            }
            X += 100;
        }
        JPanel container = new JPanel();
        container.add(S);
        JScrollPane pane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        D.add(pane);
        D.pack();
        D.show();
    }

    public static boolean Login(String User) throws FileNotFoundException {
        File Archivo = new File("./data/user.txt");
        Scanner s = new Scanner(Archivo);
        while (s.hasNextLine()) {
            try {
                String[] arr = s.nextLine().split(";");
                String COD = arr[0];
                String P = arr[1];
                String Nomb = arr[2];
                if (User.equals(COD)) {
                    Usuario = new User();
                    Usuario.setCod(COD);
                    Usuario.setPlanEstudio(P);
                    Usuario.setNomb(Nomb);
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e);
                System.err.println("No se encontró \"user.txt\"");
            }
        }
        return false;
    }

    public static void Logout() {
        Usuario.clear();
        removeApproved();
    }

    private static void fillApproved() throws FileNotFoundException {

        for (PlanEstudio tmp : Planes) {

            //Nodo[] Cla = (Nodo[]) tmp.getPlan().getVertices().toArray(new Nodo[0]);
            Nodo[] Cla = (Nodo[]) tmp.getPlan().vertexSet().toArray(new Nodo[0]);
            for (Nodo temp : Cla) {
                setApproved(temp.getData());
                if (temp.getData().isApproved()) {
                    System.out.println("S");
                    tmp.getPlan().removeVertex(temp);
                }
            }
        }
    }

    private static void removeApproved() {
        for (PlanEstudio tmp : Planes) {
            //Nodo[] Cla = (Nodo[]) tmp.getPlan().getVertices().toArray(new Nodo[0]);
            Nodo[] Cla = (Nodo[]) tmp.getPlan().vertexSet().toArray(new Nodo[0]);
            for (Nodo temp : Cla) {
                temp.getData().setApproved(false);
            }
        }
    }

    private static void run(User u) {//usuario actual
        Scanner s = new Scanner(System.in);
        System.out.println(u.getPlanEstudio().toString());
        Nodo[] Vertices = new Nodo[0];
        ArrayList<Integer> aprobadas = new ArrayList();
        int selection = 0;
        int thisPlan = 0;
        for (int i = 0; i < Planes.size(); i++) {
            if (Planes.get(i).getCodigo().equals(u.getPlanEstudio())) {
                //Vertices = (Nodo[]) Planes.get(i).getPlan().getVertices().toArray(new Nodo[0]);
                Vertices = (Nodo[]) Planes.get(i).getPlan().vertexSet().toArray(new Nodo[0]);
                thisPlan = i;
                break;
            }
        }

        /*for (int i = 0; i < Vertices.length; i++) {
         Nodo temp = Vertices[i];
         int swap=Vertices.length-1-i;
         Vertices[i] = Vertices[swap];
         Vertices[swap] = temp;
         }*/
        System.out.println("Clases: ");

        for (int i = 0; i < Vertices.length; i++) {
            System.out.println((i + 1) + Vertices[i].getData().getName());
        }

        System.out.println("Ingrese los índices de las clases que ya cursó: (0 para terminar de ingresar clases)\n");
        do {
            boolean b = false;
            selection = s.nextInt();
            if (selection != 0) {
                aprobadas.add(selection);
            }

            for (int i = 0; i < Vertices.length; i++) {
                for (int j = 0; j < aprobadas.size(); j++) {
                    b = false;
                    if ((i + 1) == aprobadas.get(j)) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    System.out.println((i + 1) + Vertices[i].getData().getName());
                }
            }
        } while (selection != 0);

        Set<Integer> set = new HashSet<Integer>(aprobadas);//o podría hacer un método para remover duplicados, pero sabemos que eso no pasará :)

        for (Integer temp : set) {
            Planes.get(thisPlan).getPlan().removeVertex(Vertices[temp - 1]);
        }
        
        int sem=1;

        System.out.println("\nIngrese el periodo actual: (1,2,3,4)");
        sem=s.nextInt();
        
        if(sem==2||sem==4){
            for(Nodo n :Vertices){
                if(n.getData().isSemestral()){
                    Planes.get(thisPlan).getPlan().removeVertex(n);
                }
            }
        }

    }

    private static void fillPlanes() throws FileNotFoundException {

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

    private static void setApproved(Clase ClaseR) throws FileNotFoundException {
        File Archivo = new File("./data/ClasesAprobadas.txt");
        Scanner s = new Scanner(Archivo);
        while (s.hasNextLine()) {
            String[] arr = s.nextLine().split(";");
            if (arr[0].equals(Usuario.getCod()) && arr[1].equals(ClaseR.getCodigo())) {
                ClaseR.setApproved(true);
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
                    //setApproved(NewC);
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
                        String G = Planes.get(Planes.size() - 1).getPlan().edgeSet().size() + "";
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
        //Nodo[] Vertices = (Nodo[]) Planes.get(Planes.size() - 1).getPlan().getVertices().toArray(new Nodo[0]);
        Nodo[] Vertices = (Nodo[]) Planes.get(Planes.size() - 1).getPlan().vertexSet().toArray(new Nodo[0]);

        for (Nodo tmp : Vertices) {
            if (tmp.getData().getCodigo().equals(COD)) {
                return tmp;
            }
        }

        return null;
    }
    /*
     private static void fillPlanesOrdenados() {
     for (int i = 0; i < Planes.size(); i++) {
     PlanesOrdenados.add(new ArrayList<Nodo>());
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

     if (!Cookie.getData().isApproved()) {
     Nodo[] IN = (Nodo[]) Planes.get(i).getPlan().getPredecessors(Cookie).toArray(new Nodo[0]);
     if (IN.length == 0) {
     PlanesOrdenados.get(i).add(Cookie);
     } else {
     boolean Ingresar = true;
     for (Nodo tmp : IN) {
     if (!tmp.getData().isApproved()) {
     Ingresar = false;
     }
     }

     if (Ingresar) {
     PlanesOrdenados.get(i).add(Cookie);
     }
     }
     }
     }

     private static void paintGraph(DirectedGraph X) {
     //SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
     // Layout<V, E>, BasicVisualizationServer<V,E>
     Layout<Nodo, String> layout = new FRLayout(X);
     layout.setSize(new Dimension(650, 650));
     BasicVisualizationServer<Nodo, String> vv = new BasicVisualizationServer<>(layout);
     vv.setPreferredSize(new Dimension(700, 700));
     // Setup up a new vertex to paint transformer...
     Transformer<Nodo, Paint> vertexPaint = new Transformer<Nodo, Paint>() {
     @Override
     public Paint transform(Nodo i) {
     if (i.getData().isApproved()) {
     return Color.gray;
     } else {
     return Color.CYAN;
     }
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
     Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
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
     */
}
