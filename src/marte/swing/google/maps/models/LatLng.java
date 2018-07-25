/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marte.swing.google.maps.models;

/**
 *
 * @author Marcio
 */
public class LatLng {
    public final double lat;
    public final double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
    public LatLng(String lat, String lng) {
        this.lat = Double.parseDouble(lat);
        this.lng = Double.parseDouble(lng);
    }
    
}
