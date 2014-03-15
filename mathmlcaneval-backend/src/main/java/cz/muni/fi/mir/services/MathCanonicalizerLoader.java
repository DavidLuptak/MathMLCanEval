/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Formula;
import java.io.IOException;

/**
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public interface MathCanonicalizerLoader
{

    /**
     * Method used for running given formula in given applicationRun. The execution of method is following:
     * <ul>
     * <li>we load jar file as URL</li>
     * <li>we create new URL Class loader and append file to it</li>
     * <li>we load main class from jar file which was specified when creating instance of this class</li>
     * <li>we obtain main method</li>
     * <li>we pass formula and configuration as arguments to main method</li>
     * <li>we invoke method</li>
     * <li>we collect result and create canonic output which is stored into formula</li>
     * <li>we delete temp files</li>
     * </ul>
     * @param formula to be canonicalized
     * @param applicationRun wrapper containing some information required for run
     * @throws IOException If any error occurs during execution
     */
    void execute(Formula formula, ApplicationRun applicationRun);
}
