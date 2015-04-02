/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */

package abinitgui.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Utils {

    private static DecimalFormat df = new DecimalFormat("#0.00");
    private static final Pattern DOS = Pattern.compile("\\r\\n", Pattern.MULTILINE);
    private static final Pattern UNIX = Pattern.compile("([^\\r])(\\n)", Pattern.MULTILINE);

    public static boolean mkdir(String dir) {
        File f = new File(dir);
        if (f.mkdir()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean exists(String dirOrFile) {
        File f = new File(dirOrFile);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static List<?> flatten(List<?> input) {
        List<Object> result = new ArrayList<>();

        for (Object o: input) {
            if (o instanceof List<?>) {
                result.addAll(flatten((List<?>) o));
            } else {
                result.add(o);
            }
        }

        return result;
    }



    public static String osName() {
        return System.getProperty("os.name");
    }

    public static String osArch() {
        return System.getProperty("os.arch");
    }

    public static String fileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String inByteFormat(double size) {
        double tmp = size / 1024;
        if (tmp < 1) {
            return df.format(tmp * 1024) + " B";
        } else {
            tmp /= 1024;
            if (tmp < 1) {
                return df.format(tmp * 1024) + " kB";
            } else {
                tmp /= 1024;
                if (tmp < 1) {
                    return df.format(tmp * 1024) + " MB";
                } else {
                    tmp /= 1024;
                    if (tmp < 1) {
                        return df.format(tmp * 1024) + " GB";
                    } else {
                        return df.format(tmp) + " TB";
                    }
                }
            }
        }
    }

    public static String formatDouble(double d) {
        return df.format(d);
    }

    public static String formatTime(double seconds) {
        return df.format(seconds) + " s";
    }

    public static String getLastToken(String str, String delim) {
        StringTokenizer strt = new StringTokenizer(str, delim);
        String token = null;
        String tmp;
        int nbt = strt.countTokens();
        for (int i = 0; strt.hasMoreTokens(); i++) {
            tmp = strt.nextToken();
            if (i == nbt - 1) {
                token = tmp;
            }
        }
        return token;
    }

    public static String dos2unix(String text) {
        return DOS.matcher(text).replaceAll("\n");
    }

    public static String unix2dos(String text) {
        return UNIX.matcher(text).replaceAll("$1\r\n");
    }

    public static void dos2unix(File file) {
        Scanner scanner;
        FileWriter fw;
        BufferedWriter bw;
        try {
            scanner = new Scanner(file).useDelimiter("\\Z");
            String fileContent = scanner.next();
            scanner.close();
            String result = DOS.matcher(fileContent).replaceAll("\n");

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(result);
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            MainFrame.printERR("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            MainFrame.printERR("IOException: " + e.getMessage());
        } catch (Exception e) {
            MainFrame.printERR("Exception: " + e.getMessage());
        }
    }

    public static void unix2dos(File file) {
        Scanner scanner;
        FileWriter fw;
        BufferedWriter bw;
        try {
            scanner = new Scanner(file).useDelimiter("\\Z");
            String fileContent = scanner.next();
            scanner.close();
            String result = UNIX.matcher(fileContent).replaceAll("$1\r\n");
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(result);
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            MainFrame.printERR("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            MainFrame.printERR("IOException: " + e.getMessage());
        } catch (Exception e) {
            MainFrame.printERR("Exception: " + e.getMessage());
        }
    }

    public static String fileToString(String fileName)
            throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String removeEndl(String str) {
        if (str.endsWith("\n")) {
            return (String) str.subSequence(0, str.lastIndexOf('\n'));
        } else {
            return str;
        }
    }

    public static String getCharset() {
        return "UTF-8";
    }
    
    // From stackoverflow
    public static void saveUrl(final String urlString, final String fileName)
        throws MalformedURLException, IOException 
    {
        try {

            HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){

                    public boolean verify(String hostname,
                            javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("gui.abinit.org")) {
                            return true;
                        }
                        return false;
                    }
                });
        } catch (Exception e) {

            e.printStackTrace();
        } 
    
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString.replace("https","http")).openStream());

            if(! Utils.exists(fileName))
                new File(new File(fileName).getParent()).mkdirs();
            fout = new FileOutputStream(fileName);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}
