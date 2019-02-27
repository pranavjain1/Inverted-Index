import java.util.*;
import java.io.*;

/**
 * 612 LAB 1 InvertedIndex
 * Purpose :  This program consists of three major tasks:
 *             • Processing a number of text documents to generate indexed terms.
 *             • Building an inverted index for the terms and documents, and
 *             • Formulate and process queries using the constructed inverted index.
 * @author : Pranav Jain
 * Date    : 17-feb-2019
 */

  public class Labfinal {              
   //attributes
   private String[] myDocs;               //input docs
   private static ArrayList<String> termList;    //dictionary
   private ArrayList<ArrayList<Integer>> docLists;   //posting lists    
   private ArrayList<Integer> docList;                  
   
    /**
     * Parameterized constructor for initialization and constructing inverted index for words
     *
     * @param docs : Data of all the .txt files in the folder 
     */

  public Labfinal(String[] docs) {            
     myDocs = docs;
     termList = new ArrayList<String>();          
     docLists = new ArrayList<ArrayList<Integer>>();
     docList = new ArrayList<Integer>();
     
     for(int i=0;i<myDocs.length;i++) {
      String[] words = myDocs[i].split(" ");
      for(String word:words) {
         if(!termList.contains(word)) {
            termList.add(word);           //adding the words of file to the termList
          
            docList = new ArrayList<Integer>();
            docList.add(new Integer(i));
            
            docLists.add(docList);                    
          }
         else {
            int index =termList.indexOf(word);
            
            docList = docLists.get(index);
           
            if(!docList.contains(new Integer(i))) {
               docList.add(new Integer(i));
               docLists.set(index, docList);       //setting the posting lists of the words with their file location.
            }
         }
      }
    }
//          System.out.println(termList);         //uncomment to see termList of words
//          System.out.println(docLists);         //uncomment to see posting lists of words
  }
   
    /**
     * Checks in posting lists, if the searched keyword query is present and returns its index from posting lists.
     *
     * @param keywordsinglequery                 Keyword to be searched
     * @return docLists.get(index)               Index of posting list of the keyword to be searched 
     * index :                                   Index of keyword from the termList
     */
     
     public ArrayList<Integer> search(String keywordsinglequery) {
 
         int index = termList.indexOf(keywordsinglequery);

            if(index >= 0) {
               return docLists.get(index);
        }
         else return null;
    }
    
    /**
     * Checks in posting lists, if the searched two words are present in the file using AND operation and returns its index from posting lists.
     *
     * @param keywordfirst,                               Keyword1 to be searched
     * @param keywordsecond                               Keyword2 to be searched
     * result1, result2                                   Storing the index of Keywords to be searched using search method in their posting lists
     * @return result3                                    Result of AND operation on the keywords
     */

    public ArrayList<Integer> searchAND(String keywordfirst, String keywordsecond) {
           
            ArrayList<Integer> result1 = search(keywordfirst);
            ArrayList<Integer> result2 = search(keywordsecond);
            ArrayList<Integer> result3 = new ArrayList<Integer>();
        if(!(result1 == null) && !(result2 == null)){      
           for(Integer i:result1) {
            
               for(Integer j:result2) {
            //   System.out.println("Found in : "+i+j);
                 if(i.equals(j)){
                  result3.add(i);
             }
          }               
        } 
      } else {  
          System.out.println("Not found in any document."); 
    }
        return result3;
    }
    
    /**
     * Checks in posting lists, if the searched two words are present in the file using OR operation and returns its index from posting lists.
     *
     * @param keywordfirst                                Keyword1 to be searched
     * @param keywordsecond                               Keyword2 to be searched
     * result1, result2                                   Storing the index of Keywords to be searched using search method in their posting lists
     * @return result3                                    Result of OR operation on the keywords
     */
     
    public ArrayList<Integer> searchOR(String keywordfirst, String keywordsecond) {
           
            ArrayList<Integer> result1 = search(keywordfirst);
            ArrayList<Integer> result2 = search(keywordsecond);
            ArrayList<Integer> result3 = new ArrayList<Integer>();
    
          if(!(result1 == null)){ 
           for(Integer i:result1) {
             result3.add(i);
             }
          }  
            if(!(result2 == null)){
             for(Integer j:result2) {
             if(!(result3.contains(j))){
             result3.add(j);
             }  
          }       
       } 
            if((result1 == null) && (result2 == null)){    
              System.out.println("Not found in any document."); 
    }
            return result3;  
  }
  
   /**
     * Checks in posting lists, if the searched three words are present in the file using AND operation and returns its index from posting lists.
     * This method is optimized to process the shorter posting lists first.
     * @param keywordfirst                                              Keyword1 to be searched
     * @param keywordsecond                                             Keyword2 to be searched
     * @param keywordthird                                              Keyword3 to be searched
     * result1, result2, result3                                        Storing the index of Keywords to be searched using search method in their posting lists
     * @return result4                                                  Result of AND operation on the keywords
     * pLL1, pLL2, pLL3                                                 Storing index of posting lists of word to compare their sizes.
     */
     
      public ArrayList<Integer> searchAND3(String keywordfirst, String keywordsecond, String keywordthird) {
   
           ArrayList<Integer> result1 = new ArrayList<Integer>();  
           ArrayList<Integer> result2 = new ArrayList<Integer>();
           ArrayList<Integer> result3 = new ArrayList<Integer>();
                       
           if(!(search(keywordfirst) == null) && !(search(keywordfirst) == null) && !(search(keywordfirst) == null)){    
           

           int pLL1 = ((docLists.get(termList.indexOf(keywordfirst))).size());  
           int pLL2 = ((docLists.get(termList.indexOf(keywordsecond))).size());    
           int pLL3 = ((docLists.get(termList.indexOf(keywordthird))).size());     
            

                        
            if(pLL1 <= pLL2 && pLL1 <= pLL3 && pLL2 <= pLL3){      
                  result1 = search(keywordfirst);   
                  result2 = search(keywordsecond); 
                  result3 = search(keywordthird); 
            }  else if(pLL1 <= pLL2 && pLL1 <= pLL3 && pLL3 <= pLL2){     
                  result1 = search(keywordfirst); 
                  result3 = search(keywordthird);
                  result2 = search(keywordsecond);
            }  else if(pLL2 <= pLL3 && pLL2 <= pLL1 && pLL1 <= pLL3){     
                  result2 = search(keywordsecond); 
                  result1 = search(keywordfirst); 
                  result3 = search(keywordthird);    
            }  else if(pLL2 <= pLL3 && pLL2 <= pLL1 && pLL3 <= pLL1){    
                  result2 = search(keywordsecond);
                  result3 = search(keywordthird); 
                  result1 = search(keywordfirst);           
            }  else if(pLL3  <= pLL1 && pLL3 <= pLL2 && pLL2 <= pLL1) {
                  result3 = search(keywordthird);
                  result2 = search(keywordfirst); 
                  result1 = search(keywordsecond); 
            }  else if(pLL3  <= pLL1 && pLL3 <= pLL2 && pLL1 <= pLL2) {
                  result3 = search(keywordthird);
                  result1 = search(keywordfirst);
                  result2 = search(keywordsecond); 
         }           
    } else  {             
                 System.out.println("Not found in any document."); 
               }


            ArrayList<Integer> result4 = new ArrayList<Integer>();
            
           for(Integer i:result1) {
            
               for(Integer j:result2) {
                   
                  for(Integer k:result3){
                    if(i.equals(j) && i.equals(k)){
                         result4.add(i);
               }
            }
         } 
      }
         return result4;     
  }
  
   
   /**
     * Binary search for a stop word
     * @param key                                  key(optional) for binary search tree
     * @throws                                     java.io.FileNotFoundException
     * scanner ss                                  stopwords.txt file parsing
     * stopwords                                   Storing stopwords list      
     * @return                                     index                              
     */
   public int searchStopWord(String key) throws FileNotFoundException {
   
      Scanner ss = new Scanner(new File("stopwords.txt"));
      ArrayList<String> stopwords1 = new ArrayList<String>();
      while (ss.hasNext()){
         stopwords1.add(ss.next());
       }
       ss.close();

     String[] stopWords = new String[stopwords1.size()];
     stopWords = stopwords1.toArray(stopWords);

     Arrays.sort(stopWords);     //sort the stop words
     
     String sw = new String();
     for(int i=0;i<stopWords.length;i++) {
      sw += stopWords[i] + " ";
     }
   //  System.out.println("Stop words: " + sw);
// 
//       int lo=0;
//       int hi = stopWords.length-1;
//       
//       while(lo<=hi) {
//          int mid = lo +(hi-lo)/2;
//          int result = key.compareTo(stopWords[mid]);
//          if(result<0) hi = mid-1;
//          else if(result > 0) lo = mid+1;
//          else return mid;
//       }
       return -1;
   }
   
   /**
     * Tokenization
     * Parses the file to obtain the token list of word.
     * @param fileName : Name of file to be searched
     * @return Array of token words from the given file
     * @throws java.io.IOException if file not found
     */

   public ArrayList<String> parseB(File fileName) throws IOException {
      String[] tokens = null;
      ArrayList<String> pureTokens = new ArrayList<String>();
      ArrayList<String> stemms = new ArrayList<String>();
      
      Scanner scan = new Scanner(fileName);
      String allLines = new String();
      
      //Case folding
      while(scan.hasNextLine()) {
         allLines += scan.nextLine().toLowerCase();
      }
      
      tokens = allLines.split("[ '\"\\.,?!:;&#!/*$%+()\\-\\*]+");
      
      //remove stop words
      for(String token:tokens) {
         if(searchStopWord(token) == -1) {
            pureTokens.add(token);
         }
      }
      
      //stemming
      Stemmer st = new Stemmer();

      for(String token:pureTokens) {
         st.add(token.toCharArray(), token.length());
         st.stem();
         stemms.add(st.toString());
         st = new Stemmer();
      }
      return stemms;
   }
   
   /**
     * toString method to format the output of inverted index (optional)
     */
     
    public String toString() {
        String outputString = new String();
        for(int i=0;i<termList.size();i++) {
            outputString += String.format("%-15s", termList.get(i));
           
            ArrayList<Integer> docList = docLists.get(i);
            
            for(int j=0;j<docList.size();j++) {
                
                outputString += docList.get(j) + "\t";
            }
            outputString += "\n";
        }
        return outputString;
    }
   
   /**
     *  main method              
     *  @param args               Main args
     *  @throws                   java.io.FileNotFoundException 
     *  names    :                All the .txt files in the folder 
     *  docs     :                Contents of all the files passed as a 'string' to my constructor.
     *  stemmed  :                All the stemmed words from the files are stored in this ArrayList of String.
     */
     
   public static void main(String[] args) throws FileNotFoundException {
   
      File f = new File("path to the folder where files are stored");
      ArrayList<String> Docs1 = new ArrayList<String>(Arrays.asList(f.list()));
      String[] names = Docs1.toArray(new String[Docs1.size()]);
      
      String[] docs = new String[names.length];
   
         for(int x=0; x<names.length; x++){
     
            Scanner in = new Scanner(new FileReader("C:\\Users\\Pranav\\Desktop\\Spring 2019\\KPT\\Labs\\612Lab01_2185\\612Lab01_2185\\LAB 1 progress\\Files\\"+names[x]));
            StringBuilder sb = new StringBuilder();
     
             while(in.hasNextLine()) {
               sb.append(in.nextLine());
            }
      
          in.close();
     
     String outString = sb.toString();
     docs[x] = outString;
   }

      Labfinal test = new Labfinal(docs);

   //  System.out.println(test);   // to get index of terms  
   
     
     for(int x=0; x<names.length; x++){
       System.out.println("File " +names[x] + " : " + (x+1));
      }
      
      // Query Searching
      if(args.length == 1) {
         System.out.println("\n"+"Query: " + args[0]);
         ArrayList<Integer> result = test.search(args[0]);
         for(Integer i:result) {
            System.out.println("Found in : " +(i+1));
         }
      } else {
            System.out.println("\n" + "Pass the single argument to search for the keyword in the files.");
    }
      
      //Stemming 

      Scanner dd = new Scanner(System.in);

      for(int j=0; j<names.length; j++){
        String[] vars = new String[]{names[j]};
       //   System.out.println(names[j]);
            int fileno = j+1;
       
                 for(int i = 0; i < vars.length; i++) {

  
                 File file = new File(vars[i]);
                 Scanner scanner = new Scanner(file);
                 ArrayList<String> Docs2 = new ArrayList<String>();

                    while (scanner.hasNext()) {
                         Docs2.add(scanner.next());  
                        } 

          try {
                 ArrayList<String> stemmed = test.parseB(file);
                  
      //   for(String stm:stemmed) {
            System.out.println("\n"+"After Tokenizing, Stemming and Removing Stop Words in Document No: "+ fileno +"\n"+ stemmed);
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
      //  }
                                 
                }catch (IOException ioe) {
                     ioe.printStackTrace();
              }  
                     
           } //end of inside for loop - words reading
           
       }  // end of outside for loop - file reading
     
     // end of task1
     
           // Task 2
   
     // First test case for searching single word query 
         String keyword1 = "movie";
         String keyword2 = "plot";
         
         System.out.println("Searching single word query for words "+ "'" +keyword1+ "'" +" , "+"'" +keyword2+ "'" +" :");
        
         System.out.println("\n"+ keyword1 + " :");
         ArrayList<Integer> resultkeyword1 = test.search(keyword1);
            for(Integer i:resultkeyword1) {
              System.out.println("Found in : " +names[i]);
         }
         
         System.out.println("\n"+ keyword2 + " :");
         ArrayList<Integer> resultkeyword2 = test.search(keyword2);
            for(Integer i:resultkeyword2) {
              System.out.println("Found in : " +names[i]);
         }
       
         // First test case of AND operator to handle query with two keywords
            String keyword3 = "watch";
            String keyword4 = "cool";
               
           System.out.println("\n"+"Test Case 1 - Searching for words : " + "'" +keyword3+ "'" +" AND "+"'" +keyword4+ "'" + " :");
           System.out.println("\n"+ keyword3 + " AND " + keyword4 + " :");
           ArrayList<Integer> resultAND1 = test.searchAND(keyword3,keyword4);
            for(Integer i:resultAND1) {
              System.out.println("Found in : " +names[i]);
         }
         
         // Second test case of AND operator to handle query with two keywords
            String keyword5 = "strain";
            String keyword6 = "fact";
      
           System.out.println("\n"+"Test Case 2 - Searching for words : " + "'" +keyword5+ "'" +" AND "+"'" +keyword6+ "'" +" :");
           System.out.println("\n"+ keyword5 + " AND " + keyword6 + " :");
           ArrayList<Integer> resultAND2 = test.searchAND(keyword5,keyword6);
             for(Integer i:resultAND2) {
               System.out.println("Found in : " +names[i]);
         }
         
         // First test case of OR operator to handle query with two keywords
            String keyword7 = "fact";
            String keyword8 = "review";
      
           System.out.println("\n"+"Test Case 1 - Searching for words : " + "'" +keyword7+ "'" +" OR "+"'" +keyword8+ "'" + " :");
           System.out.println("\n"+ keyword7 + " OR " + keyword8 + " :");
           ArrayList<Integer> resultOR1 = test.searchOR(keyword7,keyword8);
             for(Integer i:resultOR1) {
               System.out.println("Found in : " +names[i]);
         }
         
         // Second test case of OR operator to handle query with two keywords
            String keyword9  = "review";
            String keyword10 = "wide";
      
           System.out.println("\n"+"Test Case 2 - Searching for words : " + "'" +keyword9+ "'" +" OR "+"'" +keyword10+ "'" + " :");
           System.out.println("\n"+ keyword9 + " OR " + keyword10 + " :");
           ArrayList<Integer> resultOR2 = test.searchOR(keyword9,keyword10);
             for(Integer i:resultOR2) {
               System.out.println("Found in : " +names[i]);
         }
         
         // First test case of AND operator to handle query with three keywords
            String keyword11 = "plot";
            String keyword12 = "teen";
            String keyword13 = "two";
               
           System.out.println("\n"+"Test Case 1 - Searching for words : " + "'" +keyword11+ "'" +" AND "+"'" +keyword12+ "'" +" AND "+"'" +keyword13+ "'"+ " :");
           System.out.println("\n"+ keyword11 + " AND " + keyword12+ " AND " + keyword13 + " :");
           ArrayList<Integer> resultAND3 = test.searchAND3(keyword11,keyword12,keyword13);
             for(Integer i:resultAND3) {
               System.out.println("Found in : " +names[i]);
         } 
         
          
         // Second test case of AND operator to handle query with three keywords
            String keyword14 = "baldwin";
            String keyword15 = "brain";
            String keyword16 = "kick";
               
           System.out.println("\n"+"Test Case 2 - Searching for words : " + "'" +keyword14+ "'" +" AND "+"'" +keyword15+ "'" +" AND "+"'" +keyword16+ "'"+ " :");
           System.out.println("\n"+ keyword14 + " AND " + keyword15+ " AND " + keyword16 + " :");
           ArrayList<Integer> resultAND4 = test.searchAND3(keyword14,keyword15,keyword16);
             for(Integer i:resultAND4) {
               System.out.println("Found in : " +names[i]);
         } 

          
     } //end of main
   
} //end of class