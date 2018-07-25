/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marte.swing.google.maps.samples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.TreeMap;
import marte.swing.google.maps.GoogleMapsScene;
import javafx.embed.swing.JFXPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import marte.swing.google.maps.models.LatLng;

/**
 * you can use "./html/maps.html", <br>
 * remenber to set YOUR_API_KEY on this html file, see line:<br>
 * src="https://maps.googleapis.com/maps/api/js?key=<b>YOUR_API_KEY</b>&callback=initMap"<br>
 * @author Marcio
 */
public class SimpleIteraction{
    private static int count_markers = 0;
    private static boolean started = false;
    public static void main(String[] args) throws InterruptedException {
        final GoogleMapsScene api = GoogleMapsScene.launch(new File("C://maps.html"), args);    
     
        JFrame frame = new JFrame("Google Maps JavaScript API on a swing JPanel");

        final JFXPanel fxPanel = new JFXPanel(){
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                g.setColor(Color.WHITE);
                g.fillRect(15, 55, 420, 90);
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
                g.drawString("INSTRUCTIONS:", 20, 70);
                g.drawString("0) status.started = "+started, 20, 95);
                g.drawString("1) click on any location to put a new marker", 20, 115);
                g.drawString("2) you can change to fullscreen/normal at any time", 20, 135);
            }
        };
        
        api.attach(fxPanel);
        
        api.addLoadListener((s)->{
            started = s;
            fxPanel.repaint();
        });
        
        TreeMap<String, LatLng> markers = new TreeMap<>();
        
        api.addClickListener((latLng)->{
            String key = ""+(count_markers++);
            api.addMarker(latLng.lat, latLng.lng, key, ""+markers.size());
            markers.put(key, latLng);
        }); 
        
        JButton btmSetFixedCenter = new JButton("Center");
        btmSetFixedCenter.addActionListener((event)->{
            api.setCenter(-27.573252, -48.537125);
        });
        
        JButton btmAddRandomMarker = new JButton("Marker");
        btmAddRandomMarker.addActionListener((event)->{
            double dlat = (1-Math.random()*2)/1000.0;
            double dlng = (1-Math.random()*2)/1000.0;
            double lat = -27.573252+dlat;
            double lng = -48.537125+dlng;
            String key = ""+(count_markers++);
            api.addMarker(lat, lng, key, null);
        });
        JButton btmAddRandomPoly = new JButton("Poly");
        btmAddRandomPoly.addActionListener((event)->{
            //Create a square in a random position
            double size = 0.0003;
            double dlat = (1-Math.random()*2)/1000.0;
            double dlng = (1-Math.random()*2)/1000.0;
            double lat = -27.573252+dlat;
            double lng = -48.537125+dlng;
            
            api.addPolygon("#FF0000", 0.8, 2, "#FF0000", 0.35, 
                new Point2D.Double(lat+size, lng+size),
                new Point2D.Double(lat+size, lng-size),
                new Point2D.Double(lat-size, lng-size),
                new Point2D.Double(lat-size, lng+size)
            );
        });
        JButton btmClearMarkers = new JButton("Clear Makers");
        btmClearMarkers.addActionListener((event)->{
            markers.forEach((key,v)->{
                api.delMarker(key);
            });
        });
        
        JButton btmSetFullScreen = new JButton("Full Screen");
        btmSetFullScreen.addActionListener((event)->{
            api.setFullScreen(frame, fxPanel);
        });
        
        frame.setLayout(new FlowLayout());
        frame.add(btmSetFixedCenter);
        frame.add(btmAddRandomMarker);
        frame.add(btmAddRandomPoly);
        frame.add(btmClearMarkers);
        frame.add(btmSetFullScreen);
        
        
        frame.add(fxPanel);
        
        frame.setSize(1300, 820);
        fxPanel.setPreferredSize(new Dimension(frame.getWidth()-30, frame.getHeight()-80));
        
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                fxPanel.setPreferredSize(new Dimension(frame.getWidth()-30, frame.getHeight()-80));
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
