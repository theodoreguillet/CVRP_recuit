package Graphics;
import Components.Client;
import Components.Route;
import TabuSearch.App;

import javax.swing.*;
import java.awt.*;


 import java.util.ArrayList;
 import java.util.List;
 import java.util.Random;


 public class InterfaceGraphique extends JPanel {
     private static int pointWidth = 20;

     List<Route> routes;
    List<Integer> xValues = new ArrayList<>();
    List<Integer> yValues = new ArrayList<>();

    private Color lineColor = new Color(0x080867);
    private Color clientColor = new Color(255, 0, 255);
    private Color gridColor = new Color(200, 200, 200);
    private Color depositColor = new Color(0xF10101);
    private List<Color> colors = new ArrayList<>();

    public InterfaceGraphique(List<Route> routes) {
        this.routes = routes;

        Color depositColor1 = new Color(0x0012AC);
        Color depositColor2 = new Color(0x55BE00);
        Color depositColor3 = new Color(0x9901F1);
        Color depositColor4 = new Color(0xFF5600);
        Color depositColor5 = new Color(0xFFD300);
        Color depositColor6 = new Color(0x00BDBD);
        Color depositColor7 = new Color(0x130000);
        Color depositColor8 = new Color(0x527312);
        Color depositColor9 = new Color(0xFF0808);
        Color depositColor10 = new Color(0x734822);
        Color depositColor11 = new Color(0x00E366);
        Color depositColor12 = new Color(0xFF00D1);
        Color depositColor13 = new Color(0xFF434900);

        colors.add(depositColor1);
        colors.add(depositColor2);
        colors.add(depositColor3);
        colors.add(depositColor4);
        colors.add(depositColor5);
        colors.add(depositColor6);
        colors.add(depositColor7);
        colors.add(depositColor8);
        colors.add(depositColor9);
        colors.add(depositColor10);
        colors.add(depositColor11);
        colors.add(depositColor12);
        colors.add(depositColor13);

    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(depositColor);
        graphics2D.fillRect(routes.get(0).getClientsXPos().get(0)*5 - pointWidth / 2, routes.get(0).getClientsYPos().get(0)*5- pointWidth / 2, pointWidth, pointWidth);
        for(Route route : routes) {
            graphics2D.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
            //graphics2D.setColor(colors.remove(0));
            xValues = route.getClientsXPos();
            yValues = route.getClientsYPos();
            if(xValues.size() > 2) {
                for(int i = 1; i < xValues.size()-1; i++){
                    graphics2D.fillOval(xValues.get(i)*5 - pointWidth / 2, yValues.get(i)*5- pointWidth / 2, pointWidth, pointWidth);
                }
                for(int i = 0; i < xValues.size()-1; i++) {
                    graphics2D.drawLine(xValues.get(i)*5, yValues.get(i)*5, xValues.get(i+1)*5, yValues.get(i+1)*5);
                }
            }

        }
    }

     public static void createAndShowGui(List<Route> routes) {
         /* Main panel */
         InterfaceGraphique mainPanel = new InterfaceGraphique(routes);
         mainPanel.setPreferredSize(new Dimension(700, 600));
         /* creating the frame */
         JFrame frame = new JFrame("Sample Graph");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(mainPanel);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
     }
}
