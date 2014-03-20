/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Kevin Barahona
 */
public class ProjectoIV_EstructuraDatos {

    private static ArrayList<PlanEstudio> Planes = new ArrayList();
    private static ArrayList<ArrayList<Nodo>> PlanesOrdenados = new ArrayList();
    private static ArrayList<Clase> Clases = new ArrayList();
    private static ArrayList<Requisito> Requisitos = new ArrayList();
    //private static int PlanActual = 0;

    public static void main(String[] args) throws Exception {
        fillPlanes();
        fillClases("SISI");
        fillRequisitos("SISI");

        for (int i = 0; i < Clases.size(); i++) {
            System.out.println(Clases.get(i).getName());
        }
        
        for(int i=0;i<Requisitos.size();i++){
            System.out.println(Requisitos.get(i).getCodigo()+": ");
            for (int j = 0; j < Requisitos.get(i).getCodigoAbre().size(); j++) {
                System.out.println("z"+Requisitos.get(i).getCodigoAbre().get(j)); 
            }
        }
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
            } catch (Exception e) {
                System.err.println("No se encontró \"planes.txt\"");
            }
        }
    }

    private static void fillClases(String cod) throws FileNotFoundException {//cod es el código de la carrera que nos interesa
        //solo llenar clases carrera actual para no desperdiciar memoria, sin embargo hay que crear una clase para cada carrera
        //ejemplo: Español en Sistemas, Español en Derecho, Español en turismo etc... se podrá arreglar?
        File Archivo = new File("./data/clases.txt");
        Scanner s = new Scanner(Archivo);
        while (s.hasNext()) {
            try {
                String[] arr = s.nextLine().split(";");
                if (arr[2].equals(cod)) {
                    boolean temp = false;
                    if (arr[3].equals("1")) {
                        temp = true;
                    }
                    Clase c = new Clase(arr[0], arr[1], arr[2], temp);
                    Clases.add(c);
                }
            } catch (Exception e) {
                System.err.println("No se encontró \"clases.txt\"");
            }
        }
    }

    private static void fillRequisitos(String cod) throws FileNotFoundException {//similar a fillClases
        for (int i = 0; i < Clases.size(); i++) {
            Clase procesa = Clases.get(i);//clase por procesar
            ArrayList<String> ca = new ArrayList();//arraylist de strings de los codigos que abre

            File Archivo = new File("./data/requisitos.txt");
            Scanner s = new Scanner(Archivo);
            while (s.hasNext()) {
                try {
                    String[] arr = s.nextLine().split(";");
                    if (arr[0].equals(procesa.getCodigo()) && arr[2].equals(cod)) {
                        String abre = arr[1];
                        ca.add(abre);
                    }
                } catch (Exception e) {
                    System.err.println("No se encontró \"requisitos.txt\"");
                }
            }
            Requisitos.add(new Requisito(procesa.getCodigo(),ca));
        }
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
