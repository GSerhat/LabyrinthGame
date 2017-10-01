
package labyrinth;
/**
 *
 * @author Serhat
 */
public class Main {
    
    public static void main(String[] args) {
        
        Labyrinth labyrinth = new Labyrinth();
        
        int pathRow = 0;
        int pathColumn = 0;
        
        System.out.println("Black stamps: " + labyrinth.blackStamp);
        
        labyrinth.printBoard(); /*Baslangic durumu icin : Boardi ekrana basariz.*/
        
        if(labyrinth.movementInBoard(pathRow , pathColumn )){
        
            labyrinth.destination(pathRow,pathColumn);

            System.out.println("The labyrinth has been successfully solved. \n\n" +
                   "Encountered stamp : " + labyrinth.hitToBlackStamp + "\n"); 

            System.out.println("Movements: " + labyrinth.movements + " \n");

            labyrinth.printBoard(); /*Bitis durumu icin : Boardi ekrana basariz.*/
        }
        else{
            System.out.println("The Labyrinth unfinished");
        }
    }
}
