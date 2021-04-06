package ui.gui;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

// Based off of the SpaceInvaders App
// Creates a graphical organization app
public class GraphicalOrganizationApp extends JFrame {

    private static Image FAILED_IMAGE = null;

    // TODO
    static {
        try {
            FAILED_IMAGE = ImageIO.read(new File("./data/ImageError.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Calendar calendar;

    private CalendarPanel cp;
    private EditorPanel ep;
    private OptionsPanel op;
    private JLayeredPane lp;
    private InformationPanel ip;
    private PersistenceHandler persistenceHandler;
    private Image image;

    // EFFECTS: starts the application
    public GraphicalOrganizationApp() {
        super("Organization App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        try {
            image = ImageIO.read(new URL("https://images.unsplash.com/photo-1484312152213-d713e8b7c053"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        persistenceHandler = new PersistenceHandler();

        calendar = persistenceHandler.getCalendar();

        ep = new EditorPanel(this);
        op = new OptionsPanel(this);
        ip = new InformationPanel();
        lp = new JLayeredPane();
        cp = new CalendarPanel(this);

        cp.scaleImage(image);

        ip.setGraphicalOrganizationApp(this);

        add(ip,BorderLayout.WEST);

        setupLayeredPane();

        add(op,BorderLayout.EAST);

        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the layered pane
    private void setupLayeredPane() {
        lp.setPreferredSize(new Dimension(CalendarPanel.WIDTH,CalendarPanel.HEIGHT));
        lp.add(cp,0);
        lp.add(ep,1);

        cp.setBounds(0,0,CalendarPanel.WIDTH, CalendarPanel.HEIGHT);
        ep.setBounds(0,0,CalendarPanel.WIDTH, CalendarPanel.HEIGHT);
        lp.setOpaque(false);

        add(lp,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets the background image for the calendar
    public void setImage(String fileLocation) {
        try {
            image = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
            image = FAILED_IMAGE;
        }

        if (image == null) {
            image = FAILED_IMAGE;
        }

        cp.scaleImage(image);
        cp.reload();
    }

    // MODIFIES: this
    // EFFECTS:  sets frame position to center of desktop
    private void centreOnScreen() {
        Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((desktopSize.width - getWidth()) / 2, (desktopSize.height - getHeight()) / 2);
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

        cp.scaleImage(image);

        cp = new CalendarPanel(this);
        ep = new EditorPanel(this);

        cp.scaleImage(image);

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

    public InformationPanel getInformationPanel() {
        return ip;
    }

    // Starts the app
    public static void main(String[] args) {
        new GraphicalOrganizationApp();
    }
}
