package project;
import java.io.*;
import java.util.*;

class Node{
    String song;
    Node next;
    Node prev;

    Node(String song){
        this.song=song;
        this.next=null;
        this.prev=null;
    }
}



public class PlaylistManager{
    private Node head;
    private Node recentlyPlayedTop;

    PlaylistManager(String Playlistname){
        head = new Node(Playlistname);
    }

    // functions 
    private void AddSong(String song) throws IOException { 
        Node current = head;
        String header = current.song;
        saveToFile(header);
        while(current.next!=null){
            current=current.next;
        }
        Node newNode = new Node(song);
        current.next=newNode;
        newNode.prev=current;
        
        saveToFile(song);
        System.out.println("Song is added !");

    }

    private void saveToFile(String song) throws IOException{
        try(FileWriter writer = new FileWriter("playlist.txt",true)){
            writer.write(song + "\n");
        }
    }
    
    private void loadFromfile() throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader("playlist.txt"))){
            String line;
            while((line = reader.readLine())!=null){
                addSongFromfile(line);
            }
        }
    }

    private void addSongFromfile(String song){
        Node current = head;
        while(current.next!=null){
            current = current.next;
        }
        Node newNode = new Node(song);
        current.next =  newNode;
        newNode.prev = current;
    }

    private void deleteSong(String song) throws IOException{
        Node current = head.next; // to skip the playlist name 
        boolean found = false;

        while(current.next!=null){
            if(current.song.equals(song)){

                if(current.prev!=null){
                    current.prev.next=current.next;
                    
                }
                if(current.next!=null){
                    current.next.prev = current.prev;
                }

                System.out.println("Song deleted successfully");
                found = true;
                removeFromfile(song);
                break;
            }
        }
        current = current.next;
        

        if(!found){
            System.out.println("Song not found !");
        }
    }

    private void removeFromfile(String song) throws IOException{
        File inputFile = new File("playlist.txt");
        File tempFile = new File("temp.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
                String line ;
                while((line=reader.readLine())!=null){
                    if(!line.equals(song)){
                        writer.write(line);
                        writer.newLine();
                    } 
                   
                }

                if(!inputFile.delete() || !tempFile.renameTo(inputFile)){
                    System.out.println("Error updating the file");
                }
            }

    }

    private void printPlaylist(){
        Node current = head;
        System.out.println("Playlist is:");
        while(current!=null){
            System.out.println(current.song);
            current=current.next;
        }
    }
    private void countSongs(){
        Node current = head.next;
        int count = 0;
        while(current!=null){
            count++;
            current=current.next;
        }
        System.out.println("Total songs are -" + count);
    }

    private void searchSong(String song){
        Node current = head.next;
        boolean found = false;
        while(current!=null){
            if(current.song.equals(song)){
                System.out.println("Song found !" + song);
                found=true;
                break;

            }
            current=current.next;
        }
        if(!found){
            System.out.println("Song not found");
        }
    }

    private void playSong(String song){
        Node current = head.next;
        boolean found = false;
        while(current!=null){
            if(current.song.equals(song)){
                System.out.println("Now playing " + song) ;
                found=true;
                recentlyPlayed(song);
                break;

            }
            current = current.next;
            if(!found){
                System.out.println("Song not found");
            }
        }
    }

    private void recentlyPlayed(String song){
        Node newNode = new Node(song);
        if(recentlyPlayedTop==null){
            recentlyPlayedTop=newNode;
        }
        else{
            newNode.next=recentlyPlayedTop;
            recentlyPlayedTop=newNode;

        }

    }

    private void displayRecentlyPlayed(){
        Node current = recentlyPlayedTop;
        if(current==null){
            System.out.println("No recently played songs");
            return;
        }
        
        
        System.out.println("Recently played songs are:");
        while(current!=null){
                System.out.println(current.song);
                current=current.next;
            }
        
    }

    private void displaylastPlayed(){
        if(recentlyPlayedTop==null){
            System.out.println("No last played song");
        }
        else{
            System.out.println("Last Played Song is " + recentlyPlayedTop.song);
        }
    }

    private void deletePlaylist(String delhead){
        // search for head 
        // delete the head
       
        if(head.song.equals(delhead)){
            
            head=null;
            System.out.println("Whole playlist has been deleted.");
        }
        else{
            System.out.println("This playlist doesnt exist !");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner (System.in);

        System.out.println("Welcome to the PLAYLIST MANAGER !!");
        System.out.println("Enter name of playlist :");
        String playlistName = sc.nextLine();
        PlaylistManager manager = new PlaylistManager(playlistName);

        int choice;
        do{
            System.out.println();
            System.out.println("M E N U !!");
            System.out.println();
            System.out.println("1) Add Song");
            System.out.println("2) Delete a song");
            System.out.println("3) Display Playlist");
            System.out.println("4) Count Songs");
            System.out.println("5) Search Song");
            System.out.println("6) Play Song");
            System.out.println("7) Display Recently Played songs");
            System.out.println("8) Display Last Played Song");
            System.out.println("9) Add song from file");
            System.out.println("10) Delete a playlist");
            System.out.println("11) Exit ");

            System.out.println();
            System.out.println("Enter choice :");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    System.out.println("Enter song name");
                    String newsong = sc.nextLine();
                    manager.AddSong(newsong);
                    break;
                case 2:
                    System.out.println("Enter song to delete");
                    String songtodel = sc.nextLine();
                    manager.deleteSong(songtodel);
                    break;
                case 3 :
                    manager.printPlaylist();
                    break;
                case 4 :
                    manager.countSongs();
                    break;
                case 5:
                    System.out.println("Enter song to search");
                    String s_song = sc.nextLine();
                    manager.searchSong(s_song);
                    break;
                case 6 :
                    System.out.println("Enter song to play ");
                    String songtoplay = sc.nextLine();
                    manager.playSong(songtoplay);
                    break;
                case 7 :
                    manager.displayRecentlyPlayed();
                    break;
                case 8 : 
                    manager.displaylastPlayed();
                    break;
                case 9 : 
                    manager.loadFromfile();
                    break;
                case 10:
                    System.out.println("Enter the playlist name to be deleted");
                    String songdel = sc.nextLine();
                    manager.deletePlaylist(songdel);
                    break;
                case 11 :
                    System.out.println("Exiting .. ");
                    break;
                default:
                    System.out.println("Invalid choice . Try Again.");

            }

        }
        while(choice!=11 && choice!=10); // For the loop to continue, both conditions must be true , When either condition is false, the loop exits
        sc.close();
    }

}

