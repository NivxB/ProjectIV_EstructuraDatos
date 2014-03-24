/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

/**
 *
 * @author Kevin Barahona
 */
public class Clase {
    
    private final String Name;
    private final String Codigo;
    private final String CodPlan;
    private boolean Approved;
    private final boolean Semestral;
    
    public Clase(String n, String c, String cp, boolean sem){
        Name=n;
        Codigo=c;
        CodPlan=cp;
        Semestral=sem;
    }
    
    
    public void setApproved(boolean b){
        Approved=b;
    }
    
    public boolean isApproved(){
        return Approved;
    }
    
    public String getName(){
        return Name;
    }
    
    public String getCodigo(){
        return Codigo;
    }

    public boolean isSemestral() {
        return Semestral;
    }
    
    
    
}
