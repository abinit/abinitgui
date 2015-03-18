/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

/**
 *
 * @author yannick
 */
public interface Exec {

    public RetMSG sendCommand(String CMD);
    public RetMSG sendCommand(String CMD[]);
    public void mkdir(String path);
    public void createTree(String path);

}
