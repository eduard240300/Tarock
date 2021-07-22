package com.Tarock.Client.GUI.ScoreForm;

import com.Tarock.Client.GUI.Template.CustomJButton;
import com.Tarock.Client.GUI.Template.JImage;
import com.Tarock.Client.GUI.Template.MyWindowListener;
import com.Tarock.Client.GUI.Template.RowTable;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Client.Service.DataManipulationService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreForm extends JFrame{
    public static Font arialDefault = new Font("Arial", Font.BOLD, 16);
    public static Font arialBig = new Font("Arial", Font.BOLD, 19);
    public static int sizeX = 1330;
    public static int sizeY = 550;
    public static int offset1 = 0;
    public static int offset2 = 0;
    public static JLabel team1Label;
    public static JLabel team2Label;
    public static List<JImage> team1Cards = new ArrayList<>();
    public static List<JImage> team2Cards = new ArrayList<>();
    public static CustomJButton previousTeam1Button;
    public static CustomJButton nextTeam1Button;
    public static CustomJButton previousTeam2Button;
    public static CustomJButton nextTeam2Button;
    public static JLabel teamThatWonLabel;
    public static DefaultTableModel scoreTableModel;
    public static RowTable scoreTable;
    public static CustomJButton OKButton;

    public static void resetPlayerNames() {
        List<String> players = Repository.players;
        for(int i=7;i<11;i++)
        {
            scoreTable.setValueAt("Tarocks " + players.get(i-7), i, 1);
        }
    }
    public static void updateCards() throws IOException {
        for(int i=0;i<6;i++)
        {
            if ((i+offset1) < Repository.cardsWon.get(0).size())
                team1Cards.get(i).setCardID(Repository.cardsWon.get(0).get(i+offset1));
            else
                team1Cards.get(i).setCardID(0);
        }
        for(int i=0;i<6;i++)
        {
            if ((i+offset2) < Repository.cardsWon.get(1).size())
                team2Cards.get(i).setCardID(Repository.cardsWon.get(1).get(i+offset2));
            else
                team2Cards.get(i).setCardID(0);
        }
    }
    public static void updateTable(List<String> declaredOrDone, List<Integer> points) {
        for(int i=0;i<12;i++)
        {
            scoreTable.setValueAt(declaredOrDone.get(i), i, 2);
            scoreTable.setValueAt(points.get(i), i, 3);
        }
    }
    public static void updateTeamThatWon(String teamThatWon) {
        teamThatWonLabel.setText("Team that won : " + teamThatWon);
    }

    public void initTeam1Label() {
        team1Label = new JLabel();
        team1Label.setText("Team 01 Cards : ");
        team1Label.setFont(arialBig);
        team1Label.setBounds(20, 20, 191, 31);
        add(team1Label);
    }
    public void initTeam2Label() {
        team2Label = new JLabel();
        team2Label.setText("Team 02 Cards : ");
        team2Label.setFont(arialBig);
        team2Label.setBounds(20, 260, 191, 31);
        add(team2Label);
    }
    public void initTeam1Cards() {
        for(int i=0;i<6;i++) {
            team1Cards.add(new JImage(0));
            team1Cards.get(i).setBounds(80 + 110 * i, 60, 100, 180);
            add(team1Cards.get(i));
        }
    }
    public void initTeam2Cards() {
        for(int i=0;i<6;i++) {
            team2Cards.add(new JImage(0));
            team2Cards.get(i).setBounds(80 + 110 * i, 300, 100, 180);
            add(team2Cards.get(i));
        }
    }

    public void initPreviousTeam1Button() {
        previousTeam1Button = new CustomJButton("<", arialDefault);
        previousTeam1Button.setBounds(10, 120, 60, 60);
        previousTeam1Button.setEnabled(false);
        add(previousTeam1Button);
    }
    public void initNextTeam1Button() {
        nextTeam1Button = new CustomJButton(">", arialDefault);
        nextTeam1Button.setBounds(740, 120, 60, 60);
        add(nextTeam1Button);
    }
    public void initPreviousTeam2Button() {
        previousTeam2Button = new CustomJButton("<", arialDefault);
        previousTeam2Button.setBounds(10, 360, 60, 60);
        previousTeam2Button.setEnabled(false);
        add(previousTeam2Button);
    }
    public void initNextTeam2Button() {
        nextTeam2Button = new CustomJButton(">", arialDefault);
        nextTeam2Button.setBounds(740, 360, 60, 60);
        add(nextTeam2Button);
    }

    public void initTeamThatWonLabel() {
        teamThatWonLabel = new JLabel();
        teamThatWonLabel.setText("Team that Won : ");
        teamThatWonLabel.setFont(arialBig);
        teamThatWonLabel.setBounds(820, 20, 600, 31);
        add(teamThatWonLabel);
    }
    public void initScoreTable() {
        scoreTableModel = new DefaultTableModel();
        scoreTable = new RowTable(scoreTableModel);
        scoreTable.setRowHeight(scoreTable.getRowHeight() + 12);
        scoreTable.getTableHeader().setFont(arialDefault);
        scoreTable.setFont(arialDefault);

        JScrollPane scoreTableScrollPane = new JScrollPane(scoreTable);
        scoreTableScrollPane.setBounds(820, 50, 501, 362);
        add(scoreTableScrollPane);

        scoreTableModel.addColumn("");
        scoreTableModel.addColumn("Attribute");
        scoreTableModel.addColumn("Declared or done");
        scoreTableModel.addColumn("Points");

        scoreTable.getColumnModel().getColumn(0).setPreferredWidth(27);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(163);
        scoreTable.getColumnModel().getColumn(2).setPreferredWidth(255);
        scoreTable.getColumnModel().getColumn(3).setPreferredWidth(53);

        scoreTableModel.addRow(new String[]{"1", "Game", "", ""});
        scoreTableModel.addRow(new String[]{"2", "Pope at finish", "", ""});
        scoreTableModel.addRow(new String[]{"3", "Pagat at finish", "", ""});
        scoreTableModel.addRow(new String[]{"4", "All popes", "", ""});
        scoreTableModel.addRow(new String[]{"5", "All trull cards", "", ""});
        scoreTableModel.addRow(new String[]{"6", "Pope Caught", "", ""});
        scoreTableModel.addRow(new String[]{"7", "Luna Caught", "", ""});
        scoreTableModel.addRow(new String[]{"8", "Tarocks Player 01", "", ""});
        scoreTableModel.addRow(new String[]{"9", "Tarocks Player 02", "", ""});
        scoreTableModel.addRow(new String[]{"10", "Tarocks Player 03", "", ""});
        scoreTableModel.addRow(new String[]{"11", "Tarocks Player 04", "", ""});
        scoreTableModel.addRow(new String[]{"12", "Total", "", ""});

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0;i<4;i++)
            scoreTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    public void initOKButton() {
        OKButton = new CustomJButton("OK", arialBig);
        OKButton.setBounds(965, 449, 211, 41);
        add(OKButton);
    }

    public ScoreForm() {
        setLayout(null);

        initTeam1Label();
        initTeam2Label();
        initTeam1Cards();
        initTeam2Cards();

        initPreviousTeam1Button();
        initNextTeam1Button();
        initPreviousTeam2Button();
        initNextTeam2Button();

        initTeamThatWonLabel();
        initScoreTable();
        initOKButton();

        if (System.getProperty("os.name").equals("Windows 10"))
            sizeX += 18;
        setSize(sizeX, sizeY);
        setTitle("Tarock Client : Score");

        getContentPane().setBackground(DataManipulationService.backgroundColor);
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        setResizable(false);
        //setVisible(true);

        new ControllerScoreForm();
    }
}