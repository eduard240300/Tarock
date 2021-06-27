package GUI.GameForm;

import GUI.Template.ClickableImage;
import GUI.Template.CustomJButton;
import GUI.Template.JImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.metal.MetalCheckBoxUI;
import javax.swing.plaf.metal.MetalRadioButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameForm extends JFrame{
    public static List<JLabel> playerLabels;
    public static List<JImage> cardsRound;
    public static List<ClickableImage> playerCards;
    public static List<JLabel> statusPlayers;
    public static List<JLabel> playerTurnsLabels;
    public static CustomJButton the1of2Button;
    public static CustomJButton passButton;
    public static CustomJButton cancelGameButton;
    public static JCheckBox popeAtFinishCheckBox;
    public static JCheckBox pagatAtFinishCheckBox;
    public static JCheckBox allPopesCheckBox;
    public static JCheckBox trulaCheckBox;
    public static JComboBox<String> numberOfTarocksComboBox;
    public static CustomJButton submitButton;
    public static JRadioButton heartRadio;
    public static JRadioButton diamondRadio;
    public static JRadioButton clubRadio;
    public static JRadioButton spadeRadio;
    public static String scoreTableData[][];
    public static String scoreTableHeader[];
    public static List<JLabel> declarationsPlayers;

    public GameForm() throws IOException {
        playerLabels = new ArrayList<JLabel>();
        cardsRound = new ArrayList<JImage>();
        playerCards = new ArrayList<ClickableImage>();
        statusPlayers = new ArrayList<JLabel>();
        playerTurnsLabels = new ArrayList<JLabel>();
        declarationsPlayers = new ArrayList<JLabel>();

        Font arialDefault = new Font("Arial", Font.BOLD, 16);
        Font arialBig = new Font("Arial", Font.BOLD, 19);
        setLayout(null);

        for(int i=0;i<4;i++)
        {
            playerLabels.add(new JLabel());
            playerLabels.get(i).setText("Player " + String.valueOf(i+1));
            playerLabels.get(i).setFont(arialDefault);
            add(playerLabels.get(i));

            cardsRound.add(new JImage(0, 100, 175));
            add(cardsRound.get(i));
        }

        playerLabels.get(0).setBounds(200, 10, 111, 20);
        playerLabels.get(1).setBounds(40, 110, 111, 20);
        playerLabels.get(2).setBounds(200, 230, 111, 20);
        playerLabels.get(3).setBounds(360, 110, 111, 20);

        cardsRound.get(0).setBounds(190, 40, 100, 180);
        cardsRound.get(1).setBounds(30, 140, 100, 180);
        cardsRound.get(2).setBounds(190, 260, 100, 180);
        cardsRound.get(3).setBounds(350, 140, 100, 180);

        for(int i=0;i<12;i++){
            playerCards.add(new ClickableImage(0, "GameForm",i+1,100,175));
            playerCards.get(i).setBounds(10+110*i, 460, 100, 180);
            add(playerCards.get(i));
        }

        JLabel statusLabel = new JLabel();
        statusLabel.setText("Status : ");
        statusLabel.setFont(arialDefault);
        statusLabel.setBounds(470, 10, 80, 19);
        add(statusLabel);

        for(int i=0;i<4;i++)
        {
            statusPlayers.add(new JLabel());
            statusPlayers.get(i).setText("Player " + String.valueOf(i+1) + " : ");
            statusPlayers.get(i).setFont(arialDefault);
            statusPlayers.get(i).setBounds(470, 30+i*20, 230, 19);
            add(statusPlayers.get(i));

            playerTurnsLabels.add(new JLabel());
            playerTurnsLabels.get(i).setText("Turn");
            playerTurnsLabels.get(i).setFont(arialDefault);
            playerTurnsLabels.get(i).setBounds(650, 30+i*20, 230, 19);
            add(playerTurnsLabels.get(i));
        }

        the1of2Button = new CustomJButton("1/2", arialDefault);
        the1of2Button.setBounds(470, 120, 94, 27);
        the1of2Button.setEnabled(false);
        add(the1of2Button);

        passButton = new CustomJButton("Pass", arialDefault);
        passButton.setBounds(570, 120, 94, 27);
        passButton.setEnabled(false);
        add(passButton);

        cancelGameButton = new CustomJButton("Cancel Game", arialDefault);
        cancelGameButton.setBounds(670, 120, 111, 27);
        cancelGameButton.setEnabled(false);
        add(cancelGameButton);

        JPanel declarationsPanel = new JPanel();
        declarationsPanel.setLayout(null);
        declarationsPanel.setBorder(BorderFactory.createTitledBorder("Declarations"));
        declarationsPanel.setBackground(new Color(78, 154, 6));
        declarationsPanel.setName("Declarations");
        declarationsPanel.setFont(arialDefault);
        declarationsPanel.setBounds(470, 160, 341, 241);
        add(declarationsPanel);

        popeAtFinishCheckBox = new JCheckBox();
        popeAtFinishCheckBox.setUI(new MetalCheckBoxUI() {
            protected Color getDisabledTextColor() {
                return popeAtFinishCheckBox.getForeground();
            }
            protected Icon getDisabledIcon() {
                return popeAtFinishCheckBox.getDisabledIcon();
            }
        });
        popeAtFinishCheckBox.setEnabled(false);
        popeAtFinishCheckBox.setBackground(new Color(78, 154, 6));
        popeAtFinishCheckBox.setFocusPainted(false);
        popeAtFinishCheckBox.setText("Pope at finish");
        popeAtFinishCheckBox.setFont(arialDefault);
        popeAtFinishCheckBox.setBounds(10, 20, 150, 25);
        declarationsPanel.add(popeAtFinishCheckBox);

        pagatAtFinishCheckBox = new JCheckBox();
        pagatAtFinishCheckBox.setUI(new MetalCheckBoxUI() {
            protected Color getDisabledTextColor() {
                return pagatAtFinishCheckBox.getForeground();
            }
            protected Icon getDisabledIcon() {
                return pagatAtFinishCheckBox.getDisabledIcon();
            }
        });
        pagatAtFinishCheckBox.setEnabled(false);
        pagatAtFinishCheckBox.setBackground(new Color(78, 154, 6));
        pagatAtFinishCheckBox.setFocusPainted(false);
        pagatAtFinishCheckBox.setText("Pagat at finish");
        pagatAtFinishCheckBox.setFont(arialDefault);
        pagatAtFinishCheckBox.setBounds(10, 50, 150, 25);
        declarationsPanel.add(pagatAtFinishCheckBox);

        allPopesCheckBox = new JCheckBox();
        allPopesCheckBox.setUI(new MetalCheckBoxUI() {
            protected Color getDisabledTextColor() {
                return allPopesCheckBox.getForeground();
            }
            protected Icon getDisabledIcon() {
                return allPopesCheckBox.getDisabledIcon();
            }
        });
        allPopesCheckBox.setEnabled(false);
        allPopesCheckBox.setBackground(new Color(78, 154, 6));
        allPopesCheckBox.setFocusPainted(false);
        allPopesCheckBox.setText("All popes");
        allPopesCheckBox.setFont(arialDefault);
        allPopesCheckBox.setBounds(10, 80, 150, 25);
        declarationsPanel.add(allPopesCheckBox);

        trulaCheckBox = new JCheckBox();
        trulaCheckBox.setUI(new MetalCheckBoxUI() {
            protected Color getDisabledTextColor() {
                return trulaCheckBox.getForeground();
            }
            protected Icon getDisabledIcon() {
                return trulaCheckBox.getDisabledIcon();
            }
        });
        trulaCheckBox.setEnabled(false);
        trulaCheckBox.setBackground(new Color(78, 154, 6));
        trulaCheckBox.setFocusPainted(false);
        trulaCheckBox.setText("Trula");
        trulaCheckBox.setFont(arialDefault);
        trulaCheckBox.setBounds(10, 110, 150, 25);
        declarationsPanel.add(trulaCheckBox);

        JLabel numberOfTarocksLabel = new JLabel();
        numberOfTarocksLabel.setText("No. Tarocks : ");
        numberOfTarocksLabel.setFont(arialDefault);
        numberOfTarocksLabel.setBounds(10, 145, 110, 31);
        declarationsPanel.add(numberOfTarocksLabel);

        numberOfTarocksComboBox = new JComboBox<String>();
        numberOfTarocksComboBox.setEnabled(false);
        numberOfTarocksComboBox.addItem("No");
        numberOfTarocksComboBox.addItem("8");
        numberOfTarocksComboBox.addItem("9");
        numberOfTarocksComboBox.addItem("10");
        numberOfTarocksComboBox.setFont(arialDefault);
        numberOfTarocksComboBox.setBounds(120, 145, 51, 31);
        declarationsPanel.add(numberOfTarocksComboBox);

        submitButton = new CustomJButton("Submit", arialDefault);
        submitButton.setEnabled(false);
        submitButton.setBounds(30, 190, 94, 27);
        declarationsPanel.add(submitButton);

        JPanel selectPopePanel = new JPanel();
        selectPopePanel.setLayout(null);
        selectPopePanel.setBorder(BorderFactory.createTitledBorder("Select Pope : "));
        selectPopePanel.setBackground(new Color(78, 154, 6));
        selectPopePanel.setName("Selected Pope");
        selectPopePanel.setFont(arialDefault);
        selectPopePanel.setBounds(180, 30, 151, 151);
        declarationsPanel.add(selectPopePanel);

        heartRadio = new JRadioButton();
        heartRadio.setUI(new MetalRadioButtonUI() {
            protected Color getDisabledTextColor() {
                return heartRadio.getForeground();
            }
            protected Icon getDisabledIcon() {
                return heartRadio.getDisabledIcon();
            }
        });
        heartRadio.setEnabled(false);
        heartRadio.setBackground(new Color(78, 154, 6));
        heartRadio.setFocusPainted(false);
        heartRadio.setText("Heart");
        heartRadio.setFont(arialDefault);
        heartRadio.setBounds(10, 20, 117, 25);
        selectPopePanel.add(heartRadio);

        diamondRadio = new JRadioButton();
        diamondRadio.setUI(new MetalRadioButtonUI() {
            protected Color getDisabledTextColor() {
                return diamondRadio.getForeground();
            }
            protected Icon getDisabledIcon() {
                return diamondRadio.getDisabledIcon();
            }
        });
        diamondRadio.setEnabled(false);
        diamondRadio.setBackground(new Color(78, 154, 6));
        diamondRadio.setFocusPainted(false);
        diamondRadio.setText("Diamond");
        diamondRadio.setFont(arialDefault);
        diamondRadio.setBounds(10, 50, 117, 25);
        selectPopePanel.add(diamondRadio);

        clubRadio = new JRadioButton();
        clubRadio.setUI(new MetalRadioButtonUI() {
            protected Color getDisabledTextColor() {
                return clubRadio.getForeground();
            }
            protected Icon getDisabledIcon() {
                return clubRadio.getDisabledIcon();
            }
        });
        clubRadio.setEnabled(false);
        clubRadio.setBackground(new Color(78, 154, 6));
        clubRadio.setFocusPainted(false);
        clubRadio.setText("Club");
        clubRadio.setFont(arialDefault);
        clubRadio.setBounds(10, 80, 117, 25);
        selectPopePanel.add(clubRadio);

        spadeRadio = new JRadioButton();
        spadeRadio.setUI(new MetalRadioButtonUI() {
            protected Color getDisabledTextColor() {
                return spadeRadio.getForeground();
            }
            protected Icon getDisabledIcon() {
                return spadeRadio.getDisabledIcon();
            }
        });
        spadeRadio.setEnabled(false);
        spadeRadio.setBackground(new Color(78, 154, 6));
        spadeRadio.setFocusPainted(false);
        spadeRadio.setText("Spade");
        spadeRadio.setFont(arialDefault);
        spadeRadio.setBounds(10, 110, 117, 25);
        selectPopePanel.add(spadeRadio);

        ButtonGroup popeButtonGroup = new ButtonGroup();
        popeButtonGroup.add(heartRadio);
        popeButtonGroup.add(diamondRadio);
        popeButtonGroup.add(clubRadio);
        popeButtonGroup.add(spadeRadio);

        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score : ");
        scoreLabel.setFont(arialBig);
        scoreLabel.setBounds(820, 10, 150, 19);
        add(scoreLabel);

        scoreTableData = new String[][]{};
        scoreTableHeader = new String[]{"Player 1", "Player 2", "Player 3", "Player 4", "Declared"};
        JTable scoreTable = new JTable(scoreTableData, scoreTableHeader);
        scoreTable.setRowHeight(scoreTable.getRowHeight() + 12);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0;i<5;i++)
            scoreTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        scoreTable.getTableHeader().setFont(arialDefault);
        scoreTable.setFont(arialDefault);
        JScrollPane scoreTableScrollPane = new JScrollPane(scoreTable);
        scoreTableScrollPane.setBounds(820, 40, 501, 281);
        add(scoreTableScrollPane);

        JLabel declarationsLabel = new JLabel();
        declarationsLabel.setText("Declarations : ");
        declarationsLabel.setFont(arialDefault);
        declarationsLabel.setBounds(830, 340, 150, 19);
        add(declarationsLabel);

        for(int i=0;i<4;i++)
        {
            declarationsPlayers.add(new JLabel());
            declarationsPlayers.get(i).setText("Player " + String.valueOf(i+1) + " : ");
            declarationsPlayers.get(i).setFont(arialDefault);
            declarationsPlayers.get(i).setBounds(830, 360+20*i, 150, 19);
            add(declarationsPlayers.get(i));
        }

        setSize(1330, 690);
        setTitle("Tarock Client : Game");
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);

        ControllerGameForm controllerGameForm = new ControllerGameForm();
    }
}
