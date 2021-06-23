package ojedaFernandez.acciones;

import ojedaFernandez.framework.Accion;

public class AccionNueva implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Mostrar algo");
    }

    @Override
    public String nombreItemMenu() {
        return "NuevaAccion";
    }

    @Override
    public String descripcionItemMenu() {
        return "Se implementaria una nueva accion";
    }
}
