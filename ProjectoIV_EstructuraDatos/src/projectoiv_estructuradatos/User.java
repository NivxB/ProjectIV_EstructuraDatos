/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoiv_estructuradatos;

/**
 *
 * @author Kevin Barahona
 */
public class User {
    
    private String Cod;
    private String PlanEstudio;
    private String Rol;//???????????????
    private String Nomb;

    public String getNomb() {
        return Nomb;
    }

    public void setNomb(String Nomb) {
        this.Nomb = Nomb;
    }
    
       
    public void clear(){
        Nomb = "";
        Cod = "";
        PlanEstudio = "";
        Rol = "";
    }
    public String getCod() {
        return Cod;
    }

    public void setCod(String Cod) {
        this.Cod = Cod;
    }

    public String getPlanEstudio() {
        return PlanEstudio;
    }

    public void setPlanEstudio(String PlanEstudio) {
        this.PlanEstudio = PlanEstudio;
    }

    public User(String Cod, String PlanEstudio) {
        this.Cod = Cod;
        this.PlanEstudio = PlanEstudio;
    }

    public User() {
    }
    
        
}
