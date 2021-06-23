package ojedaFernandez.framework;
/**
 * Para poder ejecutar el framework debera en cada accion creada implementar la
 * interface Accion del framework\
 * en la configuracion se debera indicar la ruta de los accion*
 * Debe crear una clase AccionSalida con un ejecutar System.exit(o)
 */

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
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
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            Panel titlePanel = new Panel();
            titlePanel.addComponent(new Label(" "));
            titlePanel.addComponent(new Label(" Arch Linux Installation").addStyle(SGR.BOLD));
            Panel buttonsPanel = new Panel(new GridLayout(2).setHorizontalSpacing(1));

                for (int i = 0; i < acciones.size(); i++) {
                    System.out.println(acciones.get(i).nombreItemMenu());
                    buttonsPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
                    int finalI = i;
                    System.out.println(finalI);
                    buttonsPanel.addComponent(new Button(acciones.get(i).nombreItemMenu(), () -> acciones.get(finalI).ejecutar()));
                }
                buttonsPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, false, false));

            Panel contentPanel = new Panel(new GridLayout(1).setLeftMarginSize(1).setRightMarginSize(1));
            contentPanel.addComponent(new Label("Interfaz de Framework con Lanterna."));
            contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            buttonsPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER,false,false)).addTo(contentPanel);



            Panel mainPanel = new Panel(new BorderLayout());
            mainPanel.addComponent(titlePanel, BorderLayout.Location.TOP);

            BasicWindow mainWindow = new BasicWindow();
            mainWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS));
            mainWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE));
            mainWindow.setComponent(mainPanel);

            BasicWindow window = new BasicWindow("Arch Linux Installation");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            window.setComponent(contentPanel);

            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            gui.addWindow(mainWindow);
            gui.addWindow(window);
            gui.waitForWindowToClose(mainWindow);
            gui.setActiveWindow(window);

        } catch (Exception e) {
            throw new RuntimeException("No se puede cargar el panel", e);
        }
    }
}
