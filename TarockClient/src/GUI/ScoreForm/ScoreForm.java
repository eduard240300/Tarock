package GUI.ScoreForm;

import GUI.Template.CustomJButton;
import GUI.Template.JImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreForm extends JFrame{
    public static JLabel team1Label;
    public static JLabel team2Label;
    public static List<JImage> team1Cards;
    public static List<JImage> team2Cards;
    public static CustomJButton previousTeam1Button;
    public static CustomJButton nextTeam1Button;
    public static CustomJButton previousTeam2Button;
    public static CustomJButton nextTeam2Button;
    public static JLabel teamThatWonLabel;
    public static String[][] scoreTableData;
    public static String[] scoreTableHeader;
    public static CustomJButton OKButton;

    public ScoreForm() throws IOException {
        team1Cards = new ArrayList<JImage>();
        team2Cards = new ArrayList<JImage>();

        Font arialDefault = new Font("Arial", Font.BOLD, 16);
        Font arialBig = new Font("Arial", Font.BOLD, 19);
        setLayout(null);

        team1Label = new JLabel();
        team1Label.setText("Team 01 Cards : ");
        team1Label.setFont(arialBig);
        team1Label.setBounds(20, 20, 191, 31);
        add(team1Label);

        team2Label = new JLabel();
        team2Label.setText("Team 02 Cards : ");
        team2Label.setFont(arialBig);
        team2Label.setBounds(20, 260, 191, 31);
        add(team2Label);

        for(int i=0;i<6;i++) {
            team1Cards.add(new JImage(0, 100, 175));
            team1Cards.get(i).setBounds(80 + 110 * i, 60, 100, 180);
            add(team1Cards.get(i));

            team2Cards.add(new JImage(0, 100, 175));
            team2Cards.get(i).setBounds(80 + 110 * i, 300, 100, 180);
            add(team2Cards.get(i));
        }

        previousTeam1Button = new CustomJButton("<", arialDefault);
        previousTeam1Button.setBounds(10, 120, 60, 60);
        add(previousTeam1Button);

        nextTeam1Button = new CustomJButton(">", arialDefault);
        nextTeam1Button.setBounds(740, 120, 60, 60);
        add(nextTeam1Button);

        previousTeam2Button = new CustomJButton("<", arialDefault);
        previousTeam2Button.setBounds(10, 360, 60, 60);
        add(previousTeam2Button);

        nextTeam2Button = new CustomJButton(">", arialDefault);
        nextTeam2Button.setBounds(740, 360, 60, 60);
        add(nextTeam2Button);

        teamThatWonLabel = new JLabel();
        teamThatWonLabel.setText("Team that Won : ");
        teamThatWonLabel.setFont(arialBig);
        teamThatWonLabel.setBounds(820, 20, 501, 31);
        add(teamThatWonLabel);

        scoreTableData = new String[][]{
                {"1", "Game", "", "2"},
                {"2", "Pope at finish", "No", ""},
                {"3", "Pagat at finish", "No", ""},
                {"4", "All popes", "No", ""},
                {"5", "Trula", "No", ""},
                {"6", "Pope Caught", "No", ""},
                {"7", "Luna Caught", "No", ""},
                {"8", "Tarocks Player 01", "", ""},
                {"9", "Tarocks Player 02", "", ""},
                {"10", "Tarocks Player 03", "", ""},
                {"11", "Tarocks Player 04", "", ""},
                {"12", "Total", "", ""}
        };
        scoreTableHeader = new String[]{"", "Attribute", "Declared or Done", "Points"};
        JTable scoreTable = new JTable(scoreTableData, scoreTableHeader);
        scoreTable.setRowHeight(scoreTable.getRowHeight() + 12);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0;i<4;i++)
            scoreTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        scoreTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scoreTable.getColumnModel().getColumn(0).setPreferredWidth(27);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        scoreTable.getColumnModel().getColumn(2).setPreferredWidth(171);
        scoreTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        scoreTable.getTableHeader().setFont(arialDefault);
        scoreTable.setFont(arialDefault);
        JScrollPane scoreTableScrollPane = new JScrollPane(scoreTable);
        scoreTableScrollPane.setBounds(820, 50, 501, 362);
        add(scoreTableScrollPane);

        OKButton = new CustomJButton("OK", arialBig);
        OKButton.setBounds(965, 449, 211, 41);
        add(OKButton);

        setSize(1330, 550);
        setTitle("Tarock Client : Score");
        try
        {
            setIconImage(ImageIO.read(new FileInputStream("./src/Resources/icon.png")));
        }catch(Exception e){}
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        //setVisible(true);

        ControllerScoreForm controllerScoreForm = new ControllerScoreForm();
    }
}