package ojedaFernandez.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public final class MenuAccion {
    private final ArrayList<Accion> acciones = new ArrayList<>();

    public MenuAccion() {
        cargarMenu();
    }


    public void generarArrayAcciones() {
        Properties prop = new Properties();
        try {
            InputStream input = getClass().getResourceAsStream("/configuracion.properties");
            prop.load(input);
            String accionesString = prop.getProperty("acciones").toString();
            ArrayList<String> aList = new ArrayList(Arrays.asList(accionesString.split(",")));

            for (String st : aList) {
                Class c = Class.forName(st);
                this.acciones.add((Accion) c.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException("Problemas en la configuracion ", e);
        }
    }


    public void cargarMenu() {
        Scanner scanner = new Scanner(System.in);
        generarArrayAcciones();
        while (true) {
            for (int i = 0; i < acciones.size(); i++) {
                System.out.println(i + 1 + " )" + acciones.get(i).nombreItemMenu() + "--->" + acciones.get(i).descripcionItemMenu());
            }
            System.out.println("Seleccione una opcion \n");
            acciones.get(scanner.nextInt() - 1).ejecutar();
        }
    }
}
