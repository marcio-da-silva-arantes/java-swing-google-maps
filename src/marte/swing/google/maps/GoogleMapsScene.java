/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marte.swing.google.maps;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Marcio
 */
public class GoogleMapsScene extends Application{
    private static GoogleMapsScene singleton;
    private static File maps_html;
    private WebView browser;
    private WebEngine engine;
    private Scene scene;  


    public static GoogleMapsScene getInstance(){
        if(singleton==null){
            throw new IllegalArgumentException("You must call launch(file, args) first");
        }
        return singleton;
    }
    public static GoogleMapsScene launch(File maps_html, String... args) throws InterruptedException{
        if(singleton!=null){
            throw new IllegalArgumentException("You can have only one instance of this class, call without args getInstance()");
        }
        GoogleMapsScene.maps_html = maps_html.exists() ? maps_html : new File("./html/maps.html");
        //System.out.println(GoogleMapsScene.maps_html);
        Executors.newSingleThreadExecutor().execute(()->{
            Application.launch(GoogleMapsScene.class, args);
        });
        int time_out_count = 0;
        while(singleton==null && time_out_count<100){
            Thread.sleep(100);
            time_out_count++;
        }
        if(singleton==null){
            throw new IllegalStateException("Application.launch doesn't work");
        }
        //System.out.println("pass");
        return singleton;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        browser = new WebView();
        engine = browser.getEngine();
        engine.setJavaScriptEnabled(true);
        //System.out.println(this.maps_html);
        engine.load(maps_html.toURI().toString());
        scene = new Scene(browser);//,1200,720);
        singleton = this;
        //System.out.println("notify");
    }

    public void attach(JFXPanel fxPanel){
        fxPanel.setScene(scene);
    }
    
    
    public void addMarker(double lat, double lng, String title){
        Platform.runLater(()->{
            engine.executeScript(
                "var position = new google.maps.LatLng("+lat+","+lng+");\n" +
                "var marker = new google.maps.Marker({\n" +
                "    position: position,\n" +
                "    map: map,\n" +
                "    title: '"+title+"'\n" +
                "});"
            );
        });
            
    }
    public void addPolygon(String strokeColor, double strokeOpacity, double strokeWeight, String fillColor, double fillOpacity, Point2D... points){
        Platform.runLater(()->{
            String coords = "var coords = [\n"+Arrays.stream(points).map(p->"{lat: "+p.getX()+", lng: "+p.getY()+"}").reduce("", (a, c)->a+",\n\t"+c).substring(2)+"];\n";
            System.out.println(coords);
            engine.executeScript(
                coords+
                "var poly = new google.maps.Polygon({\n" +
                "   paths: coords,\n" +
                "   strokeColor: '"+strokeColor+"',\n" +
                "   strokeOpacity: "+strokeOpacity+",\n" +
                "   strokeWeight: "+strokeWeight+",\n" +
                "   fillColor: '"+fillColor+"',\n" +
                "   fillOpacity: "+fillOpacity+"\n" +
                "});\n" +
                "poly.setMap(map);"
            );
            
        });
    }
    public void setCenter(double lat, double lng){
        Platform.runLater(()->{
            engine.executeScript("map.setCenter({lat: "+lat+", lng: "+lng+"})");
        });
    }
    public void event(){
        Platform.runLater(()->{
            Object obj = engine.executeScript("event.latLng;");
            System.out.println("obj = "+obj.toString());
        });
    }
}
