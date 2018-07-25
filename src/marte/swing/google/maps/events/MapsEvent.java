/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marte.swing.google.maps.events;

/**
 *
 * @author Marcio
 */
public interface MapsEvent<T> {
    void handle(T event);
}
