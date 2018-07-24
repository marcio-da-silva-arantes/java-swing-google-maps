/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marte.swing.google.maps.samples;

import java.awt.FlowLayout;
import java.awt.geom.Point2D;
import java.io.File;
import marte.swing.google.maps.GoogleMapsScene;
import javafx.embed.swing.JFXPanel;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * you can use "./html/maps.html", <br>
 * remenber to set YOUR_API_KEY on this html file, see line:<br>
 * src="https://maps.googleapis.com/maps/api/js?key=<b>YOUR_API_KEY</b>&callback=initMap"<br>
 * @author Marcio
 */
public class SimpleIteraction{
    
    public static void main(String[] args) throws InterruptedException {
        final GoogleMapsScene api = GoogleMapsScene.launch(new File("C://maps.html"), args);    
     
        JFrame frame = new JFrame("Google Maps JavaScript API on a swing JPanel");

        final JFXPanel fxPanel = new JFXPanel();
        
        api.attach(fxPanel);
        
        JButton btmCenter = new JButton("Center");
        btmCenter.addActionListener((event)->{
            api.setCenter(-20.4444323, -45.4539065);
        });
        
        JButton btmMarker = new JButton("Marker");
        btmMarker.addActionListener((event)->{
            api.addMarker(-20.4444323, -45.4539065, "Hello World!");
        });
        JButton btmPoly = new JButton("Poly");
        btmPoly.addActionListener((event)->{
            api.addPolygon("#FF0000", 0.8, 2, "#FF0000", 0.35, 
                new Point2D.Double(-20.444729, -45.456241),
                new Point2D.Double(-20.444269, -45.455999),
                new Point2D.Double(-20.444448, -45.455571),
                new Point2D.Double(-20.444945, -45.455810)
            );
        });
        JButton btmEvent = new JButton("Event");
        btmEvent.addActionListener((event)->{
            api.event();
        });
        
        frame.setLayout(new FlowLayout());
        frame.add(btmCenter);
        frame.add(btmMarker);
        frame.add(btmPoly);
        frame.add(btmEvent);
        
        frame.add(fxPanel);
        frame.setSize(1300, 820);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
}
