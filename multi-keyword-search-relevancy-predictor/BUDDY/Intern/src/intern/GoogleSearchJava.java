/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intern;

/**
 *
 * @author Abhishek Jaiswal
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Student {
    String fname, lname, branch, email, phone, dob, college;
}


public class GoogleSearchJava {
 
    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    
    

    static String Probability(int score) {
        if(score >= 19) {
            return " very sure";
        }
        else if(score >= 10) {
            return " sure\n";
        }
        else if(score >= 6) {
            return " may be\n";
        }
        else if(score >= 1){
            return " can not say\n";
        }
        else {
            return " NO\n";
        }
    }
        
    
    
    
    public static void main(String[] args) throws IOException {
        //Taking search term input from console
        
        /*Properties systemProperties = System.getProperties();
        systemProperties.put("proxySet", "true");
        systemProperties.setProperty("http.proxyHost", "172.31.1.3");
        systemProperties.setProperty("http.proxyPort", "8080");*/
        
        HashMap<String, String> monthMap = new HashMap<String, String>();

        monthMap.put("1", "jan");
        monthMap.put("2", "feb");
        monthMap.put("3", "march");
        monthMap.put("4", "april");
        monthMap.put("5", "may");
        monthMap.put("6", "june");
        monthMap.put("7", "july");
        monthMap.put("8", "aug");
        monthMap.put("9", "sep");
        monthMap.put("10", "oct");
        monthMap.put("11", "nov");
        monthMap.put("12", "Dec");
        
        
        
        
        Student s = new Student();
        
        
        Scanner scanner = new Scanner(System.in);
        
        ArrayList<String> Links = new ArrayList<String>();
        
        //---------------------------------------------------------------------------------
        System.out.println("Enter Student Name");
        s.lname = scanner.nextLine().toLowerCase(); // stands for full name of student.
        
        
        //System.out.println("Enter Students Last Name");
        String arr[] = s.lname.split(" ", 2);
        
        s.fname = arr[0];
        
        /*System.out.println("Enter Students Branch");
        s.branch = scanner.nextLine().toLowerCase();*/
        
        System.out.println("Enter Students College Name");
        s.college = scanner.nextLine().toLowerCase();
        
        System.out.println("Enter Students email");
        s.email = scanner.nextLine().toLowerCase();
        
        System.out.println("Enter Students contact no..");
        s.phone = scanner.nextLine().toLowerCase();
        
        System.out.println("Enter Students DOB ex(24/2/1995) don't add a 0 before 2");
        s.dob = scanner.nextLine().toLowerCase();
        
        String temp = s.dob;
        
        String day = temp.substring(0, temp.indexOf("/")); temp = temp.substring(temp.indexOf("/") + 1);
        String month = temp.substring(0, temp.indexOf("/")); temp = temp.substring(temp.indexOf("/") + 1);
        String year = temp;
        
        //System.out.println(date + " " + month + " " + year);
        //------------------------------------------------------------------
        
        
        System.out.println("Please enter the search term.");
        String searchTerm = scanner.nextLine();
        System.out.println("Please enter the number of results. Example: 5 10 20");
        int num = scanner.nextInt();
        scanner.close();
         
        //System.out.println(searchTerm);
        
        String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
        //without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
         .get();
         
        FileWriter ans = new FileWriter("Result.txt");
        ans.write("FINAL OUTPUT-----------------------------------------------\n");
        
        Elements results = doc.select("h3.r > a");
 
        for (Element result : results) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
            
            Links.add(linkHref.substring(7, linkHref.indexOf("&")));
        }
        
        int i = 1;
        
        for(Iterator it = Links.iterator(); it.hasNext(); ) {
            String link = (String) it.next();
            int score = 0;
            if(link.contains("?"))
                continue;
            
            FileWriter fw = new FileWriter("link" + i++ + ".txt");
            
            fw.write("link grabbed :"+ link );
            fw.append("text begins ---------------------------------\n");
            
            ans.append("link grabbed :"+ link + "\n" );
            ans.append("text begins ---------------------------------\n");
            
            System.out.println("link grabbed :"+ link );
            
            
            URL url = new URL(link);
            
            
            
            try {
                System.out.println("text begins ---------------------------------\n");
                
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                String line = null;
                
                int checkCollege = 0, checkFname = 0, checkLname = 0, checkBranch = 0, checkPhone = 0, checkDob = 0, checkEmail = 0;
                
                try {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                
                
                while ((line = in.readLine()) != null) {
                    //System.out.println(line);
                    
                    line = line.toLowerCase(); // so that upper case letter do not effect matching
                    
                    if(line.contains(s.fname) && checkFname == 0) {
                        score += 2;
                        checkFname = 1;
                        System.out.println("first name matched -> " + s.fname);
                    }
                    
                    if(line.contains(s.college) && checkCollege == 0) {
                        score += 5;
                        checkCollege = 1;
                        System.out.println("college matched -> " + s.college);
                    }
                    
                    if(line.contains(s.lname) && checkLname == 0) {
                        score += 4;
                        checkLname = 1;
                        System.out.println("last name matched -> " + s.lname);
                    }
                    
                    /*if(line.contains(s.branch) && checkBranch == 0) {
                        score += 1;
                        checkBranch = 1;
                        System.out.println("branch name matched -> " + s.branch);
                    }*/
                    
                    if(line.contains(s.phone) && checkPhone == 0) {
                        score += 10;
                        checkPhone = 1;
                        System.out.println("phone matched -> " + s.phone);
                    }
                    
                    int index = line.indexOf(day);
                    while( index >= 0 && checkDob == 0) {
                        String forward = line.substring(index, Integer.min(line.length() - 1, index + 15));
                        String back = line.substring(Integer.max(0, index - 15), index);
                        
                        if ((forward.contains(month) && forward.contains(year)) || (back.contains(month) && back.contains(year))
                            || (forward.contains(monthMap.get(month)) && forward.contains(year)) || (back.contains(monthMap.get(month)) && back.contains(year))    ) {
                            score += 9;
                            checkDob = 1;
                        }
                        
                        index = line.indexOf(day, index + 1);
                    }
                        
                        
                    
                    if(line.contains(s.email) && checkEmail == 0) {
                        score += 10;
                        checkEmail = 1;
                        System.out.println("email matched -> " + s.email);
                    }
                    
                    fw.append(line);
                }
 
                System.out.println("-----------------------------------------------------------");
                ans.append("-----------------------------------------------------------\n");
                
                
                
                System.out.println(link + " score-> "+ score);
                ans.append(link + " score-> "+ score + "\n");
                
                System.out.printf("%s %s", link, Probability(score));
                ans.append(link + Probability(score) + "\n");
                
                
                
                System.out.println("\n\n");
                ans.append("\n\n");
                
                }catch(FileNotFoundException  foe) {}
                
            } catch (MalformedURLException mue) { } 
              catch (IOException ioe) { } 
            
            
            fw.close();
        
        }
        
        ans.close();
        
    }
 
}