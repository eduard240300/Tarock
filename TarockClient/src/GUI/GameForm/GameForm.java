package GUI.GameForm;

import GUI.ScoreForm.ScoreForm;
import GUI.Template.*;
import Repository.Repository;

import javax.swing.*;
import javax.swing.plaf.metal.MetalCheckBoxUI;
import javax.swing.plaf.metal.MetalRadioButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;
import GUI.GameForm.*;
import Service.DataManipulationService;

public class GameForm extends JFrame{
    public static int heartCode = 0x2665;
    public static int diamondCode = 0x2666;
    public static int clubCode = 0x2663;
    public static int spadeCode = 0x2660;
    public static int the1of2 = 1;
    public static String typeOfActivation = "";
    public static boolean canGiveCard = false;
    public static List<JLabel> playerLabels;
    public static List<JImage> cardsRound;
    public static List<ClickableImage> playerCards;
    public static List<JLabel> statusPlayers;
    public static List<JLabel> playerTurnsLabels;
    public static List<JLabel> teamsLabels;
    public static CustomJButton the1of2Button;
    public static CustomJButton passButton;
    public static CustomJButton cancelGameButton;
    public static JCheckBox popeAtFinishCheckBox;
    public static JCheckBox pagatAtFinishCheckBox;
    public static JCheckBox allPopesCheckBox;
    public static JCheckBox trullCheckBox;
    public static JComboBox<String> numberOfTarocksComboBox;
    public static CustomJButton submitButton;
    public static ButtonGroup popeButtonGroup;
    public static JRadioButton heartRadio;
    public static JRadioButton diamondRadio;
    public static JRadioButton clubRadio;
    public static JRadioButton spadeRadio;
    public static DefaultTableModel scoreTableModel;
    public static RowTable scoreTable;
    public static List<JLabel> declarationsPlayers;
    public static List<JLabel> declarationsTurnsLabels;
    public static JLabel previousRoundWonByLabel;
    public static Font arialDefault;
    public static Font arialChair;
    public static Font arialBig;

    public static void popUpMessage(String message)
    {
        showMessageDialog(null, message);
    }

    public void updateCard(int chair, int cardID) throws IOException {
        cardsRound.get(chair).setCardID(cardID);
    }

    public void resetCards() throws IOException {
        for(int i=0;i<4;i++)
            updateCard(i, 0);
    }

    public void resetPlayerNames()
    {
        for(int i=0;i<4;i++)
        {
            playerLabels.get(i).setText((i + 1) + ". " + Repository.players.get(i) + " : ");
            statusPlayers.get(i).setText(Repository.players.get(i) + " : ");

            declarationsPlayers.get(i).setText(Repository.players.get(i) + " : ");
        }
        if (scoreTableModel.getColumnCount() == 0)
        {
            for(int i=0;i<4;i++)
            {
                scoreTableModel.addColumn(Repository.players.get(i));
            }
            scoreTableModel.addColumn("Declaration");
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            for(int i=0;i<5;i++)
                scoreTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            scoreTable.getColumnModel().getColumn(0).setPreferredWidth(70);
            scoreTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            scoreTable.getColumnModel().getColumn(2).setPreferredWidth(70);
            scoreTable.getColumnModel().getColumn(3).setPreferredWidth(70);
            scoreTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        }
    }

    public void addToScoreTable(int score1, int score2, int score3, int score4, String declaration)
    {
        StringBuilder declarationBuilder = new StringBuilder(declaration);
        String stringScore1 = String.valueOf(score1);
        String stringScore2 = String.valueOf(score2);
        String stringScore3 = String.valueOf(score3);
        String stringScore4 = String.valueOf(score4);
        if (declaration.charAt(4) == '0')
            declarationBuilder.setCharAt(4, (char)(heartCode));
        else if (declaration.charAt(4) == '1')
            declarationBuilder.setCharAt(4, (char)(diamondCode));
        else if (declaration.charAt(4) == '2')
            declarationBuilder.setCharAt(4, (char)(clubCode));
        else if (declaration.charAt(4) == '3')
            declarationBuilder.setCharAt(4, (char)(spadeCode));
        declaration = declarationBuilder.toString();
        scoreTableModel.addRow(new Object[]{stringScore1, stringScore2, stringScore3, stringScore4, declaration});
    }

    public void updateCards() throws IOException {
        for(int i=0;i<Repository.cards.size();i++)
            playerCards.get(i).setCardID(Repository.cards.get(i));
        for(int i=Repository.cards.size();i<12;i++)
            playerCards.get(i).setCardID(0);
    }

    public void updateChair()
    {
        playerLabels.get(Repository.chair).setFont(arialChair);
    }

    public void reset1of2()
    {
        the1of2Button.setText(the1of2 + "/2");
    }

    public void increase1of2()
    {
        the1of2++;
        the1of2Button.setText(the1of2 + "/2");
    }

    public void resetTeams()
    {
        for(int i=0;i<4;i++)
        {
            teamsLabels.get(i).setText("");
        }
    }

    public void showTeams()
    {
        for(int i=0;i<Repository.teams.get(0).size();i++)
        {
            teamsLabels.get(Repository.teams.get(0).get(i)).setText("Team 1");
        }
        for(int i=0;i<Repository.teams.get(1).size();i++)
        {
            teamsLabels.get(Repository.teams.get(1).get(i)).setText("Team 2");
        }
    }

    public void updateNextStatus(int requestedChair, String type)
    {
        List<JLabel> updateList = null;
        if (type.equals("Status"))
            updateList = playerTurnsLabels;
        else if (type.equals("Declaration"))
            updateList = declarationsTurnsLabels;
        if (requestedChair == -1)
        {
            for(int i=0;i<4;i++)
            {
                updateList.get(i).setText("");
            }
        }
        else{
            updateList.get(requestedChair).setText("Next");
            for(int i=0;i<4;i++)
            {
                if (i != requestedChair)
                    updateList.get(i).setText("");
            }
        }
    }

    public void changeStatusPlayer(int player, String status) {
        String text = Repository.players.get(player) + " : ";
        if (status.equals("1/2"))
            text = text + the1of2 + "/2";
        else if (status.equals("pass"))
            text = text + "Pass";
        statusPlayers.get(player).setText(text);
    }

    public void changeDeclarationPlayer(int player, String declaration) {
        StringBuilder declarationBuilder = new StringBuilder(declaration);

        if (declaration.charAt(1) == '0')
            declarationBuilder.setCharAt(1, (char)(heartCode));
        else if (declaration.charAt(1) == '1')
            declarationBuilder.setCharAt(1, (char)(diamondCode));
        else if (declaration.charAt(1) == '2')
            declarationBuilder.setCharAt(1, (char)(clubCode));
        else if (declaration.charAt(1) == '3')
            declarationBuilder.setCharAt(1, (char)(spadeCode));

        String text = Repository.players.get(player) + " : ";
        text = text + declarationBuilder.toString();
        declarationsPlayers.get(player).setText(text);
    }

    public void resetDeclarationSelection() {
        popeAtFinishCheckBox.setSelected(false);
        pagatAtFinishCheckBox.setSelected(false);
        allPopesCheckBox.setSelected(false);
        trullCheckBox.setSelected(false);
        numberOfTarocksComboBox.setSelectedIndex(0);
        popeButtonGroup.clearSelection();
    }

    public void switchDeclarationSelection(boolean canPopeAtFinish, boolean canPagatAtFinish, boolean canAllPopes, boolean canTrull, boolean isRequestPlayer, boolean value) {
        if (value)
            typeOfActivation = "Declaration";
        else
            typeOfActivation = "";
        if (canPopeAtFinish)
            popeAtFinishCheckBox.setEnabled(value);
        if (canPagatAtFinish)
            pagatAtFinishCheckBox.setEnabled(value);
        if (canAllPopes)
            allPopesCheckBox.setEnabled(value);
        if (canTrull)
            trullCheckBox.setEnabled(value);
        submitButton.setEnabled(value);
        numberOfTarocksComboBox.setEnabled(value);
    }

    public void switchPopeSelection(boolean value) {
        if (value)
            typeOfActivation = "Pope";
        else
            typeOfActivation = "";
        heartRadio.setEnabled(value);
        diamondRadio.setEnabled(value);
        clubRadio.setEnabled(value);
        spadeRadio.setEnabled(value);
        submitButton.setEnabled(value);
    }

    public void updatePreviousRoundWonByLabel(String text)
    {
        if (text == "")
            previousRoundWonByLabel.setText("");
        else
            previousRoundWonByLabel.setText("Previous round won by : " + text);
    }

    public GameForm() throws IOException {
        playerLabels = new ArrayList<JLabel>();
        cardsRound = new ArrayList<JImage>();
        playerCards = new ArrayList<ClickableImage>();
        statusPlayers = new ArrayList<JLabel>();
        playerTurnsLabels = new ArrayList<JLabel>();
        teamsLabels = new ArrayList<JLabel>();
        declarationsPlayers = new ArrayList<JLabel>();
        declarationsTurnsLabels = new ArrayList<JLabel>();

        arialDefault = new Font("Arial", Font.BOLD, 16);

        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        arialChair = new Font("Arial",Font.BOLD, 16).deriveFont(fontAttributes);

        arialBig = new Font("Arial", Font.BOLD, 19);
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
            playerCards.add(new ClickableImage(0, "GameForm",i,100,175));
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
            playerTurnsLabels.get(i).setText("");
            playerTurnsLabels.get(i).setFont(arialDefault);
            playerTurnsLabels.get(i).setBounds(650, 30+i*20, 230, 19);
            add(playerTurnsLabels.get(i));

            teamsLabels.add(new JLabel());
            teamsLabels.get(i).setText("");
            teamsLabels.get(i).setFont(arialDefault);
            teamsLabels.get(i).setBounds(720, 30+i*20, 230, 19);
            add(teamsLabels.get(i));
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

        trullCheckBox = new JCheckBox();
        trullCheckBox.setUI(new MetalCheckBoxUI() {
            protected Color getDisabledTextColor() {
                return trullCheckBox.getForeground();
            }
            protected Icon getDisabledIcon() {
                return trullCheckBox.getDisabledIcon();
            }
        });
        trullCheckBox.setEnabled(false);
        trullCheckBox.setBackground(new Color(78, 154, 6));
        trullCheckBox.setFocusPainted(false);
        trullCheckBox.setText("All trull cards");
        trullCheckBox.setFont(arialDefault);
        trullCheckBox.setBounds(10, 110, 150, 25);
        declarationsPanel.add(trullCheckBox);

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
        String text = "Heart (" + Character.toString((char)(heartCode)) + ")";
        heartRadio.setText(text);
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
        text = "Diamond (" + Character.toString((char)(diamondCode)) + ")";
        diamondRadio.setText(text);
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
        text = "Club (" + Character.toString((char)(clubCode)) + ")";
        clubRadio.setText(text);
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
        text = "Spade (" + Character.toString((char)(spadeCode)) + ")";
        spadeRadio.setText(text);
        spadeRadio.setFont(arialDefault);
        spadeRadio.setBounds(10, 110, 117, 25);
        selectPopePanel.add(spadeRadio);

        popeButtonGroup = new ButtonGroup();
        popeButtonGroup.add(heartRadio);
        popeButtonGroup.add(diamondRadio);
        popeButtonGroup.add(clubRadio);
        popeButtonGroup.add(spadeRadio);

        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score : ");
        scoreLabel.setFont(arialBig);
        scoreLabel.setBounds(820, 10, 150, 19);
        add(scoreLabel);

        scoreTableModel = new DefaultTableModel();
        scoreTable = new RowTable(scoreTableModel);
        scoreTable.setRowHeight(scoreTable.getRowHeight() + 12);
        scoreTable.getTableHeader().setFont(arialDefault);
        scoreTable.setFont(arialDefault);
        JScrollPane scoreTableScrollPane = new JScrollPane(scoreTable);
        scoreTableScrollPane.setBounds(820, 40, 500, 281);
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
            declarationsPlayers.get(i).setBounds(830, 360+20*i, 400, 19);
            add(declarationsPlayers.get(i));

            declarationsTurnsLabels.add(new JLabel());
            declarationsTurnsLabels.get(i).setText("");
            declarationsTurnsLabels.get(i).setFont(arialDefault);
            declarationsTurnsLabels.get(i).setBounds(1150, 360+i*20, 230, 19);
            add(declarationsTurnsLabels.get(i));
        }

        previousRoundWonByLabel = new JLabel();
        previousRoundWonByLabel.setText("");
        previousRoundWonByLabel.setFont(arialDefault);
        previousRoundWonByLabel.setBounds(470, 420, 400, 19);
        add(previousRoundWonByLabel);

        if (System.getProperty("os.name").equals("Linux"))
            setSize(1330, 690);
        else if (System.getProperty("os.name").equals("Windows 10"))
            setSize(1348, 690);
        setTitle("Tarock Client : Game");
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        //setVisible(true);

        ControllerGameForm controllerGameForm = new ControllerGameForm();
    }
}
