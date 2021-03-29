package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Based off of the SpaceInvaders App
// Creates a graphical organization app
public class GraphicalOrganizationApp extends JFrame {

    private Calendar calendar;

    private CalendarPanel cp;
    private EditorPanel ep;
    private OptionsPanel op;
    private JLayeredPane lp;
    private InformationPanel ip;
    private PersistenceHandler persistenceHandler;

    // EFFECTS: starts the application
    public GraphicalOrganizationApp() {
        super("Organization App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);

        persistenceHandler = new PersistenceHandler();

        calendar = persistenceHandler.getCalendar();

        ep = new EditorPanel(this);
        op = new OptionsPanel(this);
        ip = new InformationPanel();
        lp = new JLayeredPane();
        cp = new CalendarPanel(calendar, ip);

        ip.setGraphicalOrganizationApp(this);

        add(ip,BorderLayout.WEST);

        lp.setPreferredSize(new Dimension(CalendarPanel.WIDTH,CalendarPanel.HEIGHT));
        lp.add(cp,0);
        lp.add(ep,1);

        cp.setBounds(0,0,CalendarPanel.WIDTH, CalendarPanel.HEIGHT);
        ep.setBounds(0,0,CalendarPanel.WIDTH, CalendarPanel.HEIGHT);
        lp.setOpaque(false);

        add(lp,BorderLayout.CENTER);
        add(op,BorderLayout.EAST);

        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  sets frame position to center of desktop
    private void centreOnScreen() {
        Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((desktopSize.width - getWidth()) / 2, (desktopSize.height - getHeight()) / 2);
    }

    // Starts the app
    public static void main(String[] args) {
        new GraphicalOrganizationApp();
    }

    // MODIFIES: this
    // EFFECTS: loads the calendar at fileName, and updates the calendar
    public void loadCalendar(String fileName) {
        try {
            persistenceHandler.saveCalendar();
            persistenceHandler.load(fileName);
            calendar = persistenceHandler.getCalendar();
            reloadCalendar();

        } catch (IOException e) {
            e.printStackTrace();
            remove(lp);
            add(new JLabel("Something went terribly wrong while loading the file."));
        }
    }

    // MODIFIES: this
    // EFFECTS: recreates cp and ep, and repaints
    public void reloadCalendar() {
        lp.remove(cp);
        lp.remove(ep);

        cp = new CalendarPanel(calendar, ip);
        ep = new EditorPanel(this);

        ip.newEmptyInformation();

        lp.add(cp, 0);
        lp.add(ep, 1);

        cp.setBounds(0, 0, CalendarPanel.WIDTH, CalendarPanel.HEIGHT);
        ep.setBounds(0, 0, CalendarPanel.WIDTH, CalendarPanel.HEIGHT);

        validate();
        repaint();
    }

    // getters
    public PersistenceHandler getPersistenceHandler() {
        return persistenceHandler;
    }

    public EditorPanel getEditorPanel() {
        return ep;
    }

    public CalendarPanel getCalendarPanel() {
        return cp;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public OptionsPanel getOptionsPanel() {
        return op;
    }
}
