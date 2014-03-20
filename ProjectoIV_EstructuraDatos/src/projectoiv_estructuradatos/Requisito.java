package projectoiv_estructuradatos;

import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class Requisito {
    private final String Codigo;
    private final ArrayList<String> CodigoAbre;
    
    public Requisito(String c, ArrayList<String> ca){
        Codigo=c;
        CodigoAbre=ca;
    }
    
    public String getCodigo(){
        return Codigo;
    }
    
    public ArrayList<String> getCodigoAbre(){
        return CodigoAbre;
    }
    
}
