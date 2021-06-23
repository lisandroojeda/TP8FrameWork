package ojedaFernandez.utilizacion;

import ojedaFernandez.framework.Accion;

public class AccionSalida implements Accion {
    @Override
    public void ejecutar() {
        System.exit(0);
    }

    @Override
    public String nombreItemMenu() {
        return "Salida";
    }

    @Override
    public String descripcionItemMenu() {
        return "";
    }
}
