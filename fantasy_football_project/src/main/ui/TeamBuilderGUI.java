package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * class that represents main window frame for GUI
 */
public class TeamBuilderGUI extends JFrame {
    private Team tb;
    private JInternalFrame controlPanel;
    private static final int WIDTH = 5000;
    private static final int HEIGHT = 4800;
    private JDesktopPane desktop;
    private JOptionPane optionPane;
    private JTextField textBoxName;
    private JTextField textBoxTeam;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/team.json";
    String[][] data = new String[6][6];

    public TeamBuilderGUI() {
        tb = new Team();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane();
        controlPanel = new JInternalFrame("Control Panel");
        controlPanel.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("CPSC 210 Project: Fantasy Football Team Builder");
        setSize(WIDTH, HEIGHT);

        addTextBox();
        addButtonPanel();
        addMenu();

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);

    }

    // MODIFIES: controlPanel
    // EFFECTS: Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // MODIFIES: controlPanel
    // EFFECTS: adds text boxes w/ label
    private void addTextBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
        textBoxName = new JTextField();
        JLabel teamLabel = new JLabel("Team:", SwingConstants.RIGHT);
        textBoxTeam = new JTextField();
        panel.add(nameLabel);
        panel.add(textBoxName);
        panel.add(teamLabel);
        panel.add(textBoxTeam);

        controlPanel.add(panel);
    }

    // EFFECTS: helper method for scaling images
    private ImageIcon scaledImageIcon(ImageIcon srcImg, int w, int h) {
        Image image = srcImg.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }

    // EFFECTS: helper to set JButton to specified icon image
    private JButton iconSetter(JButton button, ImageIcon icon) {
        button.setIcon(icon);
        return button;
    }

    // MODIFIES: controlPanel
    // EFFECTS: button panel containing add player of each type w/ image icons for each and save/load option
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));

        ImageIcon qbIcon = new ImageIcon("src/icons/qb.png");

        ImageIcon rbIcon = new ImageIcon("src/icons/rb.jpeg");

        ImageIcon wrIcon = new ImageIcon("src/icons/wr.jpeg");

        ImageIcon teIcon = new ImageIcon("src/icons/te.jpeg");

        ImageIcon kickerIcon = new ImageIcon("src/icons/Kicker.jpg");

        ImageIcon defIcon = new ImageIcon("src/icons/Def.jpg");

        JButton qbButton = iconSetter(new JButton(new AddQBAction()), qbIcon);
        JButton rbButton = iconSetter(new JButton(new AddRBAction()), rbIcon);
        JButton wrButton = iconSetter(new JButton(new AddWRAction()), wrIcon);
        JButton teButton = iconSetter(new JButton(new AddTEAction()), teIcon);
        JButton kickerButton = iconSetter(new JButton(new AddKickerAction()), kickerIcon);
        JButton defenseButton = iconSetter(new JButton(new AddDefenseAction()), defIcon);

        buttonPanel.add(qbButton);
        buttonPanel.add(rbButton);
        buttonPanel.add(wrButton);
        buttonPanel.add(teButton);
        buttonPanel.add(kickerButton);
        buttonPanel.add(defenseButton);
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new LoadAction()));
        buttonPanel.add(new JButton(new RemoveAction()));


        controlPanel.add(buttonPanel, BorderLayout.WEST);

    }

    // MODIFIES: controlPanel
    // EFFECTS: adds menu
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu playerMenu = new JMenu("player");
        playerMenu.setMnemonic('P');
        addMenuItem(playerMenu, new AddQBAction(), null);
        addMenuItem(playerMenu, new AddRBAction(), null);
        addMenuItem(playerMenu, new AddWRAction(), null);
        addMenuItem(playerMenu, new AddTEAction(), null);
        addMenuItem(playerMenu, new AddKickerAction(), null);
        addMenuItem(playerMenu, new AddDefenseAction(), null);
        menuBar.add(playerMenu);

        JMenu viewMenu = new JMenu("view");
        JMenu persistenceMenu = new JMenu("file");
        persistenceMenu.setMnemonic('F');
        addMenuItem(persistenceMenu, new SaveAction(), KeyStroke.getKeyStroke("control S"));
        addMenuItem(persistenceMenu, new SaveAction(), KeyStroke.getKeyStroke("control L"));
        menuBar.add(persistenceMenu);

        setJMenuBar(menuBar);
    }

    /** EFFECTS:
     * Adds an item with given handler to the given menu
     * @param theMenu  menu to which new item is added
     * @param action   handler for new menu item
     * @param accelerator    keystroke accelerator for this menu item
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // class for remove first player action
    private class RemoveAction extends AbstractAction {
        RemoveAction() {
            super("Remove first player");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            tb.removePlayer();
        }
    }



    // saves current team class
    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save team");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                jsonWriter.open();
                jsonWriter.write(tb);
                jsonWriter.close();

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // class to load previous team
    private class LoadAction extends AbstractAction {
        LoadAction() {
            super("Load team");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                tb = jsonReader.read();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // adds QB class
    private class AddQBAction extends AbstractAction {
        AddQBAction() {
            super("Add QB");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String name = textBoxName.getText();
            String team = textBoxTeam.getText();
            Player player = new QB(name, team);
            tb.addPlayer(player);
            textBoxName.setText("");
            textBoxTeam.setText("");

        }
    }

    // adds RB class
    private class AddRBAction extends AbstractAction {
        AddRBAction() {
            super("Add RB");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String name = textBoxName.getText();
            String team = textBoxTeam.getText();
            Player player = new RB(name, team);
            tb.addPlayer(player);
            textBoxName.setText("");
            textBoxTeam.setText("");

        }
    }

    // adds TE class
    private class AddTEAction extends AbstractAction {
        AddTEAction() {
            super("Add TE");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String name = textBoxName.getText();
            String team = textBoxTeam.getText();
            Player player = new TE(name, team);
            tb.addPlayer(player);
            textBoxName.setText("");
            textBoxTeam.setText("");

        }
    }

    // adds WR class
    private class AddWRAction extends AbstractAction {
        AddWRAction() {
            super("Add WR");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String name = textBoxName.getText();
            String team = textBoxTeam.getText();
            Player player = new WR(name, team);
            tb.addPlayer(player);
            textBoxName.setText("");
            textBoxTeam.setText("");

        }
    }

    // adds kicker class
    private class AddKickerAction extends AbstractAction {
        AddKickerAction() {
            super("Add Kicker");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String name = textBoxName.getText();
            String team = textBoxTeam.getText();
            Player player = new Kicker(name, team);
            tb.addPlayer(player);
            textBoxName.setText("");
            textBoxTeam.setText("");

        }
    }

    // adds defense class
    private class AddDefenseAction extends AbstractAction {
        AddDefenseAction() {
            super("Add Defense");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String team = textBoxTeam.getText();
            Player player = new Defense(team);
            tb.addPlayer(player);
            textBoxName.setText("");
            textBoxTeam.setText("");

        }
    }

    public static void main(String []args) {
        new TeamBuilderGUI();
    }
}
