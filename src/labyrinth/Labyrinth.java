package labyrinth;

import java.util.ArrayList;

/**
 *
 * Created by Serhat Guzel 
 * Date : 08.04.2017
 * Program : 8 x 8 lik bir satranc tahtasi uzerinde oynanan bir Labirent oyunu.
 * 
 * - Oyunda  white pul A1 konumundan H8 konumuna gitmelidir.
 * - Oyunda kuralları bozmayacak sekilde random 3-9 arasinda 
 *      random olarak belirlenmis Black pullar gecilerek oyun bitebilmelidir.
 * - White pul ,Black pulun üstünden atlayarak gecemez onu yiyemez .
 * - White pul en az bir Black pul ile karsilasmalıdır.
 * - Oyunda White pul mumkunse sag alt capraz olacak sekilde gitmeli yoksa sirasiyla
 *      asagı - sag - sag üst capraz - sol alt capraz - yukarı - sol şeklinde gitmelidir.
 * - White pul H8 konumuna en kısa yoldan ulasmalidir.
 * 
 * 
 */
public final class Labyrinth {
    
    private int count = 0;
    private final int ROW_VALUE = 8; /*Satir sayisi*/
    private final int COLUMN_VALUE = 8; // Sututn sayisi
    private final  char board[][] = new char[ROW_VALUE][COLUMN_VALUE]; // Board 
    private final  ArrayList<Integer> countArr = new ArrayList<>();
    public  ArrayList<Coordinate> movements = new ArrayList<>(); // gidilen pathi tutar
    public  ArrayList<Coordinate> blackStamp = new ArrayList<>(); // Boardda random dagilmis Black stampleri tutar.
    public  ArrayList<Coordinate> hitToBlackStamp = new ArrayList<>(); // Karsilasilan Black Stampleri tutar.
    /*Constructor*/
    public Labyrinth(){
        // Boardin ilklendirmesi yaptik.
        for(int a = 0 ; a < ROW_VALUE ; ++a){
            for(int b = 0 ; b < COLUMN_VALUE ; ++b){
                board[a][b] = '.';
            }
        }
        board[0][0] = 'W';
        //Boardda A1 konumunda White tas var
        
        // 3 ile 9 arasında Black tas secmek icin Math classından random() methodunu kullandik.
        int randomNumber = 3 + (int)(Math.random()*7);
        // Black taslardan bi tanesi kısıtlandildi. 
        //Capraz gitme onceliginden dolayı 2-2 3-3 4-4 5-5 6-6 7-7 konumumlarından birine atanacak
        int restrictedStamp = 9 * ( 1 + (int)(Math.random()* 6));
        
        int row = restrictedStamp / ROW_VALUE;
        int column = restrictedStamp % COLUMN_VALUE ;
        
        board[row][column] = 'B';
        blackStamp.add(new Coordinate(row, column));
        
        fillBoard(randomNumber);
    }
    public void setArray(int row , int column , char value){
        
        this.board[row][column]  = value ;
    }
    // Bu method gidilen pathi cizer.
    public void destination(int pathRow, int pathColumn){
        
        for(int path = 0 ; path < countArr.size() ; ++path){
            
            pathRow = countArr.get(path) / ROW_VALUE;
            
            pathColumn = countArr.get(path) % COLUMN_VALUE;
            
            setArray(pathRow,pathColumn ,'*');
        }
        
        setArray(pathRow,pathColumn ,'W');
    }
    /*Boardi ekrana basan method*/
    public void printBoard(){
        
        System.out.println("  A B C D E F G H");
        
        System.out.println("  - - - - - - - - ");
        
        for(int i = 0 ; i <ROW_VALUE ; ++i){
            
            System.out.print( i+1 + "|");
            
            for(int j = 0 ; j < COLUMN_VALUE ; ++j ){
                
                System.out.print(board[i][j]  + " ");
            }
            
            System.out.println("|");
        }
        
        System.out.println("  - - - - - - - - ");
    }
    /*Black stampleri boarda ekleyen method*/
    public void fillBoard(int randomNumber){
        
        for(int i = 1 ; i < randomNumber ; ++i){
            
            int index = (int) (1 + Math.random()*62) ;
            
            int r_row = index / ROW_VALUE;
            int r_column = index % COLUMN_VALUE ;
            
            if(board[r_row][r_column] == '.'){
                
                board[r_row][r_column] = 'B';
                
                blackStamp.add(new Coordinate(r_row, r_column));
            }
            else{
                --i;
            }
        }
    }
    // Recursive cagri yaptigimiz method H8 konumuna tek tek ilerleriz.
    // Oncelik olarak  sag alt capraz gitmektedir.
    /* Ardindan Asagi - Sag - Sag üst capraz - Sol Alt Capraz - Yukarı - Sola 
    gidecek sekilde ayaarlandi*/
    public boolean movementInBoard( int goToRow ,int goToColumn ){
        
        int index = goToRow*ROW_VALUE + goToColumn;
        // Board sinir kontrolu
        if(goToRow < 0 || goToRow >= ROW_VALUE ||  goToColumn < 0 || goToColumn >= COLUMN_VALUE  ){
            return false;
        }
        // Black stamp kontrolu
        if(board[goToRow][goToColumn] == 'B'){
           hitToBlackStamp.add(new Coordinate(goToRow, goToColumn));
           
           return false;
        }
        countArr.add(index);
        // Pathin sonuna geldik mi diye kontrol eder.
        if(goToRow == ROW_VALUE-1 && goToColumn == COLUMN_VALUE-1 ){
            blackStamp.add(new Coordinate(goToRow , goToColumn));
            countArr.add(index);
            return true;
        }
        // Sag alt capraz gider.
        if(movementInBoard(goToRow+1 ,goToColumn+1 ) == true){
            
            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        // Asagi gider.
        if(movementInBoard(goToRow+1 ,goToColumn ) == true){

            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        // Saga gider
        if(movementInBoard(goToRow ,goToColumn+1 ) == true){

            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        // Sag üst capraz gider.
        if(movementInBoard(goToRow-1 ,goToColumn+1 ) == true){

            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        // Sol alt capraz gider.
        if(movementInBoard( goToRow+1 ,goToColumn-1 ) == true){

            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        // Yukari gider.
        if(movementInBoard( goToRow-1 ,goToColumn ) == true){

            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        // Sola gider.
        if(movementInBoard(goToRow ,goToColumn-1 ) == true){

            movements.add(new Coordinate(goToRow , goToColumn));
            return true;
        }
        
        return false ;
    }
    
}
