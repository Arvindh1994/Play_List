package org.test.playlist;

import java.util.*;

public class RecentlyPlayedStore {
    private final int initialCapacity;
    private final int maxSongsPerUser;
    private Map<String, LinkedList<String>> songUserMap;

    public RecentlyPlayedStore(int initialCapacity, int maxSongsPerUser) {
        this.initialCapacity = initialCapacity;
        this.maxSongsPerUser = maxSongsPerUser;
        this.songUserMap = new HashMap<>();
    }

    public void playSong(String user, String song) {
        LinkedList<String> songs = songUserMap.get(user);

        if (songs == null) {
            songs = new LinkedList<>();
            songUserMap.put(user, songs);
        }

        songs.remove(song);
        songs.addFirst(song);

        if (songs.size() > maxSongsPerUser) {
            songs.removeLast();
        }
    }

    public List<String> getRecentlyPlayedSongs(String user) {
        LinkedList<String> songs = songUserMap.get(user);
        if (songs != null) {
            return new ArrayList<>(songs);
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        RecentlyPlayedStore store = new RecentlyPlayedStore(3, 3);

        store.playSong("user1", "S1");
        store.playSong("user1", "S2");
        store.playSong("user1", "S3");

        assert store.getRecentlyPlayedSongs("user1").equals(Arrays.asList("S3", "S2", "S1"));

        store.playSong("user1", "S4");

        assert store.getRecentlyPlayedSongs("user1").equals(Arrays.asList("S4", "S2", "S1"));

        store.playSong("user1", "S2");

        assert store.getRecentlyPlayedSongs("user1").equals(Arrays.asList("S2", "S4", "S1"));

        store.playSong("user1", "S1");

        assert store.getRecentlyPlayedSongs("user1").equals(Arrays.asList("S1", "S2", "S4"));

        System.out.println("All assertions passed!");
    }
}